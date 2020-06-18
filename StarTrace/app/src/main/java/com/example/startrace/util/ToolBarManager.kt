package com.example.startrace.util

import android.content.Intent
import android.view.MenuItem
import androidx.appcompat.widget.Toolbar
import com.example.startrace.R
import com.example.startrace.ui.activity.SettingActivity


/**
 * ClassName:ToolBarManager
 * Created bu ZhangBo at 2020/6/16
 * Describe:
 **/
interface ToolBarManager {
    val toolbar: Toolbar
    fun initToolBar(){
        toolbar.setTitle("星迹")
        toolbar.inflateMenu(R.menu.main)
        toolbar.setOnMenuItemClickListener(object :Toolbar.OnMenuItemClickListener{


            override fun onMenuItemClick(item: MenuItem?): Boolean {
                when(item?.itemId){
                    R.id.action_settings->{
//                        Toast.makeText(toolbar.context,"setting",Toast.LENGTH_SHORT).show()

                        toolbar.context.startActivity(Intent(toolbar.context, SettingActivity::class.java))
                    }
                }
                return true
            }
        })
    }
    fun initSettingToolBar(){
        toolbar.setTitle("设置界面")
    }
}