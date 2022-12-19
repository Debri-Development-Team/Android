package com.example.debri_lize.view.auth

import com.example.debri_lize.data.auth.Email

interface EmailView {
    fun onEmailSuccess(code:Int, result: Email)
    fun onEmailFailure(code:Int, message : String)
}