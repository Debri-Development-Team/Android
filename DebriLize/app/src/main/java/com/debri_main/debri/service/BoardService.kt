package com.debri_main.debri.service

import android.util.Log
import com.debri_main.debri.utils.RetrofitInterface
import com.debri_main.debri.data.board.Board
import com.debri_main.debri.base.BaseResponse
import com.debri_main.debri.data.board.BoardFavorite
import com.debri_main.debri.utils.getJwt
import com.debri_main.debri.utils.getRetrofit
import com.debri_main.debri.view.board.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BoardService {

    private lateinit var boardListView: BoardListView

    private lateinit var unScrapBoardListView: UnScrapBoardListView

    private lateinit var scrapBoardListView: ScrapBoardListView

    private lateinit var createScrapBoardView: CreateScrapBoardView
    private lateinit var cancelScrapBoardView: CancelScrapBoardView

    fun setBoardListView(boardListView: BoardListView){
        this.boardListView = boardListView
    }

    fun setUnScrapBoardListView(unScrapBoardListView: UnScrapBoardListView){
        this.unScrapBoardListView = unScrapBoardListView
    }

    fun setScrapBoardListView(scrapBoardListView: ScrapBoardListView){
        this.scrapBoardListView = scrapBoardListView
    }

    fun setCreateScrapBoardView(createScrapBoardView: CreateScrapBoardView){
        this.createScrapBoardView = createScrapBoardView
    }

    fun setCancelScrapBoardView(cancelScrapBoardView: CancelScrapBoardView){
        this.cancelScrapBoardView = cancelScrapBoardView
    }

    fun showBoardList(){
        Log.d("boardList", "enter")
        val boardService = getRetrofit().create(RetrofitInterface::class.java)
        boardService.showBoardList(getJwt()!!).enqueue(object: Callback<BaseResponse<List<Board>>> {
            //응답이 왔을 때 처리
            override fun onResponse(call: Call<BaseResponse<List<Board>>>, response: Response<BaseResponse<List<Board>>>) {
                Log.d("boardList", "response")
                val resp: BaseResponse<List<Board>> = response.body()!!
                Log.d("boardListCode", resp.code.toString())

                when(resp.code){
                    //API code값 사용
                    200->boardListView.onBoardListSuccess(resp.code, resp.result)
                    else->boardListView.onBoardListFailure(resp.code)
                }
            }
            //실패했을 때 처리
            override fun onFailure(call: Call<BaseResponse<List<Board>>>, t: Throwable) {
                Log.d("boardListFail", t.toString())
            }

        })
    }

    fun showUnScrapBoardList(){
        Log.d("unScrapBoardList", "enter")
        val boardService = getRetrofit().create(RetrofitInterface::class.java)
        boardService.showUnScrapBoardList(getJwt()!!).enqueue(object: Callback<BaseResponse<List<Board>>> {
            //응답이 왔을 때 처리
            override fun onResponse(call: Call<BaseResponse<List<Board>>>, response: Response<BaseResponse<List<Board>>>) {
                Log.d("unScrapBoardList", "response")
                val resp: BaseResponse<List<Board>> = response.body()!!
                Log.d("unScrapBoardListCode", resp.code.toString())

                when(resp.code){
                    //API code값 사용
                    200->unScrapBoardListView.onUnScrapBoardListSuccess(resp.code, resp.result)
                    else->unScrapBoardListView.onUnScrapBoardListFailure(resp.code)
                }
            }
            //실패했을 때 처리
            override fun onFailure(call: Call<BaseResponse<List<Board>>>, t: Throwable) {
                Log.d("unScrapBoardListFail", t.toString())
            }

        })
    }

    fun showScrapBoardList(){
        Log.d("scrapBoardList", "enter")
        val boardService = getRetrofit().create(RetrofitInterface::class.java)
        boardService.showScrapBoardList(getJwt()!!).enqueue(object: Callback<BaseResponse<List<BoardFavorite>>> {
            //응답이 왔을 때 처리
            override fun onResponse(call: Call<BaseResponse<List<BoardFavorite>>>, response: Response<BaseResponse<List<BoardFavorite>>>) {
                Log.d("scrapBoardList", "response")

                val resp: BaseResponse<List<BoardFavorite>> = response.body()!!
                Log.d("scrapBoardListCode", resp.code.toString())

                when(resp.code){
                    //API code값 사용
                    200->scrapBoardListView.onScrapBoardListSuccess(resp.code, resp.result)
                    else->scrapBoardListView.onScrapBoardListFailure(resp.code)
                }
            }
            //실패했을 때 처리
            override fun onFailure(call: Call<BaseResponse<List<BoardFavorite>>>, t: Throwable) {
                Log.d("scrapBoardListFail", t.toString())
            }

        })
    }

    fun createScrapBoard(boardIdx: Int){
        Log.d("createScrapBoard", "enter")
        val boardService = getRetrofit().create(RetrofitInterface::class.java)
        boardService.createScrapBoard(boardIdx, getJwt()!!).enqueue(object: Callback<BaseResponse<String>> {
            //응답이 왔을 때 처리
            override fun onResponse(call: Call<BaseResponse<String>>, response: Response<BaseResponse<String>>) {
                Log.d("createScrapBoard", "response")

                val resp: BaseResponse<String> = response.body()!!
                Log.d("createScrapBoardCode", resp.code.toString())

                when(resp.code){
                    //API code값 사용
                    200->createScrapBoardView.onCreateScrapBoardSuccess(resp.code)
                    else->createScrapBoardView.onCreateScrapBoardFailure(resp.code)
                }
            }
            //실패했을 때 처리
            override fun onFailure(call: Call<BaseResponse<String>>, t: Throwable) {
                Log.d("createScrapBoardFail", t.toString())
            }

        })
    }

    fun cancelScrapBoard(boardIdx: Int){
        Log.d("cancelScrapBoard", "enter")
        val boardService = getRetrofit().create(RetrofitInterface::class.java)
        boardService.cancelScrapBoard(boardIdx, getJwt()!!).enqueue(object: Callback<BaseResponse<String>> {
            //응답이 왔을 때 처리
            override fun onResponse(call: Call<BaseResponse<String>>, response: Response<BaseResponse<String>>) {
                Log.d("cancelScrapBoard", "response")

                val resp: BaseResponse<String> = response.body()!!
                Log.d("cancelScrapBoardCode", resp.code.toString())

                when(resp.code){
                    //API code값 사용
                    200->cancelScrapBoardView.onCancelScrapBoardSuccess(resp.code)
                    else->cancelScrapBoardView.onCancelScrapBoardFailure(resp.code)
                }
            }
            //실패했을 때 처리
            override fun onFailure(call: Call<BaseResponse<String>>, t: Throwable) {
                Log.d("cancelScrapBoardFail", t.toString())
            }

        })
    }

}