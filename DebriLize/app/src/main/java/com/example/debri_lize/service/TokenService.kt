package com.example.debri_lize.service

import android.util.Log
import com.example.debri_lize.data.RetrofitInterface
import com.example.debri_lize.data.auth.UserLogin
import com.example.debri_lize.data.auth.UserSignup
import com.example.debri_lize.response.AuthResponse
import com.example.debri_lize.response.TokenResponse
import com.example.debri_lize.view.auth.LoginView
import com.example.debri_lize.view.auth.SignUpView
import com.example.debri_lize.utils.getRetrofit
import com.example.debri_lize.view.auth.TokenView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TokenService {
    private lateinit var tokenView: TokenView

    fun setTokenView(tokenView: TokenView){
        this.tokenView = tokenView
    }

    fun token(refreshToken: String){
        Log.d("token", "enter")
        //서비스 객체 생성
        val tokenService = getRetrofit().create(RetrofitInterface::class.java)

        tokenService.token(refreshToken).enqueue(object: Callback<TokenResponse> {
            //응답이 왔을 때 처리

            override fun onResponse(call: Call<TokenResponse>, response: Response<TokenResponse>) {
                Log.d("token", "response")
                val resp:TokenResponse = response.body()!!
                Log.d("tokenCode", resp.code.toString())
                when(val code = resp.code){
                    //API code값 사용
                    200->tokenView.onTokenSuccess(code, resp.result)
                    else-> tokenView.onTokenFailure(code)

                }
            }

            //실패했을 때 처리
            override fun onFailure(call: Call<TokenResponse>, t: Throwable) {
                Log.d("tokenFail", t.toString())
            }

        })
    }
}