package com.example.ignis.detail

import android.content.Context
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ignis.R
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

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

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: DetailAdapter.DetailViewHolder, position: Int) {
        itemList[position].apply {
            Glide
                .with(context)
                .load(author_profile_url)
                .into(holder.profile)

            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS")
            val parsedDateTime = LocalDateTime.parse(created_date, formatter)
            val formattedDate = parsedDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
            holder.name.text = author_name
            holder.date.text = formattedDate
            holder.content.text = content
            Log.d("TEST","zz $created_date")
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