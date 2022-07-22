package com.example.debri_lize.data

import com.example.debri_lize.data.response.AuthResponse
import com.example.debri_lize.data.response.PostResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface RetrofitInterface {

    //회원가입
    @POST("api/user/signUp")
    fun signUp(@Body user: UserSignup): Call<AuthResponse>

    //로그인
    @POST("api/auth/login")
    fun login(@Body user : UserLogin): Call<AuthResponse>

    //게시물 생성
    @POST("api/post/create")
    fun createPost(@Body post : Post): Call<PostResponse>

    //

}