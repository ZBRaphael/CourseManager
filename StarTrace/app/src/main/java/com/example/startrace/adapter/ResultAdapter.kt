package com.example.startrace.adapter

import android.content.Intent
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.startrace.model.CourseBean
import com.example.startrace.ui.activity.CourseInfoActivity
import com.example.startrace.widget.HomeItemView
import com.example.startrace.widget.ResultItemView

/**
 * ClassName:ResultAdapter
 * Created bu ZhangBo at 2020/6/20
 * Describe:
 **/
class ResultAdapter: RecyclerView.Adapter<ResultAdapter.ResultHolder>() {
    private var list = ArrayList<CourseBean>()
    var sessionId = ""
    fun upDataList(list:List<CourseBean>, sessionId:String ){
        this.list.clear()
        this.list.addAll(list)
        notifyDataSetChanged()
        this.sessionId = sessionId
    }
    class ResultHolder(itemView:View):RecyclerView.ViewHolder(itemView){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResultHolder {

        return ResultHolder(ResultItemView(parent.context))
    }

    override fun getItemCount(): Int {
//        Log.v("return", list.size.toString())
        return list.size
    }

    override fun onBindViewHolder(holder: ResultHolder, position: Int) {
        val data = list[position]
        val itemView = holder.itemView as ResultItemView
        itemView.setdata(data)
        itemView.setOnClickListener{
            val intent = Intent(it.context,
                CourseInfoActivity::class.java)
            intent.putExtra("from", "result");
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