package com.example.startrace.adapter

import android.content.Intent
import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.startrace.model.CourseBean
import com.example.startrace.ui.activity.CourseInfoActivity
import com.example.startrace.widget.GalleryItemView
import com.example.startrace.widget.HomeItemView
import java.io.IOException

/**
 * ClassName:GalleryAdapter
 * Created bu ZhangBo at 2020/6/19
 * Describe:
 **/
class GalleryAdapter:RecyclerView.Adapter<GalleryAdapter.GalleryHolder>() {
    private var list = ArrayList<CourseBean>()
    var sessionId = ""
    fun upDataList(list:List<CourseBean>, sessionId:String ){
        this.list.clear()
        this.list.addAll(list)
        notifyDataSetChanged()
        this.sessionId = sessionId
        Log.v("updata",list.size.toString())
    }
    class GalleryHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): GalleryHolder {
        return GalleryHolder(HomeItemView(parent.context))
    }

    override fun getItemCount(): Int {
        Log.v("return",list.size.toString())
        return list.size
    }

    override fun onBindViewHolder(holder: GalleryHolder, position: Int) {
        val data = list[position]
        val itemView = holder.itemView as HomeItemView
        itemView.setdata(data)
        itemView.setOnClickListener{
            val intent = Intent(it.context,
                CourseInfoActivity::class.java)
            intent.putExtra("from","select")
            intent.putExtra("time", data.courseDate);
            intent.putExtra("local", data.courseLocation);
            intent.putExtra("cost",data.courseCostHour)
            intent.putExtra("num_stu",data.Enrollments.size);
            intent.putExtra("title",data.interest);
            intent.putExtra("des",data.courseDescription);
            intent.putExtra("session",sessionId)
            it.context.startActivity(intent)
        }
    }
}