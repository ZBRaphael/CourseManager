package com.example.teacherstartrace.util

import android.content.Intent
import android.view.MenuItem
import androidx.appcompat.widget.Toolbar
import com.example.teacherstartrace.R
import com.example.teacherstartrace.ui.activity.SettingActivity


/**
 * ClassName:ToolBarManager
 * Created bu ZhangBo at 2020/6/16
 * Describe:
 **/
interface ToolBarManager {
    val toolbar: Toolbar
    fun initToolBar(){
        toolbar.setTitle(R.string.app_name)
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
    fun initSettingToolBar(title:String){
        toolbar.setTitle(title)

    }
}