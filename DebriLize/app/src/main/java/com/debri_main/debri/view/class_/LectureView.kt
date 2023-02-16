package com.debri_main.debri.view.class_

import com.debri_main.debri.data.class_.Lecture
import com.debri_main.debri.data.class_.SearchLecture

//7.2 강의 즐겨찾기 생성
interface CreateLectureScrapView {
    fun onCreateLectureScrapSuccess(code: Int)
    fun onCreateLectureScrapFailure(code : Int)
}

//7.2.1 강의 즐겨찾기 삭제
interface CancelLectureScrapView {
    fun onCancelLectureScrapSuccess(code: Int)
    fun onCancelLectureScrapFailure(code : Int)
}

//7.3 즐겨찾기한 강의 리스트 조회
interface LectureFavoriteView {
    fun onLectureFavoriteSuccess(code: Int, result: List<Lecture>)
    fun onLectureFavoriteFailure(code : Int)
}

//7.4 강의 상세 조회
interface ShowLectureDetailView {
    fun onShowLectureDetailSuccess(code: Int, result: Lecture)
    fun onShowLectureDetailFailure(code: Int)
}

//7.4.1 강의 검색 필터
interface LectureFilterView {
    fun onLectureFilterSuccess(code: Int, result: SearchLecture)
    fun onLectureFilterFailure(code : Int)
}

//7.7 강의 좋아요 생성
interface CreateLectureLikeView {
    fun onCreateLectureLikeSuccess(code: Int)
    fun onCreateLectureLikeFailure(code: Int)
}

//7.7.1 강의 좋아요 삭제
interface DeleteLectureLikeView {
    fun onDeleteLectureLikeSuccess(code: Int)
    fun onDeleteLectureLikeFailure(code: Int)
}