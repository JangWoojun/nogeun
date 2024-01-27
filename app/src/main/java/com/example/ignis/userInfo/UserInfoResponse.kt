package com.example.ignis.userInfo

data class UserInfoResponse(
    val id: Long,
    val user_name: String,
    val age: Int,
    val profile_url: String,
    val point: Int,
)
