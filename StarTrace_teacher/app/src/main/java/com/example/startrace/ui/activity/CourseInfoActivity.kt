package com.example.startrace.ui.activity

import android.os.Bundle
import android.preference.PreferenceManager
import android.view.View
import android.widget.Button
import androidx.appcompat.widget.Toolbar
import com.example.startrace.R
import com.example.startrace.base.BaseActivity
import com.example.startrace.util.ToolBarManager
import org.jetbrains.anko.find

/**
 * ClassName:CourseInfo
 * Created bu ZhangBo at 2020/6/20
 * Describe:
 **/
class CourseInfoActivity() :BaseActivity(), ToolBarManager {
    override fun getLayoutId(): Int {
        return R.layout.activity_courseinfo
    }
    override val toolbar by lazy { find<Toolbar>(R.id.toolbar) }
    override fun initData() {
        initSettingToolBar("课程详细信息")
        val sp = PreferenceManager.getDefaultSharedPreferences(this)
        sp.getBoolean("push",false)

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