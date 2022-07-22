package com.example.debri_lize.data

import com.google.gson.annotations.SerializedName

data class Post(
    @SerializedName(value = "boardIdx") var boardIdx : Int? = 0,
    @SerializedName(value = "userIdx") var userIdx : Int? = 0,
    @SerializedName(value = "postContent") val postContent : String? = "",
    @SerializedName(value = "postName") var postName : String? = ""
)
