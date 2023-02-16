package com.debri_main.debri.base

import com.google.gson.annotations.SerializedName

data class BaseResponse<T>(
    @SerializedName(value = "isSuccess") val isSuccess:Boolean,
    @SerializedName(value = "returnCode") val code:Int,
    @SerializedName(value = "returnMsg") val message:String,
    @SerializedName(value = "result") val result: T
)
