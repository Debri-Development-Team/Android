package com.debri_main.debri.view.auth

import com.debri_main.debri.data.auth.Token

interface TokenView {
    fun onTokenSuccess(code:Int, result : Token?)
    fun onTokenFailure(code:Int)
}