package com.example.debri_lize.service

import android.util.Log
import com.example.debri_lize.utils.RetrofitInterface
import com.example.debri_lize.data.post.ReportComment
import com.example.debri_lize.data.post.ReportPost
import com.example.debri_lize.base.BaseResponse
import com.example.debri_lize.utils.getJwt
import com.example.debri_lize.utils.getRetrofit
import com.example.debri_lize.view.post.ReportCommentView
import com.example.debri_lize.view.post.ReportPostView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ReportService {
    private lateinit var reportPostView: ReportPostView

    private lateinit var reportCommentView: ReportCommentView

    fun setReportPostView(reportPostView: ReportPostView){
        this.reportPostView = reportPostView
    }

    fun setReportCommentView(reportCommentView: ReportCommentView){
        this.reportCommentView = reportCommentView
    }

    fun reportPost(report: ReportPost){
        Log.d("reportPost", "enter")
        //서비스 객체 생성
        val reportService = getRetrofit().create(RetrofitInterface::class.java)

        reportService.reportPost(report, getJwt()!!).enqueue(object: Callback<BaseResponse<String>> {
            //응답이 왔을 때 처리

            override fun onResponse(call: Call<BaseResponse<String>>, response: Response<BaseResponse<String>>) {
                Log.d("reportPost", "response")
                val resp: BaseResponse<String> = response.body()!!
                Log.d("reportPostCode", resp.code.toString())
                when(val code = resp.code){
                    //API code값 사용
                    200->reportPostView.onReportPostSuccess(code)
                    else-> reportPostView.onReportPostFailure(code)

                }
            }

            //실패했을 때 처리
            override fun onFailure(call: Call<BaseResponse<String>>, t: Throwable) {
                Log.d("reportPostFail", t.toString())
            }

        })
    }

    fun reportComment(report: ReportComment){
        Log.d("reportComment", "enter")
        //서비스 객체 생성
        val reportService = getRetrofit().create(RetrofitInterface::class.java)
        reportService.reportComment(report, getJwt()!!).enqueue(object: Callback<BaseResponse<String>> {
            //응답이 왔을 때 처리

            override fun onResponse(call: Call<BaseResponse<String>>, response: Response<BaseResponse<String>>) {
                Log.d("reportComment", "response")
                val resp: BaseResponse<String> = response.body()!!
                Log.d("reportCommentCode", resp.code.toString())
                when(val code = resp.code){
                    //API code값 사용
                    200->reportCommentView.onReportCommentSuccess(code)
                    else-> reportCommentView.onReportCommentFailure(code)

                }
            }

            //실패했을 때 처리
            override fun onFailure(call: Call<BaseResponse<String>>, t: Throwable) {
                Log.d("reportCommentFail", t.toString())
            }

        })
    }
}