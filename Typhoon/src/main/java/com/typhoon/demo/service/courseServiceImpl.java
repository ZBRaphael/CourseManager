package com.typhoon.demo.service;

import com.typhoon.demo.mapper.courseMapper;
import com.typhoon.demo.pojo.course;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class courseServiceImpl implements courseService{

    @Autowired
    private courseMapper coursemapper;
    @Override
    public course queryCourseById(int courseId) {
        return coursemapper.queryCourseById(courseId);
    }
}
