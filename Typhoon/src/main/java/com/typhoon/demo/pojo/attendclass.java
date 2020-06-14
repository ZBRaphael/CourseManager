package com.typhoon.demo.pojo;

import java.util.Date;

public class attendclass {
    private int courseId;
    private int stuId;
    private Date courseDate;
    private String courseLocation;
    private int isAttend;

    public attendclass() {
    }

    public attendclass(int courseId, int stuId, Date courseDate, String courseLocation, int isAttend) {
        this.courseId = courseId;
        this.stuId = stuId;
        this.courseDate = courseDate;
        this.courseLocation = courseLocation;
        this.isAttend = isAttend;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public int getStuId() {
        return stuId;
    }

    public void setStuId(int stuId) {
        this.stuId = stuId;
    }

    public Date getCourseDate() {
        return courseDate;
    }

    public void setCourseDate(Date courseDate) {
        this.courseDate = courseDate;
    }

    public String getCourseLocation() {
        return courseLocation;
    }

    public void setCourseLocation(String courseLocation) {
        this.courseLocation = courseLocation;
    }

    public int getIsAttend() {
        return isAttend;
    }

    public void setIsAttend(int isAttend) {
        this.isAttend = isAttend;
    }
}
