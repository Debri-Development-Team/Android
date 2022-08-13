package com.example.debri_lize.service

import android.util.Log
import com.example.debri_lize.utils.RetrofitInterface
import com.example.debri_lize.data.post.*
import com.example.debri_lize.base.BaseResponse
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
    private lateinit var showScrapPostListView: ShowScrapPostListView
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

    fun setShowScrapPostListView(showScrapPostListView: ShowScrapPostListView){
        this.showScrapPostListView = showScrapPostListView
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

        postService.createPost(post, getJwt()!!).enqueue(object: Callback<BaseResponse<Post>> {
            //응답이 왔을 때 처리
            override fun onResponse(call: Call<BaseResponse<Post>>, response: Response<BaseResponse<Post>>) {
                val resp: BaseResponse<Post> = response.body()!!
                Log.d("postcreate", resp.code.toString())
                Log.d("post", resp.result.toString())
                when(resp.code){
                    //API code값 사용
                    200->postCreateView.onPostCreateSuccess(resp.code)
                    else->postCreateView.onPostCreateFailure(resp.code)
                }
            }
            //실패했을 때 처리
            override fun onFailure(call: Call<BaseResponse<Post>>, t: Throwable) {

            }

        })
    }

    fun editPost(editPost: EditPost, postIdx : Int){
        Log.d("editPost", "enter")
        val postService = getRetrofit().create(RetrofitInterface::class.java)
        postService.editPost(editPost, postIdx, getJwt()!!).enqueue(object: Callback<BaseResponse<String>> {
            //응답이 왔을 때 처리
            override fun onResponse(call: Call<BaseResponse<String>>, response: Response<BaseResponse<String>>) {
                Log.d("editPost", "response")
                Log.d("posteditresp","${response.body()}")
                val resp: BaseResponse<String> = response.body()!!
                Log.d("editPostCode", resp.code.toString())
                when(resp.code){
                    //API code값 사용
                    200->editPostView.onEditPostSuccess(resp.code)
                    else->editPostView.onEditPostFailure(resp.code)
                }
            }
            //실패했을 때 처리
            override fun onFailure(call: Call<BaseResponse<String>>, t: Throwable) {
                Log.d("editPostFail", t.toString())
            }

        })
    }

    fun deletePost(postIdx : Int){
        Log.d("deletePost", "enter")
        val postService = getRetrofit().create(RetrofitInterface::class.java)
        postService.deletePost(postIdx, getJwt()!!).enqueue(object: Callback<BaseResponse<String>> {
            //응답이 왔을 때 처리
            override fun onResponse(call: Call<BaseResponse<String>>, response: Response<BaseResponse<String>>) {
                Log.d("deletePost", "response")
                val resp: BaseResponse<String> = response.body()!!
                Log.d("deletePostCode", resp.code.toString())
                when(resp.code){
                    //API code값 사용
                    200->deletePostView.onDeletePostSuccess(resp.code)
                    else->deletePostView.onDeletePostFailure(resp.code)
                }
            }
            //실패했을 때 처리
            override fun onFailure(call: Call<BaseResponse<String>>, t: Throwable) {
                Log.d("deletePostFail", t.toString())
            }

        })
    }

    fun showPostList(keyword : String){
        Log.d("postList", "enter")
        val postService = getRetrofit().create(RetrofitInterface::class.java)
        postService.showPostList(keyword, getJwt()!!).enqueue(object: Callback<BaseResponse<List<PostList>>> {
            //응답이 왔을 때 처리
            override fun onResponse(call: Call<BaseResponse<List<PostList>>>, response: Response<BaseResponse<List<PostList>>>) {
                Log.d("postList", "response")
                val resp: BaseResponse<List<PostList>> = response.body()!!
                Log.d("postListCode", resp.code.toString())
                when(resp.code){
                    //API code값 사용
                    200->postListView.onPostListSuccess(resp.code, resp.result)
                    else->postListView.onPostListFailure(resp.code)
                }
            }
            //실패했을 때 처리
            override fun onFailure(call: Call<BaseResponse<List<PostList>>>, t: Throwable) {
                Log.d("postListFail", t.toString())
            }

        })
    }

    fun showEachPostList(boardIdx:Int){
        Log.d("eachpostlist", "enter")
        val postService = getRetrofit().create(RetrofitInterface::class.java)
        postService.showEachPostList(boardIdx, getJwt()!!).enqueue(object: Callback<BaseResponse<List<PostList>>> {
            //응답이 왔을 때 처리
            override fun onResponse(call: Call<BaseResponse<List<PostList>>>, response: Response<BaseResponse<List<PostList>>>) {
                Log.d("eachpostlist", "response")
                val resp: BaseResponse<List<PostList>> = response.body()!!
                Log.d("eachpostlistCode", resp.code.toString())
                when(resp.code){
                    //API code값 사용
                    200->eachPostListView.onEachPostListSuccess(resp.code, resp.result) //result를 받아서 UI를 구현해야함
                    else->eachPostListView.onEachPostListFailure(resp.code) //무슨 오류인지 알아야하므로 code가져가기
                }
            }
            //실패했을 때 처리
            override fun onFailure(call: Call<BaseResponse<List<PostList>>>, t: Throwable) {
                Log.d("postlistfail", t.toString())
            }

        })
    }

    fun showScrapPostList(){
        Log.d("showScrapPostList", "enter")
        val postService = getRetrofit().create(RetrofitInterface::class.java)
        postService.showScrapPostList(getJwt()!!).enqueue(object: Callback<BaseResponse<List<PostList>>> {
            //응답이 왔을 때 처리
            override fun onResponse(call: Call<BaseResponse<List<PostList>>>, response: Response<BaseResponse<List<PostList>>>) {
                Log.d("showScrapPostList", "response")
                val resp: BaseResponse<List<PostList>> = response.body()!!
                Log.d("showScrapPostListCode", resp.code.toString())
                when(resp.code){
                    //API code값 사용
                    200->showScrapPostListView.onShowScrapPostListSuccess(resp.code, resp.result)
                    else->showScrapPostListView.onShowScrapPostListFailure(resp.code)
                }
            }
            //실패했을 때 처리
            override fun onFailure(call: Call<BaseResponse<List<PostList>>>, t: Throwable) {
                Log.d("showScrapPostListFail", t.toString())
            }

        })
    }

    fun showPostDetail(postIdx:Int){
        Log.d("postdetail", "enter")
        val postService = getRetrofit().create(RetrofitInterface::class.java)
        postService.showPostDetail(postIdx, getJwt()!!).enqueue(object: Callback<BaseResponse<PostDetail>> {
            //응답이 왔을 때 처리
            override fun onResponse(call: Call<BaseResponse<PostDetail>>, response: Response<BaseResponse<PostDetail>>) {
                Log.d("postdetail", "response")
                val resp: BaseResponse<PostDetail> = response.body()!!
                Log.d("postdetailCode", resp.code.toString())
                when(resp.code){
                    //API code값 사용
                    200->postDetailtView.onPostDetailSuccess(resp.code, resp.result) //result를 받아서 UI를 구현해야함
                    else->postDetailtView.onPostDetailFailure(resp.code) //무슨 오류인지 알아야하므로 code가져가기
                }
            }
            //실패했을 때 처리
            override fun onFailure(call: Call<BaseResponse<PostDetail>>, t: Throwable) {
                Log.d("postdetailfail", t.toString())
            }

        })
    }

    fun createPostLike(postLike: PostLikeCreate){
        Log.d("createPostLike", "enter")
        val postService = getRetrofit().create(RetrofitInterface::class.java)
        postService.createPostLike(postLike, getJwt()!!).enqueue(object: Callback<BaseResponse<String>> {
            //응답이 왔을 때 처리
            override fun onResponse(call: Call<BaseResponse<String>>, response: Response<BaseResponse<String>>) {
                Log.d("createPostLike", "response")
                val resp: BaseResponse<String> = response.body()!!
                Log.d("createPostLikeCode", resp.code.toString())
                when(resp.code){
                    //API code값 사용
                    200->createPostLikeView.onCreatePostLikeSuccess(resp.code)
                    else->createPostLikeView.onCreatePostLikeFailure(resp.code) //무슨 오류인지 알아야하므로 code가져가기
                }
            }
            //실패했을 때 처리
            override fun onFailure(call: Call<BaseResponse<String>>, t: Throwable) {
                Log.d("createPostLikefail", t.toString())
            }

        })
    }

    fun cancelPostLike(postLike: PostLikeCancel){
        Log.d("cancelPostLike", "enter")
        val postService = getRetrofit().create(RetrofitInterface::class.java)
        postService.cancelPostLike(postLike, getJwt()!!).enqueue(object: Callback<BaseResponse<String>> {
            //응답이 왔을 때 처리
            override fun onResponse(call: Call<BaseResponse<String>>, response: Response<BaseResponse<String>>) {
                Log.d("cancelPostLike", "response")
                val resp: BaseResponse<String> = response.body()!!
                Log.d("cancelPostLikeCode", resp.code.toString())
                when(resp.code){
                    //API code값 사용
                    200->cancelPostLikeView.onCancelPostLikeSuccess(resp.code) //result를 받아서 UI를 구현해야함
                    else->cancelPostLikeView.onCancelPostLikeFailure(resp.code) //무슨 오류인지 알아야하므로 code가져가기
                }
            }
            //실패했을 때 처리
            override fun onFailure(call: Call<BaseResponse<String>>, t: Throwable) {
                Log.d("cancelPostLikefail", t.toString())
            }

        })
    }

    fun createPostScrap(postIdx: Int){
        Log.d("createPostScrap", "enter")
        val postService = getRetrofit().create(RetrofitInterface::class.java)
        postService.createPostScrap(postIdx, getJwt()!!).enqueue(object: Callback<BaseResponse<String>> {
            //응답이 왔을 때 처리
            override fun onResponse(call: Call<BaseResponse<String>>, response: Response<BaseResponse<String>>) {
                Log.d("createPostScrap", "response")
                val resp: BaseResponse<String> = response.body()!!
                Log.d("createPostScrapCode", resp.code.toString())
                when(resp.code){
                    //API code값 사용
                    200->createPostScrapView.onCreatePostScrapSuccess(resp.code) //result를 받아서 UI를 구현해야함
                    else->createPostScrapView.onCreatePostScrapFailure(resp.code) //무슨 오류인지 알아야하므로 code가져가기
                }
            }
            //실패했을 때 처리
            override fun onFailure(call: Call<BaseResponse<String>>, t: Throwable) {
                Log.d("createPostLikefail", t.toString())
            }

        })
    }

    fun cancelPostScrap(postIdx: Int){
        Log.d("cancelPostScrap", "enter")
        val postService = getRetrofit().create(RetrofitInterface::class.java)
        postService.cancelPostScrap(postIdx, getJwt()!!).enqueue(object: Callback<BaseResponse<String>> {
            //응답이 왔을 때 처리
            override fun onResponse(call: Call<BaseResponse<String>>, response: Response<BaseResponse<String>>) {
                Log.d("cancelPostScrap", "response")
                val resp: BaseResponse<String> = response.body()!!
                Log.d("cancelPostScrapCode", resp.code.toString())
                when(resp.code){
                    //API code값 사용
                    200->cancelPostScrapView.onCancelPostScrapSuccess(resp.code) //result를 받아서 UI를 구현해야함
                    else->cancelPostScrapView.onCancelPostScrapFailure(resp.code) //무슨 오류인지 알아야하므로 code가져가기
                }
            }
            //실패했을 때 처리
            override fun onFailure(call: Call<BaseResponse<String>>, t: Throwable) {
                Log.d("createPostLikefail", t.toString())
            }

        })
    }
}