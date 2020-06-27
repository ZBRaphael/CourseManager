package com.example.startrace.ui.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

import com.example.startrace.R
import com.example.startrace.base.BaseActivity
import com.example.startrace.ui.login.LoginViewModel
import com.example.startrace.util.ThreadUtil
import com.example.startrace.util.URLProviderUtils
import okhttp3.*
import java.io.IOException
import java.util.concurrent.ConcurrentHashMap

class LoginActivity : BaseActivity() {

    private lateinit var loginViewModel: LoginViewModel
    var sessionId = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(getLayoutId())

        val username = findViewById<EditText>(R.id.username)
        val password = findViewById<EditText>(R.id.password)
        val login = findViewById<Button>(R.id.login)
        val register = findViewById<Button>(R.id.btn_register)

        register.setOnClickListener {
            val intent = Intent(
                this,
                RegisterActivity::class.java
            )
            startActivity(intent)
        }

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

                        Toast.makeText(
                            applicationContext,
                            "密码错误或用户不存在",
                            Toast.LENGTH_LONG
                        ).show()
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

