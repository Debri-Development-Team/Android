package com.example.debri_lize.data.post

//@Body
//6.1 게시물 신고 api
data class ReportPost(
    var postIdx : Int,
    var reason : String
)

//6.2 댓글 신고 api
data class ReportComment(
    var commentIdx : Int,
    var reason : String
)
