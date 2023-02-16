package com.debri_main.debri.service

import android.util.Log
import com.debri_main.debri.utils.RetrofitInterface
import com.debri_main.debri.data.auth.User
import com.debri_main.debri.data.auth.UserLogin
import com.debri_main.debri.data.auth.UserSignup
import com.debri_main.debri.base.BaseResponse
import com.debri_main.debri.data.auth.Email
import com.debri_main.debri.view.auth.LoginView
import com.debri_main.debri.view.auth.SignUpView
import com.debri_main.debri.utils.getRetrofit
import com.debri_main.debri.view.auth.EmailView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AuthService {
    private lateinit var signUpView: SignUpView
    private lateinit var loginView: LoginView
    private lateinit var emailView: EmailView

    fun setSignUpView(signUpView: SignUpView){
        this.signUpView = signUpView
    }

    fun setLoginView(loginView: LoginView){
        this.loginView = loginView
    }

    fun setEmailView(emailView: EmailView){
        this.emailView = emailView
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

    //인증 코드 받기
    fun getCode(emailAddress: String){
        Log.d("email", "email")
        //서비스 객체 생성
        val authService = getRetrofit().create(RetrofitInterface::class.java)

        authService.getCode(emailAddress).enqueue(object: Callback<BaseResponse<Email>> {
            //응답이 왔을 때 처리
            override fun onResponse(call: Call<BaseResponse<Email>>, response: Response<BaseResponse<Email>>) {
                Log.d("emailSuccess", "response")
                Log.d("response", response.body().toString())
                val resp: BaseResponse<Email> = response.body()!!
                Log.d("emailcode", resp.code.toString())
                when(val code = resp.code){
                    //API code값 사용
                    200->emailView.onEmailSuccess(code, resp.result)
                    else-> emailView.onEmailFailure(code, resp.message)

                }
            }

            //실패했을 때 처리
            override fun onFailure(call: Call<BaseResponse<Email>>, t: Throwable) {
                Log.d("emailfail", t.toString())
            }

        })
    }
}