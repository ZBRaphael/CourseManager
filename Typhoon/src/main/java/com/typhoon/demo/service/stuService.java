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
    public boolean stuAddCourse(int courseId);
    public boolean stuCancelCourse(int courseId);
    public boolean stuSign(int stuId, int courseId, int flag);
    public List<course> stuQueryCourse(int stuId);
    public List<attendclass> stuQueryCourseAttend(int stuId);
    public student stuGetNyInfo(int stuId);
    public String stuQueryInterest(int stuId);
}
