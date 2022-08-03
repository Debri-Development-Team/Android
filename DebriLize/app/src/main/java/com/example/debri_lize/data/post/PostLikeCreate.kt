package com.example.debri_lize.data.post

import com.google.gson.annotations.SerializedName

data class PostLikeCreate(
    @SerializedName(value = "postIdx") var postIdx : Int? = 0,
    @SerializedName(value = "userIdx") var userIdx : Int? = 0,
    @SerializedName(value = "likeStatus") var likeStatus : String? = ""
)
