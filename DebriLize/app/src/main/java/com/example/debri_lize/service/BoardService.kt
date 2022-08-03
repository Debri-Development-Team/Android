package com.example.debri_lize.service

import android.util.Log
import com.example.debri_lize.data.RetrofitInterface
import com.example.debri_lize.fragment.BoardFragment
import com.example.debri_lize.response.BoardResponse
import com.example.debri_lize.utils.getJwt
import com.example.debri_lize.utils.getRetrofit
import com.example.debri_lize.view.board.BoardListView
import com.example.debri_lize.view.board.ScrapBoardListView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BoardService {
    private lateinit var boardListView: BoardListView

    private lateinit var scrapBoardListView: ScrapBoardListView

    fun setBoardListView(boardListView: BoardListView){
        this.boardListView = boardListView
    }

    fun setScrapBoardListView(scrapBoardListView: ScrapBoardListView){
        this.scrapBoardListView = scrapBoardListView
    }


    fun showBoardList(){
        Log.d("boardList", "enter")
        val boardService = getRetrofit().create(RetrofitInterface::class.java)
        boardService.showBoardList(getJwt()!!).enqueue(object: Callback<BoardResponse> {
            //응답이 왔을 때 처리
            override fun onResponse(call: Call<BoardResponse>, response: Response<BoardResponse>) {
                Log.d("boardList", "response")
                Log.d("boardlistresp","${response.body()}")
                val resp:BoardResponse = response.body()!!
                Log.d("boardListCode", resp.code.toString())

                when(resp.code){
                    //API code값 사용
                    200->boardListView.onBoardListSuccess(resp.code, resp.result)
                    else->boardListView.onBoardListFailure(resp.code)
                }
            }
            //실패했을 때 처리
            override fun onFailure(call: Call<BoardResponse>, t: Throwable) {
                Log.d("boardListFail", t.toString())
            }

        })
    }

    fun showScrapBoardList(){
        Log.d("scrapBoardList", "enter")
        val boardService = getRetrofit().create(RetrofitInterface::class.java)
        boardService.showScrapBoardList(getJwt()!!).enqueue(object: Callback<BoardResponse> {
            //응답이 왔을 때 처리
            override fun onResponse(call: Call<BoardResponse>, response: Response<BoardResponse>) {
                Log.d("scrapBoardList", "response")

                val resp:BoardResponse = response.body()!!
                Log.d("scrapBoardListCode", resp.code.toString())

                when(resp.code){
                    //API code값 사용
                    200->boardListView.onBoardListSuccess(resp.code, resp.result)
                    else->boardListView.onBoardListFailure(resp.code)
                }
            }
            //실패했을 때 처리
            override fun onFailure(call: Call<BoardResponse>, t: Throwable) {
                Log.d("scrapBoardListFail", t.toString())
            }

        })
    }

}