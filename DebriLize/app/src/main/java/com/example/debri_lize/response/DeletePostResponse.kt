package com.example.debri_lize.response

import com.google.gson.annotations.SerializedName

data class DeletePostResponse(
    @SerializedName(value = "isSuccess") val isSuccess:Boolean,
    @SerializedName(value = "returnCode") val code:Int,
    @SerializedName(value = "returnMsg") val message:String,
    @SerializedName(value = "result") val result: String
)
