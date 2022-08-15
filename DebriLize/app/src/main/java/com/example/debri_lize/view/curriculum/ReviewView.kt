package com.example.debri_lize.view.curriculum

import com.example.debri_lize.data.curriculum.Review

//8.12 커리큘럼 리뷰 작성 api
interface CreateReviewView {
    fun onCreateReviewSuccess(code: Int)
    fun onCreateReviewFailure(code : Int)
}

//8.12.1 커리큘럼 리뷰 조회 api
interface ShowReviewView {
    fun onShowReviewSuccess(code: Int, result : List<Review>)
    fun onShowReviewFailure(code : Int)
}
