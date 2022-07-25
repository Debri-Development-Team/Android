package com.example.debri_lize.activity

import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.view.KeyEvent.KEYCODE_ENTER
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.debri_lize.PostRVAdapter
import com.example.debri_lize.data.post.PostList
import com.example.debri_lize.databinding.ActivityPostListBinding
import com.example.debri_lize.response.Post
import com.example.debri_lize.service.PostService
import com.example.debri_lize.view.post.PostListView
import kotlin.properties.Delegates


class PostListActivity : AppCompatActivity(), PostListView {

    lateinit var binding: ActivityPostListBinding
    private lateinit var postRVAdapter: PostRVAdapter
    private val datas = ArrayList<PostList>()

    var boardIdx by Delegates.notNull<Int>()
    var boardName by Delegates.notNull<String>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPostListBinding.inflate(layoutInflater) //binding 초기화
        setContentView(binding.root)
    }

    override fun onStart() {
        super.onStart()

        //fragment to fragment
        binding.postListPreviousIv.setOnClickListener{
            finish()
        }

        binding.postListSearchEt.setOnKeyListener { v, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN && keyCode == KEYCODE_ENTER) {
                // 엔터 눌렀을때 행동
                //api
                val postService = PostService()
                postService.setPostListView(this)
                postService.showPostList(binding.postListSearchEt.toString())
                true
            }

            false
        }

    }

    override fun onPostListSuccess(code: Int, result: List<Post>) {
        when(code){
            //개발할 때는 userIdx 저장이 필요할수도
            200-> {
                binding.postListRv.layoutManager =
                    LinearLayoutManager(this@PostListActivity, LinearLayoutManager.VERTICAL, false)
                postRVAdapter = PostRVAdapter()
                binding.postListRv.adapter = postRVAdapter

                //data
                datas.apply {

                    for (i in result){
                        datas.add(PostList(i.boardIdx, i.postIdx, i.authorName, i.postName, i.likeCnt, i.timeAfterCreated, i.commentCnt))
                    }

                    postRVAdapter.datas = datas
                    postRVAdapter.notifyDataSetChanged()

                    //recyclerview item 클릭하면 activity 전환
                    postRVAdapter.setItemClickListener(object : PostRVAdapter.OnItemClickListener {
                        override fun onClick(v: View, position: Int) {
                            // 클릭 시 이벤트 작성
                            //객체 자체를 보내는 방법 (data class)
                            val intent = Intent(this@PostListActivity, PostDetailActivity::class.java)
                            intent.putExtra("postIdx", datas[position].postIdx)
                            intent.putExtra("boardName", boardName)
                            startActivity(intent)

                        }
                    })


                }
            }
        }
    }

    override fun onPostListFailure(code: Int) {

    }

}