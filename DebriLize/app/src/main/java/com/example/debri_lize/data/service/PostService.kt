package com.example.debri_lize.data.service

import android.util.Log
import com.example.debri_lize.data.Post
import com.example.debri_lize.data.RetrofitInterface
import com.example.debri_lize.data.response.PostResponse
import com.example.debri_lize.data.view.post.EachPostListView
import com.example.debri_lize.data.view.post.PostCreateView
import com.example.debri_lize.fragment.PostFragment
import com.example.debri_lize.utils.getRetrofit
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PostService {
    private lateinit var postCreateView: PostCreateView
    private lateinit var eachPostListView: EachPostListView

    fun setPostCreateView(postCreateView: PostCreateView){
        this.postCreateView = postCreateView
    }

    fun seteachPostListView(eachPostListView: PostFragment){
        this.eachPostListView = eachPostListView
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

    fun eachPostList(boardIdx:Int){
        Log.d("eachpostlist", "enter")
        val postService = getRetrofit().create(RetrofitInterface::class.java)
        postService.eachPostList(boardIdx).enqueue(object: Callback<PostResponse> {
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
}