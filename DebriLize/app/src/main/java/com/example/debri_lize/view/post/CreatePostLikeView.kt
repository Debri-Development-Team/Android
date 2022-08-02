package com.example.debri_lize.view.post

import com.example.debri_lize.response.PostDetail

interface CreatePostLikeView {
    fun onCreatePostLikeSuccess(code: Int)
    fun onCreatePostLikeFailure(code: Int)
}