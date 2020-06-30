package com.example.startrace.ui.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import com.example.startrace.R
import com.example.startrace.base.BaseActivity
import com.example.startrace.util.ThreadUtil
import com.example.startrace.util.URLProviderUtils
import kotlinx.android.synthetic.main.activity_register.*
import okhttp3.*
import org.jetbrains.anko.startActivity
import java.io.IOException
import java.util.concurrent.ConcurrentHashMap

/**
 * ClassName:RegisterActivity
 * Created bu ZhangBo at 2020/6/19
 * Describe:
 **/
class RegisterActivity : BaseActivity() {
    override fun getLayoutId(): Int {
        return R.layout.activity_register
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val register = findViewById<Button>(R.id.login_register)
        register.setOnClickListener {
            if (password.text.toString() == confirmPassword.text.toString()) {
                val path = URLProviderUtils.getRegisterUrl()
                val builder = FormBody.Builder()

                builder.add("stuUsername", userName.text.toString())
                builder.add("interest", planets_spinner.selectedItem.toString())
                builder.add("stuTell", tell.text.toString())
                builder.add("stuPassword", password.text.toString())
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
//                        println(cookies[0].hostOnly)
                            val cookieStr = StringBuilder();
                            cookieStr.append(cookies[0].name).append("=")
                                .append(cookies[0].value + ";");
                            println("cookies:$cookies")
                        }

                        override fun loadForRequest(url: HttpUrl): List<Cookie> { //加载新的cookies
                            val cookies: List<Cookie>? = cookieStore.get(url.host)
                            return cookies ?: ArrayList()
                        }
                    })
                    .build()
                Log.v("stu", formBody.value(0))
                Log.v("stu", formBody.value(1))
                Log.v("stu", formBody.value(2))
                Log.v("stu", formBody.value(3))

                val request = Request.Builder()
                    .url(path)
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
                        val result = response.body?.string().toString()

                        println("result：$result")
                        if (result == "success") {
                            updateUiWithUser(userName.text.toString())
                        } else {

                            ThreadUtil.runOnMainThread(object : Runnable {
                                override fun run() {
                                    //tanchuang

                                    Toast.makeText(
                                        applicationContext,
                                        "用户已存在，注册失败",
                                        Toast.LENGTH_LONG
                                    ).show()
                                }
                            })
                        }


                    }

                })


            } else {
                ThreadUtil.runOnMainThread(object : Runnable {
                    override fun run() {
                        //tanchuang
                        val welcome = getString(R.string.welcome)
                        Toast.makeText(
                            applicationContext,
                            "两次密码不一致，请重新输入",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                })
            }
        }

    }
    fun updateUiWithUser(username: String) {
        ThreadUtil.runOnMainThread(object : Runnable {
            override fun run() {
                //tanchuang
                val welcome = getString(R.string.welcome)
                Toast.makeText(
                    applicationContext,
                    "$username，注册成功",
                    Toast.LENGTH_LONG
                ).show()

            }
        })
        startActivityAndFinish<LoginActivity>()
    }



}