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
import com.example.debri_lize.activity.WriteActivity
import com.example.debri_lize.data.Board
import com.example.debri_lize.data.Post
import com.example.debri_lize.databinding.FragmentPostBinding


class PostFragment : Fragment() {

    lateinit var binding: FragmentPostBinding
    private lateinit var postRVAdapter: PostRVAdapter
    private val datas = ArrayList<Post>()
    private val filteredData = ArrayList<Post>() //검색했을 때 나타낼 데이터


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

    //검색어가 포함된 타이틀을 filteredData에 넣기
    private fun searchFilter(searchText: String) {
        filteredData.clear()

        for (i in 0 until datas.size) {
            //타이틀, content 필터 / 공백 제거 안함
            if (datas[i].postName!!.lowercase().contains(searchText.lowercase())
                || datas[i].postContent!!.lowercase().contains(searchText.lowercase())) {
                filteredData.add(datas[i])
            }
        }

        postRVAdapter.filterList(filteredData)
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

            add(Post(0, 0, "알ㄹ랄ㄹ라", "여기서 오류 고치는 법1", "dd"))
            add(Post(0, 0, "알ㄹ랄ㄹ라", "여기서 오류 고치는 법2", "dd"))
            add(Post(0, 0, "알ㄹ랄ㄹ라", "여기서 오류 고치는 법3", "dd"))
            add(Post(0, 0, "알ㄹ랄ㄹ라", "여기서 오류 고치는 법4", "dd"))
            add(Post(0, 0, "알ㄹ랄ㄹ라", "여기서 오류 고치는 법5", "dd"))


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