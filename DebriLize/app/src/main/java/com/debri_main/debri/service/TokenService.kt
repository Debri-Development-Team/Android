package com.debri_main.debri.service

import android.util.Log
import com.debri_main.debri.utils.RetrofitInterface
import com.debri_main.debri.data.auth.Token
import com.debri_main.debri.base.BaseResponse
import com.debri_main.debri.utils.getJwt
import com.debri_main.debri.utils.getRetrofit
import com.debri_main.debri.view.auth.TokenView
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