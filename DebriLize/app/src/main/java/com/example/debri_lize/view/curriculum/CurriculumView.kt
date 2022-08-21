package com.example.debri_lize.view.curriculum

import com.example.debri_lize.data.curriculum.Curriculum
import com.example.debri_lize.data.curriculum.CurriculumDetail
import com.example.debri_lize.data.curriculum.CurriculumLike
import com.example.debri_lize.data.curriculum.Top10

//8.1 커리큘럼 생성 api
interface CreateCurriculumView {
    fun onCreateCurriculumSuccess(code: Int)
    fun onCreateCurriculumFailure(code : Int)
}

//8.2 커리큘럼 리스트 조회 api : 내가 추가한 커리큘럼들
interface MyCurriculumListView {
    fun onMyCurriculumListSuccess(code: Int, result: List<Curriculum>)
    fun onMyCurriculumListFailure(code : Int)
}

//8.3 커리큘럼 상세 조회 api : 홈
interface ShowCurriculumDetailView {
    fun onShowCurriculumDetailSuccess(code: Int, result: CurriculumDetail)
    fun onShowCurriculumDetailFailure(code : Int)
}

//8.4.1 커리큘럼 제목 수정 api
interface EditCurriculumNameView {
    fun onEditCurriculumNameSuccess(code: Int)
    fun onEditCurriculumNameFailure(code : Int)
}

//8.4.2 커리큘럼 공유 상태 수정 api
interface EditCurriculumVisibleView {
    fun onEditCurriculumVisibleSuccess(code: Int)
    fun onEditCurriculumVisibleFailure(code : Int)
}

//8.4.3 커리큘럼 활성 상태 수정 api
interface EditCurriculumStatusView {
    fun onEditCurriculumStatusSuccess(code: Int)
    fun onEditCurriculumStatusFailure(code : Int)
}

//8.5 강의자료 추가 api : 홈 > 새로운 강의자료 추가하기
interface AddLectureInCurriculumView {
    fun onAddLectureInCurriculumSuccess(code: Int)
    fun onAddLectureInCurriculumFailure(code : Int)
}

//8.6 커리큘럼 삭제 api
interface DeleteCurriculumView {
    fun onDeleteCurriculumViewSuccess(code: Int)
    fun onDeleteCurriculumViewFailure(code : Int)
}

//8.7 챕터 수강 완료 및 취소 api
interface CompleteChapterView {
    fun onCompleteChapterSuccess(code: Int)
    fun onCompleteChapterFailure(code : Int)
}

//8.8 커리큘럼 좋아요(추천) 생성 api
interface CreateCurriLikeView {
    fun onCreateCurriLikeSuccess(code: Int)
    fun onCreateCurriLikeFailure(code: Int)
}

//8.9 커리큘럼 좋아요(추천) 취소 api
interface CancelCurriLikeView {
    fun onDeleteCurriLikeSuccess(code: Int)
    fun onDeleteCurriLikeFailure(code: Int)
}

//8.10 커리큘럼 좋아요(추천) 리스트 조회 api
interface ShowScrapCurriListView {
    fun onShowScrapCurriListSuccess(code: Int, result: List<Curriculum>)
    fun onShowScrapCurriListFailure(code: Int)
}

//8.10.1 커리큘럼 좋아요(추천) TOP 10 리스트 조회 api
interface ShowTop10ListView {
    fun onShowTop10ListSuccess(code: Int, result : List<Top10>)
    fun onShowTop10ListFailure(code : Int)
}

//8.11 커리큘럼 리셋 api
interface ResetCurriculumView {
    fun onResetCurriculumSuccess(code: Int)
    fun onResetCurriculumFailure(code : Int)
}

//8.14 최신 커리큘럼 리스트 조회 api
interface ShowGetNewCurriListView{
    fun onShowGetNewCurriListSuccess(code: Int, result : List<Top10>)
    fun onShowGetNewCurriListFailure(code : Int)
}