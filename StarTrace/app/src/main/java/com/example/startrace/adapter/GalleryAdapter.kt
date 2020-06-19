package com.example.startrace.adapter

import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.startrace.R
import com.example.startrace.util.URLProviderUtils
import com.example.startrace.widget.GalleryItemView
import com.example.startrace.widget.HomeItemView
import kotlinx.android.synthetic.main.fragment_home.*
import okhttp3.*
import java.io.IOException

/**
 * ClassName:GalleryAdapter
 * Created bu ZhangBo at 2020/6/19
 * Describe:
 **/
class GalleryAdapter:RecyclerView.Adapter<GalleryAdapter.GalleryHolder>() {
    class GalleryHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): GalleryAdapter.GalleryHolder {
        return GalleryHolder(GalleryItemView(parent.context))
    }

    override fun getItemCount(): Int {
        return 20
    }

    override fun onBindViewHolder(holder: GalleryHolder, position: Int) {

    }
}