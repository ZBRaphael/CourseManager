package com.typhoon.demo.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.typhoon.demo.pojo.course;
import com.typhoon.demo.pojo.simplifiedStudent;
import com.typhoon.demo.pojo.student;
import com.typhoon.demo.pojo.teacher;
import com.typhoon.demo.service.courseServiceImpl;
import com.typhoon.demo.service.teaServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import static com.typhoon.demo.util.dataProcess.Process_data;

@RestController
public class TeacherController {
    @Autowired
    private teaServiceImpl teaserviceimpl;
    @Autowired
    private courseServiceImpl courseserviceimpl;
    // 老师登录
    @RequestMapping("/tea/login")
    public String login(@RequestBody String string_data, HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
        //System.out.println(string_data);
        boolean ret = false;
        JSONObject json = Process_data(string_data);
        String username = java.net.URLDecoder.decode(json.get("stuUsername").toString(), "UTF-8");
        //System.out.println(username);
        String password = json.get("stuPassword").toString();
        teacher target_teacher = teaserviceimpl.queryTeaByName(username, password);
        if(target_teacher == null)
            System.out.println("hehe");
        if (target_teacher != null) {
            request.getSession().setAttribute("teaId", target_teacher.getTeaId());
            request.getSession().setAttribute("teaUsername", target_teacher.getTeaUsername());
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

    // 查询我发布的课程
    @RequestMapping("/tea/queryMyCourse")
    public List<JSONObject> queryMyCourse(HttpServletRequest request, HttpServletResponse response) {
        int teaId = 1;
        List<course> res = teaserviceimpl.queryMyCourse(teaId);// 查询老师发布了的课
        List<JSONObject> ret = new ArrayList<>();
        for(course temp : res){
            JSONObject one = new JSONObject();
            one.put("courseId", temp.getCourseId());
            one.put("interest", temp.getInterest());
            one.put("courseCostHour", temp.getCourseCostHour());
            one.put("courseDate", temp.getCourseStartDate());
            one.put("courseLocation", temp.getCourseLocation());
            one.put("isCanceledByTea", temp.getIsCanceled());
            one.put("courseDescription", temp.getCourseDescription());
            List<simplifiedStudent> students = teaserviceimpl.queryStudentByCourse(temp.getCourseId());
            one.put("Enrollments", students);
            ret.add(one);
        }
        response.setHeader("Access-Control-Allow-Origin", "http://localhost:8080");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        return ret;
    }

    // 老师发布课程
    @RequestMapping("/tea/addCourse")
    public String teaAddCourse(@RequestBody String string_data, HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
        JSONObject json = Process_data(string_data);
        //老师id
        int teaId = Integer.parseInt(request.getSession().getAttribute("teaId").toString());
        // 起始时间
        String courseStartDate = java.net.URLDecoder.decode(json.get("courseStartDate").toString(), "UTF-8");
        String courseEndDate = java.net.URLDecoder.decode(json.get("courseEndDate").toString(), "UTF-8");
        int courseCostHour = Integer.parseInt(java.net.URLDecoder.decode(json.get("courseCostHour").toString(), "UTF-8"));
        String courseLocation = java.net.URLDecoder.decode(json.get("courseLocation").toString(), "UTF-8");
        String courseDescription = java.net.URLDecoder.decode(json.get("courseDescription").toString(), "UTF-8");
        String interest = java.net.URLDecoder.decode(json.get("interest").toString(), "UTF-8");
        System.out.println("课程类型：" + interest);
        boolean res = courseserviceimpl.teaAddCourse(courseStartDate, courseEndDate, courseCostHour, courseLocation, teaId, interest, courseDescription);
        response.setHeader("Access-Control-Allow-Origin","http://localhost:8080");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        if(res)
            return "success";
        return "failure";
    }

    //老师取消课程
    @RequestMapping("/tea/cancelCourse")
    public String teaCancelCourse(@RequestBody String string_data, HttpServletRequest request, HttpServletResponse response){
        JSONObject json = Process_data(string_data);
        int courseId = Integer.parseInt(json.get("courseId").toString());
        boolean ret = teaserviceimpl.cancelCourse(courseId);
        response.setHeader("Access-Control-Allow-Origin","http://localhost:8080");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        if(ret)
            return "success";
        return "failure";

    }

}
