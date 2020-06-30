package com.example.startrace.ui.fragment

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.startrace.R
import com.example.startrace.adapter.GalleryAdapter
import com.example.startrace.base.BaseFragment
import com.example.startrace.model.CourseBean
import com.example.startrace.util.ThreadUtil
import com.example.startrace.util.URLProviderUtils
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.fragment_gallery.*

import okhttp3.*
import java.io.IOException

class GalleryFragment : BaseFragment() {
    val adapter by lazy { GalleryAdapter() }
    var username = ""
    var sessionId = ""
    override fun initView(): View? {
        return View.inflate(context, R.layout.fragment_gallery, null)
    }

    override fun initListener() {
        //初始化cyclerview
        recyclerview_gallery.layoutManager = LinearLayoutManager(context)
        //适配
        recyclerview_gallery.adapter = adapter
        //初始化刷新控件
        refreshLayout_gallery.setColorSchemeColors(Color.RED, Color.YELLOW, Color.GREEN)
        //刷新监听
        refreshLayout_gallery.setOnRefreshListener {
            //刷新监听

            loadDatas()
        }
        initData()

    }

    override fun initData() {


        loadDatas()
    }

    private fun loadDatas() {
        val intent = activity?.intent
        username = intent?.getStringExtra("username").toString();
        sessionId = intent?.getStringExtra("sessionId").toString();
        println("$username,$sessionId")
        val path = URLProviderUtils.getGalleryUrl()
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
                refreshLayout_gallery.isRefreshing = false
                Log.v("http", "获取数据出错：" + path)
                throw(e)
            }

            /**
             * 子线程调用
             */

            override fun onResponse(call: Call, response: Response) {
                refreshLayout_gallery.isRefreshing = false

                Log.v("http", "获取数据成功：" + Thread.currentThread().name)
                val result = response.body?.string()
                Log.v("http", result)
                val gson = Gson()
                val list = gson.fromJson<List<CourseBean>>(
                    result,
                    object : TypeToken<List<CourseBean>>() {}.type
                )


                ThreadUtil.runOnMainThread(object : Runnable {
                    override fun run() {
                        //刷新列表
                        adapter.upDataList(list,sessionId)

                    }
                })
            }
        })


    }

}
