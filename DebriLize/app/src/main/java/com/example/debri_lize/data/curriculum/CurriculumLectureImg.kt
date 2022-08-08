package com.example.debri_lize.data.curriculum

import java.io.Serializable

data class CurriculumLectureImg(
    val lectureOrder: Int? = 0,
    val lectureImg: Int,
    val lectureName: String? = ""
) : Serializable
