package com.example.debri_lize.service

import android.util.Log
import com.example.debri_lize.data.post.Post
import com.example.debri_lize.data.RetrofitInterface
import com.example.debri_lize.fragment.BoardFragment
import com.example.debri_lize.response.BoardResponse
import com.example.debri_lize.response.PostDetailResponse
import com.example.debri_lize.response.PostResponse
import com.example.debri_lize.view.post.EachPostListView
import com.example.debri_lize.view.post.PostCreateView
import com.example.debri_lize.view.post.PostDetailView
import com.example.debri_lize.utils.getRetrofit
import com.example.debri_lize.view.board.BoardListView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BoardService {
    private lateinit var boardListView: BoardListView

    fun setBoardListView(boardListView: BoardFragment){
        this.boardListView = boardListView
    }


    fun showBoardList(){
        Log.d("boardList", "enter")
        val boardService = getRetrofit().create(RetrofitInterface::class.java)
        boardService.showBoardList().enqueue(object: Callback<BoardResponse> {
            //응답이 왔을 때 처리
            override fun onResponse(call: Call<BoardResponse>, response: Response<BoardResponse>) {
                Log.d("boardList", "response")

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

}