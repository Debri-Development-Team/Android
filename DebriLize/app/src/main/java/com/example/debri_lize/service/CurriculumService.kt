package com.example.debri_lize.service

import android.util.Log
import com.example.debri_lize.utils.RetrofitInterface
import com.example.debri_lize.base.BaseResponse
import com.example.debri_lize.data.curriculum.AddLecture
import com.example.debri_lize.data.curriculum.Curriculum
import com.example.debri_lize.data.curriculum.CurriculumDetail
import com.example.debri_lize.utils.getJwt
import com.example.debri_lize.utils.getRetrofit
import com.example.debri_lize.view.curriculum.AddLectureInCurriculumView
import com.example.debri_lize.view.curriculum.DeleteCurriculumView
import com.example.debri_lize.view.curriculum.ShowCurriculumDetailView
import com.example.debri_lize.view.curriculum.MyCurriculumListView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CurriculumService {
    private lateinit var myCurriculumListView: MyCurriculumListView
    private lateinit var addLectureInCurriculumView: AddLectureInCurriculumView
    private lateinit var showCurriculumDetailView: ShowCurriculumDetailView
    private lateinit var deleteCurriculumView: DeleteCurriculumView

    fun setMyCurriculumListView(myCurriculumListView: MyCurriculumListView){
        this.myCurriculumListView = myCurriculumListView
    }

    fun setShowCurriculumDetailView(showCurriculumDetailView: ShowCurriculumDetailView){
        this.showCurriculumDetailView = showCurriculumDetailView
    }

    fun setAddLectureInCurriculumView(addLectureInCurriculumView: AddLectureInCurriculumView){
        this.addLectureInCurriculumView = addLectureInCurriculumView
    }

    fun setDeleteCurriculumView(deleteCurriculumView: DeleteCurriculumView){
        this.deleteCurriculumView = deleteCurriculumView
    }

    //8.2 커리큘럼 리스트 조회 api : 내가 추가한 커리큘럼들
    fun myCurriculumList(){
        val curriculumService = getRetrofit().create(RetrofitInterface::class.java)

        curriculumService.myCurriculumList(getJwt()!!).enqueue(object: Callback<BaseResponse<List<Curriculum>>> {
            //응답이 왔을 때 처리
            override fun onResponse(call: Call<BaseResponse<List<Curriculum>>>, response: Response<BaseResponse<List<Curriculum>>>) {
                Log.d("myCurriculumList", "response")
                val resp: BaseResponse<List<Curriculum>> = response.body()!!
                Log.d("myCurriculumListCode", resp.code.toString())
                when(resp.code){
                    //API code값 사용
                    200->myCurriculumListView.onMyCurriculumListSuccess(resp.code, resp.result)
                    else->myCurriculumListView.onMyCurriculumListFailure(resp.code)
                }
            }
            //실패했을 때 처리
            override fun onFailure(call: Call<BaseResponse<List<Curriculum>>>, t: Throwable) {
                Log.d("myCurriculumListFail", t.toString())
            }

        })
    }

    //8.3 커리큘럼 상세 조회 api : 홈
    fun showCurriculumDetail(curriIdx : Int){
        val curriculumService = getRetrofit().create(RetrofitInterface::class.java)

        curriculumService.showCurriculumDetail(curriIdx, getJwt()!!).enqueue(object: Callback<BaseResponse<CurriculumDetail>> {
            //응답이 왔을 때 처리
            override fun onResponse(call: Call<BaseResponse<CurriculumDetail>>, response: Response<BaseResponse<CurriculumDetail>>) {
                Log.d("showCurriDetail", "response")
                val resp: BaseResponse<CurriculumDetail> = response.body()!!
                Log.d("showCurriDetailCode", resp.code.toString())
                when(resp.code){
                    //API code값 사용
                    200->showCurriculumDetailView.onShowCurriculumDetailSuccess(resp.code, resp.result)
                    else->showCurriculumDetailView.onShowCurriculumDetailFailure(resp.code)
                }
            }
            //실패했을 때 처리
            override fun onFailure(call: Call<BaseResponse<CurriculumDetail>>, t: Throwable) {
                Log.d("showCurriDetailFail", t.toString())
            }

        })
    }

    //8.5 강의자료 추가 api : 홈 > 새로운 강의자료 추가하기
    fun addLectureInCurriculum(addLecture: AddLecture){
        val curriculumService = getRetrofit().create(RetrofitInterface::class.java)

        curriculumService.addLectureInCurriculum(addLecture, getJwt()!!).enqueue(object: Callback<BaseResponse<String>> {
            //응답이 왔을 때 처리
            override fun onResponse(call: Call<BaseResponse<String>>, response: Response<BaseResponse<String>>) {
                Log.d("addLectureInCurri", "response")
                val resp: BaseResponse<String> = response.body()!!
                Log.d("addLectureInCurriCode", resp.code.toString())
                when(resp.code){
                    //API code값 사용
                    200->addLectureInCurriculumView.onAddLectureInCurriculumSuccess(resp.code)
                    else->addLectureInCurriculumView.onAddLectureInCurriculumFailure(resp.code)
                }
            }
            //실패했을 때 처리
            override fun onFailure(call: Call<BaseResponse<String>>, t: Throwable) {
                Log.d("addLectureInCurriFail", t.toString())
            }

        })
    }

    //8.6 커리큘럼 삭제 api
    fun deleteCurriculum(curriIdx: Int){
        val curriculumService = getRetrofit().create(RetrofitInterface::class.java)
        curriculumService.deleteCurriculum(curriIdx, getJwt()!!).enqueue(object: Callback<BaseResponse<String>> {
            //응답이 왔을 때 처리
            override fun onResponse(call: Call<BaseResponse<String>>, response: Response<BaseResponse<String>>) {
                Log.d("deleteCurri", "response")
                val resp: BaseResponse<String> = response.body()!!
                Log.d("deleteCurriCode", resp.code.toString())
                when(resp.code){
                    //API code값 사용
                    200->deleteCurriculumView.onDeleteCurriculumViewSuccess(resp.code)
                    else->deleteCurriculumView.onDeleteCurriculumViewFailure(resp.code)
                }
            }
            //실패했을 때 처리
            override fun onFailure(call: Call<BaseResponse<String>>, t: Throwable) {
                Log.d("deleteCurriFail", t.toString())
            }

        })
    }
}