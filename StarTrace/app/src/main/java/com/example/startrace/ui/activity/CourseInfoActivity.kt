package com.example.startrace.ui.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.View
import android.widget.Button
import androidx.appcompat.widget.Toolbar
import com.example.startrace.R
import com.example.startrace.base.BaseActivity
import com.example.startrace.util.ToolBarManager
import kotlinx.android.synthetic.main.activity_courseinfo.*
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
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val btn:Button = findViewById<Button>(R.id.home_info_login)
        val btn_cancel:Button = findViewById<Button>(R.id.home_info_cancel)
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
                btn_cancel.visibility = View.INVISIBLE
            }
            "result"->{
                btn.visibility = View.INVISIBLE
                btn_cancel.visibility = View.INVISIBLE
            }
        }
        home_info_title.text = intent.getStringExtra("title")
        home_info_time.text = "时间："+intent.getStringExtra("time")
        home_info_local.text = "地点："+intent.getStringExtra("local")
        home_info_hours.text = "时长："+intent.getIntExtra("cost",0)
        home_info_num.text = "已签到人数："+intent.getIntExtra("num_stu",0)
        tv_des.text = intent.getStringExtra("des")

    }
}