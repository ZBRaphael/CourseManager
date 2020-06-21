package com.typhoon.demo.controller;

import com.typhoon.demo.pojo.attendclass;
import com.typhoon.demo.pojo.course;
import com.typhoon.demo.pojo.simplifiedStudent;
import com.typhoon.demo.pojo.student;
import com.typhoon.demo.service.courseServiceImpl;
import com.typhoon.demo.service.stuServiceImpl;
import com.typhoon.demo.service.teaServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import com.alibaba.fastjson.*;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import static com.typhoon.demo.util.dataProcess.Process_data;

@RestController
public class studentController {
    @Autowired
    private stuServiceImpl stuServiceimpl;
    @Autowired
    private teaServiceImpl teaServiceimpl;
    @Autowired
    private courseServiceImpl courseServiceimpl;
    @RequestMapping("/stu/test")
    public JSONObject test() {
        //student t = stuService.login("Faultz", "19981001");
        JSONObject ret = new JSONObject();
        ret.put("test", "test");
        return ret;
    }
    @RequestMapping("/stu/login")
    public String login(@RequestBody String string_data, HttpServletRequest request, HttpServletResponse response) {
        System.out.println(string_data);
        boolean ret = false;
        JSONObject json = Process_data(string_data);
        String username = json.get("stuUsername").toString();
        String password = json.get("stuPassword").toString();
//        System.out.println(username);
//        System.out.println(password);
        student target_student = stuServiceimpl.login(username, password);
        if(target_student == null)
            System.out.println("hehe");
        if (target_student != null) {
            request.getSession().setAttribute("stuId", target_student.getStuId());
            request.getSession().setAttribute("stuUsername", target_student.getStuUsername());
            //System.out.println("lalalal");
            //response.addHeader("Access-Control-Allow-Origin","*");
            ret = true;
        }
        System.out.println(request.getHeader("origin"));
        response.setHeader("Access-Control-Allow-Origin","http://localhost:8080");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        if(ret)
            return "success";
        else
            return "failure";
    }
    @RequestMapping("stu/sign")
    public JSONObject stuSign(@RequestBody String string_data, HttpServletRequest request, HttpServletResponse response){
        JSONObject json = Process_data(string_data);
        int courseId = Integer.parseInt(json.get("stuId").toString());
        int stuId = Integer.parseInt(request.getSession().getAttribute("stuId").toString());
        boolean res = stuServiceimpl.stuSign(stuId, courseId, 1);
        JSONObject ret = new JSONObject();
        ret.put("res", res);
        return ret;
    }
    // 查询和自己相关的课程
    @RequestMapping("stu/queryAllCourse")
    public List<JSONObject> queryAllCourse(HttpServletRequest request, HttpServletResponse response) {
        List<JSONObject> ret = new ArrayList<>();
        if(request.getSession().getAttribute("stuId") == null) //未登录，重定向
        {
            return ret;
        }
        else {
            int stuId = Integer.parseInt(request.getSession().getAttribute("stuId").toString());
            //查询和自己相关的课
            String interest = stuServiceimpl.stuQueryInterest(stuId);
            List<course> relatedCourse = stuServiceimpl.stuQueryRelatedCourse(interest);

            //查询自己参加的课
            List<attendclass> attendCourse = stuServiceimpl.stuQueryCourseAttend(stuId);
            HashSet<Integer> record = new HashSet<>();
            for(attendclass temp : attendCourse)
            {
                JSONObject one = new JSONObject();
                one.put("attendClassId",temp.getAttendClassId());
                one.put("courseId",temp.getCourseId());
                // 查询该课程
                course tempcourse = courseServiceimpl.queryCourseById(temp.getCourseId());
                one.put("interest",interest);
                one.put("courseCostHour", tempcourse.getCourseCostHour());
                one.put("courseDate",temp.getCourseDate());
                one.put("courseLocation", temp.getCourseLocation());
                one.put("isCanceledByStu", temp.getIsCanceledByStu());
                one.put("isAttend", temp.getIsAttend());
                // 查询上了这门课的学生
                List<simplifiedStudent> students = teaServiceimpl.queryStudentByCourse(temp.getCourseId());
                one.put("Enrollments", students);
                one.put("courseDescription", tempcourse.getCourseDescription());
                ret.add(one);
            }
        }
        response.setHeader("Access-Control-Allow-Origin","http://localhost:8080");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        return ret;
    }


    @RequestMapping("stu/getMyInfo")
    public JSONObject stuGetMyInfo(HttpServletRequest request, HttpServletResponse response)
    {
        JSONObject ret = new JSONObject();
        if(request.getSession().getAttribute("stuId") == null) //未登录，重定向
        {
            System.out.println("请先登录");
            ret.put("login", true);
        }
        else
        {
            int stuId = Integer.parseInt(request.getSession().getAttribute("stuId").toString());
            student targetStudent = stuServiceimpl.stuGetNyInfo(stuId);
            System.out.println(stuId);
            ret.put("stuUsername", targetStudent.getStuUsername());
            ret.put("stuTell", targetStudent.getStuTell());
            ret.put("stuTotalHour", targetStudent.getStuTotalClassHour());
            ret.put("stuRestHour", targetStudent.getStuRemainingClassHour());
        }
        response.setHeader("Access-Control-Allow-Origin","http://localhost:8080");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        return ret;
        ///Integer stuId = Integer.parseInt(request.getSession().getAttribute("stuId").toString());
    }
}
