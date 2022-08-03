package com.example.debri_lize.service

import android.util.Log
import com.example.debri_lize.data.RetrofitInterface
import com.example.debri_lize.data.class_.LectureFilter
import com.example.debri_lize.response.LectureResponse
import com.example.debri_lize.utils.getJwt
import com.example.debri_lize.utils.getRetrofit
import com.example.debri_lize.view.class_.LectureFavoriteView
import com.example.debri_lize.view.class_.LectureFilterView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ClassService {
    private lateinit var lectureFavoriteView: LectureFavoriteView
    private lateinit var lectureFilterView: LectureFilterView

    fun setLectureFavoriteView(lectureFavoriteView: LectureFavoriteView){
        this.lectureFavoriteView = lectureFavoriteView
    }

    fun setLectureFilterView(lectureFilterView: LectureFilterView){
        this.lectureFilterView = lectureFilterView
    }

    fun showLectureFavorite(userIdx : Int){
        Log.d("lecturefavorite", "enter")
        val classService = getRetrofit().create(RetrofitInterface::class.java)
        classService.showLectureFavorite(userIdx, getJwt()!!).enqueue(object: Callback<LectureResponse> {
            //응답이 왔을 때 처리
            override fun onResponse(call: Call<LectureResponse>, response: Response<LectureResponse>) {
                Log.d("lecturefavorite", "response")
                val resp: LectureResponse = response.body()!!
                Log.d("lecturefavoriteCode", resp.code.toString())
                when(resp.code){
                    //API code값 사용
                    200->lectureFavoriteView.onLectureFavoriteSuccess(resp.code, resp.result)    //result를 받아서 UI를 구현해야함
                    else->lectureFavoriteView.onLectureFavoriteFailure(resp.code)   //무슨 오류인지 알아야하므로 code가져가기
                }
            }
            //실패했을 때 처리
            override fun onFailure(call: Call<LectureResponse>, t: Throwable) {
                Log.d("lecturefavoriteFail",t.toString())
            }
        })
    }

    fun showLectureSearch(lectureFilter: LectureFilter){
        Log.d("lecturefilter", "enter")
        val classService = getRetrofit().create(RetrofitInterface::class.java)
        classService.showLectureSearch(lectureFilter.lang,lectureFilter.type,lectureFilter.price,lectureFilter.key, getJwt()!!).enqueue(object: Callback<LectureResponse> {
            //응답이 왔을 때 처리
            override fun onResponse(call: Call<LectureResponse>, response: Response<LectureResponse>) {
                Log.d("lecturefilter", "response")
                val resp: LectureResponse = response.body()!!
                Log.d("lecturefilterCode", resp.code.toString())
                when(resp.code){
                    //API code값 사용
                    200->lectureFilterView.onLectureFilterSuccess(resp.code, resp.result)    //result를 받아서 UI를 구현해야함
                    else->lectureFilterView.onLectureFilterFailure(resp.code)   //무슨 오류인지 알아야하므로 code가져가기
                }
            }
            //실패했을 때 처리
            override fun onFailure(call: Call<LectureResponse>, t: Throwable) {
                Log.d("lecturefilterFail",t.toString())
            }
        })
    }

}