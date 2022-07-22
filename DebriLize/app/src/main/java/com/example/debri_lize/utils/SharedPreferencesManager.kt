package com.example.debri_lize.utils

import com.example.debri_lize.utils.ApplicationClass.Companion.X_ACCESS_TOKEN
import com.example.debri_lize.utils.ApplicationClass.Companion.mSharedPreferences


fun saveJwt(jwtToken: String) {
    val editor = mSharedPreferences.edit()
    editor.putString("jwt", jwtToken)

    editor.apply()
}

fun getJwt(): String? = mSharedPreferences.getString("jwt", null)

fun saveUserIdx(userIdx: Int) {
    val editor = mSharedPreferences.edit()
    editor.putInt("userIdx", userIdx)

    editor.apply()
}

fun getUserIdx(): Int? = mSharedPreferences.getInt("userIdx", 0)


