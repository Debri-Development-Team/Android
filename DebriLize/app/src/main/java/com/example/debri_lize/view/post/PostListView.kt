package com.example.debri_lize.view.post

import com.example.debri_lize.data.post.PostList

interface PostListView {
    fun onPostListSuccess(code: Int, result: List<PostList>)
    fun onPostListFailure(code : Int)
}