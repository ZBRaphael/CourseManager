package com.typhoon.demo.service;

import com.typhoon.demo.pojo.attendclass;
import com.typhoon.demo.pojo.course;
import com.typhoon.demo.pojo.student;

import java.util.List;

public interface stuService {
    public boolean register(String stuUsername, String stuPassword, String interest, String stuTell);
    public student login(String stuUsername, String stuPassword);
    //查询自己兴趣相关的课
    public List<course> stuQueryRelatedCourse(String interest);
    // 学生选课
    public boolean stuAddCourse(int courseId, int stuId);
    // 学生退课
    public boolean stuCancelCourse(int courseId, int stuId);

    public boolean stuSign(int stuId, int courseId, int flag);
    public List<course> stuQueryCourse(int stuId);

    // 查询自己上过的课
    public List<attendclass> stuQueryCourseAttend(int stuId);

    // 查询自己上过的课 为android设计
    public List<attendclass> stuQueryCourseAttendForAndroid(int stuId);


    public student stuGetNyInfo(int stuId);
    public String stuQueryInterest(int stuId);
    public boolean stuUpdateInformation(int stuId, String stuUsername, String stuPassword, String stuTell);
}
