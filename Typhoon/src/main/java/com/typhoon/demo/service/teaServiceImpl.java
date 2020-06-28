package com.typhoon.demo.service;

import com.typhoon.demo.mapper.courseMapper;
import com.typhoon.demo.mapper.studentMapper;
import com.typhoon.demo.mapper.teacherMapper;
import com.typhoon.demo.pojo.course;
import com.typhoon.demo.pojo.simplifiedStudent;
import com.typhoon.demo.pojo.student;
import com.typhoon.demo.pojo.teacher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class teaServiceImpl implements teaService{
    @Autowired
    private teacherMapper teachermapper;
    @Autowired
    private courseMapper coursemapper;
    @Autowired
    private studentMapper studentmapper;


    @Override
    public teacher queryTeaByName(String teaUsername, String teaPassword) {
        teacher targetTeacher = teachermapper.queryTeaByName(teaUsername);
        if(targetTeacher == null || !teaPassword.equals(targetTeacher.getTeaPassword())){
            return null;
        }
        else{
            return targetTeacher;
        }
    }

    @Override
    public boolean addCourse(int teaId, int interest, int courseDate, int courseLocation) {
        return false;
    }

    // 教师取消课程
    @Override
    public boolean cancelCourse(int courseId) {
        // 首先查询有多少人选课了
        List<simplifiedStudent> students = teachermapper.queryStudentByCourse(courseId);
        // 将这门课的isCancelByTea 置为 1
        boolean res = coursemapper.cancelCourse(courseId);
        // 将attendclass 表中的isCanceledByTea 置为1
        boolean res1 = coursemapper.cancelCourseUpdateATTENDCLASS(courseId);
        res = res && res1;
        // 查询课程信息
        course theCourse = coursemapper.queryCourseById(courseId);
        // 返还课时给学生
        for(simplifiedStudent student : students){
            res &= studentmapper.stuCancelCourseReturnCost(theCourse.getCourseCostHour(), student.getStuId());
        }
        return res;
    }

    @Override
    public List<course> queryMyCourse(int teaId) {
        return teachermapper.queryMyCourse(teaId);
    }

    // 根据课程id查询哪些学生选了这些课
    @Override
    public List<simplifiedStudent> queryStudentByCourse(int courseId) {
        return teachermapper.queryStudentByCourse(courseId);
    }

    @Override
    public String queryTeaInfo(int stuId) {
        return teachermapper.queryTeaInfo(stuId);
    }
}
