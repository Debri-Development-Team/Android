package com.example.debri_lize.data.view.auth

import com.example.debri_lize.data.response.Result

interface LoginView {
    fun onLoginSuccess(code:Int, result : Result?)
    fun onLoginFailure(code:Int, message : String)
}