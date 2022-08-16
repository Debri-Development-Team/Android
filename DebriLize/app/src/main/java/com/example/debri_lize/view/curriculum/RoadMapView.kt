package com.example.debri_lize.view.curriculum

import com.example.debri_lize.data.curriculum.RoadMap
import com.example.debri_lize.data.curriculum.RoadMapList

//7.5 로드맵 리스트 조회 api
interface ShowRoadMapListView {
    fun onShowRoadMapListSuccess(code: Int, result : List<RoadMapList>)
    fun onShowRoadMapListFailure(code : Int)
}

//7.5.1 로드맵 상세 조회 api
interface ShowRoadMapDetailView {
    fun onShowRoadMapDetailSuccess(code: Int, result : List<RoadMap>)
    fun onShowRoadMapDetailFailure(code : Int)
}
