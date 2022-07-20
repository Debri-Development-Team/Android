package com.example.debri_lize.data.view

import com.example.debri_lize.data.response.Result

interface SignUpView {
    fun onSignUpSuccess(result : Result)
    fun onSignUpFailure()
}