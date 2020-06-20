package com.example.startrace.ui.activity

import android.os.Bundle
import android.view.View
import android.widget.Button
import com.example.startrace.R
import com.example.startrace.base.BaseActivity

/**
 * ClassName:CourseInfo
 * Created bu ZhangBo at 2020/6/20
 * Describe:
 **/
class CourseInfoActivity:BaseActivity() {
    override fun getLayoutId(): Int {
        return R.layout.activity_courseinfo
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val btn:Button = findViewById<Button>(R.id.home_info_login)
        when(intent.getStringExtra("from")){
            "sigin"->{
                btn.text = "签到"
                btn.setOnClickListener{
                    TODO("等待接口")
                }
            }
            "select"->{
                btn.text = "选课"
                btn.setOnClickListener{
                    TODO("等待接口")
                }
            }
            "result"->{
                btn.visibility = View.INVISIBLE
            }
        }
    }
}