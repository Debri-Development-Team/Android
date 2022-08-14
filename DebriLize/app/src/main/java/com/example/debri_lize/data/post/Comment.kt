package com.example.debri_lize.data.post

import com.google.gson.annotations.SerializedName

//Response
data class CommentList(
    @SerializedName(value = "commentIdx") var commentIdx : Int,
    @SerializedName(value = "authorIdx") var authorIdx : Int, //authorIdx로 변경
    @SerializedName(value = "postIdx") var postIdx : Int,
    @SerializedName(value = "commentLevel") var commentLevel : Int, //0:comment, 1:cocomment
    @SerializedName(value = "commentOrder") var commentOrder : Int, //대댓글 순서
    @SerializedName(value = "commentGroup") var commentGroup : Int, //대댓글의 뿌리댓글
    @SerializedName(value = "commentContent") var commentContent : String,
    @SerializedName(value = "authorName") var authorName : String,
    @SerializedName(value = "timeAfterCreated") var timeAfterCreated : Int
)

//@Body
//4.1 댓글 작성 api
data class Comment(
    @SerializedName(value = "userIdx") var userIdx : Int? = 0,
    @SerializedName(value = "postIdx") var postIdx : Int? = 0,
    @SerializedName(value = "content") val commentContent : String? = "",
    @SerializedName(value = "authorName") var authorName : String? = ""
)

//4.2 대댓글 작성 api
data class Cocomment(
    @SerializedName(value = "userIdx") var userIdx : Int? = 0,
    @SerializedName(value = "postIdx") var postIdx : Int? = 0,
    @SerializedName(value = "rootCommentIdx") var rootCommentIdx : Int? = 0,
    @SerializedName(value = "content") val commentContent : String? = "",
    @SerializedName(value = "authorName") var authorName : String? = ""
)