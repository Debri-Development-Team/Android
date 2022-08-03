package com.example.debri_lize.utils

import android.content.SharedPreferences
import com.example.debri_lize.data.post.PostLikeCreate
import com.example.debri_lize.utils.ApplicationClass.Companion.X_ACCESS_TOKEN
import com.example.debri_lize.utils.ApplicationClass.Companion.mSharedPreferences


fun saveJwt(jwtToken: String) {
    val editor = mSharedPreferences.edit()
    editor.putString("jwt", jwtToken)

    editor.apply()
}

fun getJwt(): String? = mSharedPreferences.getString("jwt", null)

fun saveRefreshToken(jwtToken: String) {
    val editor = mSharedPreferences.edit()
    editor.putString("refreshToken", jwtToken)

    editor.apply()
}

fun getRefreshToken(): String? = mSharedPreferences.getString("refreshToken", null)

fun saveUserIdx(userIdx: Int) {
    val editor = mSharedPreferences.edit()
    editor.putInt("userIdx", userIdx)

    editor.apply()
}

fun getUserIdx(): Int? = mSharedPreferences.getInt("userIdx", 0)

fun saveUserName(userName: String) {
    val editor = mSharedPreferences.edit()
    editor.putString("userName", userName)

    editor.apply()
}

fun getUserName(): String? = mSharedPreferences.getString("userName", null)

fun savePostIdx(postIdx: Int) {
    val editor = mSharedPreferences.edit()
    editor.putInt("postIdx", postIdx)

    editor.apply()
}

fun getPostIdx(): Int? = mSharedPreferences.getInt("postIdx",0)

fun saveLikeStatus(likeStatus: String, postIdx: Int, userIdx: Int){
    val editor = mSharedPreferences.edit()
    editor.putInt("postIdx", postIdx)
    editor.putInt("userIdx", userIdx)
    editor.putString("likeStatus", likeStatus)

    editor.apply()
}

fun getLikeStatus(): PostLikeCreate? {
    val postIdx = mSharedPreferences.getInt("postIdx",0)
    val userIdx = mSharedPreferences.getInt("userIdx",0)
    val likeStatus = mSharedPreferences.getString("likeStatus",null)
    return PostLikeCreate(postIdx, userIdx, likeStatus)
}