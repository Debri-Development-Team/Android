package com.example.debri_lize.view.post

import com.example.debri_lize.data.post.PostDetail
import com.example.debri_lize.data.post.PostList

//3.1 게시물 생성 api
interface PostCreateView {
    fun onPostCreateSuccess(code : Int)
    fun onPostCreateFailure(code : Int)
}

//3.2 게시물 수정 api
interface EditPostView {
    fun onEditPostSuccess(code : Int)
    fun onEditPostFailure(code : Int)
}

//3.3 게시물 삭제 api
interface DeletePostView {
    fun onDeletePostSuccess(code : Int)
    fun onDeletePostFailure(code : Int)
}
//3.4 게시물 좋아요 생성 api
interface CreatePostLikeView {
    fun onCreatePostLikeSuccess(code: Int)
    fun onCreatePostLikeFailure(code: Int)
}

//3.5 게시물 좋아요 취소 api
interface CancelPostLikeView {
    fun onCancelPostLikeSuccess(code: Int)
    fun onCancelPostLikeFailure(code: Int)
}

//3.7 [특정 게시판] 게시물 리스트 조회 api
interface EachPostListView {
    fun onEachPostListSuccess(code: Int, result: List<PostList>)
    fun onEachPostListFailure(code : Int)
}

//3.7.1 [전체 범위(키워드 검색)] 게시물 리스트 조회 api
interface PostListView {
    fun onPostListSuccess(code: Int, result: List<PostList>)
    fun onPostListFailure(code : Int)
}

//3.8 게시물 조회 api
interface PostDetailView {
    fun onPostDetailSuccess(code: Int, result: PostDetail)
    fun onPostDetailFailure(code : Int)
}

//3.9 게시물 스크랩 설정 api
interface CreatePostScrapView {
    fun onCreatePostScrapSuccess(code: Int)
    fun onCreatePostScrapFailure(code: Int)
}

//3.9.1 게시물 스크랩 해제 api
interface CancelPostScrapView {
    fun onCancelPostScrapSuccess(code: Int)
    fun onCancelPostScrapFailure(code: Int)
}

//3.9.2 스크랩 게시물 조회 api
interface ShowScrapPostListView {
    fun onShowScrapPostListSuccess(code : Int, result: List<PostList>)
    fun onShowScrapPostListFailure(code : Int)
}