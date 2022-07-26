package com.example.debri_lize.response

import com.google.gson.annotations.SerializedName


data class CommentListResponse(
    @SerializedName(value = "isSuccess") val isSuccess:Boolean,
    @SerializedName(value = "returnCode") val code:Int,
    @SerializedName(value = "returnMsg") val message:String,
    @SerializedName(value = "result") val result: List<CommentList>
)

data class CommentList(
    @SerializedName(value = "commentIdx") var commentIdx : Int,
    @SerializedName(value = "authorIdx") var authorIdx : Int, //authorIdx로 변경
    @SerializedName(value = "postIdx") var postIdx : Int,
    @SerializedName(value = "commentLevel") var commentLevel : Int, //0:comment, 1:cocomment
    @SerializedName(value = "commentOrder") var commentOrder : Int, //대댓글 순서
    @SerializedName(value = "commentGroup") var commentGroup : Int, //대댓글의 뿌리댓글
    @SerializedName(value = "commentContent") var commentContent : String,
    @SerializedName(value = "authorName") var authorName : String
)
