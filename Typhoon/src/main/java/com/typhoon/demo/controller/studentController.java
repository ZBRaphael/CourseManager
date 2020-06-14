package com.typhoon.demo.controller;

import com.typhoon.demo.pojo.course;
import com.typhoon.demo.pojo.student;
import com.typhoon.demo.service.stuServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import com.alibaba.fastjson.*;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.List;

import static com.typhoon.demo.util.dataProcess.Process_data;

@RestController
public class studentController {
    @Autowired
    private stuServiceImpl stuService;
    @RequestMapping("/stu/test")
    public String test() {
        student t = stuService.login("Faultz", "19981001");
        return t.getStuUsername();
    }
    @RequestMapping("/stu/login")
    public JSONObject login(@RequestBody String string_data, HttpServletRequest request, HttpServletResponse response) {
        JSONObject ret = new JSONObject();
        JSONObject json = Process_data(string_data);
        String username = json.get("username").toString();
        String password = json.get("password").toString();
        String identity = json.get("identity").toString();
        student target_student = stuService.login(username, password);
        if (target_student != null) {
            request.getSession().setAttribute("stuId", target_student.getStuId());
            request.getSession().setAttribute("stuUsername", target_student.getStuUsername());
            response.addHeader("Access-Control-Allow-Origin","*");
            ret.put("res", true);
        }
        else
            ret.put("res", false);
        return ret;
    }
    @RequestMapping("stu/sign")
    public JSONObject stuSign(@RequestBody String string_data, HttpServletRequest request, HttpServletResponse response){
        JSONObject json = Process_data(string_data);
        int courseId = Integer.parseInt(json.get("stuId").toString());
        int stuId = Integer.parseInt(request.getSession().getAttribute("stuId").toString());
        boolean res = stuService.stuSign(stuId, courseId, 1);
        JSONObject ret = new JSONObject();
        ret.put("res", res);
        return ret;
    }
    @RequestMapping("stu/queryAllCourse")
    public JSONObject queryAllCourse() {
        JSONObject ret = new JSONObject();
        List<course> courses = stuService.stuQueryCourse();
        ret.put("res", courses);
        return ret;
    }

}
