package com.example.startrace.ui.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import com.example.startrace.R
import com.example.startrace.base.BaseActivity
import com.example.startrace.model.CourseBean
import com.example.startrace.util.ThreadUtil
import com.example.startrace.util.ToolBarManager
import com.example.startrace.util.URLProviderUtils
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_courseinfo.*
import kotlinx.android.synthetic.main.fragment_home.*
import okhttp3.*
import org.jetbrains.anko.find
import java.io.IOException
import java.util.concurrent.ConcurrentHashMap

/**
 * ClassName:CourseInfo
 * Created bu ZhangBo at 2020/6/20
 * Describe:
 **/
class CourseInfoActivity() : BaseActivity(), ToolBarManager {
    override fun getLayoutId(): Int {
        return R.layout.activity_courseinfo
    }

    override val toolbar by lazy { find<Toolbar>(R.id.toolbar) }
    override fun initData() {
        initSettingToolBar("课程详细信息")
        val sp = PreferenceManager.getDefaultSharedPreferences(this)
        sp.getBoolean("push", false)

    }

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val btn: Button = findViewById<Button>(R.id.home_info_login)
        val btn_cancel: Button = findViewById<Button>(R.id.home_info_cancel)
        when (intent.getStringExtra("from")) {
            "sigin" -> {
                if (intent.getIntExtra("attend", 0) == 1) {
                    btn_cancel.visibility = View.INVISIBLE
                    btn.visibility = View.INVISIBLE
                }
                btn.text = "签到"
                btn.setOnClickListener {
                    val path = URLProviderUtils.sign()
                    val builder = FormBody.Builder()
                    builder.add("courseId", intent.getStringExtra("courseId"))
                    val formBody = builder.build()

                    val mOkHttpClient = OkHttpClient()
                    Log.v("stu", formBody.value(0))
                    Log.v("stu", intent.getStringExtra("session"))

                    val request = Request.Builder()
                        .url(path)
                        .header("Cookie", intent.getStringExtra("session"))
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
                            Log.v("http", result)
                            if (result == "success") {
                                ThreadUtil.runOnMainThread(object : Runnable {
                                    override fun run() {
                                        //刷新列表
                                        myToast("签到成功")

                                    }
                                })
                            } else {
                                ThreadUtil.runOnMainThread(object : Runnable {
                                    override fun run() {
                                        //刷新列表
                                        myToast("签到失败")

                                    }
                                })
                            }


                        }
                    })

                }
                btn_cancel.setOnClickListener {
                    val path = URLProviderUtils.cancelCourse()
                    val builder = FormBody.Builder()
                    builder.add("courseId", intent.getStringExtra("courseId"))
                    val formBody = builder.build()

                    val mOkHttpClient = OkHttpClient()
                    Log.v("stu", formBody.toString())

                    val request = Request.Builder()
                        .url(path)
                        .header("Cookie", intent.getStringExtra("session"))
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
                            Log.v("http", result)
                            if (result == "success") {
                                ThreadUtil.runOnMainThread(object : Runnable {
                                    override fun run() {
                                        //刷新列表
                                        myToast("取消课程成功")

                                    }
                                })
                            } else {
                                ThreadUtil.runOnMainThread(object : Runnable {
                                    override fun run() {
                                        //刷新列表
                                        myToast("取消课程失败")

                                    }
                                })
                            }


                        }
                    })
                }
            }
            "select" -> {
                btn.text = "选课"
                btn.setOnClickListener {
                    val path = URLProviderUtils.addCourse()
                    val builder = FormBody.Builder()
                    builder.add("courseId", intent.getStringExtra("courseId"))
                    val formBody = builder.build()

                    val mOkHttpClient = OkHttpClient()
                    Log.v("stu", formBody.value(0))
                    Log.v("stu", intent.getStringExtra("session"))

                    val request = Request.Builder()
                        .url(path)
                        .header("Cookie", intent.getStringExtra("session"))
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
                            Log.v("http", result)
                            if (result == "success") {
                                ThreadUtil.runOnMainThread(object : Runnable {
                                    override fun run() {
                                        //刷新列表
                                        myToast("选课成功")

                                    }
                                })
                            } else {
                                ThreadUtil.runOnMainThread(object : Runnable {
                                    override fun run() {
                                        //刷新列表
                                        myToast("选课失败")

                                    }
                                })
                            }


                        }
                    })
                }
                btn_cancel.visibility = View.INVISIBLE
            }
            "result" -> {
                btn.visibility = View.INVISIBLE
                btn_cancel.visibility = View.INVISIBLE
            }
        }
        home_info_title.text = intent.getStringExtra("title")
        home_info_time.text = "时间：" + intent.getStringExtra("time")
        home_info_local.text = "地点：" + intent.getStringExtra("local")
        home_info_hours.text = "时长：" + intent.getIntExtra("cost", 0)
        home_info_num.text =
            "已签到人数/已选课人数：" + intent.getIntExtra("num_sign", 0) + "/" + intent.getIntExtra(
                "num_stu",
                0
            )
        tv_des.text = intent.getStringExtra("des")

    }
}