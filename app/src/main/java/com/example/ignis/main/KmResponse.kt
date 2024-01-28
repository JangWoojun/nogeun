package com.example.ignis.main

data class KmResponse(
    val id: Int,
    val title: String,
    val image_url: String,
    val user: String,
    val create_at: String,
    val count: Int,
    val x: Double,
    val y: Double
)