package com.typhoon.demo.service;

import com.typhoon.demo.pojo.course;
import com.typhoon.demo.pojo.student;

import java.util.List;

public interface stuService {
    public boolean register(String stuUsername, String stuPassword, String interest, String stuTell);
    public student login(String stuUsername, String stuPassword);
    public List<course> stuQueryRelatedCourse();
    public boolean stuAddCourse(int courseId);
    public boolean delAddCourse(int courseId);
    public boolean stuSign(int stuId, int courseId, int flag);
    public List<course> stuQueryCourse();
}
