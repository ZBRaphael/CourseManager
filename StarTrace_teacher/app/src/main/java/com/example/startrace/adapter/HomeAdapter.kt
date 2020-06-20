package com.example.startrace.adapter

import android.content.Intent
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.view.menu.MenuView
import androidx.core.content.ContextCompat.startActivity
import org.jetbrains.anko.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.startrace.ui.activity.CourseInfoActivity
import com.example.startrace.ui.activity.MainActivity
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

    override fun getItemViewType(position: Int): Int {
        if(position == 20){
            //最后一条
            return 1
        }
        else{
            return 0
        }
    }
    override fun onBindViewHolder(holder: HomeHolder, position: Int) {
        val itemView = holder.itemView
        itemView.setOnClickListener{
            val intent = Intent(it.context,
                CourseInfoActivity::class.java)
            intent.putExtra("from","login")
            it.context.startActivity(intent)
        }
    }

}