package com.example.debri_lize.view.post

import com.example.debri_lize.response.Post

interface EachPostListView {
    fun onEachPostListSuccess(code: Int, result: List<Post>)
    fun onEachPostListFailure(code : Int)
}