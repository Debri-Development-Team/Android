package com.example.debri_lize.response

import com.google.gson.annotations.SerializedName

//회원가입, 로그인
data class TokenResponse(
    @SerializedName(value = "isSuccess") val isSuccess:Boolean,
    @SerializedName(value = "returnCode") val code:Int,
    @SerializedName(value = "returnMsg") val message:String,
    @SerializedName(value = "result") val result: Token?
)

data class Token(
    @SerializedName(value = "accessToken") var accessToken : String,
    @SerializedName(value = "refreshToken") var refreshToken : String
)
