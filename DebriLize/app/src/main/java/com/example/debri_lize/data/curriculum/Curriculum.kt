package com.example.debri_lize.data.curriculum

import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable
import java.sql.Timestamp

//Response
//8.1 커리큘럼 생성 api
data class CurriIdx(
    @SerializedName(value = "curriIdx") val curriculumIdx: Int
) : Serializable

//8.2 커리큘럼 리스트 조회 api : 유저들이 제공하는 커리큘럼 TOP 10
data class Curriculum(
    @SerializedName(value = "curriIdx") val curriculumIdx: Int? = 0,
    @SerializedName(value = "curriName") val curriculumName: String? = "",
    @SerializedName(value = "curriAuthor") val curriculumAuthor: String? = "",
    @SerializedName(value = "status") val status: String? = "",
    @SerializedName(value = "visibleStatus") val visibleStatus: String? = "",
    @SerializedName(value = "curriDesc") val curriDesc: String? = "",
    @SerializedName(value = "createdAt") val createdAt: String? = "",
    @SerializedName(value = "langTag") val langtag : String? = "",
    @PrimaryKey(autoGenerate = true) var roomIdx : Int =0
)

//8.3 커리큘럼 상세 조회 api
data class CurriculumDetail(
    @SerializedName(value = "curriIdx") val curriculumIdx: Int,
    @SerializedName(value = "curriName") val curriculumName: String? = "",
    @SerializedName(value = "curriAuthor") val curriculumAuthor: String? = "",
    @SerializedName(value = "visibleStatus") val visibleStatus: String,
    @SerializedName(value = "langTag") val language: String? = "",
    @SerializedName(value = "progressRate") val progressRate: Float,
    @SerializedName(value = "status") val status: String,
    @SerializedName(value = "completeAt") val completeAt: Int? = 0,
    @SerializedName(value = "createdAt") val createdAt: Timestamp,
    @SerializedName(value = "lectureListResList") val lectureListResList: List<LectureList>,
    @SerializedName(value = "chapterListResList") val chapterListResList: List<ChapterList>,
    @SerializedName(value = "dday") val dday: Int,
    @SerializedName(value = "curriDesc") val curriDesc: String,
    @SerializedName(value = "curriLikeStatus") val curriLikeStatus: String,
    @SerializedName(value = "scrapIdx") val scrapIdx: Int
) : Serializable

data class LectureList(
    @SerializedName(value = "lectureIdx") val lectureIdx: Int? = 0,
    @SerializedName(value = "lectureName") val lectureName: String? = "",
    @SerializedName(value = "langTag") val language: String? = "",
    @SerializedName(value = "chNumber") val chNum: Int,
    @SerializedName(value = "progressRate") val progressRate: Float,
    @SerializedName(value = "type") var type : String,
    @SerializedName(value = "pricing") var price : String,
    @SerializedName(value = "usedCount") var usedCnt : Int,
    @SerializedName(value = "scrapStatus") var scrapStatus : String, //즐찾여부
    @SerializedName(value = "likeStatus") var likeStatus : String
)

data class ChapterList(
    @SerializedName(value = "chIdx") val chIdx: Int? = 0,
    @SerializedName(value = "chName") val chName: String? = "",
    @SerializedName(value = "chNumber") val chNum: Int?,
    @SerializedName(value = "langTag") val language: String? = "",
    @SerializedName(value = "chComplete") val chComplete: String?,
    @SerializedName(value = "progressOrder") val progressOrder: Int?,
    @SerializedName(value = "completeChNumber") val completeChNum: Int?,
    val chapterImg : Int?,
    @SerializedName(value = "lectureIdx") val lectureIdx: Int? = 0,
    @SerializedName(value = "curriIdx") val curriIdx: Int
)

//8.8 커리큘럼 좋아요(추천) 생성 api
data class CurriculumLike(
    @SerializedName(value = "curriIdx") val curriIdx: Int? = 0,
    @SerializedName(value = "curriName") val curriName: String? = "",
    @SerializedName(value = "curriAuthor") val curriAuthor: String? = "",
    @SerializedName(value = "visibleStatus") val visibleStatus: String? = "",
    @SerializedName(value = "langTag") val langTag: String? = "",
    @SerializedName(value = "progressRate") val progressRate: Float? = 0F,
    @SerializedName(value = "status") val status: String? = "",
    @SerializedName(value = "ownerIdx") val ownerIdx: Int? = 0
)

//8.10
data class ScrapCurriculumList(
    @SerializedName(value = "curriIdx") val curriculumIdx: Int,
    @SerializedName(value = "curriName") val curriculumName: String? = "",
    @SerializedName(value = "curriAuthor") val curriculumAuthor: String? = "",
    @SerializedName(value = "status") val status: String,
    @SerializedName(value = "langTag") val language: String? = "",
    @SerializedName(value = "progressRate") val progressRate: Float
)

//8.10.1 커리큘럼 좋아요(추천) TOP 10 리스트 조회 api
data class Top10(
    @SerializedName(value = "curriIdx") val curriIdx: Int,
    @SerializedName(value = "count") val cnt: Int? = 0,
    @SerializedName(value = "ranking") val ranking: Int? = 0,
    @SerializedName(value = "curriName") val curriName: String,
    @SerializedName(value = "curriAuthor") val curriAuthor: String,
    @SerializedName(value = "visibleStatus") val visibleStatus: String,
    @SerializedName(value = "langTag") val language: String,
    @SerializedName(value = "progressRate") val progressRate: Float,
    @SerializedName(value = "status") val status: String,
    @SerializedName(value = "createdAt") val createdAt: Int,
    @SerializedName(value = "curriDesc") val curriDesc: String? = ""
) : Serializable

//8.13 커리큘럼 복붙 api
data class Copy(
    @SerializedName(value = "curriIdx") val curriculumIdx: Int? = 0,
    @SerializedName(value = "curriCopySuccess") val success: Boolean?
)

//@Body
//8.1 커리큘럼 생성 api
data class NewCurriculum(
    @SerializedName(value = "curriName") val curriculumName: String,
    @SerializedName(value = "curriAuthor") val curriculumAuthor: String,
    @SerializedName(value = "visibleStatus") val visibleStatus: String,
    @SerializedName(value = "langTag") val language: String,
    @SerializedName(value = "curriDesc") val curriDesc: String
) : Serializable

//8.4.1 커리큘럼 제목 수정 api
data class EditCurriculumName(
    @SerializedName(value = "curriIdx") val curriculumIdx: Int? = 0,
    @SerializedName(value = "curriName") val curriculumName: String?
) : Serializable

//8.4.2 커리큘럼 공유 상태 수정 api
data class EditCurriculumVisible(
    @SerializedName(value = "curriIdx") val curriculumIdx: Int? = 0,
    @SerializedName(value = "visibleStatus") val visibleStatus: String?
) : Serializable

//8.4.3 커리큘럼 활성 상태 수정 api
data class EditCurriculumStatus(
    @SerializedName(value = "curriIdx") val curriculumIdx: Int,
    @SerializedName(value = "status") val status: String
) : Serializable

//8.5 강의자료 추가 api
data class AddLecture(
    @SerializedName(value = "curriIdx") val curriculumIdx: Int? = 0,
    @SerializedName(value = "lectureIdx") val lectureIdx: Int? = 0
) : Serializable

//8.7 챕터 수강 완료 및 취소 api
data class CompleteChapter(
    @SerializedName(value = "chIdx") val chIdx: Int? = 0,
    @SerializedName(value = "curriIdx") val curriIdx: Int? = 0,
    @SerializedName(value = "lectureIdx") val lectureIdx: Int? = 0
) : Serializable

//8.13 커리큘럼 복붙 api
data class CopyCurriculum(
    @SerializedName(value = "targetCurriIdx") val targetCurriIdx: Int? = 0,
    @SerializedName(value = "targetOwnerNickName") val targetOwnerNickName: String? = ""
): Serializable
