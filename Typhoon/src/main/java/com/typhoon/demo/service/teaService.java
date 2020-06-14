package com.typhoon.demo.service;

import com.typhoon.demo.pojo.teacher;

public interface teaService {
    public teacher login(String stuUsername, String stuPassword);
    public boolean addCourse(int teaId, int interest, int courseDate, int courseLocation);
    public boolean cancelCourse(int teaId, int couseId);
    public boolean queryMyCourse(int teaId);
}
