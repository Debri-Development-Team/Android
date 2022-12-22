package com.example.debri_lize.data.class_

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Lecture(
    @SerializedName(value = "lectureIdx") var lectureIdx : Int,
    @SerializedName(value = "lectureName") var lectureName : String,
    @SerializedName(value = "chapterNumber") var chapterNum : Int,
    @SerializedName(value = "langTag") var language : String,
    @SerializedName(value = "materialType") var media : String,
    @SerializedName(value = "pricing") var price : String,
    @SerializedName(value = "userScrap") var userScrap : Boolean,   //즐찾 여부
    @SerializedName(value = "scrapNumber") var scrapNumber : Int,   //즐찾 수 : 어따씀?
    @SerializedName(value = "usedCount") var usedCount : Int,   //커리큘럼에서 사용 횟수
    @SerializedName(value = "likeNumber") var likeNumber : Int, //강의 좋아요 수
    @SerializedName(value = "userLike") var userLike : Boolean,  //유저의 좋아요 여부
    @SerializedName(value = "lectureDesc") var lectureDesc : String? = "", //강의 설명
    @SerializedName(value = "srcLink") var srcLink : String? = "",
    @SerializedName(value = "publisher") var publisher : String? = ""
) : Serializable

//7.4.1 강의 검색 api
data class SearchLecture(
    @SerializedName(value = "lectureList") var lectureList : List<Lecture>,
    @SerializedName(value = "lectureCount") var lectureCount : Int
)

//7.7 강의 좋아요
data class LikeSuccess(
    @SerializedName(value = "likeSuccess") var likeSuccess : Boolean
)