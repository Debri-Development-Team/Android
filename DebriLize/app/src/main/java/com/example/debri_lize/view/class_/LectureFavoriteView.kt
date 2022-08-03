package com.example.debri_lize.view.class_

import com.example.debri_lize.response.Lecture

interface LectureFavoriteView {
    fun onLectureFavoriteSuccess(code: Int, result: List<Lecture>)
    fun onLectureFavoriteFailure(code : Int)
}