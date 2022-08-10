package com.example.debri_lize.data.board
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Board(
    @SerializedName(value = "boardIdx") val boardIdx : Int,
    @SerializedName(value = "boardName") val boardName : String
): Serializable
