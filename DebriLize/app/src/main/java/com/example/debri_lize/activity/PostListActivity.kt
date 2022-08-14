package com.example.debri_lize.activity

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.debri_lize.PostRVAdapter
import com.example.debri_lize.data.post.PostList
import com.example.debri_lize.databinding.ActivityPostListBinding
import com.example.debri_lize.service.PostService
import com.example.debri_lize.view.post.PostListView


class PostListActivity : AppCompatActivity(), PostListView {

    lateinit var binding: ActivityPostListBinding
    private lateinit var postRVAdapter: PostRVAdapter
    private val datas = ArrayList<PostList>()

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

        //search post : 실시간 검색기능
        //검색어 입력
        binding.postListSearchEt.addTextChangedListener(object : TextWatcher {
            //입력이 끝날 때
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            //입력하기 전에
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            //타이핑되는 텍스트에 변화가 있을 때
            override fun afterTextChanged(p0: Editable?) {
                //api
                val postService = PostService()
                postService.setPostListView(this@PostListActivity)
                postService.showPostList(binding.postListSearchEt.text.toString())
                Log.d("postListSearch",binding.postListSearchEt.text.toString())

                true
            }

        })


    }

    override fun onPostListSuccess(code: Int, result: List<PostList>) {
        when(code){
            //개발할 때는 userIdx 저장이 필요할수도
            200-> {
                binding.postListRv.layoutManager =
                    LinearLayoutManager(this@PostListActivity, LinearLayoutManager.VERTICAL, false)
                postRVAdapter = PostRVAdapter()
                binding.postListRv.adapter = postRVAdapter

                //data
                datas.clear()
                datas.apply {
                    Log.d("resultSize", result.size.toString())
                    for (i in result){
                        Log.d("postlist","$result")
                        datas.add(PostList(i.boardIdx, i.postIdx, i.authorName, i.postName, i.likeCnt, i.likeStatus,i.scrapStatus, i.timeAfterCreated, i.commentCnt, i.boardName))
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
                            intent.putExtra("boardName", datas[position].boardName)
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