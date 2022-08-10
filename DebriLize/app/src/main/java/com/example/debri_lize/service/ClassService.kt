package com.example.debri_lize.service

import android.util.Log
import com.example.debri_lize.utils.RetrofitInterface
import com.example.debri_lize.data.class_.Lecture
import com.example.debri_lize.data.class_.LectureScrap
import com.example.debri_lize.data.class_.LectureFilter
import com.example.debri_lize.base.BaseResponse
import com.example.debri_lize.utils.getJwt
import com.example.debri_lize.utils.getRetrofit
import com.example.debri_lize.view.class_.CancelLectureScrapView
import com.example.debri_lize.view.class_.CreateLectureScrapView
import com.example.debri_lize.view.class_.LectureFavoriteView
import com.example.debri_lize.view.class_.LectureFilterView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ClassService {
    private lateinit var lectureFavoriteView: LectureFavoriteView
    private lateinit var lectureFilterView: LectureFilterView

    private lateinit var createLectureScrapView: CreateLectureScrapView
    private lateinit var cancelLectureScrapView: CancelLectureScrapView


    fun setLectureFavoriteView(lectureFavoriteView: LectureFavoriteView){
        this.lectureFavoriteView = lectureFavoriteView
    }

    fun setLectureFilterView(lectureFilterView: LectureFilterView){
        this.lectureFilterView = lectureFilterView
    }

    fun setCreateLectureScrapView(createLectureScrapView: CreateLectureScrapView){
        this.createLectureScrapView = createLectureScrapView
    }

    fun setCancelLectureScrapView(cancelLectureScrapView: CancelLectureScrapView){
        this.cancelLectureScrapView = cancelLectureScrapView
    }

    fun showLectureFavorite(userIdx : Int){
        Log.d("lecturefavorite", "enter")
        val classService = getRetrofit().create(RetrofitInterface::class.java)
        classService.showLectureFavorite(userIdx, getJwt()!!).enqueue(object: Callback<BaseResponse<List<Lecture>>> {
            //응답이 왔을 때 처리
            override fun onResponse(call: Call<BaseResponse<List<Lecture>>>, response: Response<BaseResponse<List<Lecture>>>) {
                Log.d("lecturefavorite", "response")
                val resp: BaseResponse<List<Lecture>> = response.body()!!
                Log.d("lecturefavoriteCode", resp.code.toString())
                when(resp.code){
                    //API code값 사용
                    200->lectureFavoriteView.onLectureFavoriteSuccess(resp.code, resp.result)    //result를 받아서 UI를 구현해야함
                    else->lectureFavoriteView.onLectureFavoriteFailure(resp.code)   //무슨 오류인지 알아야하므로 code가져가기
                }
            }
            //실패했을 때 처리
            override fun onFailure(call: Call<BaseResponse<List<Lecture>>>, t: Throwable) {
                Log.d("lecturefavoriteFail",t.toString())
            }
        })
    }

    fun showLectureSearch(lectureFilter: LectureFilter){
        Log.d("lecturefilter", "enter")
        val classService = getRetrofit().create(RetrofitInterface::class.java)
        classService.showLectureSearch(lectureFilter.lang,lectureFilter.type,lectureFilter.price,lectureFilter.key, getJwt()!!).enqueue(object: Callback<BaseResponse<List<Lecture>>> {
            //응답이 왔을 때 처리
            override fun onResponse(call: Call<BaseResponse<List<Lecture>>>, response: Response<BaseResponse<List<Lecture>>>) {
                Log.d("lecturefilter", "response")
                val resp: BaseResponse<List<Lecture>> = response.body()!!
                Log.d("lecturefilterCode", resp.code.toString())
                when(resp.code){
                    //API code값 사용
                    200->lectureFilterView.onLectureFilterSuccess(resp.code, resp.result)    //result를 받아서 UI를 구현해야함
                    else->lectureFilterView.onLectureFilterFailure(resp.code)   //무슨 오류인지 알아야하므로 code가져가기
                }
            }
            //실패했을 때 처리
            override fun onFailure(call: Call<BaseResponse<List<Lecture>>>, t: Throwable) {
                Log.d("lecturefilterFail",t.toString())
            }
        })
    }

    fun createLectureScrap(lectureScrap: LectureScrap){
        Log.d("createLectureScrap", "enter")
        val classService = getRetrofit().create(RetrofitInterface::class.java)
        classService.createLectureScrap(lectureScrap, getJwt()!!).enqueue(object: Callback<BaseResponse<String>> {
            //응답이 왔을 때 처리
            override fun onResponse(call: Call<BaseResponse<String>>, response: Response<BaseResponse<String>>) {
                Log.d("createLectureScrap", "response")
                val resp: BaseResponse<String> = response.body()!!
                Log.d("createLectureScrapCode", resp.code.toString())
                when(resp.code){
                    //API code값 사용
                    200->createLectureScrapView.onCreateLectureScrapSuccess(resp.code)    //result를 받아서 UI를 구현해야함
                    else->createLectureScrapView.onCreateLectureScrapFailure(resp.code)   //무슨 오류인지 알아야하므로 code가져가기
                }
            }
            //실패했을 때 처리
            override fun onFailure(call: Call<BaseResponse<String>>, t: Throwable) {
                Log.d("createLectureScrapFail",t.toString())
            }
        })
    }

    fun cancelLectureScrap(lectureScrap: LectureScrap){
        Log.d("cancelLectureScrap", "enter")
        val classService = getRetrofit().create(RetrofitInterface::class.java)
        classService.cancelLectureScrap(lectureScrap, getJwt()!!).enqueue(object: Callback<BaseResponse<String>> {
            //응답이 왔을 때 처리
            override fun onResponse(call: Call<BaseResponse<String>>, response: Response<BaseResponse<String>>) {
                Log.d("cancelLectureScrap", "response")
                val resp: BaseResponse<String> = response.body()!!
                Log.d("cancelLectureScrapCode", resp.code.toString())
                when(resp.code){
                    //API code값 사용
                    200->cancelLectureScrapView.onCancelLectureScrapSuccess(resp.code)    //result를 받아서 UI를 구현해야함
                    else->cancelLectureScrapView.onCancelLectureScrapFailure(resp.code)   //무슨 오류인지 알아야하므로 code가져가기
                }
            }
            //실패했을 때 처리
            override fun onFailure(call: Call<BaseResponse<String>>, t: Throwable) {
                Log.d("cancelLectureScrapFail",t.toString())
            }
        })
    }

}