package com.example.debri_lize.view.post

interface PostCreateView {
    fun onPostCreateSuccess(code : Int)
    fun onPostCreateFailure(code : Int)
}