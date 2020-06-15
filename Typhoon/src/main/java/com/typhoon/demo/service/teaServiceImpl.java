package com.typhoon.demo.service;

import com.typhoon.demo.mapper.teacherMapper;
import com.typhoon.demo.pojo.course;
import com.typhoon.demo.pojo.simplifiedStudent;
import com.typhoon.demo.pojo.teacher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class teaServiceImpl implements teaService{
    @Autowired
    private teacherMapper teachermapper;
    @Override
    public teacher login(String stuUsername, String stuPassword) {
        return null;
    }

    @Override
    public boolean addCourse(int teaId, int interest, int courseDate, int courseLocation) {
        return false;
    }

    @Override
    public boolean cancelCourse(int teaId, int couseId) {
        return false;
    }

    @Override
    public List<course> queryMyCourse(int teaId) {
        return teachermapper.queryMyCourse(teaId);
    }

    @Override
    public List<simplifiedStudent> queryStudentByCourse(int couseId) {
        return teachermapper.queryStudentByCourse(couseId);
    }
}
