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
import com.example.debri_lize.data.board.Board
import com.example.debri_lize.data.post.PostList
import com.example.debri_lize.service.PostService
import com.example.debri_lize.view.post.EachPostListView
import com.example.debri_lize.databinding.FragmentPostBinding
import com.example.debri_lize.utils.*
import kotlin.properties.Delegates


class PostFragment : Fragment(), EachPostListView {

    lateinit var binding: FragmentPostBinding
    private lateinit var postRVAdapter: PostRVAdapter
    private val datas = ArrayList<PostList>()

    var boardIdx by Delegates.notNull<Int>()
    var boardName by Delegates.notNull<String>()

    //search post
    private val filteredData = ArrayList<PostList>() //검색했을 때 나타낼 데이터

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPostBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()

        //data 받아오기 (BoardFragment -> BoardDetailFragment) : 게시판 이름
        var board = arguments?.getSerializable("board") as Board?
        //받아온 data로 변경
        Log.d("board", board.toString())
        if (board != null) {
            //게시판 이름 변경
            binding.postNameTv.text = board.boardName
            boardName = board.boardName
            boardIdx = board.boardIdx
        }

        //fragment to fragment
        binding.postPreviousIv.setOnClickListener{
            (context as MainActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.main_frm, BoardFragment()).commitAllowingStateLoss()
        }

        //api
        val postService = PostService()
        postService.seteachPostListView(this)
        postService.showEachPostList(boardIdx) //변경필요

        //게시글 작성하기 버튼
        binding.postWriteBtn.setOnClickListener{
            val intent = Intent(context, PostCreateActivity::class.java)
            intent.putExtra("boardIdx", boardIdx)
            intent.putExtra("edit", false)
            startActivity(intent)
        }



    }

    //search post
    //검색어가 포함된 타이틀을 filteredData에 넣기
    private fun searchFilter(searchText: String) {
        filteredData.clear()

        //기존 datas : <Post> -> 현재 datas : <PostList>
        //api연결로 인해 data구조가 변경되었으므로 이 부분 확인 필요
        //제목으로밖에 검색이 안됨

        //현재 data중 게시글 제목이 null인게 있어 error발생
//        for (i in 0 until datas.size) {
//            //타이틀, content 필터 / 공백 제거 안함
//            if (datas[i].postName!!.lowercase().contains(searchText.lowercase())) {
//                filteredData.add(datas[i])
//            }
//        }

        postRVAdapter.filterList(filteredData)
    }

    override fun onEachPostListSuccess(code: Int, result: List<com.example.debri_lize.response.Post>) {
        when(code){
            //개발할 때는 userIdx 저장이 필요할수도
            200-> {
                binding.postListRv.layoutManager =
                    LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                postRVAdapter = PostRVAdapter()
                binding.postListRv.adapter = postRVAdapter

                datas.clear()


                //data
                datas.apply {

                    for (i in result){
                        datas.add(PostList(i.boardIdx, i.postIdx, i.authorName, i.postName, i.likeCnt, i.likeStatus, i.scrapStatus, i.timeAfterCreated, i.commentCnt))
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
                                intent.putExtra("boardName", boardName)
                                //intent.putExtra("likeStatus", getLikeStatus())
                                startActivity(intent)

                            }

                        }
                    })


                }

                //search post
                //검색어 입력
                binding.postSearchEt.addTextChangedListener(object : TextWatcher {
                    //입력이 끝날 때
                    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                    }

                    //입력하기 전에
                    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                    }

                    //타이핑되는 텍스트에 변화가 있을 때
                    override fun afterTextChanged(p0: Editable?) {
                        val searchText: String = binding.postSearchEt.text.toString()
                        //Log.d("editText","$searchText")
                        searchFilter(searchText)
                    }

                })
            }
        }
    }

    override fun onEachPostListFailure(code: Int) {
        TODO("Not yet implemented")
    }

}