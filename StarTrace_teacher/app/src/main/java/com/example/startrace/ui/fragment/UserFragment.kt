package com.example.startrace.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.example.startrace.R
import com.example.startrace.base.BaseFragment
import com.example.startrace.ui.activity.CourseResultActivity
import com.example.startrace.ui.activity.LoginActivity

class UserFragment : BaseFragment() {

    override fun initView(): View? {
        return View.inflate(context, R.layout.fragment_user, null)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val btnLogout= view.findViewById<Button>(R.id.logout)
        btnLogout?.setOnClickListener {
            val intent = Intent(activity,
                LoginActivity::class.java)
            startActivity(intent)

        }
        val btn_result = view.findViewById<Button>(R.id.result)
        btn_result.setOnClickListener{
            val intent = Intent(activity,
                CourseResultActivity::class.java)
            startActivity(intent)
        }
    }
}
