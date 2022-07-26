package com.example.debri_lize.view.post

import com.example.debri_lize.response.PostDetail

interface PostDetailView {
    fun onPostDetailSuccess(code: Int, result: PostDetail)
    fun onPostDetailFailure(code : Int)
}