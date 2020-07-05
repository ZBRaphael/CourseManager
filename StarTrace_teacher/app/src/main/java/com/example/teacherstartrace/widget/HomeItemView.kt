package com.example.teacherstartrace.widget

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.RelativeLayout
import com.example.teacherstartrace.R
import com.example.teacherstartrace.model.CourseBean
import kotlinx.android.synthetic.main.item_home.view.*

/**
 * ClassName:HomeItemView
 * Created bu ZhangBo at 2020/6/16
 * Describe:
 **/
class HomeItemView : RelativeLayout {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    /**
     * 初始化的方法
     */
    init {
        View.inflate(context, R.layout.item_home, this)
    }

    /**
     * 刷新条目
     */
    fun setdata(data: CourseBean) {
        title.text = data.interest
        home_card_teacher.text = "课时："+data.courseCostHour
        home_card_date.text = "时间：" + data.courseDate
        home_card_loacl.text = "地点：" + data.courseLocation
        var count = 0
        data.Enrollments.forEach {
            if (it.isAttend == 1)
                count += 1
        }
        when(data.interest){
            "篮球课"-> bg.setImageResource(R.mipmap.basketball)
            "足球课"-> bg.setImageResource(R.mipmap.football)
            "体能课"-> bg.setImageResource(R.mipmap.tineng)
            "轮滑课"-> bg.setImageResource(R.mipmap.lunhua)
        }
        if (data.Enrollments.isEmpty()) {
            signin.text = "未有人选课"
        } else {
            if (count == data.Enrollments.size) {
                signin.text = "已完成签到"
            }else{
                signin.text = "未完成签到"
            }
        }
    }

}