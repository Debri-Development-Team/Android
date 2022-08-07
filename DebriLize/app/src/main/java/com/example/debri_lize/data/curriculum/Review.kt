package com.example.debri_lize.data.curriculum

import java.io.Serializable

data class Review(
    val content: String? = "",
    val authorName: String? = ""
) : Serializable
