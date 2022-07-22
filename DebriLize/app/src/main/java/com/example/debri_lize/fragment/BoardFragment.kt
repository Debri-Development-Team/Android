package com.example.debri_lize.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.debri_lize.BoardFavoriteRVAdapter
import com.example.debri_lize.BoardRVAdapter
import com.example.debri_lize.R
import com.example.debri_lize.data.board.Board
import com.example.debri_lize.databinding.FragmentBoardBinding

class BoardFragment : Fragment() {

    lateinit var binding: FragmentBoardBinding
    lateinit var boardfavoriteRVAdapter: BoardFavoriteRVAdapter
    lateinit var boardRVAdapter: BoardRVAdapter
    val datas_f = ArrayList<Board>()
    val datas = ArrayList<Board>()

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