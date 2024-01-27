package com.example.ignis.network

import com.example.ignis.login.LoginRequest
import com.example.ignis.login.LoginResponse
import com.example.ignis.signup.SignupRequest
import com.example.ignis.userInfo.UserInfoResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST

interface AllApi {
    @POST("/user/login") //로그인
    fun login(
        @Body request: LoginRequest
    ): Call<LoginResponse>

    @PATCH("/user/signup") //회원가입
    fun signup(
        @Body request: SignupRequest
    ): Call<Void>

    @GET("/user") //유저정보
    fun userInfo(
    ): Call<UserInfoResponse>
}