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
        val btn_in:Button = findViewById<Button>(R.id.home_info_in)
        val btn_out:Button = findViewById<Button>(R.id.home_info_out)
        when(intent.getStringExtra("from")){
            "select"->{
                btn.text = "已完成"
                btn.isEnabled = false
                btn_in.visibility = View.INVISIBLE
                btn_out.visibility = View.INVISIBLE
            }
        }
        btn.setOnClickListener{

        }
    }
}