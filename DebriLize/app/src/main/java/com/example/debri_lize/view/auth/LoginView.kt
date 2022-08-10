package com.example.debri_lize.view.auth

import com.example.debri_lize.data.auth.User

interface LoginView {
    fun onLoginSuccess(code:Int, result: User?)
    fun onLoginFailure(code:Int, message : String)
}