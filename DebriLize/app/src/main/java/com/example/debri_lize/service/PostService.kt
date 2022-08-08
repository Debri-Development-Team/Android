package com.example.debri_lize.service

import android.util.Log
import com.example.debri_lize.data.post.Post
import com.example.debri_lize.data.RetrofitInterface
import com.example.debri_lize.data.post.EditPost
import com.example.debri_lize.data.post.PostLikeCancel
import com.example.debri_lize.data.post.PostLikeCreate
import com.example.debri_lize.response.DeletePostResponse
import com.example.debri_lize.response.PostDetailResponse
import com.example.debri_lize.response.PostResponse
import com.example.debri_lize.utils.getJwt
import com.example.debri_lize.utils.getRetrofit
import com.example.debri_lize.view.post.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PostService {
    private lateinit var postCreateView: PostCreateView
    private lateinit var editPostView: EditPostView
    private lateinit var deletePostView: DeletePostView

    private lateinit var postListView: PostListView
    private lateinit var eachPostListView: EachPostListView
    private lateinit var postDetailtView: PostDetailView

    private lateinit var createPostLikeView: CreatePostLikeView
    private lateinit var cancelPostLikeView: CancelPostLikeView

    private lateinit var createPostScrapView: CreatePostScrapView
    private lateinit var cancelPostScrapView: CancelPostScrapView


    fun setPostCreateView(postCreateView: PostCreateView){
        this.postCreateView = postCreateView
    }

    fun setEditPostView(editPostView: EditPostView){
        this.editPostView = editPostView
    }

    fun setDeletePostView(deletePostView: DeletePostView){
        this.deletePostView = deletePostView
    }

    fun setPostListView(postListView: PostListView){
        this.postListView = postListView
    }

    fun seteachPostListView(eachPostListView: EachPostListView){
        this.eachPostListView = eachPostListView
    }

    fun setPostDetailView(postDetailtView: PostDetailView){
        this.postDetailtView = postDetailtView
    }

    fun setCreatePostLikeView(createPostLikeView: CreatePostLikeView){
        this.createPostLikeView = createPostLikeView
    }

    fun setCancelPostLikeView(cancelPostLikeView: CancelPostLikeView){
        this.cancelPostLikeView = cancelPostLikeView
    }

    fun setCreatePostScrapView(createPostScrapView: CreatePostScrapView){
        this.createPostScrapView = createPostScrapView
    }

    fun setCancelPostScrapView(cancelPostScrapView: CancelPostScrapView){
        this.cancelPostScrapView = cancelPostScrapView
    }


    fun createPost(post: Post){
        Log.d("post", post.toString())
        //서비스 객체 생성
        val postService = getRetrofit().create(RetrofitInterface::class.java)

        postService.createPost(post, getJwt()!!).enqueue(object: Callback<PostResponse> {
            //응답이 왔을 때 처리
            override fun onResponse(call: Call<PostResponse>, response: Response<PostResponse>) {
                val resp:PostResponse = response.body()!!
                Log.d("postcreate", resp.code.toString())
                Log.d("post", resp.result.toString())
                when(resp.code){
                    //API code값 사용
                    200->postCreateView.onPostCreateSuccess(resp.code)
                    else->postCreateView.onPostCreateFailure(resp.code)
                }
            }
            //실패했을 때 처리
            override fun onFailure(call: Call<PostResponse>, t: Throwable) {

            }

        })
    }

    fun editPost(editPost: EditPost, postIdx : Int){
        Log.d("editPost", "enter")
        val postService = getRetrofit().create(RetrofitInterface::class.java)
        postService.editPost(editPost, postIdx, getJwt()!!).enqueue(object: Callback<DeletePostResponse> {
            //응답이 왔을 때 처리
            override fun onResponse(call: Call<DeletePostResponse>, response: Response<DeletePostResponse>) {
                Log.d("editPost", "response")
                Log.d("posteditresp","${response.body()}")
                val resp:DeletePostResponse = response.body()!!
                Log.d("editPostCode", resp.code.toString())
                when(resp.code){
                    //API code값 사용
                    200->editPostView.onEditPostSuccess(resp.code)
                    else->editPostView.onEditPostFailure(resp.code)
                }
            }
            //실패했을 때 처리
            override fun onFailure(call: Call<DeletePostResponse>, t: Throwable) {
                Log.d("editPostFail", t.toString())
            }

        })
    }

    fun deletePost(postIdx : Int){
        Log.d("deletePost", "enter")
        val postService = getRetrofit().create(RetrofitInterface::class.java)
        postService.deletePost(postIdx, getJwt()!!).enqueue(object: Callback<DeletePostResponse> {
            //응답이 왔을 때 처리
            override fun onResponse(call: Call<DeletePostResponse>, response: Response<DeletePostResponse>) {
                Log.d("deletePost", "response")
                val resp:DeletePostResponse = response.body()!!
                Log.d("deletePostCode", resp.code.toString())
                when(resp.code){
                    //API code값 사용
                    200->deletePostView.onDeletePostSuccess(resp.code)
                    else->deletePostView.onDeletePostFailure(resp.code)
                }
            }
            //실패했을 때 처리
            override fun onFailure(call: Call<DeletePostResponse>, t: Throwable) {
                Log.d("deletePostFail", t.toString())
            }

        })
    }

    fun showPostList(keyword : String){
        Log.d("postList", "enter")
        val postService = getRetrofit().create(RetrofitInterface::class.java)
        postService.showPostList(keyword, getJwt()!!).enqueue(object: Callback<PostResponse> {
            //응답이 왔을 때 처리
            override fun onResponse(call: Call<PostResponse>, response: Response<PostResponse>) {
                Log.d("postList", "response")
                val resp:PostResponse = response.body()!!
                Log.d("postListCode", resp.code.toString())
                when(resp.code){
                    //API code값 사용
                    200->postListView.onPostListSuccess(resp.code, resp.result)
                    else->postListView.onPostListFailure(resp.code)
                }
            }
            //실패했을 때 처리
            override fun onFailure(call: Call<PostResponse>, t: Throwable) {
                Log.d("postListFail", t.toString())
            }

        })
    }

    fun showEachPostList(boardIdx:Int){
        Log.d("eachpostlist", "enter")
        val postService = getRetrofit().create(RetrofitInterface::class.java)
        postService.showEachPostList(boardIdx, getJwt()!!).enqueue(object: Callback<PostResponse> {
            //응답이 왔을 때 처리
            override fun onResponse(call: Call<PostResponse>, response: Response<PostResponse>) {
                Log.d("eachpostlist", "response")
                val resp:PostResponse = response.body()!!
                Log.d("eachpostlistCode", resp.code.toString())
                when(resp.code){
                    //API code값 사용
                    200->eachPostListView.onEachPostListSuccess(resp.code, resp.result) //result를 받아서 UI를 구현해야함
                    else->eachPostListView.onEachPostListFailure(resp.code) //무슨 오류인지 알아야하므로 code가져가기
                }
            }
            //실패했을 때 처리
            override fun onFailure(call: Call<PostResponse>, t: Throwable) {
                Log.d("postlistfail", t.toString())
            }

        })
    }

    fun showPostDetail(postIdx:Int){
        Log.d("postdetail", "enter")
        val postService = getRetrofit().create(RetrofitInterface::class.java)
        postService.showPostDetail(postIdx, getJwt()!!).enqueue(object: Callback<PostDetailResponse> {
            //응답이 왔을 때 처리
            override fun onResponse(call: Call<PostDetailResponse>, response: Response<PostDetailResponse>) {
                Log.d("postdetail", "response")
                val resp:PostDetailResponse = response.body()!!
                Log.d("postdetailCode", resp.code.toString())
                when(resp.code){
                    //API code값 사용
                    200->postDetailtView.onPostDetailSuccess(resp.code, resp.result) //result를 받아서 UI를 구현해야함
                    else->postDetailtView.onPostDetailFailure(resp.code) //무슨 오류인지 알아야하므로 code가져가기
                }
            }
            //실패했을 때 처리
            override fun onFailure(call: Call<PostDetailResponse>, t: Throwable) {
                Log.d("postdetailfail", t.toString())
            }

        })
    }

    fun createPostLike(postLike: PostLikeCreate){
        Log.d("createPostLike", "enter")
        val postService = getRetrofit().create(RetrofitInterface::class.java)
        postService.createPostLike(postLike, getJwt()!!).enqueue(object: Callback<DeletePostResponse> {
            //응답이 왔을 때 처리
            override fun onResponse(call: Call<DeletePostResponse>, response: Response<DeletePostResponse>) {
                Log.d("createPostLike", "response")
                val resp:DeletePostResponse = response.body()!!
                Log.d("createPostLikeCode", resp.code.toString())
                when(resp.code){
                    //API code값 사용
                    200->createPostLikeView.onCreatePostLikeSuccess(resp.code)
                    else->createPostLikeView.onCreatePostLikeFailure(resp.code) //무슨 오류인지 알아야하므로 code가져가기
                }
            }
            //실패했을 때 처리
            override fun onFailure(call: Call<DeletePostResponse>, t: Throwable) {
                Log.d("createPostLikefail", t.toString())
            }

        })
    }

    fun cancelPostLike(postLike: PostLikeCancel){
        Log.d("cancelPostLike", "enter")
        val postService = getRetrofit().create(RetrofitInterface::class.java)
        postService.cancelPostLike(postLike, getJwt()!!).enqueue(object: Callback<DeletePostResponse> {
            //응답이 왔을 때 처리
            override fun onResponse(call: Call<DeletePostResponse>, response: Response<DeletePostResponse>) {
                Log.d("cancelPostLike", "response")
                val resp:DeletePostResponse = response.body()!!
                Log.d("cancelPostLikeCode", resp.code.toString())
                when(resp.code){
                    //API code값 사용
                    200->cancelPostLikeView.onCancelPostLikeSuccess(resp.code) //result를 받아서 UI를 구현해야함
                    else->cancelPostLikeView.onCancelPostLikeFailure(resp.code) //무슨 오류인지 알아야하므로 code가져가기
                }
            }
            //실패했을 때 처리
            override fun onFailure(call: Call<DeletePostResponse>, t: Throwable) {
                Log.d("cancelPostLikefail", t.toString())
            }

        })
    }

    fun createPostScrap(postIdx: Int){
        Log.d("createPostScrap", "enter")
        val postService = getRetrofit().create(RetrofitInterface::class.java)
        postService.createPostScrap(postIdx, getJwt()!!).enqueue(object: Callback<DeletePostResponse> {
            //응답이 왔을 때 처리
            override fun onResponse(call: Call<DeletePostResponse>, response: Response<DeletePostResponse>) {
                Log.d("createPostScrap", "response")
                val resp:DeletePostResponse = response.body()!!
                Log.d("createPostScrapCode", resp.code.toString())
                when(resp.code){
                    //API code값 사용
                    200->createPostScrapView.onCreatePostScrapSuccess(resp.code) //result를 받아서 UI를 구현해야함
                    else->createPostScrapView.onCreatePostScrapFailure(resp.code) //무슨 오류인지 알아야하므로 code가져가기
                }
            }
            //실패했을 때 처리
            override fun onFailure(call: Call<DeletePostResponse>, t: Throwable) {
                Log.d("createPostLikefail", t.toString())
            }

        })
    }

    fun cancelPostScrap(postIdx: Int){
        Log.d("cancelPostScrap", "enter")
        val postService = getRetrofit().create(RetrofitInterface::class.java)
        postService.cancelPostScrap(postIdx, getJwt()!!).enqueue(object: Callback<DeletePostResponse> {
            //응답이 왔을 때 처리
            override fun onResponse(call: Call<DeletePostResponse>, response: Response<DeletePostResponse>) {
                Log.d("cancelPostScrap", "response")
                val resp:DeletePostResponse = response.body()!!
                Log.d("cancelPostScrapCode", resp.code.toString())
                when(resp.code){
                    //API code값 사용
                    200->cancelPostScrapView.onCancelPostScrapSuccess(resp.code) //result를 받아서 UI를 구현해야함
                    else->cancelPostScrapView.onCancelPostScrapFailure(resp.code) //무슨 오류인지 알아야하므로 code가져가기
                }
            }
            //실패했을 때 처리
            override fun onFailure(call: Call<DeletePostResponse>, t: Throwable) {
                Log.d("createPostLikefail", t.toString())
            }

        })
    }
}