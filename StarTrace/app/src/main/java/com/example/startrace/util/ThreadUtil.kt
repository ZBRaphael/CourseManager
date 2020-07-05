package com.example.startrace.util

import android.os.Handler
import android.os.Looper

/**
 * ClassName:ThreadUtil
 * Created bu ZhangBo at 2020/6/20
 * Describe:
 **/
object ThreadUtil {
    val handler = Handler(Looper.getMainLooper());
    /**
     * 运行在主线程中
     */
    fun runOnMainThread(runnable:Runnable){
        handler.post(runnable)
    }

}