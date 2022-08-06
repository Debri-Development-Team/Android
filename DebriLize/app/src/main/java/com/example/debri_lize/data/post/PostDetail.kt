package com.example.debri_lize.data.post

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class PostDetail(
    var boardIdx : Int,
    var postIdx : Int,
    var authorIdx : Int,
    var authorName : String,
    var postName : String,
    var likeCnt : Int,
    var likeStatus : Boolean?,
    var scrapStatus : Boolean?,
    var commentCnt : Int,
    var timeAfterCreated : Int,
    var postContents : String
) :Serializable
