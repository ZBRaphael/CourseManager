package com.example.startrace.model

import android.database.Cursor
import android.os.Parcel
import android.os.Parcelable
import android.provider.MediaStore

/**
 * ClassName:StudentBean
 * Created bu ZhangBo at 2020/6/17
 * Describe:
 **/
data class StudentBean(
    var strUsername: String,
    var stuPassword: String,
    var interest: String,
    var stuRemainingClassHour: Int?,
    var stuTotalClassHour: Int?,
    var stuTell: String
)