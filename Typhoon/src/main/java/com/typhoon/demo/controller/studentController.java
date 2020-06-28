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
import static com.typhoon.demo.util.dataProcess.compareDate;

@RestController
public class studentController {
    @Autowired
    private stuServiceImpl stuServiceimpl;
    @Autowired
    private teaServiceImpl teaServiceimpl;
    @Autowired
    private courseServiceImpl courseServiceimpl;
    @RequestMapping("/stu/test")
    public String test() {
        List<course> relatedCourse = stuServiceimpl.stuQueryRelatedCourse("basketball");
        System.out.println("相关的课" + relatedCourse.size());
        relatedCourse = stuServiceimpl.stuQueryRelatedCourse("篮球课");
        System.out.println("相关的课" + relatedCourse.size());
        return "hehe";
    }

    //注册
    @RequestMapping("stu/register")
    public String register(@RequestBody String string_data, HttpServletRequest request, HttpServletResponse response){
        boolean ret = false;
        JSONObject json = Process_data(string_data);
        String username = json.get("stuUsername").toString();
        String password = json.get("stuPassword").toString();
        String interest = json.get("interest").toString();
        String tell = json.get("stuTell").toString();
        ret = stuServiceimpl.register(username, password, interest, tell);
        response.setHeader("Access-Control-Allow-Origin","http://localhost:8080");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        if(ret)
            return "success";
        return "failure";
    }

    // 登录
    @RequestMapping("/stu/login")
    public String login(@RequestBody String string_data, HttpServletRequest request, HttpServletResponse response) {
        //System.out.println(string_data);
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
        //System.out.println(request.getHeader("origin"));
        response.setHeader("Access-Control-Allow-Origin","http://localhost:8080");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        if(ret)
            return "success";
        else
            return "failure";
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
            //System.out.println(interest);
            List<course> relatedCourse = stuServiceimpl.stuQueryRelatedCourse(interest);
            //System.out.println("相关的课" + relatedCourse.size());

            //查询自己参加的课
            List<attendclass> attendCourse = stuServiceimpl.stuQueryCourseAttend(stuId);
            HashSet<Integer> record = new HashSet<>();
            for(attendclass temp : attendCourse) {
                JSONObject one = new JSONObject();
                one.put("attendClassId",temp.getAttendClassId());
                one.put("courseId",temp.getCourseId());
                record.add(temp.getCourseId());
                // 查询该课程
                course tempcourse = courseServiceimpl.queryCourseById(temp.getCourseId());
                one.put("interest",interest);
                one.put("courseCostHour", tempcourse.getCourseCostHour());
                one.put("courseDate",temp.getCourseDate());
                one.put("courseLocation", temp.getCourseLocation());
                one.put("isCanceledByStu", String.valueOf(temp.getIsCanceledByStu()));
                one.put("isAttend", String.valueOf(temp.getIsAttend()));
                // 查询上了这门课的学生
                List<simplifiedStudent> students = teaServiceimpl.queryStudentByCourse(temp.getCourseId());
                one.put("Enrollments", students);
                one.put("courseDescription", tempcourse.getCourseDescription());
                ret.add(one);
            }
            // 添加和自己相关并还未选的课
            //System.out.println("record.size()"+ record.size());
            for(course temp : relatedCourse){
                if(record.contains(temp.getCourseId())){
                    continue;
                }
                // System.out.println("没选课的课程id" + temp.getCourseId());
                JSONObject one = new JSONObject();
                one.put("attendClassId", -1);
                one.put("courseId",temp.getCourseId());
                // 查询该课程
                course tempcourse = courseServiceimpl.queryCourseById(temp.getCourseId());
                one.put("interest",interest);
                one.put("courseCostHour", tempcourse.getCourseCostHour());
                one.put("courseDate",temp.getCourseStartDate());
                one.put("courseLocation", temp.getCourseLocation());
                one.put("isCanceledByStu", "1");
                one.put("isAttend", "0");
                // 查询上了这门课的学生
                List<simplifiedStudent> students = teaServiceimpl.queryStudentByCourse(temp.getCourseId());
                one.put("Enrollments", students);
                one.put("courseDescription", tempcourse.getCourseDescription());
                ret.add(one);
            }
        }
        response.setHeader("Access-Control-Allow-Origin","http://localhost:8080");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        System.out.println(ret.size());
        return ret;
    }

    // 查询自己的个人信息
    @RequestMapping("stu/getMyInfo")
    public JSONObject stuGetMyInfo(HttpServletRequest request, HttpServletResponse response) {
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
            //System.out.println(stuId);
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

    // 查询自己的上过的课
    @RequestMapping("stu/queryCourseAttend")
    public List<JSONObject> queryCourseAttend(HttpServletRequest request, HttpServletResponse response){
        List<JSONObject> ret = new ArrayList<>();
        if(request.getSession().getAttribute("stuId") == null) //未登录，重定向
        {
            return ret;
        }
        else {
            int stuId = Integer.parseInt(request.getSession().getAttribute("stuId").toString());
            //查询自己参加的课
            List<attendclass> attendCourse = stuServiceimpl.stuQueryCourseAttend(stuId);
            // 查询自己兴趣
            String interest = stuServiceimpl.stuQueryInterest(stuId);
            for(attendclass temp : attendCourse)
            {
                JSONObject one = new JSONObject();
                one.put("courseId",temp.getCourseId());
                one.put("interest",interest);
                one.put("courseDate",temp.getCourseDate());
                one.put("courseLocation", temp.getCourseLocation());
                ret.add(one);
            }
        }
        response.setHeader("Access-Control-Allow-Origin","http://localhost:8080");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        return ret;



    }

    // 安卓端特供 查询自己需要去上的课   attendclass 中的 isAttend = 0 的课
    @RequestMapping("stu/queryCourseWillAttend")
    public List<JSONObject> queryCourseWillAttend(HttpServletRequest request, HttpServletResponse response){
        List<JSONObject> ret = new ArrayList<>();
        if(request.getSession().getAttribute("stuId") == null) //未登录，重定向
        {
            return ret;
        }
        else {
            int stuId = Integer.parseInt(request.getSession().getAttribute("stuId").toString());
            //当前时间之后查询自己参加的课
            List<attendclass> attendCourse = stuServiceimpl.stuQueryCourseAttend(stuId);
            String interest = stuServiceimpl.stuQueryInterest(stuId);
            //
            for(attendclass temp : attendCourse) {
                if(!compareDate(temp.getCourseDate())){//应该返回的课程
                    continue;
                }
                JSONObject one = new JSONObject();
                one.put("attendClassId",temp.getAttendClassId());
                one.put("courseId",temp.getCourseId());
                course tempcourse = courseServiceimpl.queryCourseById(temp.getCourseId());
                one.put("interest",interest);
                one.put("courseCostHour", tempcourse.getCourseCostHour());
                one.put("courseDate",temp.getCourseDate());
                one.put("courseLocation", temp.getCourseLocation());
                one.put("isCanceledByStu", String.valueOf(temp.getIsCanceledByStu()));
                one.put("isAttend", String.valueOf(temp.getIsAttend()));
                // 查询上了这门课的学生
                List<simplifiedStudent> students = teaServiceimpl.queryStudentByCourse(temp.getCourseId());
                one.put("Enrollments", students);
                one.put("courseDescription", tempcourse.getCourseDescription());
                ret.add(one);
            }

        }
        response.setHeader("Access-Control-Allow-Origin","http://localhost:8080");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        System.out.println(ret.size());
        return ret;
    }

    // 安卓端特供 查询自己选过的课 attendclass里面出现过就行
    @RequestMapping("stu/queryCourseChoose")
    public List<JSONObject> queryCourseChoose(HttpServletRequest request, HttpServletResponse response){
        List<JSONObject> ret = new ArrayList<>();
        if(request.getSession().getAttribute("stuId") == null) //未登录，重定向
        {
            return ret;
        }
        else {
            int stuId = Integer.parseInt(request.getSession().getAttribute("stuId").toString());
            //查询和自己相关的课
            String interest = stuServiceimpl.stuQueryInterest(stuId);
            //查询自己参加的课
            List<attendclass> attendCourse = stuServiceimpl.stuQueryCourseAttendForAndroid(stuId);
            for(attendclass temp : attendCourse) {
                JSONObject one = new JSONObject();
                one.put("attendClassId",temp.getAttendClassId());
                one.put("courseId",temp.getCourseId());
                // 查询该课程
                course tempcourse = courseServiceimpl.queryCourseById(temp.getCourseId());
                one.put("interest",interest);
                one.put("courseCostHour", tempcourse.getCourseCostHour());
                one.put("courseDate",temp.getCourseDate());
                one.put("courseLocation", temp.getCourseLocation());
                one.put("isCanceledByStu", String.valueOf(temp.getIsCanceledByStu()));
                one.put("isAttend", String.valueOf(temp.getIsAttend()));
                // 查询上了这门课的学生
                List<simplifiedStudent> students = teaServiceimpl.queryStudentByCourse(temp.getCourseId());
                one.put("Enrollments", students);
                one.put("courseDescription", tempcourse.getCourseDescription());
                ret.add(one);
            }

        }
        response.setHeader("Access-Control-Allow-Origin","http://localhost:8080");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        System.out.println(ret.size());
        return ret;
    }

    // 更新自己的个人信息
    @RequestMapping("stu/updateInformation")
    public String updateInformation(@RequestBody String string_data, HttpServletRequest request, HttpServletResponse response){
        boolean ret = false;
        if(request.getSession().getAttribute("stuId") == null) {
            return "failure";
        }
        else {
            int stuId = Integer.parseInt(request.getSession().getAttribute("stuId").toString());
            System.out.println(string_data);
            JSONObject json = Process_data(string_data);
            String username = json.get("stuUsername").toString();
            String password = json.get("stuPassword").toString();
            String tell = json.get("stuTell").toString();
            ret = stuServiceimpl.stuUpdateInformation(stuId,username,password,tell);
        }
        response.setHeader("Access-Control-Allow-Origin","http://localhost:8080");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        if(ret)
            return "success";
        else
            return "failure";
    }

    // 添加一门课程
    @RequestMapping("stu/addCourse")
    public String stuAddCourse(@RequestBody String string_data, HttpServletRequest request, HttpServletResponse response){
        System.out.println(string_data);
        // 找到选课id和用户id
        JSONObject json = Process_data(string_data);
        int courseId = Integer.parseInt(json.get("courseId").toString());
        if(request.getSession().getAttribute("stuId") == null) {
            return "failure";
        }
        else {
            int stuId = Integer.parseInt(request.getSession().getAttribute("stuId").toString());
            String stuUsername = request.getSession().getAttribute("stuUsername").toString();
            stuServiceimpl.stuAddCourse(courseId, stuId);
        }
        response.setHeader("Access-Control-Allow-Origin","http://localhost:8080");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        return "success";
    }

    // 签到一门课程
    @RequestMapping("stu/sign")
    public String stuSign(@RequestBody String string_data, HttpServletRequest request, HttpServletResponse response){
        JSONObject json = Process_data(string_data);
        int courseId = Integer.parseInt(json.get("courseId").toString());
        int stuId = Integer.parseInt(request.getSession().getAttribute("stuId").toString());
        System.out.println("有人签到");
        boolean res = stuServiceimpl.stuSign(stuId, courseId, 1);
        response.setHeader("Access-Control-Allow-Origin","http://localhost:8080");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        if(res)
            return "success";
        return "failure";
    }

    //取消一门课程
    @RequestMapping("stu/cancelCourse")
    public String stuCancelCourse(@RequestBody String string_data, HttpServletRequest request, HttpServletResponse response) {
        JSONObject json = Process_data(string_data);
        int courseId = Integer.parseInt(json.get("courseId").toString());
        int stuId = Integer.parseInt(request.getSession().getAttribute("stuId").toString());
        boolean res = stuServiceimpl.stuCancelCourse(courseId, stuId);
        response.setHeader("Access-Control-Allow-Origin", "http://localhost:8080");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        if (res)
            return "success";
        return "failure";
    }
}
