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
    @Insert("insert into STUDENT(stuUsername, stuPassword, interest, stuTell) values(#{username}, #{password}, #{interest}, #{stuTell})")
    boolean insertStu(@Param("stuUsername") String stuUsername, @Param("stuPassword") String stuPassword, @Param("interest") String interest, @Param("stuTell") String stuTell);
    //登录
    @Select("select * from STUDENT where stuUsername = #{stuUsername}")
    student queryStuByName(String stuUsername);
    //查询参与过的课程
    @Select("select * from ATTENDCLASS where stuId = #{stuId}")
    List<attendclass> stuQueryAttendCourse(int stuId);

    //查询自己方向的课程
    @Select("select * from COURSE where interest = #{interest}")
    List<course> stuQueryRelatedClass(String interest);

    //查询自己的兴趣
    @Select("select interest from STUDENT where stuId = #{stuId}")
    String queryMyInterest(int stuId);

    //参与某次课程
    //@Insert()
    //boolean stuAddCourse(int stuId, int courseId);
    //取消某次课程

    //签到某次课程
    @Update("update ATTENDCLASS set isAttend = @{flag} where stuId = #{stuId} and courseId = #{courseId}")
    boolean stuSign(@Param("stuId") int stuId, @Param("courseId")int courseId, @Param("flag")int flag);

    //查询自己的信息
    @Select("select * from STUDENT where stuId = #{stuId}")
    student stuGetMyInfo(int stuId);


}
