package com.typhoon.demo.pojo;

public class simplifiedStudent {
    private int stuId;
    private String stuUsername;
    private String stuTell;

    public simplifiedStudent() {
    }

    public simplifiedStudent(int stuId, String stuUsername, String stuTell) {
        this.stuId = stuId;
        this.stuUsername = stuUsername;
        this.stuTell = stuTell;
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
}
