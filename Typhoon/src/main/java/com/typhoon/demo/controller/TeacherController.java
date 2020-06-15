package com.typhoon.demo.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.typhoon.demo.pojo.course;
import com.typhoon.demo.pojo.simplifiedStudent;
import com.typhoon.demo.service.teaServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@RestController
public class TeacherController {
    @Autowired
    private teaServiceImpl teaserviceimpl;
    @RequestMapping("/tea/queryMyCourse")
    public List<JSONObject> queryMyCourse(HttpServletRequest request, HttpServletResponse response)
    {
        int teaId = 1;
        List<course> res = teaserviceimpl.queryMyCourse(teaId);// 查询老师发布了的课
        List<JSONObject> ret = new ArrayList<>();
        for(course temp : res){
            JSONObject one = new JSONObject();
            one.put("Id", temp.getCourseId());
            one.put("Title", temp.getInterest());
            one.put("Cost", temp.getCourseCostHour());
            one.put("Date1", temp.getCourseStartDate());
            one.put("Place", temp.getCourseLocation());
            one.put("Status", temp.getIsCanceled());
            one.put("Introduction", temp.getCourseDescription());
            List<simplifiedStudent> students = teaserviceimpl.queryStudentByCourse(temp.getCourseId());
            one.put("Enrollments", students);
            ret.add(one);
        }
//        List<JSONObject> ret = new ArrayList<>();
//        JSONObject ret_1 = new JSONObject();
//        ret_1.put("Id",1);
//        ret_1.put("Title","篮球课");
//        ret_1.put("Date","2020-06-13 15:00");
//        ret_1.put("Place","理工博物馆");
//        ret_1.put("Introduction", "篮球基本技巧讲解及实践课");
//
//
//
//
//        ret.add(ret_1);
//        JSONObject ret_2 = new JSONObject();
//        ret_2.put("Id",1);
//        ret_2.put("Title","足球课");
//        ret_2.put("Date","2020-06-12 16:00");
//        ret_2.put("Place","人大体育馆");
//        ret_2.put("Introduction", "防守教学");
//        ret.add(ret_2);
        System.out.println("啦啦啦");
        response.addHeader("Access-Control-Allow-Origin","*");
        return ret;
    }
}
