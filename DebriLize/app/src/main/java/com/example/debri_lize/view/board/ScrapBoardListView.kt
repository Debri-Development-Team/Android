package com.example.debri_lize.view.board

import com.example.debri_lize.data.board.Board

interface ScrapBoardListView {
    fun onScrapBoardListSuccess(code: Int, result: List<Board>)
    fun onScrapBoardListFailure(code : Int)
}