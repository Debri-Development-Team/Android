package com.debri_main.debri.view.curriculum

import com.debri_main.debri.data.curriculum.ShowReview

//8.12 커리큘럼 리뷰 작성 api
interface CreateReviewView {
    fun onCreateReviewSuccess(code: Int)
    fun onCreateReviewFailure(code : Int)
}

//8.12.1 커리큘럼 리뷰 조회 api
interface ShowReviewView {
    fun onShowReviewSuccess(code: Int, result : ShowReview)
    fun onShowReviewFailure(code : Int)
}
