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
    val authorId: Int,
    val authorName: String,
    val authorProfileUrl: String,
    val content: String,
    val createdDate: String
)
