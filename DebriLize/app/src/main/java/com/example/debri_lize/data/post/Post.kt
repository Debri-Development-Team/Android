package com.example.debri_lize.data.post

import com.google.gson.annotations.SerializedName
import java.io.Serializable

//Response
//3.7 [특정 게시판]게시물 리스트 조회 api
//3.7.1 [전체 범위(키워드 검색)] 게시물 리스트 조회 api
data class PostInfo(
    @SerializedName(value = "postList") val postList : List<PostList>,
    @SerializedName(value = "postCount") var postCount : Int? = 0
)

data class PostList(
    @SerializedName(value = "boardIdx") var boardIdx : Int? = 0,
    @SerializedName(value = "postIdx") var postIdx : Int? = 0,
    @SerializedName(value = "authorName") var authorName : String? = "",
    @SerializedName(value = "postName") var postName : String? = "",
    @SerializedName(value = "likeNumber") var likeCnt : Int? = 0,
    @SerializedName(value = "likeStatus") var likeStatus : String? = "",
    @SerializedName(value = "scrapStatus") var scrapStatus : String? = "",
    @SerializedName(value = "timeAfterCreated") val timeAfterCreated : Int,
    @SerializedName(value = "commentNumber") var commentCnt : Int? = 0,
    @SerializedName(value = "boardName") var boardName : String?
)

//3.8 게시물 조회 api
data class PostDetail(
    @SerializedName(value = "boardIdx") var boardIdx : Int,
    @SerializedName(value = "postIdx") var postIdx : Int,
    @SerializedName(value = "authorIdx") var authorIdx : Int,
    @SerializedName(value = "authorName") var authorName : String,
    @SerializedName(value = "postName") var postName : String,
    @SerializedName(value = "likeNumber") var likeCnt : Int,
    @SerializedName(value = "userLike") var likeStatus : Boolean? = true,
    @SerializedName(value = "userScrap") var scrapStatus : Boolean? = false,
    @SerializedName(value = "commentNumber") var commentCnt : Int,
    @SerializedName(value = "timeAfterCreated") var timeAfterCreated : Int,
    @SerializedName(value = "contents") var postContents : String
) : Serializable

//@Body
//3.1 게시물 생성 api
data class Post(
    @SerializedName(value = "boardIdx") var boardIdx : Int? = 0,
    @SerializedName(value = "userIdx") var userIdx : Int? = 0,
    @SerializedName(value = "postContent") val postContent : String? = "",
    @SerializedName(value = "postName") var postName : String? = ""
)

//3.2 게시물 수정 api
data class EditPost(
    @SerializedName(value = "userIdx") var userIdx : Int? = 0,
    @SerializedName(value = "postContent") var postContent : String? = null
)

//3.4 게시물 좋아요 생성 api
data class PostLikeCreate(
    @SerializedName(value = "postIdx") var postIdx : Int? = 0,
    @SerializedName(value = "userIdx") var userIdx : Int? = 0,
    @SerializedName(value = "likeStatus") var likeStatus : String? = ""
)

//3.5 게시물 좋아요 취소 api
data class PostLikeCancel(
    @SerializedName(value = "postIdx") var postIdx : Int? = 0,
    @SerializedName(value = "userIdx") var userIdx : Int? = 0
)

//3.7.1
data class SearchPost(
    @SerializedName(value = "keyword") var keyword : String? = "",
    @SerializedName(value = "pageNum") var pageNum : Int? = 0
)
