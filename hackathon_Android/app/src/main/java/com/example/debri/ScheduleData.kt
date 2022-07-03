package com.example.debri
import java.io.Serializable

data class ScheduleData(
    var time : String? = "",
    val title : String? = "",
    val detail : String? = ""
): Serializable
