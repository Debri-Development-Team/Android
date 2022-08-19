package com.example.debri_lize.service

import android.util.Log
import com.example.debri_lize.utils.RetrofitInterface
import com.example.debri_lize.base.BaseResponse
import com.example.debri_lize.data.curriculum.Review
import com.example.debri_lize.data.curriculum.RoadMap
import com.example.debri_lize.data.curriculum.RoadMapList
import com.example.debri_lize.utils.getJwt
import com.example.debri_lize.utils.getRetrofit
import com.example.debri_lize.view.curriculum.CreateReviewView
import com.example.debri_lize.view.curriculum.ShowReviewView
import com.example.debri_lize.view.curriculum.ShowRoadMapDetailView
import com.example.debri_lize.view.curriculum.ShowRoadMapListView
import com.example.debri_lize.view.post.ReportCommentView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RoadMapService {
    private lateinit var showRoadMapListView: ShowRoadMapListView

    private lateinit var showRoadMapDetailView: ShowRoadMapDetailView

    fun setShowRoadMapListView(showRoadMapListView: ShowRoadMapListView){
        this.showRoadMapListView = showRoadMapListView
    }

    fun setShowRoadMapDetailView(showRoadMapDetailView: ShowRoadMapDetailView){
        this.showRoadMapDetailView = showRoadMapDetailView
    }

    //7.5 로드맵 리스트 조회 api
    fun showRoadMapList(){
        Log.d("showRoadMap", "enter")
        //서비스 객체 생성
        val reviewService = getRetrofit().create(RetrofitInterface::class.java)
        reviewService.showRoadMapList(getJwt()!!).enqueue(object: Callback<BaseResponse<List<RoadMapList>>> {
            //응답이 왔을 때 처리
            override fun onResponse(call: Call<BaseResponse<List<RoadMapList>>>, response: Response<BaseResponse<List<RoadMapList>>>) {
                Log.d("showRoadMap", "response")
                val resp: BaseResponse<List<RoadMapList>> = response.body()!!
                Log.d("showRoadMapCode", resp.code.toString())
                when(val code = resp.code){
                    //API code값 사용
                    200->showRoadMapListView.onShowRoadMapListSuccess(code, resp.result)
                    else-> showRoadMapListView.onShowRoadMapListFailure(code)
                }
            }
            //실패했을 때 처리
            override fun onFailure(call: Call<BaseResponse<List<RoadMapList>>>, t: Throwable) {
                Log.d("showRoadMapFail", t.toString())
            }

        })
    }

    //7.5.1 로드맵 상세 조회 api
    fun showRoadMapDetail(mod : String){
        Log.d("showReview", "enter")
        //서비스 객체 생성
        val reviewService = getRetrofit().create(RetrofitInterface::class.java)
        reviewService.showRoadMapDetail(mod, getJwt()!!).enqueue(object: Callback<BaseResponse<List<RoadMap>>> {
            //응답이 왔을 때 처리
            override fun onResponse(call: Call<BaseResponse<List<RoadMap>>>, response: Response<BaseResponse<List<RoadMap>>>) {
                Log.d("showReview", "response")
                val resp: BaseResponse<List<RoadMap>> = response.body()!!
                Log.d("showReviewCode", resp.code.toString())
                when(val code = resp.code){
                    //API code값 사용
                    200->showRoadMapDetailView.onShowRoadMapDetailSuccess(code, resp.result)
                    else-> showRoadMapDetailView.onShowRoadMapDetailFailure(code)
                }
            }
            //실패했을 때 처리
            override fun onFailure(call: Call<BaseResponse<List<RoadMap>>>, t: Throwable) {
                Log.d("showReviewFail", t.toString())
            }

        })
    }

}