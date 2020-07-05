package com.example.teacherstartrace.util

import androidx.fragment.app.Fragment
import com.example.teacherstartrace.R
import com.example.teacherstartrace.ui.fragment.GalleryFragment
import com.example.teacherstartrace.ui.fragment.HomeFragment
import com.example.teacherstartrace.ui.fragment.UserFragment

/**
 * ClassName:FragmentUtil
 * Created bu ZhangBo at 2020/6/16
 * Describe:管理Fragment
 **/
class FragmentUtil private constructor(){//私有化构造方法
    val homeFragment by lazy { HomeFragment() }
    val galleryFragment by lazy { GalleryFragment() }
    val userFragment by lazy { UserFragment() }

    companion object{
    val fragmentUtil by lazy { FragmentUtil() }
}

    /**
     * 根据tableId获取fragment
     */
    fun getFragment(tableId:Int): Fragment? {
        when(tableId){
            R.id.tab_home->return homeFragment
            R.id.tab_gallery->return galleryFragment
            R.id.tab_slideshow->return userFragment
        }
        return null
    }
}