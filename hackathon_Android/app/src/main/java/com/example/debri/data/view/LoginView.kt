package com.example.debri.data.view

import com.example.debri.data.response.Result

interface LoginView {
    fun onLoginSuccess(code:Int, result : Result)
    fun onLoginFailure()
}