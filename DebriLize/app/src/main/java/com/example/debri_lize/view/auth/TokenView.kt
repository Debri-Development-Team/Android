package com.example.debri_lize.view.auth

import com.example.debri_lize.response.Token

interface TokenView {
    fun onTokenSuccess(code:Int, result : Token?)
    fun onTokenFailure(code:Int)
}