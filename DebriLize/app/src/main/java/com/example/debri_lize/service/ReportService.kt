package com.example.debri_lize.service

import android.util.Log
import com.example.debri_lize.data.RetrofitInterface
import com.example.debri_lize.data.post.PostReport
import com.example.debri_lize.response.ReportResponse
import com.example.debri_lize.response.TokenResponse
import com.example.debri_lize.utils.getJwt
import com.example.debri_lize.utils.getRetrofit
import com.example.debri_lize.view.post.ReportPostView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ReportService {
    private lateinit var reportPostView: ReportPostView

    fun setReportPostView(reportPostView: ReportPostView){
        this.reportPostView = reportPostView
    }

    fun reportPost(postReport: PostReport){
        Log.d("reportPost", "enter")
        //서비스 객체 생성
        val reportService = getRetrofit().create(RetrofitInterface::class.java)

        reportService.reportPost(postReport, getJwt()!!).enqueue(object: Callback<ReportResponse> {
            //응답이 왔을 때 처리

            override fun onResponse(call: Call<ReportResponse>, response: Response<ReportResponse>) {
                Log.d("reportPost", "response")
                val resp:ReportResponse = response.body()!!
                Log.d("reportPostCode", resp.code.toString())
                when(val code = resp.code){
                    //API code값 사용
                    200->reportPostView.onReportPostSuccess(code)
                    else-> reportPostView.onReportPostFailure(code)

                }
            }

            //실패했을 때 처리
            override fun onFailure(call: Call<ReportResponse>, t: Throwable) {
                Log.d("reportPostFail", t.toString())
            }

        })
    }
}