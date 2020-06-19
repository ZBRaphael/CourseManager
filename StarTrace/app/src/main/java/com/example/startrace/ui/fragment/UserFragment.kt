package com.example.startrace.ui.fragment

import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.startrace.R
import com.example.startrace.base.BaseFragment
import com.example.startrace.model.SlideshowViewModel

class UserFragment : BaseFragment() {

    override fun initView(): View? {
        return View.inflate(context, R.layout.fragment_user, null)
    }
}
