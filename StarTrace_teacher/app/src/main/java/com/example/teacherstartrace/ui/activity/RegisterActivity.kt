package com.example.teacherstartrace.ui.activity

import android.os.Bundle
import android.widget.Button
import com.example.teacherstartrace.R
import com.example.teacherstartrace.base.BaseActivity
import org.jetbrains.anko.startActivity

/**
 * ClassName:RegisterActivity
 * Created bu ZhangBo at 2020/6/19
 * Describe:
 **/
class RegisterActivity: BaseActivity() {
    override fun getLayoutId(): Int {
        return R.layout.activity_register
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val register = findViewById<Button>(R.id.login_register)
        register.setOnClickListener{
            startActivityAndFinish<LoginActivity>()
        }
    }



}