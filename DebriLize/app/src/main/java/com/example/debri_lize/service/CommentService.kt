package com.example.debri_lize.service

import android.util.Log
import com.example.debri_lize.data.RetrofitInterface
import com.example.debri_lize.data.post.Cocomment
import com.example.debri_lize.data.post.Comment
import com.example.debri_lize.response.CommentListResponse
import com.example.debri_lize.response.CommentResponse
import com.example.debri_lize.utils.getRetrofit
import com.example.debri_lize.view.post.CocommentCreateView
import com.example.debri_lize.view.post.CommentCreateView
import com.example.debri_lize.view.post.ShowCommentView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CommentService {
    private lateinit var commentCreateView: CommentCreateView
    private lateinit var cocommentCreateView: CocommentCreateView
    private lateinit var showCommentView: ShowCommentView

    fun setCommentCreateView(commentCreateView: CommentCreateView){
        this.commentCreateView = commentCreateView
    }

    fun setCocommentCreateView(cocommentCreateView: CocommentCreateView){
        this.cocommentCreateView = cocommentCreateView
    }

    fun setShowCommentView(showCommentView: ShowCommentView){
        this.showCommentView = showCommentView
    }

    fun createComment(comment: Comment){
        Log.d("comment", comment.toString())
        //서비스 객체 생성
        val commentService = getRetrofit().create(RetrofitInterface::class.java)

        commentService.createComment(comment).enqueue(object: Callback<CommentResponse> {
            //응답이 왔을 때 처리
            override fun onResponse(call: Call<CommentResponse>, response: Response<CommentResponse>) {
                val resp:CommentResponse = response.body()!!
                Log.d("commentCode", resp.code.toString())
                Log.d("comment", resp.result.toString())
                when(resp.code){
                    //API code값 사용
                    200->commentCreateView.onCommentCreateSuccess(resp.code)
                    else->commentCreateView.onCommentCreateFailure(resp.code)
                }
            }
            //실패했을 때 처리
            override fun onFailure(call: Call<CommentResponse>, t: Throwable) {
                Log.d("comment", t.toString())
            }

        })
    }

    fun createCocomment(cocomment: Cocomment){
        //서비스 객체 생성
        val commentService = getRetrofit().create(RetrofitInterface::class.java)

        commentService.createCocomment(cocomment).enqueue(object: Callback<CommentResponse> {
            //응답이 왔을 때 처리
            override fun onResponse(call: Call<CommentResponse>, response: Response<CommentResponse>) {
                val resp:CommentResponse = response.body()!!
                Log.d("cocommentCode", resp.code.toString())
                Log.d("cocomment", resp.result.toString())
                when(resp.code){
                    //API code값 사용
                    200->cocommentCreateView.onCocommentCreateSuccess(resp.code)
                    else->cocommentCreateView.onCocommentCreateFailure(resp.code)
                }
            }
            //실패했을 때 처리
            override fun onFailure(call: Call<CommentResponse>, t: Throwable) {
                Log.d("cocommentFail", t.toString())
            }

        })
    }

    fun showComment(postIdx:Int){
        Log.d("showComment", "enter")
        val commentService = getRetrofit().create(RetrofitInterface::class.java)
        commentService.showComment(postIdx).enqueue(object: Callback<CommentListResponse> {
            //응답이 왔을 때 처리
            override fun onResponse(call: Call<CommentListResponse>, response: Response<CommentListResponse>) {
                Log.d("showComment", "response")
                val resp:CommentListResponse = response.body()!!
                Log.d("showCommentCode", resp.code.toString())
                when(resp.code){
                    //API code값 사용
                    200->showCommentView.onShowCommentSuccess(resp.code, resp.result) //result를 받아서 UI를 구현해야함
                    else->showCommentView.onShowCommentFailure(resp.code) //무슨 오류인지 알아야하므로 code가져가기
                }
            }
            //실패했을 때 처리
            override fun onFailure(call: Call<CommentListResponse>, t: Throwable) {
                Log.d("showComment", t.toString())
            }

        })
    }
}