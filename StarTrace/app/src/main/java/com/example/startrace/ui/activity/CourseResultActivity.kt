package com.example.startrace.ui.activity

import android.graphics.Color
import android.preference.PreferenceManager
import android.util.Log
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.startrace.R
import com.example.startrace.adapter.HomeAdapter
import com.example.startrace.adapter.ResultAdapter
import com.example.startrace.base.BaseActivity
import com.example.startrace.util.ToolBarManager
import com.example.startrace.util.URLProviderUtils
import kotlinx.android.synthetic.main.fragment_home.*
import okhttp3.*
import org.jetbrains.anko.find
import java.io.IOException

class CourseResultActivity() :BaseActivity(), ToolBarManager {
    override fun getLayoutId(): Int {
        return R.layout.resultactivity
    }
    override val toolbar by lazy { find<Toolbar>(R.id.toolbar) }
    val adapter by lazy { ResultAdapter() }


    override fun initListener() {
        //初始化cyclerview
        recyclerview.layoutManager = LinearLayoutManager(this)
        //适配
        recyclerview.adapter = adapter
        //初始化刷新控件
        refreshLayout.setColorSchemeColors(Color.RED, Color.YELLOW, Color.GREEN)
        //刷新监听
        refreshLayout.setOnRefreshListener {
            //刷新监听
            loadDatas()
        }

    }

    override fun initData() {
        initSettingToolBar("课程列表")
        val sp = PreferenceManager.getDefaultSharedPreferences(this)
        sp.getBoolean("push",false)
        loadDatas()
    }
    private fun loadDatas(){
        val path = URLProviderUtils.queryAllCourse()
        val client = OkHttpClient()
        val request = Request.Builder()
            .url(path)
            .get()
            .build()
        client.newCall(request).enqueue(object : Callback {
            /**
             * 子线程调用
             */
            override fun onFailure(call: Call, e: IOException) {
                //隐藏刷新控件
                refreshLayout.isRefreshing = false
                Log.v("http","获取数据出错："+path)
            }

            /**
             * 子线程调用
             */

            override fun onResponse(call: Call, response: Response) {
                //隐藏刷新控件
                refreshLayout.isRefreshing = false
                Log.v("http","获取数据成功："+Thread.currentThread().name)
            }

        })
    }

}
