package com.example.debri_lize.data.class_

import java.io.Serializable

data class Lecture(
    val lectureIdx : Int? = 0,
    val lectureName : String? = "",
    val chapterNum : Int? = 0,
    val language : String? = "",
    val media : String? = "",   //서적 or 영상
    val price : String? = "",    //무료 or 유료
    val userScrap : Boolean
) : Serializable
