package com.example.ignis.network

import com.example.ignis.detail.LikeResponse
import com.example.ignis.login.LoginRequest
import com.example.ignis.login.LoginResponse
import com.example.ignis.main.KmResponse
import com.example.ignis.detail.ReadResponse
import com.example.ignis.main.SearchResponse
import com.example.ignis.signup.SignupRequest
import com.example.ignis.userInfo.UserInfoResponse
import com.example.ignis.main.WriteRequest
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

interface AllApi {
    @POST("/user/login") //로그인
    fun login(
        @Body request: LoginRequest
    ): Call<LoginResponse>

    @PATCH("/user/signup") //회원가입
    fun signup(
        @Header("Authorization") authorization: String,
        @Body request: SignupRequest
    ): Call<Void>

    @GET("/user") //유저정보
    fun userInfo(
        @Header("Authorization") authorization: String,
        ): Call<UserInfoResponse>

    @Multipart
    @POST("/feed") // 게시물 생성
    fun createdFeed(
        @Header("Authorization") authorization: String,
        @Part("title") title: String,
        @Part file: MultipartBody.Part,
        @Part("request") request: WriteRequest
    ): Call<Void>

    // 게시물 상세 조회
    @GET("/feed/{feedId}")
    fun detailedRead(
        @Header("Authorization") authorization: String,
        @Path("feedId") feedId: Long,
    ): Call<ReadResponse>

    @GET("/feed") // 게시물 검색
    fun getFeed(
        @Header("Authorization") authorization: String,
        @Query("title") title: String
    ): Call<SearchResponse>

    @POST("/feed/count/{feedId}") // 좋아요
    fun addLike(
        @Header("Authorization") authorization: String,
        @Path("feedId") feedId: Long
    ): Call<LikeResponse>

    @POST("/comment/{feedId}?comment") // 댓글 달기
    fun comment(
        @Header("Authorization") authorization: String,
        @Path("feedId") feedId: Long,
        @Query("comment") comment: String,
    ): Call<Void>

    @GET("/feed/all") // 1km 이내 게시물 조회
    fun km(
        @Header("Authorization") authorization: String,
        @Query("x") x: Double,
        @Query("y") y: Double,
    ): Call<List<KmResponse>>

}