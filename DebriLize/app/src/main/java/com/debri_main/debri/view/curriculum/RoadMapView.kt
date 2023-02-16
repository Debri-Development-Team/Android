package com.debri_main.debri.view.curriculum

import com.debri_main.debri.data.curriculum.RoadMap
import com.debri_main.debri.data.curriculum.RoadMapList

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

//7.5.2 로드맵 to 커리큘럼 api
interface CopyRoadMapView {
    fun onCopyRoadMapSuccess(code: Int)
    fun onCopyRoadMapFailure(code : Int)
}
