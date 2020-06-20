package com.example.startrace.ui.activity


import android.preference.PreferenceManager
import androidx.appcompat.widget.Toolbar
import com.example.startrace.R
import com.example.startrace.base.BaseActivity
import com.example.startrace.util.ToolBarManager
import org.jetbrains.anko.find

/**
 * ClassName:SettingActivity
 * Created bu ZhangBo at 2020/6/16
 * Describe:
 **/
class SettingActivity : BaseActivity(), ToolBarManager {
    override fun getLayoutId(): Int {
        return R.layout.activity_setting
    }
    override val toolbar by lazy { find<Toolbar>(R.id.toolbar) }
    override fun initData() {
        initSettingToolBar("设置界面")
        //获取推送没有被选中
        val sp = PreferenceManager.getDefaultSharedPreferences(this)
        sp.getBoolean("push",false)

    }
}