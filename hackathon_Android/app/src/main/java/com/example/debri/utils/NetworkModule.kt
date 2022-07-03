package com.example.debri.utils

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val BASE_URL = "http://54.180.180.217/api/" //연결할 서버의 주소
//** url주소 작성시 ~.com/이라고 적었다면 interface에선 /users가 아닌 users사용

//retrofit 객체 생성
fun getRetrofit(): Retrofit {
    val retrofit = Retrofit.Builder().baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create()).build()

    return retrofit
}