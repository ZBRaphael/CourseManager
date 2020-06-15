package com.typhoon.demo.pojo;

import java.util.Date;

public class attendclass {
    private int courseId;
    private int stuId;
    private String stuUsername;
    private String stuTell;
    private Date courseDate;
    private String courseLocation;
    private int isAttend;
    private int isCanceledByStu;
    private int isCanceledByTea;

    public attendclass() {
    }

    public attendclass(int courseId, int stuId, String stuUsername, String stuTell, Date courseDate, String courseLocation, int isAttend, int isCanceledByStu, int isCanceledByTea) {
        this.courseId = courseId;
        this.stuId = stuId;
        this.stuUsername = stuUsername;
        this.stuTell = stuTell;
        this.courseDate = courseDate;
        this.courseLocation = courseLocation;
        this.isAttend = isAttend;
        this.isCanceledByStu = isCanceledByStu;
        this.isCanceledByTea = isCanceledByTea;
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

    public String getStuUsername() {
        return stuUsername;
    }

    public void setStuUsername(String stuUsername) {
        this.stuUsername = stuUsername;
    }

    public String getStuTell() {
        return stuTell;
    }

    public void setStuTell(String stuTell) {
        this.stuTell = stuTell;
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

    public int getIsCanceledByStu() {
        return isCanceledByStu;
    }

    public void setIsCanceledByStu(int isCanceledByStu) {
        this.isCanceledByStu = isCanceledByStu;
    }

    public int getIsCanceledByTea() {
        return isCanceledByTea;
    }

    public void setIsCanceledByTea(int isCanceledByTea) {
        this.isCanceledByTea = isCanceledByTea;
    }
}
