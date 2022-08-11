package com.example.debri_lize.view.board

import com.example.debri_lize.data.board.Board
import com.example.debri_lize.data.board.BoardFavorite

//2.1 게시판 scrap 생성
interface CreateScrapBoardView {
    fun onCreateScrapBoardSuccess(code: Int)
    fun onCreateScrapBoardFailure(code : Int)
}

//2.2 게시판 scrap 해제
interface CancelScrapBoardView {
    fun onCancelScrapBoardSuccess(code: Int)
    fun onCancelScrapBoardFailure(code : Int)
}

//2.3 즐겨찾기 게시판 리스트
interface ScrapBoardListView {
    fun onScrapBoardListSuccess(code: Int, result: List<BoardFavorite>)
    fun onScrapBoardListFailure(code : Int)
}

//2.4 전체 게시판 리스ㅌ
interface UnScrapBoardListView {
    fun onUnScrapBoardListSuccess(code: Int, result: List<Board>)
    fun onUnScrapBoardListFailure(code : Int)
}


