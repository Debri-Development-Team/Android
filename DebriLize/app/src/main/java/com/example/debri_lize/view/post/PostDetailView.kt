package com.example.debri_lize.view.post

import com.example.debri_lize.data.post.PostDetail

interface PostDetailView {
    fun onPostDetailSuccess(code: Int, result: PostDetail)
    fun onPostDetailFailure(code : Int)
}