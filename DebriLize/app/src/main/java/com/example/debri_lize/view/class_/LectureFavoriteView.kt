package com.example.debri_lize.view.class_

import com.example.debri_lize.data.class_.Lecture

interface LectureFavoriteView {
    fun onLectureFavoriteSuccess(code: Int, result: List<Lecture>)
    fun onLectureFavoriteFailure(code : Int)
}