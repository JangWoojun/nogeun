package com.example.ignis.detail

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ignis.R

class DetailAdapter(private val itemList: List<Comment>, private val context: Context) :
    RecyclerView.Adapter<DetailAdapter.DetailViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DetailAdapter.DetailViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.comment_item, parent, false)
        return DetailViewHolder(view)
    }

    override fun onBindViewHolder(holder: DetailAdapter.DetailViewHolder, position: Int) {
        itemList[position].apply {
            Glide
                .with(context)
                .load(authorProfileUrl)
                .into(holder.profile)
            holder.name.text = authorName
            holder.date.text = createdDate
            holder.content.text = content
        }

    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    inner class DetailViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val profile: ImageView = view.findViewById(R.id.iv_detail_item_profile)
        val name: TextView = view.findViewById(R.id.tv_detail_item_name)
        val date: TextView = view.findViewById(R.id.tv_detail_item_date)
        val content: TextView = view.findViewById(R.id.tv_detail_item_content)
    }

}