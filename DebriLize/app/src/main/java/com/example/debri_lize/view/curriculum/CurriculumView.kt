package com.example.debri_lize.view.curriculum

import com.example.debri_lize.data.curriculum.Curriculum
import com.example.debri_lize.data.curriculum.CurriculumDetail

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