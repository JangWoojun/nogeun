package com.example.ignis.userInfo

data class UserInfoResponse(
    val id: Long,
    val user_name: String,
    val age: Int,
    val profile_url: String,
    val point: Int,
    val email: String,
    val feeds: List<Feed>
)

data class Feed(
    val id: Long,
    val title: String,
    val image_url: String,
    val author_name: String,
    val created_date: String,
    val likes: Int,
)