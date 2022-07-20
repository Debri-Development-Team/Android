package com.example.debri_lize.utils

import com.example.debri_lize.utils.ApplicationClass.Companion.X_ACCESS_TOKEN
import com.example.debri_lize.utils.ApplicationClass.Companion.mSharedPreferences


fun saveJwt(jwtToken: String) {
    val editor = mSharedPreferences.edit()
    editor.putString("auth", jwtToken)

    editor.apply()
}

fun getJwt(): String? = mSharedPreferences.getString("auth", null)

fun saveUserIdx(jwtToken: Int) {
    val editor = mSharedPreferences.edit()
    editor.putInt("auth2", jwtToken)

    editor.apply()
}

fun getUserIdx(): String? = mSharedPreferences.getString("auth2", null)


