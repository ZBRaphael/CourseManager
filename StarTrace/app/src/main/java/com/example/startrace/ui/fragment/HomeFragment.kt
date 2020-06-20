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
import com.example.startrace.adapter.HomeAdapter
import com.example.startrace.base.BaseFragment
import com.example.startrace.model.HomeViewModel
import com.example.startrace.presenter.interf.HomePresenterImpl
import com.example.startrace.util.URLProviderUtils
import kotlinx.android.synthetic.main.fragment_home.*
import okhttp3.*
import java.io.IOException

class HomeFragment : BaseFragment() {
    val adapter by lazy { HomeAdapter() }

    override fun initView(): View? {
        return View.inflate(context,R.layout.fragment_home,null)
    }

    override fun initListener() {
        //初始化cyclerview
        recyclerview.layoutManager = LinearLayoutManager(context)
        //适配
        recyclerview.adapter = adapter
        //初始化刷新控件
        refreshLayout.setColorSchemeColors(Color.RED,Color.YELLOW,Color.GREEN)
        //刷新监听
        refreshLayout.setOnRefreshListener {
            //刷新监听
            loadDatas()
        }

    }

    override fun initData() {
        loadDatas()
    }
    private fun loadDatas(){
        val path = URLProviderUtils.getHomeUrl(0,20)
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
