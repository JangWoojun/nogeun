package com.example.ignis.main
data class SearchResponse(
    val count: Long,
    val feeds: List<Feed>
)

data class Feed(
    val id: Long,
    val title: String,
    val imageUrl: String,
    val user: String,
    val createAt: String,
    val count: Long,
    val x: Double,
    val y: Double
)