package com.example.startrace.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import com.example.startrace.R
import com.example.startrace.adapter.HomeAdapter
import com.example.startrace.base.BaseFragment
import com.example.startrace.model.CourseBean
import com.example.startrace.model.StuInfoBean
import com.example.startrace.ui.activity.CourseResultActivity
import com.example.startrace.ui.activity.LoginActivity
import com.example.startrace.util.ThreadUtil
import com.example.startrace.util.URLProviderUtils
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_user.*
import okhttp3.*
import java.io.IOException

class UserFragment : BaseFragment() {
    var username = ""
    var sessionId = ""



    override fun initView(): View? {
        return View.inflate(context, R.layout.fragment_user, null)

    }

    override fun initData() {
        loadDatas()
    }

    private fun loadDatas() {
        val intent = activity?.intent
        username = intent?.getStringExtra("username").toString();
        sessionId = intent?.getStringExtra("sessionId").toString();
        println("$username,$sessionId")
        val path = URLProviderUtils.getMyInfo()
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
                val stuInfo = gson.fromJson<StuInfoBean>(
                    result,
                    object : TypeToken<StuInfoBean>() {}.type
                )

                ThreadUtil.runOnMainThread(object : Runnable {
                    override fun run() {
                        //刷新列表
                        stu_username.text = stuInfo.stuUsername
                        phone.text = stuInfo.stuTell
                        ll_hours.text = "剩余课时："+stuInfo.stuRestHour.toString()
                        ll_totalhours.text ="已上课总课时："+stuInfo.stuTotalHour.toString()
                        ll_interesting.text = "专业："+stuInfo.interest

                    }
                })
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
            intent.putExtra("username", username);
            intent.putExtra("sessionId",sessionId);
            startActivity(intent)
        }
    }
}
