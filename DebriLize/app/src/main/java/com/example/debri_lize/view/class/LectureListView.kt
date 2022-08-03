package com.example.debri_lize.view.`class`

import com.example.debri_lize.response.Lecture


interface LectureListView {
    fun onLectureListSuccess(code: Int, result: List<Lecture>)
    fun onLectureListFailure(code : Int)
}