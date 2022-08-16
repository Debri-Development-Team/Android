package com.example.debri_lize.fragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.debri_lize.R
import com.example.debri_lize.activity.MainActivity
import com.example.debri_lize.adapter.class_.ClassLectureRVAdapter
import com.example.debri_lize.adapter.class_.LectureReviewRVAdapter
import com.example.debri_lize.adapter.start.ReviewRVAdapter
import com.example.debri_lize.data.board.Board
import com.example.debri_lize.data.class_.Lecture
import com.example.debri_lize.data.curriculum.Review
import com.example.debri_lize.databinding.FragmentLectureDetailBinding
import kotlin.concurrent.thread
import kotlin.properties.Delegates


class LectureDetailFragment : Fragment() {

    lateinit var binding : FragmentLectureDetailBinding
    lateinit var mainActivity: MainActivity

    lateinit var lectureReviewRVAdapter: LectureReviewRVAdapter
    val review = ArrayList<Review>()



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLectureDetailBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        mainActivity = context as MainActivity
    }

    override fun onStart() {
        super.onStart()

        initRecyclerView()

        //data 받아오기 (ClassFragment -> LectureDetailFragment) : 게시판 이름
        var lectureFav = arguments?.getSerializable("lectureFav") as Lecture?

        var language = binding.lectureDetailTagLanguageTv
        var lectureName = binding.lectureDetailTitleTv

        //받아온 데이타로 변경
        if(lectureFav != null) {
            //강의 이름 변경
            language.text = lectureFav.language
            lectureName.text = lectureFav.lectureName
//            boardIdx = lectureFav.chapterNum


            when(language.text){
                "Front" -> language.setBackgroundResource(R.drawable.border_round_transparent_front_10)
                "Back" -> language.setBackgroundResource(R.drawable.border_round_transparent_back_10)
                "C 언어" -> language.setBackgroundResource(R.drawable.border_round_transparent_c_10)
                "Python" -> language.setBackgroundResource(R.drawable.border_round_transparent_python_10)
            }
        }

        //fragment to fragment
        binding.lectureDetailPreviousIv.setOnClickListener{
            (context as MainActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.main_frm, ClassFragment()).commitAllowingStateLoss()
        }


    }

    private fun initRecyclerView() {

        binding.lectureDetailReviewRv.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        lectureReviewRVAdapter = LectureReviewRVAdapter()
        binding.lectureDetailReviewRv.adapter = lectureReviewRVAdapter

        thread(start = true) {

            while(true){
                mainActivity.runOnUiThread{
                    review.clear()

                    review.apply {

                        review.add(Review("자바가 너무 쉬워졌어요 어떡하죠?", "by 데브리짱짱걸"))
                        review.add(Review("나쁘지 않습니다...저에겐 너무 쉽군요", "by 데브리짱짱걸"))
                        review.add(Review("기본을 다지기에 좋은 커리큘럼 입니다.", "by 데브리짱짱걸"))

                    }
                    lectureReviewRVAdapter.datas = review
                    lectureReviewRVAdapter.notifyDataSetChanged()
                    binding.lectureDetailReviewRv.startLayoutAnimation()
                }
                Thread.sleep(5000)
            }
        }
    }


}