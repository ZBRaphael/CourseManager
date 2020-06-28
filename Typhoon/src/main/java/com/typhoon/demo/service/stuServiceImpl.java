package com.typhoon.demo.service;

import com.typhoon.demo.mapper.courseMapper;
import com.typhoon.demo.mapper.studentMapper;
import com.typhoon.demo.pojo.attendclass;
import com.typhoon.demo.pojo.course;
import com.typhoon.demo.pojo.student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class stuServiceImpl implements stuService{

    @Autowired
    private studentMapper studentmapper;
    @Autowired
    private courseMapper coursemapper;
    @Override
    public boolean register(String stuUsername, String stuPassword, String interest, String stuTell) {
        student targetStudent = studentmapper.queryStuByName(stuUsername);
        if(targetStudent != null)
        {
            return false;
        }
        return studentmapper.insertStu(stuUsername,stuPassword,interest,stuTell);
    }

    @Override
    public student login(String stuUsername, String stuPassword) {
        student targetStudent =  studentmapper.queryStuByName(stuUsername);
        if(targetStudent == null)
            return null;
        else{
            if(stuPassword.equals(targetStudent.getStuPassword())){
                return targetStudent;
            }
            else
                return null;
        }
    }

    //查询和自己兴趣相关的课程
    @Override
    public List<course> stuQueryRelatedCourse(String interest) {
        return studentmapper.stuQueryRelatedClass(interest);
    }

    //学生选课
    @Override
    public boolean stuAddCourse(int courseId, int stuId) {
        // 查询学生
        student theStudent = studentmapper.stuGetMyInfo(stuId);
        // 查询对应课程详细信息
        course theCourse = coursemapper.queryCourseById(courseId);
        // 课时是否足够
        if(theStudent.getStuRemainingClassHour() < theCourse.getCourseCostHour())
            return false;

        return studentmapper.stuAddcourse_cost(theStudent.getStuRemainingClassHour() - theCourse.getCourseCostHour(), stuId) && studentmapper.stuAddCourse(courseId, stuId, theStudent.getStuUsername(), theStudent.getStuTell(), theCourse.getCourseStartDate().toString(),theCourse.getCourseLocation());
    }

    // 学生取消课程
    @Override
    public boolean stuCancelCourse(int courseId, int stuId) {
        // 查询对应课程详细信息
        course theCourse = coursemapper.queryCourseById(courseId);
        return studentmapper.stuCancelCourse(stuId, courseId) && studentmapper.stuCancelCourseReturnCost(theCourse.getCourseCostHour(), stuId);
    }

    // 学生签到 将isAttend置为1
    @Override
    public boolean stuSign(int stuId, int courseId, int flag) {
        return studentmapper.stuSign(stuId, courseId, flag);
    }

    @Override
    public List<course> stuQueryCourse(int stuId) {
        return null;
    }

    //查询参与过的课程
    @Override
    public List<attendclass> stuQueryCourseAttend(int stuId) {
        return studentmapper.stuQueryAttendCourse(stuId);
    }

    @Override
    public List<attendclass> stuQueryCourseAttendForAndroid(int stuId) {
        return studentmapper.stuQueryAttendCourseForAndroid(stuId);
    }

    @Override
    public student stuGetNyInfo(int stuId) {
        return studentmapper.stuGetMyInfo(stuId);
    }

    //查询自己的兴趣
    @Override
    public String stuQueryInterest(int stuId) {
        return studentmapper.queryMyInterest(stuId);
    }

    // 更新自己的信息
    @Override
    public boolean stuUpdateInformation(int stuId, String stuUsername, String stuPassword, String stuTell) {
        return studentmapper.stuUpdateInformation(stuId, stuUsername, stuPassword, stuTell);
    }
}
