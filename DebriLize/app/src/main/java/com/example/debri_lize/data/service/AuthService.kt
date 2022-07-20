package com.example.debri_lize.data.service

import android.util.Log
import com.example.debri_lize.data.RetrofitInterface
import com.example.debri_lize.data.User
import com.example.debri_lize.data.response.AuthResponse
import com.example.debri_lize.data.view.LoginView
import com.example.debri_lize.data.view.SignUpView
import com.example.debri_lize.utils.getRetrofit
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

        authService.signUp(user).enqueue(object: Callback<AuthResponse> {
            //응답이 왔을 때 처리
            override fun onResponse(call: Call<AuthResponse>, response: Response<AuthResponse>) {
                val resp:AuthResponse = response.body()!!
                when(resp.code){
                    //API code값 사용
                    //200->signUpView.onSignUpSuccess(resp.result)
                    else->signUpView.onSignUpFailure()
                }
            }
            //실패했을 때 처리
            override fun onFailure(call: Call<AuthResponse>, t: Throwable) {

            }

        })
    }

    fun login(user: User){
        Log.d("login", "login")
        //서비스 객체 생성
        val authService = getRetrofit().create(RetrofitInterface::class.java)

        authService.login(user).enqueue(object: Callback<AuthResponse> {
            //응답이 왔을 때 처리

            override fun onResponse(call: Call<AuthResponse>, response: Response<AuthResponse>) {
                Log.d("loginSuccess", "response")
                Log.d("response.body", response.body().toString())
                val resp:AuthResponse = response.body()!!
                Log.d("code", resp.code.toString())
                when(val code = resp.code){
                    //API code값 사용
                    200->loginView.onLoginSuccess(code, resp.result)
                    else-> loginView.onLoginFailure(code, resp.message)

                }
            }

            //실패했을 때 처리
            override fun onFailure(call: Call<AuthResponse>, t: Throwable) {
                Log.d("loginfail", t.toString())
            }

        })
    }
}