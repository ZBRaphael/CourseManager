package com.typhoon.demo.mapper;

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
    //查询与自己相关的课程
    @Select("select * from COURSE where stuId = #{stuId}")
    List<course> stuQueryRelatedCourse(int stuId);
    //参与某次课程
    //@Insert()
    //boolean stuAddCourse(int stuId, int courseId);
    //取消某次课程

    //签到某次课程
    @Update("update ATTENDCLASS set isAttend = @{flag} where stuId = #{stuId} and courseId = #{courseId}")
    boolean stuSign(@Param("stuId") int stuId, @Param("courseId")int courseId, @Param("flag")int flag);


}
