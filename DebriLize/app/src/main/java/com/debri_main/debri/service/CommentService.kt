package com.debri_main.debri.service

import android.util.Log
import com.debri_main.debri.utils.RetrofitInterface
import com.debri_main.debri.base.BaseResponse
import com.debri_main.debri.data.post.*
import com.debri_main.debri.utils.getJwt
import com.debri_main.debri.utils.getRetrofit
import com.debri_main.debri.view.post.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CommentService {
    private lateinit var commentCreateView: CommentCreateView
    private lateinit var cocommentCreateView: CocommentCreateView
    private lateinit var showCommentView: ShowCommentView
    private lateinit var deleteCommentView: DeleteCommentView
    private lateinit var createCommentLikeView: CreateCommentLikeView
    private lateinit var deleteCommentLikeView: DeleteCommentLikeView

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

    fun setCreateCommentLikeView(createCommentLikeView: CreateCommentLikeView){
        this.createCommentLikeView = createCommentLikeView
    }

    fun setDeleteCommentLikeView(deleteCommentLikeView: DeleteCommentLikeView){
        this.deleteCommentLikeView = deleteCommentLikeView
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

    fun createCommentLike(commentIdx: Int){
        Log.d("createCommentLike", "enter")
        val commentService = getRetrofit().create(RetrofitInterface::class.java)
        commentService.createCommentLike(commentIdx, getJwt()!!).enqueue(object: Callback<BaseResponse<createCommentLike>> {
            //응답이 왔을 때 처리
            override fun onResponse(call: Call<BaseResponse<createCommentLike>>, response: Response<BaseResponse<createCommentLike>>) {
                Log.d("createCommentLike", "response")
                val resp: BaseResponse<createCommentLike> = response.body()!!
                Log.d("createCommentLikeCode", resp.code.toString())
                when(resp.code){
                    //API code값 사용
                    200->createCommentLikeView.onCreateCommentLikeSuccess(resp.code)
                    else->createCommentLikeView.onCreateCommentLikeFailure(resp.code)
                }
            }
            //실패했을 때 처리
            override fun onFailure(call: Call<BaseResponse<createCommentLike>>, t: Throwable) {
                Log.d("createCommentLikeFail", t.toString())
            }

        })
    }

    fun deleteCommentLike(commentIdx: Int){
        Log.d("deleteCommentLike", "enter")
        val commentService = getRetrofit().create(RetrofitInterface::class.java)
        commentService.deleteCommentLike(commentIdx, getJwt()!!).enqueue(object: Callback<BaseResponse<deleteCommentLike>> {
            //응답이 왔을 때 처리
            override fun onResponse(call: Call<BaseResponse<deleteCommentLike>>, response: Response<BaseResponse<deleteCommentLike>>) {
                Log.d("deleteCommentLike", "response")
                val resp: BaseResponse<deleteCommentLike> = response.body()!!
                Log.d("deleteCommentLikeCode", resp.code.toString())
                when(resp.code){
                    //API code값 사용
                    200->deleteCommentLikeView.onDeleteCommentLikeSuccess(resp.code)
                    else->deleteCommentLikeView.onDeleteCommentLikeFailure(resp.code)
                }
            }
            //실패했을 때 처리
            override fun onFailure(call: Call<BaseResponse<deleteCommentLike>>, t: Throwable) {
                Log.d("deleteCommentLikeFail", t.toString())
            }

        })
    }
}