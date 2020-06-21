package com.example.teacherstartrace.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.example.teacherstartrace.R
import com.example.teacherstartrace.base.BaseFragment
import com.example.teacherstartrace.ui.activity.CourseResultActivity
import com.example.teacherstartrace.ui.activity.LoginActivity

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
    }
}
