package com.example.debri.data.response

import com.google.gson.annotations.SerializedName

//회원가입, 로그인
data class AuthResponse(
    @SerializedName(value = "isSuccess") val isSuccess:Boolean,
    @SerializedName(value = "returnCode") val code:Int,
    @SerializedName(value = "returnMsg") val message:String,
    @SerializedName(value = "result") val result: Result,
    @SerializedName(value = "result2") val result2: Result2?
    //회원가입때는 result2 X, 로그인때는 result2 O
    //-> 회원가입일때는 result2값이 null이므로 null처리 해줄것 : ?
)

data class Result(
    @SerializedName(value = "userIdx") var userIdx : Int,
    @SerializedName(value = "signUpResult") var signUpResult : Boolean
)

//로그인 객체
data class Result2(
    //@SerializedName(value = "userIdx") var userIdx : Int,
    @SerializedName(value = "jwt") var jwt : String
)
