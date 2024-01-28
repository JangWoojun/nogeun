package com.example.ignis.detail

data class ReadResponse(
    val id: Int,
    val title: String,
    val image_url: String,
    val user: String,
    val createAt: String,
    val count: Int,
    val comments: List<Comment>
)

data class Comment(
    val author_id: Int,
    val author_name: String,
    val author_profile_url: String,
    val content: String,
    val created_date: String
)
