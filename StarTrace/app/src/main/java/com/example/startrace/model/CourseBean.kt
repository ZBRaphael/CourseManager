package com.example.startrace.model

import java.util.*

/**
 * ClassName:CourseBean
 * Created bu ZhangBo at 2020/6/26
 * Describe:
 **/

data class CourseBean(
    val Enrollments: List<Enrollment>,
    val attendClassId: Int,
    val courseCostHour: Int,
    val courseDate: String,
    val courseDescription: String,
    val courseId: Int,
    val courseLocation: String,
    val interest: String,
    val isAttend: Int,
    val isCanceledByStu: Int,
    val isSign:Int = 0
)

data class Enrollment(
    val stuId: Int,
    val stuTell: String,
    val stuUsername: String,
    val isAttend:Int
)
