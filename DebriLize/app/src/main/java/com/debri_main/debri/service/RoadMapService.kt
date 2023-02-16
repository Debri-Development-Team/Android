package com.debri_main.debri.service

import android.util.Log
import com.debri_main.debri.utils.RetrofitInterface
import com.debri_main.debri.base.BaseResponse
import com.debri_main.debri.data.curriculum.*
import com.debri_main.debri.utils.getJwt
import com.debri_main.debri.utils.getRetrofit
import com.debri_main.debri.view.curriculum.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RoadMapService {
    private lateinit var showRoadMapListView: ShowRoadMapListView

    private lateinit var showRoadMapDetailView: ShowRoadMapDetailView
    private lateinit var copyRoadMapView : CopyRoadMapView

    fun setShowRoadMapListView(showRoadMapListView: ShowRoadMapListView){
        this.showRoadMapListView = showRoadMapListView
    }

    fun setShowRoadMapDetailView(showRoadMapDetailView: ShowRoadMapDetailView){
        this.showRoadMapDetailView = showRoadMapDetailView
    }

    fun setCopyRoadMapView(copyRoadMapView: CopyRoadMapView){
        this.copyRoadMapView = copyRoadMapView
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

    //7.5.2 로드맵 to 커리큘럼 api
    fun copyRoadmapToCurri(copyRoadMap: copyRoadMap){
        Log.d("copyRoadMap", "enter")
        //서비스 객체 생성
        val reviewService = getRetrofit().create(RetrofitInterface::class.java)
        reviewService.copyRoadmapToCurri(copyRoadMap, getJwt()!!).enqueue(object: Callback<BaseResponse<CurriIdx>> {
            //응답이 왔을 때 처리
            override fun onResponse(call: Call<BaseResponse<CurriIdx>>, response: Response<BaseResponse<CurriIdx>>) {
                Log.d("copyRoadMap", "response")
                val resp: BaseResponse<CurriIdx> = response.body()!!
                Log.d("copyRoadMapCode", resp.code.toString())
                when(val code = resp.code){
                    //API code값 사용
                    200->copyRoadMapView.onCopyRoadMapSuccess(code)
                    else-> copyRoadMapView.onCopyRoadMapFailure(code)
                }
            }
            //실패했을 때 처리
            override fun onFailure(call: Call<BaseResponse<CurriIdx>>, t: Throwable) {
                Log.d("copyRoadMapFail", t.toString())
            }

        })
    }

}