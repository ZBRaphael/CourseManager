package com.example.teacherstartrace.ui.activity

import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.appcompat.widget.Toolbar
import com.example.teacherstartrace.R
import com.example.teacherstartrace.base.BaseActivity
import com.example.teacherstartrace.util.ThreadUtil
import com.example.teacherstartrace.util.ToolBarManager
import com.example.teacherstartrace.util.URLProviderUtils
import kotlinx.android.synthetic.main.activity_courseinfo.*
import okhttp3.*
import org.jetbrains.anko.find
import java.io.IOException

/**
 * ClassName:CourseInfo
 * Created bu ZhangBo at 2020/6/20
 * Describe:
 **/
class CourseInfoActivity() :BaseActivity(), ToolBarManager {
    override fun getLayoutId(): Int {
        return R.layout.activity_courseinfo
    }
    override val toolbar by lazy { find<Toolbar>(R.id.toolbar) }
    override fun initData() {
        initSettingToolBar("课程详细信息")
        val sp = PreferenceManager.getDefaultSharedPreferences(this)
        sp.getBoolean("push",false)

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val btn_cancel:Button = findViewById<Button>(R.id.Course_cancel)
        home_info_title.text = intent.getStringExtra("title")
        home_info_time.text = "时间："+intent.getStringExtra("time")
        home_info_local.text = "地点："+intent.getStringExtra("local")
        home_info_hours.text = "时长："+intent.getIntExtra("cost",0)
        home_info_num.text =
            "已签到人数/已选课人数：" + intent.getIntExtra("num_sign", 0) + "/" + intent.getIntExtra(
                "num_stu",
                0
            )
        tv_des.text = intent.getStringExtra("des")
        if(intent.getStringExtra("from")=="gallery"){
            btn_cancel.visibility = View.INVISIBLE
        }
        btn_cancel.setOnClickListener{
            val path = URLProviderUtils.cancelCourse()
            val builder = FormBody.Builder()
            builder.add("courseId", intent.getStringExtra("courseId"))
            val formBody = builder.build()

            val mOkHttpClient = OkHttpClient()
            Log.v("tea", formBody.value(0))
            Log.v("tea", intent.getStringExtra("session"))

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
}