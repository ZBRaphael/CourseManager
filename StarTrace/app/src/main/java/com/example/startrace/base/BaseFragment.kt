package com.example.startrace.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.startrace.util.URLProviderUtils
import okhttp3.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.runOnUiThread
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import java.io.IOException

/**
 * ClassName:BaseFragment
 * Created bu ZhangBo at 2020/6/15
 * Describe:所有fragment基类
 **/
abstract class BaseFragment:Fragment() ,AnkoLogger {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
    }

    /**
     * fragment 初始化
     */
    open protected fun init() {

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return initView()
    }
    abstract fun initView():View?

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
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
    fun myToast(msg:String){
        context?.runOnUiThread { toast(msg) }
    }

}