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
import com.example.debri_lize.adapter.board.BoardRVAdapter
import com.example.debri_lize.R
import com.example.debri_lize.activity.PostCreateActivity
import com.example.debri_lize.activity.PostListActivity
import com.example.debri_lize.activity.auth.ProfileActivity
import com.example.debri_lize.adapter.board.BoardFavoriteRVAdapter
import com.example.debri_lize.data.board.Board
import com.example.debri_lize.data.board.BoardFavorite
import com.example.debri_lize.databinding.FragmentBoardBinding
import com.example.debri_lize.service.BoardService
import com.example.debri_lize.view.board.CancelScrapBoardView
import com.example.debri_lize.view.board.CreateScrapBoardView
import com.example.debri_lize.view.board.ScrapBoardListView
import com.example.debri_lize.view.board.UnScrapBoardListView

class BoardFragment : Fragment(), UnScrapBoardListView, ScrapBoardListView, CancelScrapBoardView,
    CreateScrapBoardView {

    lateinit var binding: FragmentBoardBinding
    lateinit var boardRVAdapter: BoardRVAdapter
    lateinit var boardFavRVAdapter: BoardFavoriteRVAdapter
    val datas_f = ArrayList<BoardFavorite>()
    val datas = ArrayList<Board>()

    //search boardName
    private val filteredData = ArrayList<Board>() //검색했을 때 나타낼 데이터
    private val filteredFavoriteData = ArrayList<Board>()

    //api
    val boardService = BoardService()

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
        boardService.setUnScrapBoardListView(this)
        boardService.showUnScrapBoardList()
        boardService.setScrapBoardListView(this)
        boardService.showScrapBoardList()

        //api - 게시판 즐찾 생성, 해제
        boardService.setCreateScrapBoardView(this)
        boardService.setCancelScrapBoardView(this)


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

                    boardRVAdapter.setAllItemClickListener(object : BoardRVAdapter.OnAllItemClickListener{
                        override fun onClick(v: View, position: Int) {
                            //api - 즐겨찾기 생성
                            Log.d("data.position","${datas[position]}")
                            boardService.createScrapBoard(datas[position].boardIdx)

                        }

                    })
                }

            }
        }
    }

    override fun onUnScrapBoardListFailure(code: Int) {
        Log.d("unscrapboardlistfail","$code")
    }

    override fun onScrapBoardListSuccess(code: Int, result: List<BoardFavorite>) {
        when(code){
            200->{
                //즐겨찾기 게시판 조회
                binding.boardFavoriteRv.layoutManager =
                    LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                boardFavRVAdapter = BoardFavoriteRVAdapter()
                binding.boardFavoriteRv.adapter = boardFavRVAdapter

                datas_f.clear()

                //datas_f : 즐겨찾기
                datas_f.apply {
                    for (i in result){
                        datas_f.add(BoardFavorite(i.boardIdx, i.boardName, i.status))
                    }

                    boardFavRVAdapter.datas = datas_f
                    boardFavRVAdapter.notifyDataSetChanged()

                    //recyclerview item 클릭하면 fragment 전환
                    boardFavRVAdapter.setItemClickListener(object : BoardFavoriteRVAdapter.OnItemClickListener {
                        override fun onClick(v: View, position: Int) {
                            //PostFragment에 data보내기
                            val bundle = Bundle()
                            bundle.putSerializable("boardFav", datas_f[position])
                            val passBundleBFragment = PostFragment()
                            passBundleBFragment.arguments = bundle

                            //fragment to fragment
                            activity?.supportFragmentManager!!.beginTransaction()
                                .replace(R.id.main_frm, passBundleBFragment)
                                .commit()

                        }
                    })

                    boardFavRVAdapter.setFavItemClickListener(object : BoardFavoriteRVAdapter.OnFavItemClickListener{
                        override fun onClick(v: View, position: Int) {
                            //api - 즐겨찾기 해제
                            Log.d("datas_f.position","${datas_f[position]}")
                            boardService.cancelScrapBoard(datas_f[position].boardIdx)

                        }

                    })
                }
            }
        }
    }

    override fun onScrapBoardListFailure(code: Int) {
        when(code){
            3066 -> {
                binding.boardFavoriteRv.layoutManager =
                    LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                boardFavRVAdapter = BoardFavoriteRVAdapter()
                binding.boardFavoriteRv.adapter = boardFavRVAdapter
            }
        }
    }

    override fun onCreateScrapBoardSuccess(code: Int) {
        when(code){
            200-> {
                Log.d("scrapCreateBoardSuccess","$code")

                boardService.showScrapBoardList()
                boardService.showUnScrapBoardList()
            }
        }

    }

    override fun onCreateScrapBoardFailure(code: Int) {
        Log.d("scrapCreateBoardFail","$code")
    }

    override fun onCancelScrapBoardSuccess(code: Int) {
        when(code){
            200->{
                Log.d("scrapCancelBoardSuccess","$code")
                boardService.showScrapBoardList()
                boardService.showUnScrapBoardList()

            }
        }


    }

    override fun onCancelScrapBoardFailure(code: Int) {
        Log.d("scrapCancelBoardFail","$code")
    }



}