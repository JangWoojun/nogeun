package com.example.ignis.main

data class KmResponse(
    val id: Int,
    val title: String,
    val imageUrl: String,
    val user: String,
    val createAt: String,
    val count: Int,
    val x: Int,
    val y: Int
)