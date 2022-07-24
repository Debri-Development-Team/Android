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
import com.example.debri_lize.service.BoardService
import com.example.debri_lize.service.PostService
import com.example.debri_lize.view.board.BoardView

class BoardFragment : Fragment(), BoardView {

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

        //api
        val boardService = BoardService()
        boardService.setBoardView(this)
        boardService.showBoard()
    }



    override fun onBoardSuccess(code: Int, result: List<com.example.debri_lize.response.Board>) {
        when(code){
            200->{
                //전체 게시판 조회 (즐겨찾기된 게시판은 삭제)
                binding.boardAllRv.layoutManager =
                    LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                boardRVAdapter = BoardRVAdapter()
                binding.boardAllRv.adapter = boardRVAdapter

                //즐겨찾기 게시판 조회
                binding.boardFavoriteRv.layoutManager =
                    LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                boardfavoriteRVAdapter = BoardFavoriteRVAdapter()
                binding.boardFavoriteRv.adapter = boardfavoriteRVAdapter

                //data : 전체
                datas.apply {
                    for (i in result){

                        datas.add(Board(i.boardIdx, i.boardName))
//                        if(i.status == "INACTIVE") {
//                            datas.add(Board(i.boardIdx, i.boardName, i.status))
//                        }
                    }

                    boardRVAdapter.datas = datas
                    boardfavoriteRVAdapter.notifyDataSetChanged()

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

                //datas_f : 즐겨찾기
                datas_f.apply {
                    for (i in result){

                        datas_f.add(Board(i.boardIdx, i.boardName))

//                        if(i.status == "ACTIVE") {
//                            datas_f.add(Board(i.boardIdx, i.boardName, i.status))
//                        }
                    }

                    boardfavoriteRVAdapter.datas_f = datas_f
                    boardfavoriteRVAdapter.notifyDataSetChanged()

                    //recyclerview item 클릭하면 fragment 전환
                    boardfavoriteRVAdapter.setItemClickListener(object : BoardFavoriteRVAdapter.OnItemClickListener {
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

    override fun onBoardFailure(code: Int) {

    }

}