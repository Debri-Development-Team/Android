package com.example.debri_lize.fragment

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.debri_lize.BoardRVAdapter
import com.example.debri_lize.R
import com.example.debri_lize.activity.PostCreateActivity
import com.example.debri_lize.activity.PostListActivity
import com.example.debri_lize.activity.auth.ProfileActivity
import com.example.debri_lize.data.board.Board
import com.example.debri_lize.databinding.FragmentBoardBinding
import com.example.debri_lize.service.BoardService
import com.example.debri_lize.view.board.UnScrapBoardListView
import com.example.debri_lize.view.board.ScrapBoardListView

class BoardFragment : Fragment(), UnScrapBoardListView, ScrapBoardListView {

    lateinit var binding: FragmentBoardBinding
    lateinit var boardRVAdapter: BoardRVAdapter
    val datas_f = ArrayList<Board>()
    val datas = ArrayList<Board>()

    //search boardName
    private val filteredData = ArrayList<Board>() //검색했을 때 나타낼 데이터
    private val filteredFavoriteData = ArrayList<Board>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBoardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        //click userImg -> profile
        binding.boardDebriUserIv.setOnClickListener{
            val intent = Intent(context, ProfileActivity::class.java)
            startActivity(intent)
        }

        //search
        binding.boardSearchLayout.setOnClickListener{
            val intent = Intent(activity, PostListActivity::class.java)
            startActivity(intent)
        }

        //api
        val boardService = BoardService()
        boardService.setUnScrapBoardListView(this)
        boardService.showUnScrapBoardList()
        boardService.setScrapBoardListView(this)
        boardService.showScrapBoardList()

        //search boardName
        //검색어 입력
        binding.boardSearchEt.addTextChangedListener(object : TextWatcher {
            //입력이 끝날 때
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            //입력하기 전에
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            //타이핑되는 텍스트에 변화가 있을 때
            override fun afterTextChanged(p0: Editable?) {
                val searchText: String = binding.boardSearchEt.text.toString()
                //Log.d("editText","$searchText")
                searchFilter(searchText)

                //검색어 입력시에 즐겨찾기 게시판에서 필터하는 기능인데 오류로 일단 주석처리
                //searchFavoriteFilter(searchText)
            }

        })

        //create post
        binding.boardWriteBtn.setOnClickListener{
            val intent = Intent(context, PostCreateActivity::class.java)
            intent.putExtra("boardIdx", 1) //가장 첫번째 board로 자동 지정
            intent.putExtra("edit", false)
            startActivity(intent)
        }
    }

    //search boardName
    //검색어가 포함된 타이틀을 filteredData에 넣기
    private fun searchFilter(searchText: String) {
        filteredData.clear()

        for (i in 0 until datas.size) {
            //boardName 필터 / 공백 제거 안함
            if (datas[i].boardName!!.lowercase().contains(searchText.lowercase())) {
                filteredData.add(datas[i])
            }
        }

        boardRVAdapter.filterList(filteredData)
    }

    //검색어가 포함된 타이틀을 filteredData에 넣기
    private fun searchFavoriteFilter(searchText: String) {
        filteredFavoriteData.clear()

        for (i in 0 until datas.size) {
            //boardName 필터 / 공백 제거 안함
            if (datas[i].boardName!!.lowercase().contains(searchText.lowercase())) {
                filteredFavoriteData.add(datas[i])
            }
        }

        boardRVAdapter.filterList(filteredFavoriteData)
    }



    override fun onUnScrapBoardListSuccess(code: Int, result: List<Board>) {
        when(code){
            200->{
                //전체 게시판 조회 (즐겨찾기된 게시판은 삭제)
                binding.boardAllRv.layoutManager =
                    LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                boardRVAdapter = BoardRVAdapter()
                binding.boardAllRv.adapter = boardRVAdapter

                datas.clear()

                //data : 전체
                datas.apply {
                    for (i in result){
                        datas.add(Board(i.boardIdx, i.boardName))
                    }

                    boardRVAdapter.datas = datas
                    boardRVAdapter.notifyDataSetChanged()

                    //recyclerview item 클릭하면 fragment 전환
                    boardRVAdapter.setItemClickListener(object : BoardRVAdapter.OnItemClickListener {
                        override fun onClick(v: View, position: Int) {

                            //PostFragment에 data보내기
                            val bundle = Bundle()
                            bundle.putSerializable("board", datas[position])
                            val passBundleBFragment = PostFragment()
                            passBundleBFragment.arguments = bundle

                            //fragment to fragment
                            activity?.supportFragmentManager!!.beginTransaction()
                                .replace(R.id.main_frm, passBundleBFragment)
                                .commit()

                        }
                    })
                }

            }
        }
    }

    override fun onUnScrapBoardListFailure(code: Int) {

    }

    override fun onScrapBoardListSuccess(code: Int, result: List<Board>) {
        when(code){
            200->{
                //즐겨찾기 게시판 조회
                binding.boardFavoriteRv.layoutManager =
                    LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                boardRVAdapter = BoardRVAdapter()
                binding.boardFavoriteRv.adapter = boardRVAdapter


                //datas_f : 즐겨찾기
                datas_f.apply {
                    for (i in result){
                        datas_f.add(Board(i.boardIdx, i.boardName))
                    }

                    boardRVAdapter.datas = datas_f
                    boardRVAdapter.notifyDataSetChanged()

                    //recyclerview item 클릭하면 fragment 전환
                    boardRVAdapter.setItemClickListener(object : BoardRVAdapter.OnItemClickListener {
                        override fun onClick(v: View, position: Int) {
                            //PostFragment에 data보내기
                            val bundle = Bundle()
                            bundle.putSerializable("board", datas[position])
                            val passBundleBFragment = PostFragment()
                            passBundleBFragment.arguments = bundle

                            //fragment to fragment
                            activity?.supportFragmentManager!!.beginTransaction()
                                .replace(R.id.main_frm, passBundleBFragment)
                                .commit()

                        }
                    })
                }
            }
        }
    }

    override fun onScrapBoardListFailure(code: Int) {

    }

}