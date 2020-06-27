package com.example.startrace.ui.fragment

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
import com.example.startrace.R
import com.example.startrace.adapter.GalleryAdapter
import com.example.startrace.adapter.HomeAdapter
import com.example.startrace.base.BaseFragment
import com.example.startrace.model.GalleryViewModel
import com.example.startrace.util.URLProviderUtils
import kotlinx.android.synthetic.main.fragment_home.*
import okhttp3.*
import java.io.IOException

class GalleryFragment : BaseFragment() {

    override fun initView(): View? {
        return View.inflate(context, R.layout.fragment_gallery, null)
    }

    override fun initListener() {
        //初始化cyclerview
        recyclerview.layoutManager = LinearLayoutManager(context)
        //适配
        val adapter = GalleryAdapter()
        recyclerview.adapter = adapter

    }

    override fun initData() {
        loadDatas()
    }

    private fun loadDatas() {
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
                Log.v("http", "获取数据出错：" + path)
            }

            /**
             * 子线程调用
             */

            override fun onResponse(call: Call, response: Response) {
                Log.v("http", "获取数据出错：" + Thread.currentThread().name)
            }

        })
    }
}
