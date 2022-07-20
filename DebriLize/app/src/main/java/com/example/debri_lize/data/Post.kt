package com.example.debri_lize.data
import androidx.room.PrimaryKey

data class Post(
    var boardIdx : Int? = 0,
    var userIdx : Int? = 0,
    val postContent : String? = "",
    var postName : String? = "",
    var postImgUrls : String? = ""
){
    //자동으로 userid부여
    @PrimaryKey(autoGenerate = true) var postIdx : Int = 0
}
