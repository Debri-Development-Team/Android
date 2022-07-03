package com.example.debri.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "UserTable")
data class User(
    //서버 이용 시
    //@SerializedName(value = "서버에서 데이터를 보낼 때 설정해둔 값")
    @SerializedName(value = "id") var id : String,
    @SerializedName(value = "nickname") var nickname : String,
    @SerializedName(value = "birthday") var birth : String,
    @SerializedName(value = "password") var password : String
){
    //자동으로 userid부여
    @PrimaryKey(autoGenerate = true) var userIdx : Int = 0
}
