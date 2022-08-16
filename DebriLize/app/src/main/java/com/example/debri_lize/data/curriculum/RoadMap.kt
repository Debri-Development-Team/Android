package com.example.debri_lize.data.curriculum

import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable
import java.sql.Timestamp

//Response
//8.3 커리큘럼 상세 조회 api
data class RoadMap(
    @SerializedName(value = "curriIdx") val curriculumIdx: Int,
    @SerializedName(value = "curriName") val curriculumName: String? = "",
    @SerializedName(value = "visibleStatus") val visibleStatus: String,
    @SerializedName(value = "langTag") val language: String? = "",
    @SerializedName(value = "progressRate") val progressRate: Float,
    @SerializedName(value = "status") val status: String,
    @SerializedName(value = "completeAt") val completeAt: Int? = 0,
    @SerializedName(value = "createdAt") val createdAt: Timestamp,
    @SerializedName(value = "lectureListResList") val lectureListResList: List<LectureList>,
    @SerializedName(value = "chapterListResList") val chapterListResList: List<ChapterList>,
    @SerializedName(value = "dday") val dday: Int
) : Serializable
