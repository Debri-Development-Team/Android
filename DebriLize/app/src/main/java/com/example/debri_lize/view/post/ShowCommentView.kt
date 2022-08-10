package com.example.debri_lize.view.post

import com.example.debri_lize.data.post.CommentList

interface ShowCommentView {
    fun onShowCommentSuccess(code: Int, result: List<CommentList>)
    fun onShowCommentFailure(code : Int)
}