package com.example.startrace.ui.activity

import android.view.View
import androidx.core.view.ViewCompat
import androidx.core.view.ViewPropertyAnimatorListener

import com.example.startrace.R
import com.example.startrace.base.BaseActivity

import kotlinx.android.synthetic.main.activity_splash.*
import org.jetbrains.anko.startActivity


/**
 * ClassName:SplashActivity
 * Created bu ZhangBo at 2020/6/15
 * Describe:
 **/
class SplashActivity: BaseActivity(), ViewPropertyAnimatorListener {
    override fun getLayoutId(): Int {
        return R.layout.activity_splash
    }

    override fun initData() {
        ViewCompat.animate(welcomeView).scaleX(1.0F).scaleY(1.0F).setListener(this).setDuration(2000)
    }

    override fun onAnimationEnd(view: View?) {
        //进入登陆界面
        startActivity<LoginActivity>()
        finish()
//        startActivityAndFinish<LoginActivity>()
    }

    override fun onAnimationCancel(view: View?) {

    }

    override fun onAnimationStart(view: View?) {

    }
}