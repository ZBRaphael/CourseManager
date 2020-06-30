package com.example.startrace.adapter

import android.content.Intent
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.view.menu.MenuView
import androidx.core.content.ContextCompat.startActivity
import org.jetbrains.anko.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.startrace.model.CourseBean
import com.example.startrace.ui.activity.CourseInfoActivity
import com.example.startrace.ui.activity.MainActivity
import com.example.startrace.widget.HomeItemView

/**
 * ClassName:HomeAdapter
 * Created bu ZhangBo at 2020/6/16
 * Describe:
 **/
class HomeAdapter:RecyclerView.Adapter<HomeAdapter.HomeHolder>() {
    private var list = ArrayList<CourseBean>()
    var sessionId = ""
    fun upDataList(list:List<CourseBean>, sessionId:String ){
        this.list.clear()
        this.list.addAll(list)
        notifyDataSetChanged()
        this.sessionId = sessionId
    }
    class HomeHolder(itemView:View):RecyclerView.ViewHolder(itemView){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeHolder {

        return HomeHolder(HomeItemView(parent.context))
    }

    override fun getItemCount(): Int {
        Log.v("homereturn", list.size.toString())
        return list.size
    }

    override fun onBindViewHolder(holder: HomeHolder, position: Int) {
        val data = list[position]
        val itemView = holder.itemView as HomeItemView
        itemView.setdata(data)
        itemView.setOnClickListener{
            val intent = Intent(it.context,
                CourseInfoActivity::class.java)
            intent.putExtra("from", "sigin");
            intent.putExtra("time", data.courseDate);
            intent.putExtra("local", data.courseLocation);
            intent.putExtra("cost",data.courseCostHour)
            intent.putExtra("num_stu",data.Enrollments.size);
            intent.putExtra("title",data.interest);
            intent.putExtra("des",data.courseDescription);
            intent.putExtra("session",sessionId)
            Log.v("123",data.courseId.toString())
            intent.putExtra("courseId",data.courseId.toString())
            it.context.startActivity(intent)
        }
    }


}