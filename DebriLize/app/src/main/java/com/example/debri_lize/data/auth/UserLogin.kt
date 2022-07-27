package com.example.debri_lize.data.auth

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

data class UserLogin(
    //서버 이용 시
    //@SerializedName(value = "서버에서 데이터를 보낼 때 설정해둔 값")
    @SerializedName(value = "email") var id : String,
    @SerializedName(value = "pwd") var password : String
){
    //자동으로 userid부여
    @PrimaryKey(autoGenerate = true) var userIdx : Int = 0
}
