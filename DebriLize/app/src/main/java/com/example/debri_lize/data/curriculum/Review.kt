package com.example.debri_lize.data.curriculum

import com.google.gson.annotations.SerializedName
import java.io.Serializable

//@Body
//8.12 커리큘럼 리뷰 작성 api
data class Review(
    @SerializedName(value = "curriIdx") val curriculumIdx: Int,
    @SerializedName(value = "authorName") val authorName: String?,
    @SerializedName(value = "content") val content: String
) : Serializable


