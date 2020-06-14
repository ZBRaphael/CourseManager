package com.typhoon.demo.service;

import com.typhoon.demo.mapper.studentMapper;
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

    @Override
    public List<course> stuQueryRelatedCourse() {
        return null;
    }

    @Override
    public boolean stuAddCourse(int courseId) {
        return false;
    }

    @Override
    public boolean delAddCourse(int courseId) {
        return false;
    }

    @Override
    public boolean stuSign(int stuId, int courseId, int flag) {
        return false;
    }

    @Override
    public List<course> stuQueryCourse() {
        return null;
    }
}
