package com.example.debri_lize.data.auth

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
//Response
// 1.2 로그인 api
data class User(
    @SerializedName(value = "userIdx") var userIdx : Int,
    @SerializedName(value = "userName") var userName : String,
    @SerializedName(value = "userID") var userID : String,
    @SerializedName(value = "userBirthday") var userBirthday : String,
    @SerializedName(value = "jwt") var jwt : String,
    @SerializedName(value = "refreshToken") var refreshToken : String,
    @SerializedName(value = "firstLogin") var firstLogin : Boolean
)

// 1.5 이메일 api
data class Email(
    @SerializedName(value = "authNumber") var code : Int,
    @SerializedName(value = "timeout") var timeout : Int
)

//5.1 jwt ACCESS-TOKEN 갱신 api
data class Token(
    @SerializedName(value = "accessToken") var accessToken : String,
    @SerializedName(value = "refreshToken") var refreshToken : String
)

//@Body
// 1.1 회원가입 api
data class UserSignup(
    @SerializedName(value = "userId") var id : String,
    @SerializedName(value = "password") var password : String,
    @SerializedName(value = "password2") var password2 : String,
    @SerializedName(value = "nickname") var nickname : String,
    @SerializedName(value = "birthday") var birth : String
)

//1.2 로그인 api
data class UserLogin(
    @SerializedName(value = "email") var id : String,
    @SerializedName(value = "pwd") var password : String
)




