package com.example.debri.data

import com.example.debri.data.response.AuthResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface RetrofitInterface {

    @POST("user/create")
    fun signUp(@Body user : User): Call<AuthResponse>

    @POST("user/login")
    fun login(@Body user : User): Call<AuthResponse>

}