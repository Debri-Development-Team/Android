package com.example.debri_lize.service

import android.util.Log
import com.example.debri_lize.utils.RetrofitInterface
import com.example.debri_lize.data.auth.User
import com.example.debri_lize.data.auth.UserLogin
import com.example.debri_lize.data.auth.UserSignup
import com.example.debri_lize.base.BaseResponse
import com.example.debri_lize.view.auth.LoginView
import com.example.debri_lize.view.auth.SignUpView
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

    fun signUp(user: UserSignup){
        Log.d("user", user.toString())
        //서비스 객체 생성
        val authService = getRetrofit().create(RetrofitInterface::class.java)

        authService.signUp(user).enqueue(object: Callback<BaseResponse<User>> {
            //응답이 왔을 때 처리
            override fun onResponse(call: Call<BaseResponse<User>>, response: Response<BaseResponse<User>>) {
                Log.d("signup", "response")
                Log.d("signupResponse", response.body().toString())
                val resp: BaseResponse<User> = response.body()!!
                Log.d("signupCode", resp.code.toString())
                when(resp.code){
                    //API code값 사용
                    200->signUpView.onSignUpSuccess(resp.code)
                    else->signUpView.onSignUpFailure(resp.code)
                }
            }
            //실패했을 때 처리
            override fun onFailure(call: Call<BaseResponse<User>>, t: Throwable) {

            }

        })
    }

    fun login(user: UserLogin){
        Log.d("login", "login")
        //서비스 객체 생성
        val authService = getRetrofit().create(RetrofitInterface::class.java)

        authService.login(user).enqueue(object: Callback<BaseResponse<User>> {
            //응답이 왔을 때 처리
            override fun onResponse(call: Call<BaseResponse<User>>, response: Response<BaseResponse<User>>) {
                Log.d("loginSuccess", "response")
                Log.d("response", response.body().toString())
                val resp: BaseResponse<User> = response.body()!!
                Log.d("code", resp.code.toString())
                when(val code = resp.code){
                    //API code값 사용
                    200->loginView.onLoginSuccess(code, resp.result)
                    else-> loginView.onLoginFailure(code, resp.message)

                }
            }

            //실패했을 때 처리
            override fun onFailure(call: Call<BaseResponse<User>>, t: Throwable) {
                Log.d("loginfail", t.toString())
            }

        })
    }
}