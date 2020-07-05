package com.example.startrace.ui.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ProgressBar
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar

import androidx.appcompat.widget.Toolbar
import com.example.startrace.util.ToolBarManager
import com.example.startrace.R

import com.example.startrace.base.BaseActivity
import com.example.startrace.util.FragmentUtil
import kotlinx.android.synthetic.main.content_main.*
import org.jetbrains.anko.find

class MainActivity : BaseActivity(), ToolBarManager {

    override val toolbar by lazy { find<Toolbar>(R.id.toolbar) }
    override fun initData() {
        initToolBar()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val fab: FloatingActionButton = findViewById(R.id.fab)
        fab.setOnClickListener { view ->

        }
        fab.hide()

    }


    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun initListener() {
        bottomBar.setOnTabSelectListener{
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.container, FragmentUtil.fragmentUtil.getFragment(it)!!,it.toString())
            transaction.commit()
            println(it.toString())
        }
    }



}
