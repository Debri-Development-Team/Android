package com.example.debri_lize.response

import com.google.gson.annotations.SerializedName

data class LectureResponse(
    @SerializedName(value = "isSuccess") val isSuccess:Boolean,
    @SerializedName(value = "returnCode") val code:Int,
    @SerializedName(value = "returnMsg") val message:String,
    @SerializedName(value = "result") val result: List<Lecture>,
)

data class Lecture(
    @SerializedName(value = "lectureIdx") var lectureIdx : Int,
    @SerializedName(value = "lectureName") var lectureName : String,
    @SerializedName(value = "chapterNumber") var chapterNumber : Int,
    @SerializedName(value = "langTag") var langTag : String,
    @SerializedName(value = "pricing") var price : String,
    @SerializedName(value = "materialType") var media : String,
    @SerializedName(value = "userScrap") var userScrap : Boolean,
)