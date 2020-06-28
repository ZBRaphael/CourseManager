package com.typhoon.demo.mapper;

import com.typhoon.demo.pojo.course;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface courseMapper {
    @Select("select * from COURSE where courseId = #{courseId}")
    public course queryCourseById(int courseId);

    // 添加课程
    @Insert("insert into COURSE(courseStartDate, courseEndDate, courseCostHour, courseLocation, teaId, interest, courseDescription) values (#{courseStartDate}, #{courseEndDate}, #{courseCostHour}, #{courseLocation},#{teaId},#{interest},#{courseDescription})")
    public boolean insertCourse(@Param("courseStartDate")String courseStartDate,@Param("courseEndDate") String courseEndDate,@Param("courseCostHour")int courseCostHour,@Param("courseLocation") String courseLocation, @Param("teaId") int teaId, @Param("interest")String interest, @Param("courseDescription")String courseDescription);
    // 取消课程
    @Update("update COURSE set isCanceledByTea = 1 where courseId = #{courseId}")
    public boolean cancelCourse(int courseId);
    @Update("update ATTENDCLASS set isCanceledByTea = 1 where courseId = #{courseId}")
    public boolean cancelCourseUpdateATTENDCLASS(int courseID);
}
