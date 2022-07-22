package com.example.debri_lize.data

data class Post(
    var boardIdx : Int? = 0,
    var userIdx : Int? = 0,
    val postContent : String? = "",
    var postName : String? = ""
)
