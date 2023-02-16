package com.debri_main.debri.view.auth

import com.debri_main.debri.data.auth.User

interface LoginView {
    fun onLoginSuccess(code:Int, result: User?)
    fun onLoginFailure(code:Int, message : String)
}