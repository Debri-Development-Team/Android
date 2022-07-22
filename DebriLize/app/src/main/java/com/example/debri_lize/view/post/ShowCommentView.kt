package com.example.debri_lize.view.post

import com.example.debri_lize.response.Comment

interface ShowCommentView {
    fun onShowCommentSuccess(code: Int, result: List<Comment>)
    fun onShowCommentFailure(code : Int)
}