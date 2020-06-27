package com.example.startrace.model

import java.util.*

/**
 * ClassName:CourseBean
 * Created bu ZhangBo at 2020/6/26
 * Describe:
 **/

data class CourseBean(
    val courseId:String,
    val courseStartDate:String,
    val courseLocation:String,
    val isCanceledByStu:String,
    val isCanceledByTea:String,
    val Enrollments:List<StuInfoBean>
)