package com.example.debri_lize.view.board

import com.example.debri_lize.response.Board

interface UnScrapBoardListView {
    fun onUnScrapBoardListSuccess(code: Int, result: List<Board>)
    fun onUnScrapBoardListFailure(code : Int)
}