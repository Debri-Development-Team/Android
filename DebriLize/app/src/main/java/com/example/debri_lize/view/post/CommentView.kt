package com.example.debri_lize.view.post

import com.example.debri_lize.data.post.CommentList


//4.1 게시물 댓글 작성 api
interface CommentCreateView {
    fun onCommentCreateSuccess(code : Int)
    fun onCommentCreateFailure(code : Int)
}

//4.2 대댓글 작성 api
interface CocommentCreateView {
    fun onCocommentCreateSuccess(code : Int)
    fun onCocommentCreateFailure(code : Int)
}

//4.3 댓글/대댓글 조회 api
interface ShowCommentView {
    fun onShowCommentSuccess(code: Int, result: List<CommentList>)
    fun onShowCommentFailure(code : Int)
}

//4.4 댓글, 대댓글 삭제
interface DeleteCommentView {
    fun onDeleteCommentSuccess(code : Int)
    fun onDeleteCommentFailure(code : Int)
}

