package com.example.debri_lize.view.class_

import com.example.debri_lize.response.Lecture

interface CancelLectureScrapView {
    fun onCancelLectureScrapSuccess(code: Int)
    fun onCancelLectureScrapFailure(code : Int)
}