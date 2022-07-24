package com.example.debri_lize.response

import com.google.gson.annotations.SerializedName

data class BoardResponse(
    @SerializedName(value = "isSuccess") val isSuccess:Boolean,
    @SerializedName(value = "returnCode") val code:Int,
    @SerializedName(value = "returnMsg") val message:String,
    @SerializedName(value = "result") val result: List<Board>,
)

data class Board(
    @SerializedName(value = "boardIdx") var boardIdx : Int,
    @SerializedName(value = "boardName") var boardName : String,
    @SerializedName(value = "boardAdmin") var boardAdmin : String
)
