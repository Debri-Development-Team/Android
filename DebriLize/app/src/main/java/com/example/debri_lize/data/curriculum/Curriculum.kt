package com.example.debri_lize.data.curriculum

import com.google.gson.annotations.SerializedName
import java.io.Serializable
import java.sql.Timestamp

//Response
//8.2 커리큘럼 리스트 조회 api : 유저들이 제공하는 커리큘럼 TOP 10
data class Curriculum(
    @SerializedName(value = "curriIdx") val curriculumIdx: Int,
    @SerializedName(value = "curriName") val curriculumName: String? = "",
    @SerializedName(value = "curriAuthor") val curriculumAuthor: String? = ""
) : Serializable

//8.3 커리큘럼 상세 조회 api
data class CurriculumDetail(
    @SerializedName(value = "curriIdx") val curriculumIdx: Int,
    @SerializedName(value = "curriName") val curriculumName: String? = "",
    @SerializedName(value = "visibleStatus") val visibleStatus: String? = "",
    @SerializedName(value = "langTag") val language: String? = "",
    @SerializedName(value = "progressRate") val progressRate: Float,
    @SerializedName(value = "status") val status: String,
    @SerializedName(value = "completeAt") val completeAt: Int? = 0,
    @SerializedName(value = "createdAt") val createdAt: Timestamp,
    @SerializedName(value = "lectureListResList") val lectureListResList: List<LectureList>,
    @SerializedName(value = "chapterListResList") val chapterListResList: List<ChapterList>

) : Serializable

data class LectureList(
    @SerializedName(value = "lectureIdx") val lectureIdx: Int? = 0,
    @SerializedName(value = "lectureName") val lectureName: String? = "",
    @SerializedName(value = "langTag") val language: String? = "",
    @SerializedName(value = "chNumber") val chNum: String? = "",
    @SerializedName(value = "progressRate") val progressRate: Float
)

data class ChapterList(
    @SerializedName(value = "chIdx") val chIdx: Int? = 0,
    @SerializedName(value = "chName") val chName: String? = "",
    @SerializedName(value = "chNumber") val chNum: String? = "",
    @SerializedName(value = "langTag") val language: String? = "",
    @SerializedName(value = "chComplete") val chComplete: Float,
    @SerializedName(value = "progressOrder") val progressOrder: String? = "",
    @SerializedName(value = "completeChNumber") val completeChNum: String? = "",
    val chapterImg : Int?
)


//@Body
//8.5 강의자료 추가 api
data class AddLecture(
    @SerializedName(value = "curriIdx") val curriculumIdx: Int? = 0,
    @SerializedName(value = "lectureIdx") val lectureIdx: Int? = 0
) : Serializable
