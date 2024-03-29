package com.debri_main.debri.service

import android.util.Log
import com.debri_main.debri.utils.RetrofitInterface
import com.debri_main.debri.base.BaseResponse
import com.debri_main.debri.data.class_.LectureReview
import com.debri_main.debri.data.class_.ShowLectureReview
import com.debri_main.debri.data.curriculum.Review
import com.debri_main.debri.data.curriculum.ShowReview
import com.debri_main.debri.utils.getJwt
import com.debri_main.debri.utils.getRetrofit
import com.debri_main.debri.view.class_.CreateLectureReviewView
import com.debri_main.debri.view.class_.ShowLectureReviewView
import com.debri_main.debri.view.curriculum.CreateReviewView
import com.debri_main.debri.view.curriculum.ShowReviewView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ReviewService {
    private lateinit var createLectureReviewView: CreateLectureReviewView
    private lateinit var showLectureReviewView: ShowLectureReviewView

    private lateinit var createReviewView: CreateReviewView
    private lateinit var showReviewView: ShowReviewView

    fun setCreateLectureReviewView(createLectureReviewView: CreateLectureReviewView){
        this.createLectureReviewView = createLectureReviewView
    }

    fun setShowLectureReviewView(showLectureReviewView: ShowLectureReviewView){
        this.showLectureReviewView = showLectureReviewView
    }

    fun setCreateReviewView(createReviewView: CreateReviewView){
        this.createReviewView = createReviewView
    }

    fun setShowReviewView(showReviewView: ShowReviewView){
        this.showReviewView = showReviewView
    }

    //7.6 강의 리뷰 작성 api
    fun createLectureReview(lectureReview: LectureReview){
        Log.d("createLectureReview", "enter")
        //서비스 객체 생성
        val reviewService = getRetrofit().create(RetrofitInterface::class.java)
        reviewService.createLectureReview(lectureReview, getJwt()!!).enqueue(object: Callback<BaseResponse<LectureReview>> {
            //응답이 왔을 때 처리
            override fun onResponse(call: Call<BaseResponse<LectureReview>>, response: Response<BaseResponse<LectureReview>>) {
                Log.d("createLectureReview", "response")
                val resp: BaseResponse<LectureReview> = response.body()!!
                Log.d("createLectureReviewCode", resp.code.toString())
                when(val code = resp.code){
                    //API code값 사용
                    200->createLectureReviewView.onCreateLectureReviewSuccess(code, resp.result)
                    else-> createLectureReviewView.onCreateLectureReviewFailure(code)
                }
            }
            //실패했을 때 처리
            override fun onFailure(call: Call<BaseResponse<LectureReview>>, t: Throwable) {
                Log.d("createLectureReviewFail", t.toString())
            }

        })
    }

    //7.6.1 강의 리뷰 조회 api
    fun showLectureReview(pageNum: Int, lectureIdx : Int){
        Log.d("showLectureReview", "enter")
        //서비스 객체 생성
        val reviewService = getRetrofit().create(RetrofitInterface::class.java)
        reviewService.showLectureReview(pageNum, lectureIdx, getJwt()!!).enqueue(object: Callback<BaseResponse<ShowLectureReview>> {
            //응답이 왔을 때 처리
            override fun onResponse(call: Call<BaseResponse<ShowLectureReview>>, response: Response<BaseResponse<ShowLectureReview>>) {
                Log.d("showLectureReview", "response")
                val resp: BaseResponse<ShowLectureReview> = response.body()!!
                Log.d("showLectureReviewCode", resp.code.toString())
                when(val code = resp.code){
                    //API code값 사용
                    200->showLectureReviewView.onShowLectureReviewSuccess(code, resp.result)
                    else-> showLectureReviewView.onShowLectureReviewFailure(code)
                }
            }
            //실패했을 때 처리
            override fun onFailure(call: Call<BaseResponse<ShowLectureReview>>, t: Throwable) {
                Log.d("showLectureReviewFail", t.toString())
            }

        })
    }

    //8.12 커리큘럼 리뷰 작성 api
    fun createReview(review: Review){
        Log.d("createReview", "enter")
        //서비스 객체 생성
        val reviewService = getRetrofit().create(RetrofitInterface::class.java)
        reviewService.createReview(review, getJwt()!!).enqueue(object: Callback<BaseResponse<Review>> {
            //응답이 왔을 때 처리
            override fun onResponse(call: Call<BaseResponse<Review>>, response: Response<BaseResponse<Review>>) {
                Log.d("createReview", "response")
                val resp: BaseResponse<Review> = response.body()!!
                Log.d("createReviewCode", resp.code.toString())
                when(val code = resp.code){
                    //API code값 사용
                    200->createReviewView.onCreateReviewSuccess(code)
                    else-> createReviewView.onCreateReviewFailure(code)
                }
            }
            //실패했을 때 처리
            override fun onFailure(call: Call<BaseResponse<Review>>, t: Throwable) {
                Log.d("createReviewFail", t.toString())
            }

        })
    }

    //8.12.1 커리큘럼 리뷰 조회 api
    fun showReview(curriIdx : Int, pageNum : Int){
        Log.d("showReview", "enter")
        //서비스 객체 생성
        val reviewService = getRetrofit().create(RetrofitInterface::class.java)
        reviewService.showReview(curriIdx, pageNum, getJwt()!!).enqueue(object: Callback<BaseResponse<ShowReview>> {
            //응답이 왔을 때 처리
            override fun onResponse(call: Call<BaseResponse<ShowReview>>, response: Response<BaseResponse<ShowReview>>) {
                Log.d("showReview", "response")
                val resp: BaseResponse<ShowReview> = response.body()!!
                Log.d("showReviewCode", resp.code.toString())
                when(val code = resp.code){
                    //API code값 사용
                    200->showReviewView.onShowReviewSuccess(code, resp.result)
                    else-> showReviewView.onShowReviewFailure(code)
                }
            }
            //실패했을 때 처리
            override fun onFailure(call: Call<BaseResponse<ShowReview>>, t: Throwable) {
                Log.d("showReviewFail", t.toString())
            }

        })
    }

}