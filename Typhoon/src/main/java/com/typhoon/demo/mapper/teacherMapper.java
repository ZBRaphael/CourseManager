package com.typhoon.demo.mapper;

import com.typhoon.demo.pojo.attendclass;
import com.typhoon.demo.pojo.course;
import com.typhoon.demo.pojo.simplifiedStudent;
import com.typhoon.demo.pojo.teacher;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Mapper
@Repository
public interface teacherMapper {
    // 查询老师信息
    @Select("select teaUsername from TEACHER where teaId = #{teaId}")
    public String queryTeaInfo(int stuId);
    //登录
    @Select("select * from TEACHER where teaUsername = #{teaUsername}")
    public teacher queryTeaByName(String teaUsername);
    //添加课程
    @Insert("Insert into COURSE(teaId, CourseStartDate, CourseEndDate, courseLocation, interest) values (#)" )
    public boolean addCourse(@Param("teaId")int teaId, @Param("courseStartDate")String courseStartDate, @Param("courseEndDate")String courseEndDate, @Param("courseLocation")String courseLocation, @Param("interest")String interest);
    //取消课程
//    @Update("update COURSE ")//todo
    //查询自己发布的课程
    @Select("select * from COURSE where teaId = #{teaId}")
    public List<course> queryMyCourse(int teaId);
    //查询选了这门课的学生
    @Select("select stuId, stuUsername, stuTell, isAttend from ATTENDCLASS where courseId = #{courseId}")
    public List<simplifiedStudent> queryStudentByCourse(int courseId);

}
