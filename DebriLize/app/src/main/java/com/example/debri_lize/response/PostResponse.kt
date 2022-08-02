package com.example.debri_lize.response

import com.google.gson.annotations.SerializedName

data class PostResponse(
    @SerializedName(value = "isSuccess") val isSuccess:Boolean,
    @SerializedName(value = "returnCode") val code:Int,
    @SerializedName(value = "returnMsg") val message:String,
    @SerializedName(value = "result") val result: List<Post>,
)

data class Post(
    @SerializedName(value = "boardIdx") var boardIdx : Int,
    @SerializedName(value = "postIdx") var postIdx : Int,
    @SerializedName(value = "authorIdx") var authorIdx : Int,
    @SerializedName(value = "authorName") var authorName : String,
    @SerializedName(value = "postName") var postName : String,
    @SerializedName(value = "likeNumber") var likeCnt : Int,
    @SerializedName(value = "likeStatus") var likeStatus : String,
    @SerializedName(value = "scrapStatus") var scrapStatus : String,
    @SerializedName(value = "timeAfterCreated") var timeAfterCreated : Int,
    @SerializedName(value = "commentNumber") var commentCnt : Int
)
