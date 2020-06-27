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
import com.example.startrace.data.model.LoggedInUser
import com.example.startrace.model.HomeViewModel
import com.example.startrace.presenter.interf.HomePresenterImpl
import com.example.startrace.util.PersistentCookieStore
import com.example.startrace.util.URLProviderUtils
import kotlinx.android.synthetic.main.fragment_home.*
import okhttp3.*
import java.io.IOException
import java.util.concurrent.ConcurrentHashMap

class HomeFragment : BaseFragment() {
    val adapter by lazy { HomeAdapter() }
    var username = ""
    var sessionId = ""
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
            val intent = activity?.intent
            username = intent?.getStringExtra("username").toString();
            sessionId = intent?.getStringExtra("sessionId").toString();
            println("$username,$sessionId")
            loadDatas()
        }

    }

    override fun initData() {


        loadDatas()
    }
    private fun loadDatas(){
        val path = URLProviderUtils.queryAllCourse()
        val builder = FormBody.Builder()
        val formBody = builder.build()
        val cookieStore: ConcurrentHashMap<String, List<Cookie>> =
            ConcurrentHashMap()
        val mOkHttpClient = OkHttpClient.Builder()
            .cookieJar(object : CookieJar {
                //这里可以做cookie传递，保存等操作
                override fun saveFromResponse(
                    url: HttpUrl,
                    cookies: List<Cookie>
                ) { //可以做保存cookies操作
                    cookieStore.put(url.host, cookies)
                    sessionId = cookies.toString()
                    println("cookies:$cookies")
                }

                override fun loadForRequest(url: HttpUrl): List<Cookie> { //加载新的cookies

                    val cookies: List<Cookie>? = cookieStore.get(url.host)
                    return cookies ?: ArrayList()
                }
            })
            .build()
        Log.v("stu", formBody.toString())

        val request = Request.Builder()
            .url(path)
            .post(formBody)
            .build()
        mOkHttpClient.newCall(request).enqueue(object : Callback {
            /**
             * 子线程调用
             */
            override fun onFailure(call: Call, e: IOException) {
                refreshLayout.isRefreshing  = false
                Log.v("http", "获取数据出错：" + path)
                throw(e)
            }

            /**
             * 子线程调用
             */

            override fun onResponse(call: Call, response: Response) {
                refreshLayout.isRefreshing  = false

                Log.v("http", "获取数据成功：" + Thread.currentThread().name)
                val result = response.body?.string().toString()
                println(result)



            }

        })
    }

}
