package com.example.debri_lize.response

import com.google.gson.annotations.SerializedName

//회원가입, 로그인
data class AuthResponse(
    @SerializedName(value = "isSuccess") val isSuccess:Boolean,
    @SerializedName(value = "returnCode") val code:Int,
    @SerializedName(value = "returnMsg") val message:String,
    @SerializedName(value = "result") val result: Result?
)

data class Result(
    @SerializedName(value = "userIdx") var userIdx : Int,
    @SerializedName(value = "userName") var userName : String,
    @SerializedName(value = "jwt") var jwt : String,
    @SerializedName(value = "refreshToken") var refreshToken : String
)