package com.example.debri_lize.view.board

import com.example.debri_lize.response.Board

interface BoardView {
    fun onBoardSuccess(code: Int, result: List<Board>)
    fun onBoardFailure(code : Int)
}