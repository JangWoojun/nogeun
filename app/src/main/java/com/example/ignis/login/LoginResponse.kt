package com.example.ignis.login

data class LoginResponse(
    val access_token: String,
    val is_signed_up: Boolean,
)
