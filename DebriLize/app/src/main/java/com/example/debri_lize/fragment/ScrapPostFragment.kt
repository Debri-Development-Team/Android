package com.example.debri_lize.fragment

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.debri_lize.PostRVAdapter
import com.example.debri_lize.R
import com.example.debri_lize.activity.MainActivity
import com.example.debri_lize.activity.PostCreateActivity
import com.example.debri_lize.activity.PostDetailActivity
import com.example.debri_lize.data.post.PostList
import com.example.debri_lize.service.PostService
import com.example.debri_lize.databinding.FragmentScrapPostBinding
import com.example.debri_lize.utils.savePostIdx
import com.example.debri_lize.view.post.ShowScrapPostListView


class ScrapPostFragment : Fragment(), ShowScrapPostListView {

    lateinit var binding: FragmentScrapPostBinding
    private lateinit var postRVAdapter: PostRVAdapter
    private val datas = ArrayList<PostList>()

    //search post
    private val filteredData = ArrayList<PostList>() //검색했을 때 나타낼 데이터

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentScrapPostBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()

        //fragment to fragment
        binding.postScrapPreviousIv.setOnClickListener{
            (context as MainActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.main_frm, BoardFragment()).commitAllowingStateLoss()
        }

        //api
        val postService = PostService()
        postService.setShowScrapPostListView(this)
        postService.showScrapPostList()

        //create post
        binding.postWriteBtn.setOnClickListener{
            val intent = Intent(context, PostCreateActivity::class.java)
            intent.putExtra("boardIdx", 1)
            intent.putExtra("edit", false)
            startActivity(intent)
        }

    }

    //search post
    //검색어가 포함된 타이틀을 filteredData에 넣기
    private fun searchFilter(searchText: String) {
        filteredData.clear()

        for (i in 0 until datas.size) {
            //타이틀 필터
            if (datas[i].postName!!.lowercase().trim().contains(searchText.lowercase().trim())) {
                filteredData.add(datas[i])
            }
        }

        postRVAdapter.filterList(filteredData)
    }

    //recycler view
    override fun onShowScrapPostListSuccess(code: Int, result: List<PostList>) {
        when(code){
            //개발할 때는 userIdx 저장이 필요할수도
            200-> {
                binding.postScrapListRv.layoutManager =
                    LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                postRVAdapter = PostRVAdapter()
                binding.postScrapListRv.adapter = postRVAdapter

                datas.clear()


                //data
                datas.apply {

                    for (i in result){
                        datas.add(PostList(i.boardIdx, i.postIdx, i.authorName, i.postName, i.likeCnt, i.likeStatus, i.scrapStatus, i.timeAfterCreated, i.commentCnt, i.boardName))
                    }

                    postRVAdapter.datas = datas
                    postRVAdapter.notifyDataSetChanged()

                    //recyclerview item 클릭하면 fragment 전환
                    postRVAdapter.setItemClickListener(object : PostRVAdapter.OnItemClickListener {
                        override fun onClick(v: View, position: Int) {
                            // 클릭 시 이벤트 작성
                            activity?.let {
                                savePostIdx(datas[position].postIdx!!)

                                //객체 자체를 보내는 방법 (data class)
                                val intent = Intent(context, PostDetailActivity::class.java)
                                intent.putExtra("postIdx", datas[position].postIdx)
                                intent.putExtra("boardName", datas[position].boardName)
                                startActivity(intent)

                            }

                        }
                    })


                }

                //search post
                //검색어 입력
                binding.postScrapSearchEt.addTextChangedListener(object : TextWatcher {
                    //입력이 끝날 때
                    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                    }

                    //입력하기 전에
                    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                    }

                    //타이핑되는 텍스트에 변화가 있을 때
                    override fun afterTextChanged(p0: Editable?) {
                        val searchText: String = binding.postScrapSearchEt.text.toString()
                        //Log.d("editText","$searchText")
                        searchFilter(searchText)
                    }

                })
            }
        }
    }

    override fun onShowScrapPostListFailure(code: Int) {

    }

}