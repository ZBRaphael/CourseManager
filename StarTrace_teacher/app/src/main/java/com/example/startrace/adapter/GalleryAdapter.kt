package com.example.startrace.adapter

import android.content.Intent
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.startrace.ui.activity.CourseInfoActivity
import com.example.startrace.widget.GalleryItemView
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
        val itemView = holder.itemView
        itemView.setOnClickListener{
            val intent = Intent(it.context,
                CourseInfoActivity::class.java)
            intent.putExtra("from","select")
            it.context.startActivity(intent)
        }
    }
}