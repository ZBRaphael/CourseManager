package com.example.teacherstartrace.ui.fragment

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.teacherstartrace.R
import com.example.teacherstartrace.adapter.HomeAdapter
import com.example.teacherstartrace.base.BaseFragment
import com.example.teacherstartrace.model.CourseBean
import com.example.teacherstartrace.model.HomeViewModel
import com.example.teacherstartrace.presenter.interf.HomePresenterImpl
import com.example.teacherstartrace.util.ThreadUtil
import com.example.teacherstartrace.util.URLProviderUtils
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.fragment_home.*
import okhttp3.*
import java.io.IOException

class HomeFragment : BaseFragment() {
    val adapter by lazy { HomeAdapter() }
    var username = ""
    var sessionId = ""
    override fun initView(): View? {
        return View.inflate(context, R.layout.fragment_home, null)
    }

    override fun initListener() {
        //初始化cyclerview
        recyclerview.layoutManager = LinearLayoutManager(context)
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


        loadDatas()
    }

    private fun loadDatas() {
        val intent = activity?.intent
        username = intent?.getStringExtra("username").toString();
        sessionId = intent?.getStringExtra("sessionId").toString();
        println("$username,$sessionId")
        val path = URLProviderUtils.getHomeUrl()
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
                refreshLayout.isRefreshing = false
                Log.v("http", "获取数据出错：" + path)
                throw(e)
            }

            /**
             * 子线程调用
             */

            override fun onResponse(call: Call, response: Response) {
                refreshLayout.isRefreshing = false

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