package com.example.teacherstartrace.ui.activity

import android.app.Activity
import android.content.Intent
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.annotation.StringRes
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.widget.Toolbar

import com.example.teacherstartrace.R
import com.example.teacherstartrace.base.BaseActivity
import com.example.teacherstartrace.ui.login.LoggedInUserView
import com.example.teacherstartrace.ui.login.LoginViewModel
import com.example.teacherstartrace.ui.login.LoginViewModelFactory
import com.example.teacherstartrace.util.ThreadUtil
import com.example.teacherstartrace.util.ToolBarManager
import com.example.teacherstartrace.util.URLProviderUtils
import okhttp3.*
import org.jetbrains.anko.startActivity
import java.io.IOException
import java.util.concurrent.ConcurrentHashMap
import org.jetbrains.anko.find

class LoginActivity : BaseActivity(),ToolBarManager {
    override val toolbar by lazy { find<Toolbar>(R.id.toolbar) }
    private lateinit var loginViewModel: LoginViewModel
    var sessionId = ""
    override fun initData() {
        initSettingToolBar("教练登陆")

    }
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContentView(getLayoutId())

        val username = findViewById<EditText>(R.id.username)
        val password = findViewById<EditText>(R.id.password)
        val login = findViewById<Button>(R.id.login)

        login.setOnClickListener {

            val path = URLProviderUtils.loginUrl()
            val builder = FormBody.Builder()
            builder.add("stuUsername", username.text.toString())
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
                        cookieStr.append(cookies[0].name).append("=").append(cookies[0].value + ";");
                        sessionId = cookieStr.toString()
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
                        updateUiWithUser(username.text.toString())
                    }
                    else{

                        ThreadUtil.runOnMainThread(object : Runnable {
                            override fun run() {
                                //tanchuang

                                Toast.makeText(
                                    applicationContext,
                                    "密码错误或用户不存在",
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        })
                    }


                }

            })



        }


    }
    override fun getLayoutId(): Int {
        return R.layout.activity_login
    }

    private fun updateUiWithUser(username: String) {
        ThreadUtil.runOnMainThread(object : Runnable {
            override fun run() {
                //tanchuang
                val welcome = getString(R.string.welcome)
                Toast.makeText(
                    applicationContext,
                    "$welcome $username",
                    Toast.LENGTH_LONG
                ).show()
            }
        })


        val intent = Intent(
            this,
            MainActivity::class.java
        )
        intent.putExtra("username", username);
        intent.putExtra("sessionId",sessionId);
        startActivity(intent)
    }

}
