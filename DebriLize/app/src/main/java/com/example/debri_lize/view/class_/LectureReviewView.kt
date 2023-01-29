package com.example.debri_lize.view.class_

import com.example.debri_lize.data.class_.LectureReview
import com.example.debri_lize.data.class_.ShowLectureReview

//7.6 강의 리뷰 작성 api
interface CreateLectureReviewView {
    fun onCreateLectureReviewSuccess(code: Int, result : LectureReview)
    fun onCreateLectureReviewFailure(code : Int)
}

//7.6.1 강의 리뷰 조회 api
interface ShowLectureReviewView {
    fun onShowLectureReviewSuccess(code: Int, result : ShowLectureReview)
    fun onShowLectureReviewFailure(code : Int)
}
