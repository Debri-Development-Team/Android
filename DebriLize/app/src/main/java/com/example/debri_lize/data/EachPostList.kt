package com.example.debri_lize.data

data class EachPostList(
    var boardIdx : Int? = 0,
    var postIdx : Int? = 0,
    var authorName : String? = "",
    var postName : String? = "",
    var likeCnt : Int? = 0,
    val timeAfterCreated : Int? = 0,
    var commentCnt : Int? = 0
)
