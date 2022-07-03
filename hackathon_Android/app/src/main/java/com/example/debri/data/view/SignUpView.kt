package com.example.debri.data.view

import com.example.debri.data.response.Result

interface SignUpView {
    fun onSignUpSuccess(result : Result)
    fun onSignUpFailure()
}