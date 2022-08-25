package com.example.debri_lize.service

import android.util.Log
import com.example.debri_lize.utils.RetrofitInterface
import com.example.debri_lize.base.BaseResponse
import com.example.debri_lize.data.curriculum.*
import com.example.debri_lize.utils.getJwt
import com.example.debri_lize.utils.getRetrofit
import com.example.debri_lize.view.curriculum.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CurriculumService {
    private lateinit var createCurriculumView: CreateCurriculumView

    private lateinit var myCurriculumListView: MyCurriculumListView
    private lateinit var addLectureInCurriculumView: AddLectureInCurriculumView
    private lateinit var showCurriculumDetailView: ShowCurriculumDetailView

    private lateinit var editCurriculumNameView: EditCurriculumNameView
    private lateinit var editCurriculumVisibleView: EditCurriculumVisibleView
    private lateinit var editCurriculumStatusView: EditCurriculumStatusView

    private lateinit var deleteCurriculumView: DeleteCurriculumView
    private lateinit var resetCurriculumView: ResetCurriculumView

    private lateinit var completeChapterView: CompleteChapterView

    private lateinit var createCurriLikeView: CreateCurriLikeView
    private lateinit var cancelCurriLikeView: CancelCurriLikeView

    private lateinit var showScrapCurriListView: ShowScrapCurriListView
    private lateinit var showTop10ListView: ShowTop10ListView

    private lateinit var showGetNewCurriListView: ShowGetNewCurriListView

    private lateinit var copyCurriculumView: CopyCurriculumView

    fun setCreateCurriculumView(createCurriculumView: CreateCurriculumView){
        this.createCurriculumView = createCurriculumView
    }

    fun setMyCurriculumListView(myCurriculumListView: MyCurriculumListView){
        this.myCurriculumListView = myCurriculumListView
    }

    fun setShowCurriculumDetailView(showCurriculumDetailView: ShowCurriculumDetailView){
        this.showCurriculumDetailView = showCurriculumDetailView
    }

    fun setShowTop10ListView(showTop10ListView: ShowTop10ListView){
        this.showTop10ListView = showTop10ListView
    }

    fun setCreateCurriLikeView(createCurriLikeView: CreateCurriLikeView){
        this.createCurriLikeView = createCurriLikeView
    }

    fun setCancelCurriLikeView(cancelCurriLikeView: CancelCurriLikeView){
        this.cancelCurriLikeView = cancelCurriLikeView
    }

    fun setShowScrapCurriListView(showScrapCurriListView: ShowScrapCurriListView){
        this.showScrapCurriListView = showScrapCurriListView
    }

    fun setShowGetNewCurriListView(showGetNewCurriListView: ShowGetNewCurriListView){
        this.showGetNewCurriListView = showGetNewCurriListView
    }

    //edit
    fun setEditCurriculumNameView(editCurriculumNameView: EditCurriculumNameView){
        this.editCurriculumNameView = editCurriculumNameView
    }

    fun setEditCurriculumVisibleView(editCurriculumVisibleView: EditCurriculumVisibleView){
        this.editCurriculumVisibleView = editCurriculumVisibleView
    }

    fun setEditCurriculumStatusView(editCurriculumStatusView: EditCurriculumStatusView){
        this.editCurriculumStatusView = editCurriculumStatusView
    }

    fun setAddLectureInCurriculumView(addLectureInCurriculumView: AddLectureInCurriculumView){
        this.addLectureInCurriculumView = addLectureInCurriculumView
    }

    fun setDeleteCurriculumView(deleteCurriculumView: DeleteCurriculumView){
        this.deleteCurriculumView = deleteCurriculumView
    }

    fun setResetCurriculumView(resetCurriculumView: ResetCurriculumView){
        this.resetCurriculumView = resetCurriculumView
    }

    fun setCopyCurriculumView(copyCurriculumView: CopyCurriculumView){
        this.copyCurriculumView = copyCurriculumView
    }

    //chapter
    fun setCompleteChapterView(completeChapterView: CompleteChapterView){
        this.completeChapterView = completeChapterView
    }

    //8.1 커리큘럼 생성 api
    fun createCurriculum(newCurriculum : NewCurriculum){
        val curriculumService = getRetrofit().create(RetrofitInterface::class.java)
        curriculumService.createCurriculum(newCurriculum, getJwt()!!).enqueue(object: Callback<BaseResponse<CurriIdx>> {
            //응답이 왔을 때 처리
            override fun onResponse(call: Call<BaseResponse<CurriIdx>>, response: Response<BaseResponse<CurriIdx>>) {
                Log.d("createCurri", "response")
                val resp: BaseResponse<CurriIdx> = response.body()!!
                Log.d("createCurriCode", resp.code.toString())
                when(resp.code){
                    //API code값 사용
                    200->createCurriculumView.onCreateCurriculumSuccess(resp.code)
                    else->createCurriculumView.onCreateCurriculumFailure(resp.code)
                }
            }
            //실패했을 때 처리
            override fun onFailure(call: Call<BaseResponse<CurriIdx>>, t: Throwable) {
                Log.d("createCurriFail", t.toString())
            }

        })
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
        Log.d("curriidx","$curriIdx")
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

    //8.4.1 커리큘럼 제목 수정 api
    fun editCurriculumName(editCurriculumName : EditCurriculumName){
        val curriculumService = getRetrofit().create(RetrofitInterface::class.java)

        curriculumService.editCurriculumName(editCurriculumName, getJwt()!!).enqueue(object: Callback<BaseResponse<String>> {
            //응답이 왔을 때 처리
            override fun onResponse(call: Call<BaseResponse<String>>, response: Response<BaseResponse<String>>) {
                Log.d("editCurriName", "response")
                val resp: BaseResponse<String> = response.body()!!
                Log.d("editCurriNameCode", resp.code.toString())
                when(resp.code){
                    //API code값 사용
                    200->editCurriculumNameView.onEditCurriculumNameSuccess(resp.code)
                    else->editCurriculumNameView.onEditCurriculumNameFailure(resp.code)
                }
            }
            //실패했을 때 처리
            override fun onFailure(call: Call<BaseResponse<String>>, t: Throwable) {
                Log.d("editCurriNameFail", t.toString())
            }

        })
    }

    //8.4.2 커리큘럼 공유 상태 수정 api
    fun editCurriculumVisible(editCurriculumVisible : EditCurriculumVisible){
        val curriculumService = getRetrofit().create(RetrofitInterface::class.java)

        curriculumService.editCurriculumVisible(editCurriculumVisible, getJwt()!!).enqueue(object: Callback<BaseResponse<String>> {
            //응답이 왔을 때 처리
            override fun onResponse(call: Call<BaseResponse<String>>, response: Response<BaseResponse<String>>) {
                Log.d("editCurriVisible", "response")
                val resp: BaseResponse<String> = response.body()!!
                Log.d("editCurriVisibleCode", resp.code.toString())
                when(resp.code){
                    //API code값 사용
                    200->editCurriculumVisibleView.onEditCurriculumVisibleSuccess(resp.code)
                    else->editCurriculumVisibleView.onEditCurriculumVisibleFailure(resp.code)
                }
            }
            //실패했을 때 처리
            override fun onFailure(call: Call<BaseResponse<String>>, t: Throwable) {
                Log.d("editCurriVisibleFail", t.toString())
            }

        })
    }

    //8.4.3 커리큘럼 활성 상태 수정 api
    fun editCurriculumStatus(editCurriculumStatus : EditCurriculumStatus){
        val curriculumService = getRetrofit().create(RetrofitInterface::class.java)

        curriculumService.editCurriculumStatus(editCurriculumStatus, getJwt()!!).enqueue(object: Callback<BaseResponse<String>> {
            //응답이 왔을 때 처리
            override fun onResponse(call: Call<BaseResponse<String>>, response: Response<BaseResponse<String>>) {
                Log.d("editCurriStatus", "response")
                val resp: BaseResponse<String> = response.body()!!
                Log.d("editCurriStatusCode", resp.code.toString())
                when(resp.code){
                    //API code값 사용
                    200->editCurriculumStatusView.onEditCurriculumStatusSuccess(resp.code)
                    else->editCurriculumStatusView.onEditCurriculumStatusFailure(resp.code)
                }
            }
            //실패했을 때 처리
            override fun onFailure(call: Call<BaseResponse<String>>, t: Throwable) {
                Log.d("editCurriStatusFail", t.toString())
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
                Log.d("response","${response}")
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

    //8.7 챕터 수강 완료 및 취소 api
    fun completeChapter(completeChapter : CompleteChapter){
        Log.d("chapterComplete", completeChapter.toString())
        val curriculumService = getRetrofit().create(RetrofitInterface::class.java)
        curriculumService.completeChapter(completeChapter, getJwt()!!).enqueue(object: Callback<BaseResponse<String>> {
            //응답이 왔을 때 처리
            override fun onResponse(call: Call<BaseResponse<String>>, response: Response<BaseResponse<String>>) {
                Log.d("completeCh", "response")
                val resp: BaseResponse<String> = response.body()!!
                Log.d("completeChCode", resp.code.toString())
                when(resp.code){
                    //API code값 사용
                    200->completeChapterView.onCompleteChapterSuccess(resp.code)
                    else->completeChapterView.onCompleteChapterFailure(resp.code)
                }
            }
            //실패했을 때 처리
            override fun onFailure(call: Call<BaseResponse<String>>, t: Throwable) {
                Log.d("completeChFail", t.toString())
            }

        })
    }

    //8.8 커리큘럼 좋아요(추천) 생성 api
    fun createCurriLike(curriIdx: Int){
        val curriculumService = getRetrofit().create(RetrofitInterface::class.java)
        curriculumService.createCurriLike(curriIdx, getJwt()!!).enqueue(object: Callback<BaseResponse<CurriculumLike>> {
            //응답이 왔을 때 처리
            override fun onResponse(call: Call<BaseResponse<CurriculumLike>>, response: Response<BaseResponse<CurriculumLike>>) {
                Log.d("createCurriLike", "response")
                val resp: BaseResponse<CurriculumLike> = response.body()!!
                Log.d("createCurriLikeCode", resp.code.toString())
                when(resp.code){
                    //API code값 사용
                    200->createCurriLikeView.onCreateCurriLikeSuccess(resp.code)
                    else->createCurriLikeView.onCreateCurriLikeFailure(resp.code)
                }
            }
            //실패했을 때 처리
            override fun onFailure(call: Call<BaseResponse<CurriculumLike>>, t: Throwable) {
                Log.d("createCurriLikeFail", t.toString())
            }
        })
    }

    //8.9 커리큘럼 좋아요(추천) 취소 api
    fun cancelCurriLike(scrapIdx : Int){
        val curriculumService = getRetrofit().create(RetrofitInterface::class.java)
        curriculumService.cancelCurriLike(scrapIdx, getJwt()!!).enqueue(object: Callback<BaseResponse<String>> {
            //응답이 왔을 때 처리
            override fun onResponse(call: Call<BaseResponse<String>>, response: Response<BaseResponse<String>>) {
                Log.d("cancelCurriLike", "response")
                val resp: BaseResponse<String> = response.body()!!
                Log.d("cancelCurriLikeCode", resp.code.toString())
                when(resp.code){
                    //API code값 사용
                    200->cancelCurriLikeView.onDeleteCurriLikeSuccess(resp.code)
                    else->cancelCurriLikeView.onDeleteCurriLikeFailure(resp.code)
                }
            }
            //실패했을 때 처리
            override fun onFailure(call: Call<BaseResponse<String>>, t: Throwable) {
                Log.d("cancelCurriLikeFail", t.toString())
            }
        })
    }

    //8.10 커리큘럼 좋아요(추천) 리스트 조회 api
    fun showScrapCurriList(){
        val curriculumService = getRetrofit().create(RetrofitInterface::class.java)
        curriculumService.showScrapCurriList(getJwt()!!).enqueue(object: Callback<BaseResponse<List<Curriculum>>> {
            //응답이 왔을 때 처리
            override fun onResponse(call: Call<BaseResponse<List<Curriculum>>>, response: Response<BaseResponse<List<Curriculum>>>) {
                Log.d("showScrapCurriList", "response")
                val resp: BaseResponse<List<Curriculum>> = response.body()!!
                Log.d("showScrapCurriListCode", resp.code.toString())
                when(resp.code){
                    //API code값 사용
                    200->showScrapCurriListView.onShowScrapCurriListSuccess(resp.code, resp.result)
                    else->showScrapCurriListView.onShowScrapCurriListFailure(resp.code)
                }
            }
            //실패했을 때 처리
            override fun onFailure(call: Call<BaseResponse<List<Curriculum>>>, t: Throwable) {
                Log.d("showScrapCurriListFail", t.toString())
            }

        })
    }

    //8.10.1 커리큘럼 좋아요(추천) TOP 10 리스트 조회 api
    fun showTop10List(){
        val curriculumService = getRetrofit().create(RetrofitInterface::class.java)

        curriculumService.showTop10List(getJwt()!!).enqueue(object: Callback<BaseResponse<List<Top10>>> {
            //응답이 왔을 때 처리
            override fun onResponse(call: Call<BaseResponse<List<Top10>>>, response: Response<BaseResponse<List<Top10>>>) {
                Log.d("showTop10List", "response")
                val resp: BaseResponse<List<Top10>> = response.body()!!
                Log.d("showTop10ListCode", resp.code.toString())
                when(resp.code){
                    //API code값 사용
                    200->showTop10ListView.onShowTop10ListSuccess(resp.code, resp.result)
                    else->showTop10ListView.onShowTop10ListFailure(resp.code)
                }
            }
            //실패했을 때 처리
            override fun onFailure(call: Call<BaseResponse<List<Top10>>>, t: Throwable) {
                Log.d("showTop10ListFail", t.toString())
            }

        })
    }

    //8.11 커리큘럼 리셋 api
    fun resetCurriculum(curriculumIdx : Int){
        val curriculumService = getRetrofit().create(RetrofitInterface::class.java)
        curriculumService.resetCurriculum(curriculumIdx, getJwt()!!).enqueue(object: Callback<BaseResponse<String>> {
            //응답이 왔을 때 처리
            override fun onResponse(call: Call<BaseResponse<String>>, response: Response<BaseResponse<String>>) {
                Log.d("resetCurri", "response")
                val resp: BaseResponse<String> = response.body()!!
                Log.d("resetCurriCode", resp.code.toString())
                when(resp.code){
                    //API code값 사용
                    200->resetCurriculumView.onResetCurriculumSuccess(resp.code)
                    else->resetCurriculumView.onResetCurriculumFailure(resp.code)
                }
            }
            //실패했을 때 처리
            override fun onFailure(call: Call<BaseResponse<String>>, t: Throwable) {
                Log.d("resetCurriFail", t.toString())
            }

        })
    }

    //8.13 커리큘럼 복붙 api
    fun copyCurriculum(copyCurriculum: CopyCurriculum){
        val curriculumService = getRetrofit().create(RetrofitInterface::class.java)
        curriculumService.copyCurriculum(copyCurriculum, getJwt()!!).enqueue(object: Callback<BaseResponse<Copy>> {
            //응답이 왔을 때 처리
            override fun onResponse(call: Call<BaseResponse<Copy>>, response: Response<BaseResponse<Copy>>) {
                Log.d("copyCurri", "response")
                val resp: BaseResponse<Copy> = response.body()!!
                Log.d("copyCurriCode", resp.code.toString())
                Log.d("copy", resp.result.curriculumIdx.toString())
                when(resp.code){
                    //API code값 사용

                    200->copyCurriculumView.onCopyCurriculumSuccess(resp.code)
                    else->copyCurriculumView.onCopyCurriculumFailure(resp.code)
                }
            }
            //실패했을 때 처리
            override fun onFailure(call: Call<BaseResponse<Copy>>, t: Throwable) {
                Log.d("copyCurriFail", t.toString())
            }

        })
    }

    //8.14 최신 커리큘럼 리스트 조회 api
    fun showGetNewCurriList(){
        val curriculumService = getRetrofit().create(RetrofitInterface::class.java)

        curriculumService.showTop10List(getJwt()!!).enqueue(object: Callback<BaseResponse<List<Top10>>> {
            //응답이 왔을 때 처리
            override fun onResponse(call: Call<BaseResponse<List<Top10>>>, response: Response<BaseResponse<List<Top10>>>) {
                Log.d("showGetNewCurriList", "response")
                val resp: BaseResponse<List<Top10>> = response.body()!!
                Log.d("showGetNewCurriListCode", resp.code.toString())
                when(resp.code){
                    //API code값 사용
                    200->showGetNewCurriListView.onShowGetNewCurriListSuccess(resp.code, resp.result)
                    else->showGetNewCurriListView.onShowGetNewCurriListFailure(resp.code)
                }
            }
            //실패했을 때 처리
            override fun onFailure(call: Call<BaseResponse<List<Top10>>>, t: Throwable) {
                Log.d("showGetNewCurriListFail", t.toString())
            }

        })
    }
}