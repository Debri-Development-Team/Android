package com.example.debri_lize.data.view.post

import com.example.debri_lize.data.response.PostDetail

interface PostDetailView {
    fun onPostDetailSuccess(code: Int, result: PostDetail)
    fun onPostDetailFailure(code : Int)
}