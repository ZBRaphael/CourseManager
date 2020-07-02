package com.example.teacherstartrace.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import com.example.teacherstartrace.R
import com.example.teacherstartrace.base.BaseFragment
import com.example.teacherstartrace.model.StuInfoBean
import com.example.teacherstartrace.model.TeaInfoBean
import com.example.teacherstartrace.ui.activity.CourseResultActivity
import com.example.teacherstartrace.ui.activity.LoginActivity
import com.example.teacherstartrace.util.ThreadUtil
import com.example.teacherstartrace.util.URLProviderUtils
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.fragment_user.*
import okhttp3.*
import java.io.IOException

class UserFragment : BaseFragment() {
    var username = ""
    var sessionId = ""

    override fun initView(): View? {
        return View.inflate(context, R.layout.fragment_user, null)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val btnLogout= view.findViewById<Button>(R.id.logout)
        btnLogout?.setOnClickListener {
            val intent = Intent(activity,
                LoginActivity::class.java)
            startActivity(intent)

        }
    }

    override fun initData() {
        loadData()
    }
    fun loadData() {
        val intent = activity?.intent
        username = intent?.getStringExtra("username").toString();
        sessionId = intent?.getStringExtra("sessionId").toString();
        println("$username,$sessionId")
        val path = URLProviderUtils.getTeacherInfoUrl()
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
                Log.v("http", "获取数据出错：" + path)
                throw(e)
            }

            /**
             * 子线程调用
             */

            override fun onResponse(call: Call, response: Response) {

                Log.v("http", "获取数据成功：" + Thread.currentThread().name)
                val result = response.body?.string()
                println(result)
                val gson = Gson()
                val teaInfo = gson.fromJson<TeaInfoBean>(
                    result,
                    object : TypeToken<TeaInfoBean>() {}.type
                )

                ThreadUtil.runOnMainThread(object : Runnable {
                    override fun run() {
                        //刷新列表
                        tea_username.text = teaInfo.teaUsername
                        ll_interesting.text = ""
                        phone.text = teaInfo.teaTell
                        ll_totalhours.text ="已上课总课时："+teaInfo.teaAllClassHour.toString()

                    }
                })
            }
        })
    }
}
