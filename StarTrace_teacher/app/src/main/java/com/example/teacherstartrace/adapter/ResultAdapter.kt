package com.example.teacherstartrace.adapter

import android.content.Intent
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.teacherstartrace.ui.activity.CourseInfoActivity
import com.example.teacherstartrace.widget.HomeItemView
import com.example.teacherstartrace.widget.ResultItemView

/**
 * ClassName:ResultAdapter
 * Created bu ZhangBo at 2020/6/20
 * Describe:
 **/
class ResultAdapter: RecyclerView.Adapter<ResultAdapter.ResultHolder>() {
    class ResultHolder(itemView: View): RecyclerView.ViewHolder(itemView){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResultHolder {

        return ResultHolder(
            ResultItemView(parent.context)
        )
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
    override fun onBindViewHolder(holder: ResultHolder, position: Int) {
        val itemView = holder.itemView
        itemView.setOnClickListener{
            val intent = Intent(it.context,
                CourseInfoActivity::class.java)
            intent.putExtra("from","result")
            it.context.startActivity(intent)
        }
    }

}