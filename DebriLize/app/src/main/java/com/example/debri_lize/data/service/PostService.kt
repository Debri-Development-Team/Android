package com.example.debri_lize.data.service

import android.util.Log
import com.example.debri_lize.data.Post
import com.example.debri_lize.data.RetrofitInterface
import com.example.debri_lize.data.response.PostDetailResponse
import com.example.debri_lize.data.response.PostResponse
import com.example.debri_lize.data.view.post.EachPostListView
import com.example.debri_lize.data.view.post.PostCreateView
import com.example.debri_lize.data.view.post.PostDetailView
import com.example.debri_lize.fragment.PostFragment
import com.example.debri_lize.utils.getRetrofit
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PostService {
    private lateinit var postCreateView: PostCreateView
    private lateinit var eachPostListView: EachPostListView
    private lateinit var postDetailtView: PostDetailView

    fun setPostCreateView(postCreateView: PostCreateView){
        this.postCreateView = postCreateView
    }

    fun seteachPostListView(eachPostListView: EachPostListView){
        this.eachPostListView = eachPostListView
    }

    fun setPostDetailView(postDetailtView: PostDetailView){
        this.postDetailtView = postDetailtView
    }


    fun createPost(post: Post){
        Log.d("post", post.toString())
        //서비스 객체 생성
        val postService = getRetrofit().create(RetrofitInterface::class.java)

        postService.createPost(post).enqueue(object: Callback<PostResponse> {
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

    fun showEachPostList(boardIdx:Int){
        Log.d("eachpostlist", "enter")
        val postService = getRetrofit().create(RetrofitInterface::class.java)
        postService.showEachPostList(boardIdx).enqueue(object: Callback<PostResponse> {
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
        postService.showPostDetail(postIdx).enqueue(object: Callback<PostDetailResponse> {
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
}