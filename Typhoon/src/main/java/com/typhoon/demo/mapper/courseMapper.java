package com.typhoon.demo.mapper;

import com.typhoon.demo.pojo.course;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface courseMapper {
    @Select("select * from COURSE where courseId = #{courseId}")
    public course queryCourseById(int courseId);
}
