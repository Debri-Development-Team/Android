package com.example.debri_lize.data.board
import com.google.gson.annotations.SerializedName
import java.io.Serializable

//전체 게시판
data class Board(
    @SerializedName(value = "boardIdx") val boardIdx : Int,
    @SerializedName(value = "boardName") val boardName : String
): Serializable

//즐겨찾기된 게시판
data class BoardFavorite(
    @SerializedName(value = "boardIdx") val boardIdx : Int,
    @SerializedName(value = "boardName") val boardName : String,
    @SerializedName(value = "status") val status : String
): Serializable
