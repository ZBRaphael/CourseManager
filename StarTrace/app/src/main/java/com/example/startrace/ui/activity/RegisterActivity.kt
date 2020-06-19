package com.example.startrace.ui.activity

import android.os.Bundle
import android.widget.Button
import com.example.startrace.R
import com.example.startrace.base.BaseActivity
import org.jetbrains.anko.startActivity

/**
 * ClassName:RegisterActivity
 * Created bu ZhangBo at 2020/6/19
 * Describe:
 **/
class RegisterActivity:BaseActivity() {
    override fun getLayoutId(): Int {
        return R.layout.activity_register
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val register = findViewById<Button>(R.id.login_register)
        register.setOnClickListener{
            startActivity<MainActivity>()
        }
    }



}