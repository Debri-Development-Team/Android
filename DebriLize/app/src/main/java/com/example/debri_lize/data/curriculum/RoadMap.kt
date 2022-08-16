package com.example.debri_lize.data.curriculum

import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable
import java.sql.Timestamp

//Response
//7.5 로드맵 리스트 조회 api
data class RoadMapList(
    @SerializedName(value = "roadmapIdx") val roadmapIdx: Int,
    @SerializedName(value = "roadmapName") val roadmapName: String? = "",
    @SerializedName(value = "roadmapExplain") val roadmapExplain: String,
    @SerializedName(value = "roadmapAuthor") val roadmapAuthor: String? = ""
) : Serializable

//7.5.1 로드맵 상세 조회 api
data class RoadMap(
    @SerializedName(value = "roadmapIdx") val roadmapIdx: Int,
    @SerializedName(value = "roadmapName") val roadmapName: String? = "",
    @SerializedName(value = "roadmapExplain") val roadmapExplain: String,
    @SerializedName(value = "authorName") val authorName: String? = "",
    @SerializedName(value = "requireDay") val requireDay: Int,
    @SerializedName(value = "roadmapChildCurriList") val roadmapChildCurriList: List<VirtualCurri>
) : Serializable

data class VirtualCurri( //가상 커리큘럼
    @SerializedName(value = "roadmapIdx") val roadmapIdx: Int,
    @SerializedName(value = "childCurriIdx") val childCurriIdx: Int,
    @SerializedName(value = "childOrder") val childOrder: Int,
    @SerializedName(value = "childExplain") val childExplain: String,
    @SerializedName(value = "childName") val childName: String,
    @SerializedName(value = "roadmapChildLectureList") val roadmapChildLectureList: List<RoadMapLecture>
) : Serializable

data class RoadMapLecture( //화면에 띄워지는 강의자료
    @SerializedName(value = "childLectureIdx") val childLectureIdx: Int,
    @SerializedName(value = "childLectureName") val childLectureName: String,
    @SerializedName(value = "childChapterNumber") val childChapterNumber: Int,
    @SerializedName(value = "childLangTag") val childLangTag: String,
    @SerializedName(value = "childPricing") val childPricing: String,
    @SerializedName(value = "childMaterialType") val childMaterialType: String,
    @SerializedName(value = "userScrap") val userScrap: Boolean,
    @SerializedName(value = "scrapNumber") val scrapNumber: Int,
    @SerializedName(value = "usedCount") val usedCount: Int,
    @SerializedName(value = "likeNumber") val likeNumber: Int,
    @SerializedName(value = "userLike") val userLike: Boolean,
    @SerializedName(value = "pcurriIdx") val pcurriIdx: Int
) : Serializable
