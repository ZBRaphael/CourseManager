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
import com.example.startrace.model.CourseBean
import com.example.startrace.util.ThreadUtil
import com.example.startrace.util.ToolBarManager
import com.example.startrace.util.URLProviderUtils
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.fragment_gallery.*
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.recyclerview
import kotlinx.android.synthetic.main.resultactivity.*
import okhttp3.*
import org.jetbrains.anko.find
import java.io.IOException

class CourseResultActivity() :BaseActivity(), ToolBarManager {
    var username = ""
    var sessionId = ""
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
        refreshLayout_result.setColorSchemeColors(Color.RED, Color.YELLOW, Color.GREEN)
        //刷新监听
        refreshLayout_result.setOnRefreshListener {
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
        username = intent?.getStringExtra("username").toString();
        sessionId = intent?.getStringExtra("sessionId").toString();
        println("$username,$sessionId")
        val path = URLProviderUtils.queryAllCourse()
        val builder = FormBody.Builder()
        val formBody = builder.build()
        val mOkHttpClient = OkHttpClient()
        Log.v("stu", formBody.toString())

        val request = Request.Builder()
            .url(path)
            .header("Cookie", sessionId)
            .post(formBody)
            .build()
        mOkHttpClient.newCall(request).enqueue(object : Callback {
            /**
             * 子线程调用
             */
            override fun onFailure(call: Call, e: IOException) {
                refreshLayout_result.isRefreshing = false
                Log.v("http", "获取数据出错：" + path)
                throw(e)
            }

            /**
             * 子线程调用
             */

            override fun onResponse(call: Call, response: Response) {
                refreshLayout_result.isRefreshing = false

                Log.v("http", "获取数据成功：" + Thread.currentThread().name)
                val result = response.body?.string()
                Log.v("http", result)
                val gson = Gson()
                val list = gson.fromJson<List<CourseBean>>(
                    result,
                    object : TypeToken<List<CourseBean>>() {}.type
                )
                val filtedList: List<CourseBean> = list.filter {
                    it.isCanceledByStu == 0
                }

                ThreadUtil.runOnMainThread(object : Runnable {
                    override fun run() {
                        //刷新列表
                        adapter.upDataList(filtedList,sessionId)

                    }
                })
            }
        })
    }

}
