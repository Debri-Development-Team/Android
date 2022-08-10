package com.example.debri_lize.view.auth

import com.example.debri_lize.data.auth.Token

interface TokenView {
    fun onTokenSuccess(code:Int, result : Token?)
    fun onTokenFailure(code:Int)
}