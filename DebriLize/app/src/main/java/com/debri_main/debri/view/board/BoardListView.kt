package com.debri_main.debri.view.board

import com.debri_main.debri.data.board.Board

interface BoardListView {
    fun onBoardListSuccess(code: Int, result: List<Board>)
    fun onBoardListFailure(code : Int)
}