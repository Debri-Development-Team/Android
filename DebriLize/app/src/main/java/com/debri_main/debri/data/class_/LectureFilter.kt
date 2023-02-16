package com.debri_main.debri.data.class_

import java.io.Serializable

data class LectureFilter(
    var lang: String? = "", //언어 태그
    var type: String? = "", //서적, 비디오
    var price: String? = "", //유료, 무료
    var key: String? = "",   //검색어
    var pageNum: Int? = 1
): Serializable
