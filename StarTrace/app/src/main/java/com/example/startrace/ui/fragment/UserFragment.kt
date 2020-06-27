package com.example.startrace.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import com.example.startrace.R
import com.example.startrace.base.BaseFragment
import com.example.startrace.ui.activity.CourseResultActivity
import com.example.startrace.ui.activity.LoginActivity
import com.example.startrace.util.URLProviderUtils
import okhttp3.*
import java.io.IOException

class UserFragment : BaseFragment() {



    override fun initView(): View? {
        return View.inflate(context, R.layout.fragment_user, null)

    }

    override fun initData() {
        loadDatas()
    }

    private fun loadDatas() {
        val path = URLProviderUtils.loginUrl()
        val builder = FormBody.Builder()
        builder.add("stuUsername", "aab")
        builder.add("stuPassword", "123456")
        val formBody = builder.build()
        val client = OkHttpClient()
        Log.v("http", formBody.toString())

        val request = Request.Builder()
            .url(path)
            .post(formBody)
            .build()
        client.newCall(request).enqueue(object : Callback {
            /**
             * 子线程调用
             */
            override fun onFailure(call: Call, e: IOException) {

                Log.v("http","获取数据出错："+path)
                throw(e)
            }

            /**
             * 子线程调用
             */

            override fun onResponse(call: Call, response: Response) {

                Log.v("http","获取数据成功："+Thread.currentThread().name)
                val result = response.body?.string()
                println("result：$result")
            }

        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val btnLogout= view.findViewById<Button>(R.id.logout)
        btnLogout?.setOnClickListener {
            val intent = Intent(activity,
                LoginActivity::class.java)
            startActivity(intent)

        }
        val btn_result = view.findViewById<Button>(R.id.result)
        btn_result.setOnClickListener{
            val intent = Intent(activity,
                CourseResultActivity::class.java)
            startActivity(intent)
        }
    }
}
