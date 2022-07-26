package com.example.debri_lize.view.post

import com.example.debri_lize.response.CommentList

interface ShowCommentView {
    fun onShowCommentSuccess(code: Int, result: List<CommentList>)
    fun onShowCommentFailure(code : Int)
}