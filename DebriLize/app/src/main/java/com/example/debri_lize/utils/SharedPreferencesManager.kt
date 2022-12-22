package com.example.debri_lize.utils

import android.content.SharedPreferences
import com.example.debri_lize.data.post.PostLikeCreate
import com.example.debri_lize.utils.ApplicationClass.Companion.X_ACCESS_TOKEN
import com.example.debri_lize.utils.ApplicationClass.Companion.mSharedPreferences

//accessToken
fun saveJwt(jwtToken: String) {
    val editor = mSharedPreferences.edit()
    editor.putString("jwt", jwtToken)

    editor.apply()
}

fun getJwt(): String? = mSharedPreferences.getString("jwt", null)

//refreshToken
fun saveRefreshToken(jwtToken: String) {
    val editor = mSharedPreferences.edit()
    editor.putString("refreshToken", jwtToken)

    editor.apply()
}

fun getRefreshToken(): String? = mSharedPreferences.getString("refreshToken", null)

//userIdx
fun saveUserIdx(userIdx: Int) {
    val editor = mSharedPreferences.edit()
    editor.putInt("userIdx", userIdx)

    editor.apply()
}

fun getUserIdx(): Int? = mSharedPreferences.getInt("userIdx", 0)

//userName
fun saveUserName(userName: String) {
    val editor = mSharedPreferences.edit()
    editor.putString("userName", userName)

    editor.apply()
}

fun getUserName(): String? = mSharedPreferences.getString("userName", "lize")

//userID
fun saveUserID(userName: String) {
    val editor = mSharedPreferences.edit()
    editor.putString("userID", userName)

    editor.apply()
}

fun getUserID(): String? = mSharedPreferences.getString("userID", "lize")

//homeFragment isFirst
fun saveIsFirst(isFirst: Boolean) {
    val editor = mSharedPreferences.edit()
    editor.putBoolean("isFirst", isFirst)

    editor.apply()
}

fun getIsFirst(): Boolean? = mSharedPreferences.getBoolean("isFirst", true)

//homeFragment curriIdx
fun saveCurriIdx(curriIdx: Int) {
    val editor = mSharedPreferences.edit()
    editor.putInt("curriIdx", curriIdx)

    editor.apply()
}

fun getCurriIdx(): Int = mSharedPreferences.getInt("curriIdx", 0)

//UI size
fun saveUISize(key : String, size : Int){
    val editor = mSharedPreferences.edit()
    editor.putInt(key, size)
    editor.apply()
}

fun getUISize(key : String) : Int = mSharedPreferences.getInt(key, 0)

fun savePostIdx(postIdx: Int) {
    val editor = mSharedPreferences.edit()
    editor.putInt("postIdx", postIdx)

    editor.apply()
}

fun getPostIdx(): Int? = mSharedPreferences.getInt("postIdx",0)

//sendEmail
fun saveSendEmailTF(sendEmailTF: Boolean) {
    val editor = mSharedPreferences.edit()
    editor.putBoolean("sendEmailTF", sendEmailTF)

    editor.apply()
}

fun getSendEmailTF(): Boolean? = mSharedPreferences.getBoolean("sendEmailTF", true)