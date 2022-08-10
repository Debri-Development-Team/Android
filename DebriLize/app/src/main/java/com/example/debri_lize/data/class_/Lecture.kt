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
    @SerializedName(value = "userScrap") var userScrap : Boolean
) : Serializable
