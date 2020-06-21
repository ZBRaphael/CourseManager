package com.typhoon.demo.service;

import com.typhoon.demo.pojo.course;

public interface courseService {
    // 根据课程id查询课程
    course queryCourseById(int courseId);
}
