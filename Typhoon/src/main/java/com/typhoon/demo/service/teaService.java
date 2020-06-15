package com.typhoon.demo.service;

import com.typhoon.demo.pojo.course;
import com.typhoon.demo.pojo.simplifiedStudent;
import com.typhoon.demo.pojo.teacher;

import java.util.List;

public interface teaService {
    public teacher login(String stuUsername, String stuPassword);
    public boolean addCourse(int teaId, int interest, int courseDate, int courseLocation);
    public boolean cancelCourse(int teaId, int couseId);
    public List<course> queryMyCourse(int teaId);
    public List<simplifiedStudent> queryStudentByCourse(int couseId);
}
