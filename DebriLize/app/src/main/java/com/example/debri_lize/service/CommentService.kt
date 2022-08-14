package com.example.debri_lize.service

import android.util.Log
import com.example.debri_lize.utils.RetrofitInterface
import com.example.debri_lize.data.post.Cocomment
import com.example.debri_lize.data.post.Comment
import com.example.debri_lize.data.post.CommentList
import com.example.debri_lize.base.BaseResponse
import com.example.debri_lize.utils.getJwt
import com.example.debri_lize.utils.getRetrofit
import com.example.debri_lize.view.post.CocommentCreateView
import com.example.debri_lize.view.post.CommentCreateView
import com.example.debri_lize.view.post.DeleteCommentView
import com.example.debri_lize.view.post.ShowCommentView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CommentService {
    private lateinit var commentCreateView: CommentCreateView
    private lateinit var cocommentCreateView: CocommentCreateView
    private lateinit var showCommentView: ShowCommentView
    private lateinit var deleteCommentView: DeleteCommentView

    fun setCommentCreateView(commentCreateView: CommentCreateView){
        this.commentCreateView = commentCreateView
    }

    fun setCocommentCreateView(cocommentCreateView: CocommentCreateView){
        this.cocommentCreateView = cocommentCreateView
    }

    fun setShowCommentView(showCommentView: ShowCommentView){
        this.showCommentView = showCommentView
    }

    fun setDeleteCommentView(deleteCommentView: DeleteCommentView){
        this.deleteCommentView = deleteCommentView
    }

    fun createComment(comment: Comment){
        Log.d("comment", comment.toString())
        //서비스 객체 생성
        val commentService = getRetrofit().create(RetrofitInterface::class.java)

        commentService.createComment(comment, getJwt()!!).enqueue(object: Callback<BaseResponse<Comment>> {
            //응답이 왔을 때 처리
            override fun onResponse(call: Call<BaseResponse<Comment>>, response: Response<BaseResponse<Comment>>) {
                val resp: BaseResponse<Comment> = response.body()!!
                Log.d("commentCode", resp.code.toString())
                Log.d("comment", resp.result.toString())
                when(resp.code){
                    //API code값 사용
                    200->commentCreateView.onCommentCreateSuccess(resp.code)
                    else->commentCreateView.onCommentCreateFailure(resp.code)
                }
            }
            //실패했을 때 처리
            override fun onFailure(call: Call<BaseResponse<Comment>>, t: Throwable) {
                Log.d("comment", t.toString())
            }

        })
    }

    fun createCocomment(cocomment: Cocomment){
        //서비스 객체 생성
        val commentService = getRetrofit().create(RetrofitInterface::class.java)

        commentService.createCocomment(cocomment, getJwt()!!).enqueue(object: Callback<BaseResponse<Comment>> {
            //응답이 왔을 때 처리
            override fun onResponse(call: Call<BaseResponse<Comment>>, response: Response<BaseResponse<Comment>>) {
                val resp: BaseResponse<Comment> = response.body()!!
                Log.d("cocommentCode", resp.code.toString())
                Log.d("cocomment", resp.result.toString())
                when(resp.code){
                    //API code값 사용
                    200->cocommentCreateView.onCocommentCreateSuccess(resp.code)
                    else->cocommentCreateView.onCocommentCreateFailure(resp.code)
                }
            }
            //실패했을 때 처리
            override fun onFailure(call: Call<BaseResponse<Comment>>, t: Throwable) {
                Log.d("cocommentFail", t.toString())
            }

        })
    }

    fun showComment(postIdx:Int){
        Log.d("showComment", "enter")
        val commentService = getRetrofit().create(RetrofitInterface::class.java)
        commentService.showComment(postIdx, getJwt()!!).enqueue(object: Callback<BaseResponse<List<CommentList>>> {
            //응답이 왔을 때 처리
            override fun onResponse(call: Call<BaseResponse<List<CommentList>>>, response: Response<BaseResponse<List<CommentList>>>) {
                Log.d("showComment", "response")
                val resp: BaseResponse<List<CommentList>> = response.body()!!
                Log.d("showCommentCode", resp.code.toString())
                when(resp.code){
                    //API code값 사용
                    200->showCommentView.onShowCommentSuccess(resp.code, resp.result) //result를 받아서 UI를 구현해야함
                    else->showCommentView.onShowCommentFailure(resp.code) //무슨 오류인지 알아야하므로 code가져가기
                }
            }
            //실패했을 때 처리
            override fun onFailure(call: Call<BaseResponse<List<CommentList>>>, t: Throwable) {
                Log.d("showComment", t.toString())
            }

        })
    }

    fun deleteComment(commentIdx : Int){
        Log.d("deleteComment", "enter")
        val commentService = getRetrofit().create(RetrofitInterface::class.java)
        commentService.deleteComment(commentIdx, getJwt()!!).enqueue(object: Callback<BaseResponse<Comment>> {
            //응답이 왔을 때 처리
            override fun onResponse(call: Call<BaseResponse<Comment>>, response: Response<BaseResponse<Comment>>) {
                Log.d("deleteComment", "response")
                val resp: BaseResponse<Comment> = response.body()!!
                Log.d("deleteCommentCode", resp.code.toString())
                when(resp.code){
                    //API code값 사용
                    200->deleteCommentView.onDeleteCommentSuccess(resp.code)
                    else->deleteCommentView.onDeleteCommentFailure(resp.code)
                }
            }
            //실패했을 때 처리
            override fun onFailure(call: Call<BaseResponse<Comment>>, t: Throwable) {
                Log.d("deletePostFail", t.toString())
            }

        })
    }
}