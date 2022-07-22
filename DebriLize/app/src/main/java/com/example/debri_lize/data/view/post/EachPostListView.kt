package com.example.debri_lize.data.view.post

import com.example.debri_lize.data.response.Post

interface EachPostListView {
    fun onEachPostListSuccess(code: Int, result: List<Post>)
    fun onEachPostListFailure(code : Int)
}