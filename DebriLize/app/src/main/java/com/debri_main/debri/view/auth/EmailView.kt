package com.debri_main.debri.view.auth

import com.debri_main.debri.data.auth.Email

interface EmailView {
    fun onEmailSuccess(code:Int, result: Email)
    fun onEmailFailure(code:Int, message : String)
}