package com.example.debri_lize.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

data class UserSignup(
    //서버 이용 시
    //@SerializedName(value = "서버에서 데이터를 보낼 때 설정해둔 값")
    @SerializedName(value = "userId") var id : String,
    @SerializedName(value = "password") var password : String,
    @SerializedName(value = "password2") var password2 : String,
    @SerializedName(value = "nickname") var nickname : String,
    @SerializedName(value = "birthday") var birth : String
)
