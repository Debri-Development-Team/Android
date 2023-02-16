package com.debri_main.debri.view.auth

interface SignUpView {
    fun onSignUpSuccess(code : Int)
    fun onSignUpFailure(code : Int)
}