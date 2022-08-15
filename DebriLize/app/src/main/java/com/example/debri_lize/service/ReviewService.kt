package com.example.debri_lize.service

import android.util.Log
import com.example.debri_lize.utils.RetrofitInterface
import com.example.debri_lize.base.BaseResponse
import com.example.debri_lize.data.curriculum.Review
import com.example.debri_lize.utils.getJwt
import com.example.debri_lize.utils.getRetrofit
import com.example.debri_lize.view.curriculum.CreateReviewView
import com.example.debri_lize.view.curriculum.ShowReviewView
import com.example.debri_lize.view.post.ReportCommentView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ReviewService {
    private lateinit var createReviewView: CreateReviewView

    private lateinit var showReviewView: ShowReviewView

    fun setCreateReviewView(createReviewView: CreateReviewView){
        this.createReviewView = createReviewView
    }

    fun setShowReviewView(showReviewView: ShowReviewView){
        this.showReviewView = showReviewView
    }

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