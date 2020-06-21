package com.typhoon.demo.service;

import com.typhoon.demo.mapper.studentMapper;
import com.typhoon.demo.pojo.attendclass;
import com.typhoon.demo.pojo.course;
import com.typhoon.demo.pojo.student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class stuServiceImpl implements stuService{

    @Autowired
    private studentMapper studentmapper;
    @Override
    public boolean register(String stuUsername, String stuPassword, String interest, String stuTell) {
        student targetStudent = studentmapper.queryStuByName(stuUsername);
        if(targetStudent != null)
        {
            return false;
        }
        return true;
    }

    @Override
    public student login(String stuUsername, String stuPassword) {
        student targetStudent =  studentmapper.queryStuByName(stuUsername);
        if(targetStudent == null)
            return null;
        else{
            if(stuPassword.equals(targetStudent.getStuPassword())){
                return targetStudent;
            }
            else
                return null;
        }
    }

    //查询和自己兴趣相关的课程
    @Override
    public List<course> stuQueryRelatedCourse(String interest) {
        return studentmapper.stuQueryRelatedClass(interest);
    }
    @Override
    public boolean stuAddCourse(int courseId) {
        return false;
    }

    @Override
    public boolean stuCancelCourse(int courseId) {
        return false;
    }
    @Override
    public boolean stuSign(int stuId, int courseId, int flag) {
        return false;
    }

    @Override
    public List<course> stuQueryCourse(int stuId) {
        return null;
    }

    //查询参与过的课程
    @Override
    public List<attendclass> stuQueryCourseAttend(int stuId) {
        return studentmapper.stuQueryAttendCourse(stuId);
    }

    @Override
    public student stuGetNyInfo(int stuId) {
        return studentmapper.stuGetMyInfo(stuId);
    }

    //查询自己的兴趣
    @Override
    public String stuQueryInterest(int stuId) {
        return studentmapper.queryMyInterest(stuId);
    }
}
