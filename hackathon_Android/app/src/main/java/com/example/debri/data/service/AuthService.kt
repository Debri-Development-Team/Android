package com.example.debri.data.service

import android.util.Log
import com.example.debri.data.RetrofitInterface
import com.example.debri.data.User
import com.example.debri.data.response.AuthResponse
import com.example.debri.data.view.LoginView
import com.example.debri.data.view.SignUpView
import com.example.debri.utils.getRetrofit
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AuthService {
    private lateinit var signUpView: SignUpView
    private lateinit var loginView: LoginView

    fun setSignUpView(signUpView: SignUpView){
        this.signUpView = signUpView
    }

    fun setLoginView(loginView: LoginView){
        this.loginView = loginView
    }

    fun signUp(user: User){
        //서비스 객체 생성
        val authService = getRetrofit().create(RetrofitInterface::class.java)
        //AuthRetrofitInterface에서 Call을 했다면, 받는 부분도 있어야함 : enqueue()
        authService.signUp(user).enqueue(object: Callback<AuthResponse> {
            //응답이 왔을 때 처리
            override fun onResponse(call: Call<AuthResponse>, response: Response<AuthResponse>) {
                val resp:AuthResponse = response.body()!!
                Log.d("code", resp.code.toString())
                Log.d("isSuccess", resp.isSuccess.toString())
                when(resp.code){
                    //API code값 사용
                    200->signUpView.onSignUpSuccess(resp.result)
                    else->signUpView.onSignUpFailure()
                }
            }
            //실패했을 때 처리
            override fun onFailure(call: Call<AuthResponse>, t: Throwable) {
                Log.d("nanana1", t.toString())
            }

        })
    }

    fun login(user: User){
        //서비스 객체 생성
        val authService = getRetrofit().create(RetrofitInterface::class.java)

        //AuthRetrofitInterface에서 Call을 했다면, 받는 부분도 있어야함 : enqueue()
        authService.login(user).enqueue(object: Callback<AuthResponse> {
            //응답이 왔을 때 처리

            override fun onResponse(call: Call<AuthResponse>, response: Response<AuthResponse>) {
                Log.d("isSuccess2", "success")
                val resp:AuthResponse = response.body()!!
                Log.d("code2", resp.code.toString())
                Log.d("result", resp.result.toString())
                when(val code = resp.code){
                    //API code값 사용
                    200->loginView.onLoginSuccess(code, resp.result)
                    else-> loginView.onLoginFailure()

                }
            }
            //실패했을 때 처리
            override fun onFailure(call: Call<AuthResponse>, t: Throwable) {
                Log.d("loginfail", t.toString())
            }

        })
    }
}