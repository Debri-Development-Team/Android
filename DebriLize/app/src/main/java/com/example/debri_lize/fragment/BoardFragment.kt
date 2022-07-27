package com.example.debri_lize.fragment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.debri_lize.BoardFavoriteRVAdapter
import com.example.debri_lize.BoardRVAdapter
import com.example.debri_lize.R
import com.example.debri_lize.data.Board
import com.example.debri_lize.databinding.FragmentBoardBinding

class BoardFragment : Fragment() {

    lateinit var binding: FragmentBoardBinding
    lateinit var boardfavoriteRVAdapter: BoardFavoriteRVAdapter
    lateinit var boardRVAdapter: BoardRVAdapter
    val datas_f = ArrayList<Board>()
    val datas = ArrayList<Board>()
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
        initBoardFavoriteRecycler()
        initBoardRecycler()

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
    }

    //검색어가 포함된 타이틀을 filteredData에 넣기
    private fun searchFilter(searchText: String) {
        filteredData.clear()

        for (i in 0 until datas.size) {
            //title1, title2 필터 / 공백 제거 안함
            if (datas[i].title1!!.lowercase().contains(searchText.lowercase())
                || datas[i].title2!!.lowercase().contains(searchText.lowercase())) {
                filteredData.add(datas[i])
            }
        }

        boardRVAdapter.filterList(filteredData)
    }

    //검색어가 포함된 타이틀을 filteredData에 넣기
    private fun searchFavoriteFilter(searchText: String) {
        filteredFavoriteData.clear()

        for (i in 0 until datas.size) {
            //title1, title2 필터 / 공백 제거 안함
            if (datas[i].title1!!.lowercase().contains(searchText.lowercase())
                || datas[i].title2!!.lowercase().contains(searchText.lowercase())) {
                filteredFavoriteData.add(datas[i])
            }
        }

        boardfavoriteRVAdapter.filterList(filteredFavoriteData)
    }


    private fun initBoardFavoriteRecycler() {
        binding.boardFavoriteRv.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        boardfavoriteRVAdapter = BoardFavoriteRVAdapter()
        binding.boardFavoriteRv.adapter = boardfavoriteRVAdapter

        //dummy
        datas_f.apply {
            add(Board("&quot;JAVA&quot;","게시판"))
            add(Board("&quot;JAVA&quot;","게시판"))
            add(Board("&quot;JAVA&quot;","게시판"))
            add(Board("&quot;JAVA&quot;","게시판"))

            boardfavoriteRVAdapter.datas_f = datas_f
            boardfavoriteRVAdapter.notifyDataSetChanged()

            //recyclerview item 클릭하면 fragment 전환
            boardfavoriteRVAdapter.setItemClickListener(object : BoardFavoriteRVAdapter.OnItemClickListener {
                override fun onClick(v: View, position: Int) {



                }
            })


        }


    }

    private fun initBoardRecycler() {
        binding.boardAllRv.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        boardRVAdapter = BoardRVAdapter()
        binding.boardAllRv.adapter = boardRVAdapter

        //data
        datas.apply {
            add(Board("C언어", "게시판"))
            add(Board("C언어", "질문 게시판"))
            add(Board("JAVA", "게시판"))
            add(Board("JAVA", "질문 게시판"))
            add(Board("", "개발 뉴스 &amp; 이슈"))
            add(Board("", "질문 게시판"))
            add(Board("", "커리큘럼 질문게시판"))

            boardRVAdapter.datas = datas
            boardfavoriteRVAdapter.notifyDataSetChanged()

            //recyclerview item 클릭하면 fragment 전환
            boardRVAdapter.setItemClickListener(object : BoardRVAdapter.OnItemClickListener {
                override fun onClick(v: View, position: Int) {

                    //BoardDetailFragment에 data보내기
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