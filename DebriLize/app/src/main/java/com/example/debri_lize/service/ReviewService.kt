package com.example.debri_lize.service

import android.util.Log
import com.example.debri_lize.utils.RetrofitInterface
import com.example.debri_lize.base.BaseResponse
import com.example.debri_lize.data.class_.LectureReview
import com.example.debri_lize.data.curriculum.Review
import com.example.debri_lize.utils.getJwt
import com.example.debri_lize.utils.getRetrofit
import com.example.debri_lize.view.class_.CreateLectureReviewView
import com.example.debri_lize.view.class_.ShowLectureReviewView
import com.example.debri_lize.view.curriculum.CreateReviewView
import com.example.debri_lize.view.curriculum.ShowReviewView
import com.example.debri_lize.view.post.ReportCommentView
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
    fun showLectureReview(lectureIdx : Int){
        Log.d("showLectureReview", "enter")
        //서비스 객체 생성
        val reviewService = getRetrofit().create(RetrofitInterface::class.java)
        reviewService.showLectureReview(lectureIdx, getJwt()!!).enqueue(object: Callback<BaseResponse<List<LectureReview>>> {
            //응답이 왔을 때 처리
            override fun onResponse(call: Call<BaseResponse<List<LectureReview>>>, response: Response<BaseResponse<List<LectureReview>>>) {
                Log.d("showLectureReview", "response")
                val resp: BaseResponse<List<LectureReview>> = response.body()!!
                Log.d("showLectureReviewCode", resp.code.toString())
                when(val code = resp.code){
                    //API code값 사용
                    200->showLectureReviewView.onShowLectureReviewSuccess(code, resp.result)
                    else-> showLectureReviewView.onShowLectureReviewFailure(code)
                }
            }
            //실패했을 때 처리
            override fun onFailure(call: Call<BaseResponse<List<LectureReview>>>, t: Throwable) {
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
    fun showReview(curriIdx : Int){
        Log.d("showReview", "enter")
        //서비스 객체 생성
        val reviewService = getRetrofit().create(RetrofitInterface::class.java)
        reviewService.showReview(curriIdx, getJwt()!!).enqueue(object: Callback<BaseResponse<List<Review>>> {
            //응답이 왔을 때 처리
            override fun onResponse(call: Call<BaseResponse<List<Review>>>, response: Response<BaseResponse<List<Review>>>) {
                Log.d("showReview", "response")
                val resp: BaseResponse<List<Review>> = response.body()!!
                Log.d("showReviewCode", resp.code.toString())
                when(val code = resp.code){
                    //API code값 사용
                    200->showReviewView.onShowReviewSuccess(code, resp.result)
                    else-> showReviewView.onShowReviewFailure(code)
                }
            }
            //실패했을 때 처리
            override fun onFailure(call: Call<BaseResponse<List<Review>>>, t: Throwable) {
                Log.d("showReviewFail", t.toString())
            }

        })
    }

}