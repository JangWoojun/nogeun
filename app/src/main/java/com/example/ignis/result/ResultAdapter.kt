package com.example.ignis.result

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ignis.R
import com.example.ignis.main.Feed

class ResultAdapter(private val itemList: List<Feed>, private val context: Context) : RecyclerView.Adapter<ResultAdapter.ResultViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ResultAdapter.ResultViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.result_item,parent,false)
        return ResultViewHolder(view)
    }

    override fun onBindViewHolder(holder: ResultAdapter.ResultViewHolder, position: Int) {
        itemList[position].apply {
            Glide
                .with(context)
                .load(image_url)
                .into(holder.img)
            holder.title.text = title
            holder.name.text = user
            holder.date.text = createAt
            holder.like.text = "$count 개"
        }
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    inner class ResultViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val img: ImageView = view.findViewById(R.id.iv_result_item)
        val title: TextView = view.findViewById(R.id.tv_result_item_title)
        val name: TextView = view.findViewById(R.id.tv_result_item_name)
        val date: TextView = view.findViewById(R.id.tv_result_item_date)
        val like: TextView = view.findViewById(R.id.tv_result_item_like_cnt)
    }
}