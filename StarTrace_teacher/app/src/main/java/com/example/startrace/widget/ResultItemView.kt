package com.example.startrace.widget

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.RelativeLayout
import com.example.startrace.R

/**
 * ClassName:ResultItemView
 * Created bu ZhangBo at 2020/6/20
 * Describe:
 **/

class ResultItemView: RelativeLayout {
    constructor(context: Context):super(context)
    constructor(context: Context, attrs: AttributeSet?):super(context,attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr:Int):super(context,attrs,defStyleAttr)
    /**
     * 初始化的方法
     */
    init {
        View.inflate(context, R.layout.item_result,this)
    }

}