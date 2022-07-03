package com.example.debri.utils

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class ApplicationClass : Application() {
    companion object{
        const val X_ACCESS_TOKEN: String = "X-ACCESS-TOKEN"         // JWT Token Key
        const val TAG: String = "TEMPLATE-APP"                      // Log, SharedPreference
        const val APP_DATABASE = "$TAG-DB"

        const val DEV_URL: String = "http://54.180.180.217/api/"       // 테스트 서버 주소
        const val PROD_URL: String = "http://54.180.180.217/api/"    // 실서버 주소
        const val BASE_URL: String = DEV_URL

        lateinit var mSharedPreferences: SharedPreferences
        lateinit var retrofit: Retrofit
    }

    override fun onCreate() {
        super.onCreate()

        val client: OkHttpClient = OkHttpClient.Builder()
            .readTimeout(30000, TimeUnit.MILLISECONDS)
            .connectTimeout(30000, TimeUnit.MILLISECONDS)
            .addNetworkInterceptor(XAccessTokenInterceptor()) // JWT 자동 헤더 전송
            .build()

        //레트로핏
        retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        //sharedPreference
        mSharedPreferences = applicationContext.getSharedPreferences(TAG, Context.MODE_PRIVATE)
    }
}