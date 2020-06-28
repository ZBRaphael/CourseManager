package com.typhoon.demo.service;

import com.typhoon.demo.pojo.course;

public interface courseService {
    // 根据课程id查询课程
    course queryCourseById(int courseId);
    // 插入课程
    public boolean teaAddCourse(String courseStartDate, String courseEndDate, int courseCostHour, String courseLocation, int teaId, String interest, String courseDescription);
}
