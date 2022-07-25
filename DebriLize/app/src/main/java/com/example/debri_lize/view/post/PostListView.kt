package com.example.debri_lize.view.post

import com.example.debri_lize.response.Post

interface PostListView {
    fun onPostListSuccess(code: Int, result: List<Post>)
    fun onPostListFailure(code : Int)
}