package com.typhoon.demo.pojo;

public class student {
    private int stuId;
    private String stuUsername;
    private String stuPassword;
    private String interest;
    private int stuRemainingClassHour;
    private int stuTotalClassHour;
    private String stuTell;

    public student() {
    }

    public student(int stuId, String stuUsername, String stuPassword, String interest, int stuRemainingClassHour, int stuTotalClassHour, String stuTell) {
        this.stuId = stuId;
        this.stuUsername = stuUsername;
        this.stuPassword = stuPassword;
        this.interest = interest;
        this.stuRemainingClassHour = stuRemainingClassHour;
        this.stuTotalClassHour = stuTotalClassHour;
        this.stuTell = stuTell;
    }

    public int getStuTotalClassHour() {
        return stuTotalClassHour;
    }

    public void setStuTotalClassHour(int stuTotalClassHour) {
        this.stuTotalClassHour = stuTotalClassHour;
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

    public String getStuPassword() {
        return stuPassword;
    }

    public void setStuPassword(String stuPassword) {
        this.stuPassword = stuPassword;
    }

    public String getInterest() {
        return interest;
    }

    public void setInterest(String interest) {
        this.interest = interest;
    }

    public int getStuRemainingClassHour() {
        return stuRemainingClassHour;
    }

    public void setStuRemainingClassHour(int stuRemainingClassHour) {
        this.stuRemainingClassHour = stuRemainingClassHour;
    }

    public String getStuTell() {
        return stuTell;
    }

    public void setStuTell(String stuTell) {
        this.stuTell = stuTell;
    }
}
