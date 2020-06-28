package com.typhoon.demo.pojo;

import java.util.Date;

public class course {
    private int courseId;
    private String courseStartDate;
    private String courseEndDate;
    private int courseCostHour;
    private String courseLocation;
    private int teaId;
    private String interest;
    private int isCanceled;  // 是否被老师取消
    private String courseDescription;

    public String getCourseStartDate() {
        return courseStartDate;
    }

    public void setCourseStartDate(String courseStartDate) {
        this.courseStartDate = courseStartDate;
    }

    public String getCourseEndDate() {
        return courseEndDate;
    }

    public void setCourseEndDate(String courseEndDate) {
        this.courseEndDate = courseEndDate;
    }

    public course(int courseId, String courseStartDate, String courseEndDate, int courseCostHour, String courseLocation, int teaId, String interest, int isCanceled, String courseDescription) {
        this.courseId = courseId;
        this.courseStartDate = courseStartDate;
        this.courseEndDate = courseEndDate;
        this.courseCostHour = courseCostHour;
        this.courseLocation = courseLocation;
        this.teaId = teaId;
        this.interest = interest;
        this.isCanceled = isCanceled;
        this.courseDescription = courseDescription;
    }

    public course() {
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }


    public int getCourseCostHour() {
        return courseCostHour;
    }

    public void setCourseCostHour(int courseCostHour) {
        this.courseCostHour = courseCostHour;
    }

    public String getCourseLocation() {
        return courseLocation;
    }

    public void setCourseLocation(String courseLocation) {
        this.courseLocation = courseLocation;
    }

    public int getTeaId() {
        return teaId;
    }

    public void setTeaId(int teaId) {
        this.teaId = teaId;
    }

    public String getInterest() {
        return interest;
    }

    public void setInterest(String interest) {
        this.interest = interest;
    }

    public int getIsCanceled() {
        return isCanceled;
    }

    public void setIsCanceled(int isCanceled) {
        this.isCanceled = isCanceled;
    }

    public String getCourseDescription() {
        return courseDescription;
    }

    public void setCourseDescription(String courseDescription) {
        this.courseDescription = courseDescription;
    }
}
