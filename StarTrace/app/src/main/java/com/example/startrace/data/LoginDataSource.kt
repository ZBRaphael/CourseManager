package com.example.startrace.data

import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.util.Log
import com.example.startrace.data.model.LoggedInUser
import com.example.startrace.util.URLProviderUtils
import okhttp3.*
import okhttp3.OkHttpClient
import java.io.IOException
import java.util.concurrent.ConcurrentHashMap


/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
class LoginDataSource {

    fun login(username: String, password: String): Result<LoggedInUser> {
        try {

            // TODO: handle loggedInUser authentication
            var fakeUser = LoggedInUser(username, username)
            val path = URLProviderUtils.loginUrl()
            val builder = FormBody.Builder()
            builder.add("stuUsername", username)
            builder.add("stuPassword", password)
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

                    Log.v("http","获取数据出错："+path)
                    throw(e)
                }

                /**
                 * 子线程调用
                 */

                override fun onResponse(call: Call, response: Response) {



                    Log.v("http","获取数据成功："+Thread.currentThread().name)
                    val result = response.body?.string()
                    val headers = response.headers
                    val cookies:List<String> = headers.values("Set-Cookie");
                    println(cookies.toString())
                    val s = cookies[0]
                    val session = s.substring(0, s.indexOf(";"));

                    println("result：$result")
                    fakeUser = LoggedInUser(username, username)
//                    Session.instance.sessionId.set(session)
//                    Session.instance.userId.set(username)
                }

            })

            return Result.Success(fakeUser)
        } catch (e: Throwable) {
            return Result.Error(IOException("Error logging in", e))
        }
    }

    fun logout() {
        // TODO: revoke authentication
    }

}

private fun String?.set(index: String) {

}

