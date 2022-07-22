package com.example.debri_lize.data

import com.example.debri_lize.data.auth.UserLogin
import com.example.debri_lize.data.auth.UserSignup
import com.example.debri_lize.data.post.Comment
import com.example.debri_lize.data.post.Post
import com.example.debri_lize.response.*
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface RetrofitInterface {

    //회원가입
    @POST("api/user/signUp")
    fun signUp(@Body user: UserSignup): Call<AuthResponse>

    //로그인
    @POST("api/auth/login")
    fun login(@Body user : UserLogin): Call<AuthResponse>

    //게시물 작성
    @POST("api/post/create")
    fun createPost(@Body post: Post): Call<PostResponse>

    //[특정 게시판] 게시물 조회
    @GET("api/post/getList/{boardIdx}")
    fun showEachPostList(@Path("boardIdx") boardIdx: Int) : Call<PostResponse>

    //게시물 상세 조회
    @GET("api/post/get/{postIdx}")
    fun showPostDetail(@Path("postIdx") boardIdx: Int) : Call<PostDetailResponse>

    //댓글 작성
    @POST("api/comment/replyOnPost/create")
    fun createComment(@Body comment: Comment): Call<CommentResponse>

    //댓글, 대댓글 조회
    @GET("api/comment/get/{postIdx}")
    fun showComment(@Path("postIdx") postIdx: Int) : Call<CommentResponse>
}