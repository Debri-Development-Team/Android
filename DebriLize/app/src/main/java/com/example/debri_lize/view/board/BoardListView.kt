package com.example.debri_lize.view.board

import com.example.debri_lize.response.Board

interface BoardListView {
    fun onBoardListSuccess(code: Int, result: List<Board>)
    fun onBoardListFailure(code : Int)
}