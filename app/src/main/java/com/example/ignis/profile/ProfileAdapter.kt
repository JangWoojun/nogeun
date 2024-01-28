package com.example.ignis.profile

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ignis.R
import com.example.ignis.userInfo.Feed
import de.hdodenhof.circleimageview.CircleImageView

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

    override fun onBindViewHolder(holder: ProfileAdapter.ProfileViewHolder, position: Int) {
        itemList[position].apply {
            holder.title.text = title
            holder.name.text = author_name
            holder.date.text = created_date
            holder.like.text = "$likes 개"
        }
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    inner class ProfileViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = view.findViewById(R.id.tv_profile_item_title)
        val name: TextView = view.findViewById(R.id.tv_profile_item_name)
        val date: TextView = view.findViewById(R.id.tv_profile_item_date)
        val like: TextView = view.findViewById(R.id.tv_profile_item_like_cnt)
    }
}