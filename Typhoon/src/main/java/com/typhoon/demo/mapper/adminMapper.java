package com.typhoon.demo.mapper;

        import org.apache.ibatis.annotations.Insert;
        import org.apache.ibatis.annotations.Mapper;
        import org.apache.ibatis.annotations.Param;
        import org.apache.ibatis.annotations.Update;
        import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface adminMapper {
    @Update("update STUDENT set stuRemainingClassHour = stuRemainingClassHour + #{classHour} where stuId = #{stuId}")
    public boolean addCLassHour(@Param("stuId")int stuId, @Param("classHour")int classHour);
    @Insert("insert into TEACHER(teaUsername, teaPassword) values (#{teaUsername}, #{teaPassword})")
    public boolean addTeacher(@Param("teaUsername") String teaUsername, @Param("teaPassword") String teaPassword);
}
