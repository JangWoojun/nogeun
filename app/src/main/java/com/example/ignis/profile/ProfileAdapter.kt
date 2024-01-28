package com.example.ignis.profile

import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ignis.R
import com.example.ignis.userInfo.Feed
import de.hdodenhof.circleimageview.CircleImageView
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class ProfileAdapter(private val itemList: List<Feed>, private val context: Context) :
RecyclerView.Adapter<ProfileAdapter.ProfileViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ProfileAdapter.ProfileViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.profile_item,parent,false)
        return ProfileViewHolder(view)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: ProfileAdapter.ProfileViewHolder, position: Int) {
        itemList[position].apply {
            Glide
                .with(context)
                .load(image_url)
                .into(holder.img)
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS")
            val parsedDateTime = LocalDateTime.parse(created_date, formatter)
            val formattedDate = parsedDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
            holder.title.text = title
            holder.name.text = author_name
            holder.date.text = formattedDate
            holder.like.text = "$likes 개"
        }
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    inner class ProfileViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val img: ImageView = view.findViewById(R.id.iv_profile_item)
        val title: TextView = view.findViewById(R.id.tv_profile_item_title)
        val name: TextView = view.findViewById(R.id.tv_profile_item_name)
        val date: TextView = view.findViewById(R.id.tv_profile_item_date)
        val like: TextView = view.findViewById(R.id.tv_profile_item_like_cnt)
    }
}