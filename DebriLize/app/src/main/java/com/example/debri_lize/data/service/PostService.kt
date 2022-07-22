package com.example.debri_lize.data.service

import android.util.Log
import com.example.debri_lize.data.Post
import com.example.debri_lize.data.RetrofitInterface
import com.example.debri_lize.data.UserLogin
import com.example.debri_lize.data.UserSignup
import com.example.debri_lize.data.response.AuthResponse
import com.example.debri_lize.data.response.PostResponse
import com.example.debri_lize.data.view.PostCreateView
import com.example.debri_lize.utils.getRetrofit
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PostService {
    private lateinit var postCreateView: PostCreateView

    fun setPostCreateView(postCreateView: PostCreateView){
        this.postCreateView = postCreateView
    }


    fun createPost(post: Post){
        Log.d("post", post.toString())
        //서비스 객체 생성
        val authService = getRetrofit().create(RetrofitInterface::class.java)

        authService.createPost(post).enqueue(object: Callback<PostResponse> {
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
}