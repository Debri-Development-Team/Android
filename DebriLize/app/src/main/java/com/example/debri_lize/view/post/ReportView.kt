package com.example.debri_lize.view.post

//6.1 게시물 신고 api
interface ReportPostView {
    fun onReportPostSuccess(code: Int)
    fun onReportPostFailure(code : Int)
}

//6.2 댓글, 대댓글 신고 api
interface ReportCommentView {
    fun onReportCommentSuccess(code: Int)
    fun onReportCommentFailure(code : Int)
}

//6.3 사용자 신고 & 차단 api
interface ReportUserView {
    fun onReportUserSuccess(code: Int)
    fun onReportUserFailure(code : Int)
}