package com.debri_main.debri.data.class_

import com.google.gson.annotations.SerializedName
import java.io.Serializable

//@Body
//8.12 커리큘럼 리뷰 작성 api
data class LectureReview(
    @SerializedName(value = "lectureIdx") val lectureIdx: Int,
    @SerializedName(value = "authorName") val authorName: String?,
    @SerializedName(value = "content") val content: String
) : Serializable


//7.6.1 강의 리뷰 조회 api
data class ShowLectureReview(
    @SerializedName(value = "reviewList") val reviewList: List<LectureReview>,
    @SerializedName(value = "reviewCount") val reviewCount: Int
)