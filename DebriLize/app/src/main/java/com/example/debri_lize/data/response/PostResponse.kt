package com.example.debri_lize.data.response

import com.google.gson.annotations.SerializedName

//회원가입, 로그인
data class PostResponse(
    @SerializedName(value = "isSuccess") val isSuccess:Boolean,
    @SerializedName(value = "returnCode") val code:Int,
    @SerializedName(value = "returnMsg") val message:String,
    @SerializedName(value = "result") val result: Post,
)

data class Post(
    @SerializedName(value = "postIdx") var userIdx : Int
)
