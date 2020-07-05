package com.typhoon.demo.mapper;

import com.typhoon.demo.pojo.attendclass;
import com.typhoon.demo.pojo.course;
import com.typhoon.demo.pojo.student;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface studentMapper {
    //注册
    @Insert("insert into STUDENT(stuUsername, stuPassword, interest, stuTell) values(#{stuUsername}, #{stuPassword}, #{interest}, #{stuTell})")
    boolean insertStu(@Param("stuUsername") String stuUsername, @Param("stuPassword") String stuPassword, @Param("interest") String interest, @Param("stuTell") String stuTell);
    //登录
    @Select("select * from STUDENT where stuUsername = #{stuUsername}")
    student queryStuByName(String stuUsername);

    //查询参与过的课程
    @Select("select * from ATTENDCLASS where stuId = #{stuId} and isCanceledByStu = 0")
    List<attendclass> stuQueryAttendCourse(int stuId);

    // 查询选过的课程
    @Select("select * from ATTENDCLASS where stuId = #{stuId}")
    List<attendclass> stuQueryAttendCourseForAndroid(int stuId);


    //查询自己方向的课程
    @Select("select * from COURSE where interest = #{interest}")
    List<course> stuQueryRelatedClass(String interest);

    // 添加课程
    @Update("update STUDENT set stuRemainingClassHour = #{stuRemainingClassHour} where stuId = #{stuId}")
    boolean stuAddcourse_cost(@Param("stuRemainingClassHour")int stuRemainingClassHour, @Param("stuId")int stuId);
    @Insert("insert into ATTENDCLASS(courseId, stuId, stuUsername, stuTell, courseDate, courseLocation) values(#{courseId},#{stuId},#{stuUsername},#{stuTell},#{courseDate},#{courseLocation})")
    boolean stuAddCourse(@Param("courseId") int courseId, @Param("stuId") int stuId, @Param("stuUsername") String stuUsername, @Param("stuTell") String stuTell, @Param("courseDate") String courseDate, @Param("courseLocation")String courseLocation);

    //查询自己的兴趣
    @Select("select interest from STUDENT where stuId = #{stuId}")
    String queryMyInterest(int stuId);

    //取消某次课程
    @Update("update STUDENT set stuRemainingClassHour = #{courseCostHour} + stuRemainingClassHour where stuId = #{stuId}")
    boolean stuCancelCourseReturnCost(@Param("courseCostHour")int courseCostHour, @Param("stuId")int stuId);
    @Update("update ATTENDCLASS set isCanceledByStu = 1 where stuId = #{stuId} and courseId = #{courseId}")
    boolean stuCancelCourse(@Param("stuId") int stuId, @Param("courseId") int courseId);


    //签到某次课程
    @Update("update ATTENDCLASS set isAttend = #{flag} where stuId = #{stuId} and courseId = #{courseId}")
    boolean stuSign(@Param("stuId") int stuId, @Param("courseId")int courseId, @Param("flag")int flag);

    //查询自己的信息
    @Select("select * from STUDENT where stuId = #{stuId}")
    student stuGetMyInfo(int stuId);
    //更新自己的个人信息
    @Update("update STUDENT set stuUsername = #{stuUsername}, stuTell = #{stuTell}, stuPassword = #{stuPassword} where stuId = #{stuId}")
    boolean stuUpdateInformation(@Param("stuId") int stuId, @Param("stuUsername") String stuUsername, @Param("stuPassword")String stuPassword, @Param("stuTell") String stuTell);
}
