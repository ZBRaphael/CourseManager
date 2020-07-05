package com.example.startrace.widget

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.RelativeLayout
import com.example.startrace.R
import com.example.startrace.model.CourseBean
import kotlinx.android.synthetic.main.item_home.view.*

/**
 * ClassName:HomeItemView
 * Created bu ZhangBo at 2020/6/16
 * Describe:
 **/
class HomeItemView: RelativeLayout {
    constructor(context:Context):super(context)
    constructor(context: Context,attrs:AttributeSet?):super(context,attrs)
    constructor(context: Context,attrs: AttributeSet?,defStyleAttr:Int):super(context,attrs,defStyleAttr)
    /**
     * 初始化的方法
     */
    init {
        View.inflate(context, R.layout.item_home,this)
    }

    /**
     * 刷新条目
     */
    fun setdata(data:CourseBean){
        title.text = data.interest
        home_card_teacher.text = "课时："+data.courseCostHour
        home_card_date.text = "时间："+data.courseDate
        home_card_loacl.text = "地点："+data.courseLocation
        when(data.interest){
            "篮球课"-> bg_home.setImageResource(R.drawable.basketball)
            "足球课"-> bg_home.setImageResource(R.drawable.football)
            "体能课"-> bg_home.setImageResource(R.drawable.tineng)
            "轮滑课"-> bg_home.setImageResource(R.drawable.lunhua)
        }
        if(data.isAttend==1){
            signin.text = "已签到"
        }

    }

}