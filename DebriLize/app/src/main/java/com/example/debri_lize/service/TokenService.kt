package com.example.debri_lize.service

import android.util.Log
import com.example.debri_lize.utils.RetrofitInterface
import com.example.debri_lize.data.auth.Token
import com.example.debri_lize.base.BaseResponse
import com.example.debri_lize.utils.getJwt
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

        tokenService.token(getJwt()!!, refreshToken).enqueue(object: Callback<BaseResponse<Token>> {
            //응답이 왔을 때 처리

            override fun onResponse(call: Call<BaseResponse<Token>>, response: Response<BaseResponse<Token>>) {
                Log.d("token", "response")
                val resp: BaseResponse<Token> = response.body()!!
                Log.d("tokenCode", resp.code.toString())
                when(val code = resp.code){
                    //API code값 사용
                    200->tokenView.onTokenSuccess(code, resp.result)
                    else-> tokenView.onTokenFailure(code)

                }
            }

            //실패했을 때 처리
            override fun onFailure(call: Call<BaseResponse<Token>>, t: Throwable) {
                Log.d("tokenFail", t.toString())
            }

        })
    }
}