package pojo;

import java.util.Date;

public class course {
    private int courseId;
    private Date courseDate;
    private String courseLocation;
    private int teaId;
    private String interest;
    private int isCanceled;

    public course(int courseId, Date courseDate, String courseLocation, int teaId, String interest, int isCanceled) {
        this.courseId = courseId;
        this.courseDate = courseDate;
        this.courseLocation = courseLocation;
        this.teaId = teaId;
        this.interest = interest;
        this.isCanceled = isCanceled;
    }

    public course() {
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
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
}
