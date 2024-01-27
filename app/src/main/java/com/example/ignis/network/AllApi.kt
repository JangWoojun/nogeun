package com.example.ignis.network

import com.example.ignis.detail.LikeResponse
import com.example.ignis.login.LoginRequest
import com.example.ignis.login.LoginResponse
import com.example.ignis.main.KmResponse
import com.example.ignis.detail.ReadResponse
import com.example.ignis.main.SearchResponse
import com.example.ignis.signup.SignupRequest
import com.example.ignis.userInfo.UserInfoResponse
import com.example.ignis.main.WriteResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
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
        @Body request: SignupRequest
    ): Call<Void>

    @GET("/user") //유저정보
    fun userInfo(
    ): Call<UserInfoResponse>

    @POST("/feed") // 게시물 생성
    fun createdFeed(
        @Part("title") title: String,
        @Part("description") description: String,
        @Part("request") request: WriteResponse
    ): Call<Void>

    @Multipart // 게시물 상세 조회
    @GET("/feed/{feedId}")
    fun detailedRead(
        @Path("feedId") feedId: Long,
    ): Call<ReadResponse>

    @GET("/feed") // 게시물 검색
    fun getFeed(
        @Query("title") title: String
    ): Call<SearchResponse>

    @POST("/feed/add/count/{feedId}") // 좋아요
    fun addLike(
        @Query("feedId") feedId: Long
    ): Call<LikeResponse>

    @POST("/feed/delete/count/{feedId}") // 좋아요 취소
    fun deleteLike(
        @Query("feedId") feedId: Long
    ): Call<LikeResponse>

    @POST("/comment/{feedId}?comment") // 댓글 달기
    fun comment(
        @Query("feedId") feedId: Long
    ): Call<String>

    @GET("/feed/all?x=0&y=0") // 1km 이내 게시물 조회
    fun km(
        @Query("x") x: Double,
        @Query("y") y: Double,
    ): Call<List<KmResponse>>

}