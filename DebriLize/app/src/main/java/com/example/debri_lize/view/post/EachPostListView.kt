package com.example.debri_lize.view.post

import com.example.debri_lize.data.post.PostList

interface EachPostListView {
    fun onEachPostListSuccess(code: Int, result: List<PostList>)
    fun onEachPostListFailure(code : Int)
}