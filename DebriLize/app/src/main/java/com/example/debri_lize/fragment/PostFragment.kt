package com.example.debri_lize.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.debri_lize.PostRVAdapter
import com.example.debri_lize.R
import com.example.debri_lize.activity.MainActivity
import com.example.debri_lize.activity.WriteActivity
import com.example.debri_lize.data.Board
import com.example.debri_lize.data.Post
import com.example.debri_lize.databinding.FragmentPostBinding


class PostFragment : Fragment() {

    lateinit var binding: FragmentPostBinding
    private lateinit var postRVAdapter: PostRVAdapter
    private val datas = ArrayList<Post>()

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
            //레이아웃에 있는 text를 변경
            binding.postTitleTv1.text = board.title1
            binding.postTitleTv2.text = board.title2
        }

        //fragment to fragment
        binding.postPreviousIv.setOnClickListener{
            (context as MainActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.main_frm, BoardFragment()).commitAllowingStateLoss()
        }

        //post data 받아오기



        binding.postWriteBtn.setOnClickListener{
            val intent = Intent(context, WriteActivity::class.java)
            //intent.putExtra("userid", userid)
            startActivity(intent)
        }

    }

    override fun onResume() {
        super.onResume()

        initBoardDetailRecycler()
    }


    private fun initBoardDetailRecycler() {
        binding.postListRv.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        postRVAdapter = PostRVAdapter()
        binding.postListRv.adapter = postRVAdapter

        //data
        datas.apply {

            add(Post(0, 0, "알ㄹ랄ㄹ라", "여기서 오류 고치는 법", "dd"))
            add(Post(0, 0, "알ㄹ랄ㄹ라", "여기서 오류 고치는 법", "dd"))
            add(Post(0, 0, "알ㄹ랄ㄹ라", "여기서 오류 고치는 법", "dd"))
            add(Post(0, 0, "알ㄹ랄ㄹ라", "여기서 오류 고치는 법", "dd"))
            add(Post(0, 0, "알ㄹ랄ㄹ라", "여기서 오류 고치는 법", "dd"))


            postRVAdapter.datas = datas
            postRVAdapter.notifyDataSetChanged()

            //recyclerview item 클릭하면 fragment 전환
            postRVAdapter.setItemClickListener(object : PostRVAdapter.OnItemClickListener {
                override fun onClick(v: View, position: Int) {
                    // 클릭 시 이벤트 작성
                    activity?.let {

                        //객체 자체를 보내는 방법 (data class)
                        //val intent = Intent(context, DailyCalendarActivity::class.java)
                        //intent.putExtra("schedule", datas[position])
                        //startActivity(intent)

                    }

                }
            })


        }


    }

}