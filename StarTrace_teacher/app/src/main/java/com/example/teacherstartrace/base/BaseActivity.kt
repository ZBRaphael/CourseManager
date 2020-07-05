package com.example.teacherstartrace.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.teacherstartrace.ui.activity.LoginActivity
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

abstract class BaseActivity: AppCompatActivity(),AnkoLogger {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutId())
        initListener()
        initData()
    }

    /**
     * 初始化数据
     */
    open protected fun initData() {

    }

    /**
     * listener 和 adapter
     */
    open protected fun initListener() {

    }

    /**
     *
     */
    abstract fun getLayoutId():Int
    fun myToast(msg:String){
        runOnUiThread { toast(msg) }
    }

    /**
     * 开启一个Activity并且完成该页面
     */
    inline fun <reified T: BaseActivity> startActivityAndFinish(){
        startActivity<T>()
        finish()
    }
}