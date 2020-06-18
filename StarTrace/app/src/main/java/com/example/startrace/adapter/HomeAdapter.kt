package com.example.startrace.adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.startrace.widget.HomeItemView

/**
 * ClassName:HomeAdapter
 * Created bu ZhangBo at 2020/6/16
 * Describe:
 **/
class HomeAdapter:RecyclerView.Adapter<HomeAdapter.HomeHolder>() {
    class HomeHolder(itemView:View):RecyclerView.ViewHolder(itemView){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeHolder {
        return HomeHolder(HomeItemView(parent.context))
    }

    override fun getItemCount(): Int {
        return 20
    }

    override fun onBindViewHolder(holder: HomeHolder, position: Int) {

    }
}