package com.example.debri_lize.fragment

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.debri_lize.R
import com.example.debri_lize.activity.MainActivity
import com.example.debri_lize.activity.PostCreateActivity
import com.example.debri_lize.activity.PostDetailActivity
import com.example.debri_lize.adapter.post.PostRVAdapter
import com.example.debri_lize.data.board.Board
import com.example.debri_lize.data.board.BoardFavorite
import com.example.debri_lize.data.post.PostInfo
import com.example.debri_lize.data.post.PostList
import com.example.debri_lize.databinding.FragmentPostBinding
import com.example.debri_lize.service.PostService
import com.example.debri_lize.utils.*
import com.example.debri_lize.view.post.EachPostListView
import kotlin.properties.Delegates


class PostFragment : Fragment(), EachPostListView {

    lateinit var binding: FragmentPostBinding
    private lateinit var postRVAdapter: PostRVAdapter
    private val datas = ArrayList<PostList>()


    var boardIdx by Delegates.notNull<Int>()
    var pageNum : Int = 1  //현재 페이지 번호
    var page : Int = 1      //현재 페이지가 속한 곳 pageNum이 1~5면 1, 6~10이면 2

    var totalPage : Int = 0
    var boardName by Delegates.notNull<String>()

    //search post
    private val filteredData = ArrayList<PostList>() //검색했을 때 나타낼 데이터

    val postService = PostService()

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
        var boardFav = arguments?.getSerializable("boardFav") as BoardFavorite?
        //받아온 data로 변경
        Log.d("board", board.toString())
        Log.d("boardFav", boardFav.toString())
        if (board != null) {
            //게시판 이름 변경
            binding.postNameTv.text = board.boardName
            boardName = board.boardName
            boardIdx = board.boardIdx
            binding.postFavoriteIv.setImageResource(R.drawable.ic_favorite_off)
        }
        if(boardFav != null) {
            //게시판 이름 변경
            binding.postNameTv.text = boardFav.boardName
            boardName = boardFav.boardName
            boardIdx = boardFav.boardIdx
            binding.postFavoriteIv.setImageResource(R.drawable.ic_favorite_on)
        }


        //fragment to fragment
        binding.postPreviousIv.setOnClickListener{
            (context as MainActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.main_frm, BoardFragment()).commitAllowingStateLoss()
        }


        //api

        postService.seteachPostListView(this)
        postService.showEachPostList(boardIdx, pageNum) //변경필요

        //create post
        binding.postWriteBtn.setOnClickListener{
            val intent = Intent(context, PostCreateActivity::class.java)
            intent.putExtra("boardIdx", boardIdx)
            intent.putExtra("edit", false)
            startActivity(intent)
        }

        pageButtonClick()

        //focus
        binding.postSearchEt.setOnFocusChangeListener(object : View.OnFocusChangeListener {
            override fun onFocusChange(view: View, hasFocus: Boolean) {
                if (hasFocus) {
                    //  포커스시
                    binding.postSearchLayout.setBackgroundResource(R.drawable.border_round_debri_transparent_10)
                    binding.postSearchIv.setImageResource(R.drawable.btm_nav_search_on)
                } else {
                    //  포커스 뺏겼을 때
                    binding.postSearchLayout.setBackgroundResource(R.drawable.border_round_white_transparent_10)
                    binding.postSearchIv.setImageResource(R.drawable.btm_nav_search)
                }
            }
        })

    }

    //search post
    //검색어가 포함된 타이틀을 filteredData에 넣기
    private fun searchFilter(searchText: String) {
        filteredData.clear()

        //기존 datas : <Post> -> 현재 datas : <PostList>
        //api연결로 인해 data구조가 변경되었으므로 이 부분 확인 필요
        //제목으로밖에 검색이 안됨

        //현재 data중 게시글 제목이 null인게 있어 error발생
        for (i in 0 until datas.size) {
            //타이틀 필터
            if (datas[i].postName!!.lowercase().trim().contains(searchText.lowercase().trim())) {
                filteredData.add(datas[i])
            }
        }

        postRVAdapter.filterList(filteredData)
    }

    override fun onEachPostListSuccess(code: Int, result: PostInfo) {
        when(code){
            //개발할 때는 userIdx 저장이 필요할수도
            200-> {
                binding.postListRv.layoutManager =
                    LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                postRVAdapter = PostRVAdapter()
                binding.postListRv.adapter = postRVAdapter

                datas.clear()

                totalPage = if(result.postCount!!%12==0) result.postCount!!/12 else result.postCount!!/12 + 1
                page = if(pageNum%5==0) pageNum/5 else pageNum/5+1


                pageButton()
//                pageButtonClick()


                //data
                datas.apply {

                    for (i in result.postList){
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
        Log.d("eachpostlistfail",code.toString())
    }

    private fun pageButtonClick(){
        binding.postPagenum1Tv.setOnClickListener {
            pageNum = (page-1)*5+1
            pageButton()
            postService.showEachPostList(boardIdx, pageNum)
        }
        binding.postPagenum2Tv.setOnClickListener {
            pageNum = (page-1)*5+2
            pageButton()
            postService.showEachPostList(boardIdx, pageNum)
        }
        binding.postPagenum3Tv.setOnClickListener {
            pageNum = (page-1)*5+3
            pageButton()
            postService.showEachPostList(boardIdx, pageNum)
        }
        binding.postPagenum4Tv.setOnClickListener {
            pageNum = (page-1)*5+4
            pageButton()
            postService.showEachPostList(boardIdx, pageNum)
        }
        binding.postPagenum5Tv.setOnClickListener {
            pageNum = (page-1)*5+5
            pageButton()
            postService.showEachPostList(boardIdx, pageNum)
        }
//        Log.d("pageclick",pageNum.toString())



    }

    private fun pageButton(){
        //페이지 번호
        binding.postPagenum1Tv.text = ((page-1)*5+1).toString()
        binding.postPagenum2Tv.text = ((page-1)*5+2).toString()
        binding.postPagenum3Tv.text = ((page-1)*5+3).toString()
        binding.postPagenum4Tv.text = ((page-1)*5+4).toString()
        binding.postPagenum5Tv.text = ((page-1)*5+5).toString()

        //화살표 visibility 설정
        if(page == 1)   binding.postPagePreviousIv.visibility = View.INVISIBLE
        else    binding.postPagePreviousIv.visibility = View.VISIBLE
        if(totalPage>=(page-1)*5+1 && totalPage<=(page-1)*5+5)
            binding.postPageNextIv.visibility = View.INVISIBLE
        else    binding.postPageNextIv.visibility = View.VISIBLE

        //숫자 버튼 visibility 설정
        if(totalPage-page*5 == -1){
            binding.postPagenum2Tv.visibility = View.VISIBLE
            binding.postPagenum3Tv.visibility = View.VISIBLE
            binding.postPagenum4Tv.visibility = View.VISIBLE
            binding.postPagenum5Tv.visibility = View.INVISIBLE
        }else if(totalPage-page*5 == -2){
            binding.postPagenum2Tv.visibility = View.VISIBLE
            binding.postPagenum3Tv.visibility = View.VISIBLE
            binding.postPagenum4Tv.visibility = View.INVISIBLE
            binding.postPagenum5Tv.visibility = View.INVISIBLE
        } else if(totalPage-page*5 == -3){
            binding.postPagenum2Tv.visibility = View.VISIBLE
            binding.postPagenum3Tv.visibility = View.INVISIBLE
            binding.postPagenum4Tv.visibility = View.INVISIBLE
            binding.postPagenum5Tv.visibility = View.INVISIBLE
        }
        else if(totalPage-page*5 == -4){
            binding.postPagenum2Tv.visibility = View.INVISIBLE
            binding.postPagenum3Tv.visibility = View.INVISIBLE
            binding.postPagenum4Tv.visibility = View.INVISIBLE
            binding.postPagenum5Tv.visibility = View.INVISIBLE
        }else{
            binding.postPagenum2Tv.visibility = View.VISIBLE
            binding.postPagenum3Tv.visibility = View.VISIBLE
            binding.postPagenum4Tv.visibility = View.VISIBLE
            binding.postPagenum5Tv.visibility = View.VISIBLE
        }

        //background circle 설정
        if(pageNum%5 == 1) {
            binding.postPagenum1Tv.setBackgroundResource(R.drawable.circle_debri_debri_8)
            binding.postPagenum2Tv.setBackgroundResource(R.color.transparent)
            binding.postPagenum3Tv.setBackgroundResource(R.color.transparent)
            binding.postPagenum4Tv.setBackgroundResource(R.color.transparent)
            binding.postPagenum5Tv.setBackgroundResource(R.color.transparent)
        }else if(pageNum%5 == 2){
            binding.postPagenum1Tv.setBackgroundResource(R.color.transparent)
            binding.postPagenum2Tv.setBackgroundResource(R.drawable.circle_debri_debri_8)
            binding.postPagenum3Tv.setBackgroundResource(R.color.transparent)
            binding.postPagenum4Tv.setBackgroundResource(R.color.transparent)
            binding.postPagenum5Tv.setBackgroundResource(R.color.transparent)
        }else if(pageNum%5 == 3){
            binding.postPagenum1Tv.setBackgroundResource(R.color.transparent)
            binding.postPagenum2Tv.setBackgroundResource(R.color.transparent)
            binding.postPagenum3Tv.setBackgroundResource(R.drawable.circle_debri_debri_8)
            binding.postPagenum4Tv.setBackgroundResource(R.color.transparent)
            binding.postPagenum5Tv.setBackgroundResource(R.color.transparent)
        }else if(pageNum%5 == 4){
            binding.postPagenum1Tv.setBackgroundResource(R.color.transparent)
            binding.postPagenum2Tv.setBackgroundResource(R.color.transparent)
            binding.postPagenum3Tv.setBackgroundResource(R.color.transparent)
            binding.postPagenum4Tv.setBackgroundResource(R.drawable.circle_debri_debri_8)
            binding.postPagenum5Tv.setBackgroundResource(R.color.transparent)
        }else if(pageNum%5 == 0){
            binding.postPagenum1Tv.setBackgroundResource(R.color.transparent)
            binding.postPagenum2Tv.setBackgroundResource(R.color.transparent)
            binding.postPagenum3Tv.setBackgroundResource(R.color.transparent)
            binding.postPagenum4Tv.setBackgroundResource(R.color.transparent)
            binding.postPagenum5Tv.setBackgroundResource(R.drawable.circle_debri_debri_8)
        }

    }
}