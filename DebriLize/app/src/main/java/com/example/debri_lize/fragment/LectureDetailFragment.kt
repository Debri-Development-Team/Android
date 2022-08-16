package com.example.debri_lize.fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
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
import com.example.debri_lize.service.ClassService
import com.example.debri_lize.view.class_.CreateLectureLikeView
import com.example.debri_lize.view.class_.DeleteLectureLikeView
import com.example.debri_lize.view.class_.ShowLectureDetailView
import java.io.Serializable
import kotlin.concurrent.thread
import kotlin.properties.Delegates


class LectureDetailFragment : Fragment(), ShowLectureDetailView, CreateLectureLikeView,
    DeleteLectureLikeView {

    lateinit var binding : FragmentLectureDetailBinding
    lateinit var mainActivity: MainActivity

    lateinit var lectureReviewRVAdapter: LectureReviewRVAdapter
    val review = ArrayList<Review>()

    private lateinit var lectureFav : Lecture

    var lectureIdx : Int = 0

    val classService = ClassService()


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
        lectureFav = (arguments?.getSerializable("lectureFav") as Lecture?)!!



        //fragment to fragment
        binding.lectureDetailPreviousIv.setOnClickListener{
            (context as MainActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.main_frm, ClassFragment()).commitAllowingStateLoss()
        }

        //api
        classService.setShowLectureDetailView(this)
        classService.showLectureDetail(lectureIdx)

        classService.setCreateLectureLikeView(this)
        classService.setDeleteLectureLikeView(this)

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

//                        review.add(Review("자바가 너무 쉬워졌어요 어떡하죠?", "by 데브리짱짱걸"))
//                        review.add(Review("나쁘지 않습니다...저에겐 너무 쉽군요", "by 데브리짱짱걸"))
//                        review.add(Review("기본을 다지기에 좋은 커리큘럼 입니다.", "by 데브리짱짱걸"))

                    }
                    lectureReviewRVAdapter.datas = review
                    lectureReviewRVAdapter.notifyDataSetChanged()
                    binding.lectureDetailReviewRv.startLayoutAnimation()
                }
                Thread.sleep(5000)
            }
        }
    }

    override fun onShowLectureDetailSuccess(code: Int, result: List<Lecture>) {
        when(code){
            200->{
                var language = binding.lectureDetailTagLanguageTv
                var lectureName = binding.lectureDetailTitleTv
                var content = binding.lectureDetailContentTv
                var chapterNum = binding.lectureDetailChapternumTv
                var media = binding.lectureDetailMediaTagTv
                var price = binding.lectureDetailPriceTagTv
                var usedCount = binding.lectureDetailUserusedTv
                var likeNum = binding.lectureDetailUserLikeNumTv    //현재 유저가 이 강의를 좋아요 누름
                var publisher = binding.lectureDetailPublisherTv


                //받아온 데이타로 변경
                if(lectureFav != null) {
                    //강의 이름 변경
                    language.text = lectureFav.language
                    lectureName.text = lectureFav.lectureName
                    content.text = lectureFav.lectureDesc
                    chapterNum.text = lectureFav.chapterNum.toString()
                    media.text = lectureFav.media
                    price.text = lectureFav.price
                    usedCount.text = lectureFav.usedCount.toString()
                    publisher.text = lectureFav.publisher
                    if(lectureFav.likeNumber > 99)  likeNum.text = "99+"
                    else likeNum.text = lectureFav.likeNumber.toString()
                    binding.lectureDetailLikenumTv.text = lectureFav.likeNumber.toString()
                    lectureIdx = lectureFav.lectureIdx

                    when(language.text){
                        "Front" -> language.setBackgroundResource(R.drawable.border_round_transparent_front_10)
                        "Back" -> language.setBackgroundResource(R.drawable.border_round_transparent_back_10)
                        "C 언어" -> language.setBackgroundResource(R.drawable.border_round_transparent_c_10)
                        "Python" -> language.setBackgroundResource(R.drawable.border_round_transparent_python_10)
                    }

                    if(lectureFav.userLike)  {
                        binding.lectureDetailLikeIv.setImageResource(R.drawable.ic_like_on)
                        binding.lectureDetailLikeLayout.setBackgroundResource(R.drawable.border_round_debri_darkmode_10)
                        binding.lectureDetailLikenumTv.setTextColor(R.color.white)
                    }else{
                        binding.lectureDetailLikeIv.setImageResource(R.drawable.ic_like_off)
                        binding.lectureDetailLikeLayout.setBackgroundResource(R.drawable.border_round_white_transparent_10)
                        binding.lectureDetailLikenumTv.setTextColor(R.color.darkmode_background)
                    }

                    if(lectureFav.userScrap)   binding.lectureDetailFavIv.setImageResource(R.drawable.ic_favorite_on)
                    else    binding.lectureDetailFavIv.setImageResource(R.drawable.ic_favorite_off)

                    binding.lectureDetailLikeLayout.setOnClickListener {
                        if(lectureFav.userLike){
                            //api - create lecture like
                            classService.createLectureLike(lectureIdx)
                        }else{
                            //api - delete lecture like
                            classService.deleteLectureLike(lectureIdx)
                        }
                    }
                }






            }
        }
    }

    override fun onShowLectureDetailFailure(code: Int) {
        Log.d("showlecturedetailfail", code.toString())
    }

    override fun onCreateLectureLikeSuccess(code: Int) {
        when(code){
            200->{
                classService.showLectureDetail(lectureIdx)
            }
        }
    }

    override fun onCreateLectureLikeFailure(code: Int) {
        Log.d("createlecturelikefail","$code")
    }

    override fun onDeleteLectureLikeSuccess(code: Int) {
        when(code){
            200->{
                classService.showLectureDetail(lectureIdx)
            }
        }
    }

    override fun onDeleteLectureLikeFailure(code: Int) {
        Log.d("deletelecturelikefail","$code")
    }


}