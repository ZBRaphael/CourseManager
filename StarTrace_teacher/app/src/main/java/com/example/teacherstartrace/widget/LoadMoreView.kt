package com.example.teacherstartrace.widget

import android.content.Context
import android.view.View
import android.widget.RelativeLayout
import com.example.teacherstartrace.R

/**
 * ClassName:LoadMoreView
 * Created bu ZhangBo at 2020/6/20
 * Describe:
 **/
class LoadMoreView(context: Context?) : RelativeLayout(context) {
    init {
        View.inflate(context, R.layout.view_loadmore,this)
    }

}