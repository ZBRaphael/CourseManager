package com.example.teacherstartrace.adapter

import android.content.Intent
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.view.menu.MenuView
import androidx.core.content.ContextCompat.startActivity
import org.jetbrains.anko.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.teacherstartrace.model.CourseBean
import com.example.teacherstartrace.ui.activity.CourseInfoActivity
import com.example.teacherstartrace.ui.activity.MainActivity
import com.example.teacherstartrace.widget.HomeItemView

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
//        Log.v("return", list.size.toString())
        return list.size
    }

    override fun onBindViewHolder(holder: HomeHolder, position: Int) {
        val data = list[position]
        val itemView = holder.itemView as HomeItemView
        itemView.setdata(data)
        itemView.setOnClickListener{
            val intent = Intent(it.context,
                CourseInfoActivity::class.java)
            intent.putExtra("from", "home");
            intent.putExtra("time", data.courseDate);
            intent.putExtra("local", data.courseLocation);
            intent.putExtra("cost",data.courseCostHour)
            intent.putExtra("num_stu",data.Enrollments.size);
            intent.putExtra("title",data.interest);
            intent.putExtra("des",data.courseDescription);
            intent.putExtra("courseId",data.courseId.toString())
            intent.putExtra("session",sessionId)
            var count = 0
            data.Enrollments.forEach {
                if(it.isAttend==1)
                    count += 1
            }
            intent.putExtra("num_sign",count)
            it.context.startActivity(intent)
        }
    }


}