package com.example.teacherstartrace.widget

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.RelativeLayout
import com.example.teacherstartrace.R
import com.example.teacherstartrace.model.CourseBean
import kotlinx.android.synthetic.main.item_home.view.*

/**
 * ClassName:GalleryItemView
 * Created bu ZhangBo at 2020/6/19
 * Describe:
 **/
class GalleryItemView: RelativeLayout {
    constructor(context: Context):super(context)
    constructor(context: Context, attrs: AttributeSet?):super(context,attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr:Int):super(context,attrs,defStyleAttr)
    /**
     * 初始化的方法
     */
    init {
        View.inflate(context, R.layout.item_gallery,this)
    }
    /**
     * 刷新条目
     */
    fun setdata(data: CourseBean){
        title.text = data.interest
        home_card_teacher.text = "课时："+data.courseCostHour
        home_card_date.text = "时间："+data.courseDate
        home_card_loacl.text = "地点："+data.courseLocation
        if(data.isAttend==0){
            signin.text = "已签到"
        }
    }
}