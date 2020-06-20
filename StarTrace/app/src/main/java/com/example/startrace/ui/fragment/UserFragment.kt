package com.example.startrace.ui.fragment

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.startrace.R
import com.example.startrace.base.BaseFragment
import com.example.startrace.model.SlideshowViewModel
import com.example.startrace.ui.activity.LoginActivity
import com.example.startrace.ui.activity.MainActivity
import kotlinx.android.synthetic.*
import kotlinx.android.synthetic.main.content_main.*

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
