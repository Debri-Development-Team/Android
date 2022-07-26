package com.example.debri_lize.data.post

import com.google.gson.annotations.SerializedName

data class Cocomment(
    @SerializedName(value = "userIdx") var userIdx : Int? = 0,
    @SerializedName(value = "postIdx") var postIdx : Int? = 0,
    @SerializedName(value = "rootCommentIdx") var rootCommentIdx : Int? = 0,
    @SerializedName(value = "content") val commentContent : String? = "",
    @SerializedName(value = "authorName") var authorName : String? = ""
)
