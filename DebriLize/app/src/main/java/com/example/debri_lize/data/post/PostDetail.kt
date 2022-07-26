package com.example.debri_lize.data.post

import com.google.gson.annotations.SerializedName

data class PostDetail(
    @SerializedName(value = "boardIdx") var boardIdx : Int? = 0,
    @SerializedName(value = "postIdx") var postIdx : Int? = 0,
    @SerializedName(value = "authorIdx") var authorIdx : Int? = 0,
    @SerializedName(value = "authorName") var authorName : String? = "",
    @SerializedName(value = "postName") var postName : String? = "",
    @SerializedName(value = "likeNumber") var likeCnt : Int? = 0,
    @SerializedName(value = "commentNumber") var commentCnt : Int? = 0,
    @SerializedName(value = "timeAfterCreated") val timeAfterCreated : Int? = 0,
    @SerializedName(value = "contents") var postContent : Int? = 0
)
