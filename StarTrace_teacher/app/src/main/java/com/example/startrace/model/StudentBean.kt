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
    var strUsername: String?,
    var stuPassword: String?,
    var interest: String?,
    var stuRemainingClassHour: Int,
    var stuTotalClassHour: Int,
    var stuTell: String?
):Parcelable {
    constructor(parcel: Parcel):this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readString()
    ){}

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(strUsername)
        parcel.writeString(stuPassword)
        parcel.writeString(interest)
        parcel.writeInt(stuRemainingClassHour)
        parcel.writeInt(stuTotalClassHour)
        parcel.writeString(stuTell)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<StudentBean> {
        override fun createFromParcel(parcel: Parcel): StudentBean {
            return StudentBean(parcel)
        }

        override fun newArray(size: Int): Array<StudentBean?> {
            return arrayOfNulls(size)
        }
    }

}