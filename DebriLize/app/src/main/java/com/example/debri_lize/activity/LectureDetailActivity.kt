package com.example.debri_lize.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.annotation.UiThread
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.debri_lize.R
import com.example.debri_lize.adapter.class_.LectureReviewRVAdapter
import com.example.debri_lize.data.class_.Lecture
import com.example.debri_lize.data.class_.LectureReview
import com.example.debri_lize.data.class_.ShowLectureReview
import com.example.debri_lize.data.curriculum.Review
import com.example.debri_lize.databinding.ActivityLectureDetailBinding
import com.example.debri_lize.service.ClassService
import com.example.debri_lize.service.ReviewService
import com.example.debri_lize.utils.getUserName
import com.example.debri_lize.view.class_.*
import kotlin.concurrent.thread
import kotlin.properties.Delegates

class LectureDetailActivity : AppCompatActivity(), ShowLectureDetailView, CreateLectureLikeView,
    DeleteLectureLikeView, CreateLectureReviewView, ShowLectureReviewView {
    lateinit var binding : ActivityLectureDetailBinding

    //review
    lateinit var lectureReviewRVAdapter: LectureReviewRVAdapter
    val review = ArrayList<Review>()
    //api
    var reviewService = ReviewService()

    private var pageNum : Int = 1 //현재 페이지 번호
    var page : Int = 1      //현재 페이지가 속한 곳 pageNum이 1~5면 1, 6~10이면 2
    var totalPage : Int = 0

    private var lectureIdx by Delegates.notNull<Int>()
    private lateinit var lectureName : String

    val classService = ClassService()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLectureDetailBinding.inflate(layoutInflater) //binding 초기화
        setContentView(binding.root)

        //data 받아오기 (ClassFragment -> LectureDetailActivity)
        //data 받아오기 (HomeActive/Inactive Fragment -> LectureDetailActivity)
        val intent = intent //전달할 데이터를 받을 Intent
        lectureIdx = intent.getIntExtra("lectureIdx", 0)
        lectureName = intent.getStringExtra("lectureName").toString()
        Log.d("lectureIdx", lectureIdx.toString())

        //7.6.1 강의 리뷰 조회 api
        reviewService.setShowLectureReviewView(this)
        reviewService.showLectureReview(pageNum, lectureIdx)

    }

    override fun onStart() {
        super.onStart()

//        liveAnimation()

        //add curriculum
        binding.lectureDetailCurriAddBtn.setOnClickListener{
            val intent = Intent(this, ChooseMyCurriculumActivity::class.java)
            intent.putExtra("lectureIdx", lectureIdx)
            intent.putExtra("lectureName", lectureName)
            startActivity(intent)
        }

        //finish
        binding.lectureDetailPreviousIv.setOnClickListener{
            finish()
        }

        //review recycler view
        binding.lectureDetailReviewRv.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        lectureReviewRVAdapter = LectureReviewRVAdapter()
        binding.lectureDetailReviewRv.adapter = lectureReviewRVAdapter

        //write review <- enter
        binding.lectureDetailWriteReviewEt.setOnKeyListener { v, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                createReview()
                true
            }
            false
        }

        //페이징 버튼 클릭
        pageButtonClick()

        //api
        classService.setShowLectureDetailView(this)
        classService.showLectureDetail(lectureIdx)

        classService.setCreateLectureLikeView(this)
        classService.setDeleteLectureLikeView(this)
    }


    //review
    //사용자가 입력한 값 가져오기
    private fun getReview() : LectureReview {
        val content : String = binding.lectureDetailWriteReviewEt.text.toString()

        return LectureReview(lectureIdx, getUserName(), content)
    }

    private fun createReview(){
        //리뷰가 입력되지 않은 경우
        if(binding.lectureDetailWriteReviewEt.text.toString().isEmpty()){
            Toast.makeText(this, "한 줄 평을 입력해주세요", Toast.LENGTH_SHORT).show()
            return
        }

        //7.6 강의 리뷰 작성 api
        reviewService.setCreateLectureReviewView(this)
        reviewService.createLectureReview(getReview())

    }

    override fun onShowLectureDetailSuccess(code: Int, result: Lecture) {
        when(code){
            200->{
                var language = binding.lectureDetailTagLanguageTv
                Log.d("lecture",result.toString())

                //screen
                language.text = result.language
                binding.lectureDetailTitleTv.text = result.lectureName
                binding.lectureDetailContentTv.text = result.lectureDesc
                binding.lectureDetailChapternumTv.text = "총 " + result.chapterNum.toString() + "챕터"
                binding.lectureDetailMediaTagTv.text = "#" + result.media
                binding.lectureDetailPriceTagTv.text = "#" + result.price
                binding.itemClassUsedCountTv.text = result.usedCount.toString()
                binding.lectureDetailPublisherTv.text = result.publisher
                binding.lectureDetailLikenumTv.text = result.likeNumber.toString()



                    when(language.text){
                        "Front" -> language.setBackgroundResource(R.drawable.border_round_transparent_front_10)
                        "Back" -> language.setBackgroundResource(R.drawable.border_round_transparent_back_10)
                        "C 언어" -> language.setBackgroundResource(R.drawable.border_round_transparent_c_10)
                        "Python" -> language.setBackgroundResource(R.drawable.border_round_transparent_python_10)
                    }

                    if(result.userLike)  {
                        binding.lectureDetailLikeIv.setImageResource(R.drawable.ic_like_on)
                        binding.lectureDetailSmallLikeIv.setImageResource(R.drawable.ic_like_on)
                        binding.lectureDetailLikeLayout.setBackgroundResource(R.drawable.border_round_debri_darkmode_10)
                    }else{
                        binding.lectureDetailLikeIv.setImageResource(R.drawable.ic_like_off)
                        binding.lectureDetailSmallLikeIv.setImageResource(R.drawable.ic_like_off)
                        binding.lectureDetailLikeLayout.setBackgroundResource(R.drawable.border_round_white_transparent_10)
                    }

                    if(result.userScrap)   binding.lectureDetailFavIv.setImageResource(R.drawable.ic_favorite_on)
                    else    binding.lectureDetailFavIv.setImageResource(R.drawable.ic_favorite_off)

                    binding.lectureDetailLikeLayout.setOnClickListener {
                        Log.d("lecturelike",result.userLike.toString())
                        if(!result.userLike){
                            //api - create lecture like
                            classService.createLectureLike(result.lectureIdx)
                        }else{
                            //api - delete lecture like
                            classService.deleteLectureLike(result.lectureIdx)
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

    //7.6 강의 리뷰 작성 api
    override fun onCreateLectureReviewSuccess(code: Int, result : LectureReview) {
        when(code){
            200->{
                Toast.makeText(this, "lectureReview ok", Toast.LENGTH_SHORT).show()
                binding.lectureDetailWriteReviewEt.text.clear()

                //lectureReviewRVAdapter.datas.add(Review(result.lectureIdx, result.authorName, result.content))
                //lectureReviewRVAdapter.notifyDataSetChanged()

                reviewService.setShowLectureReviewView(this)
                reviewService.showLectureReview(pageNum, lectureIdx)
            }
        }
    }

    override fun onCreateLectureReviewFailure(code: Int) {

    }

    //live animation
    @UiThread
    private fun liveAnimation(){
        val startAnim : Animation = AnimationUtils.loadAnimation(this, R.anim.anim_appear)
        val endAnim : Animation = AnimationUtils.loadAnimation(this, R.anim.anim_disappear)
        binding.lectureDetailLiveIv.startAnimation(startAnim)

        startAnim.setAnimationListener(object : Animation.AnimationListener{
            override fun onAnimationStart(p0: Animation?) {

            }

            override fun onAnimationEnd(p0: Animation?) {
                binding.lectureDetailLiveIv.startAnimation(endAnim)
            }

            override fun onAnimationRepeat(p0: Animation?) {

            }
        })

        endAnim.setAnimationListener(object : Animation.AnimationListener{
            override fun onAnimationStart(p0: Animation?) {

            }

            override fun onAnimationEnd(p0: Animation?) {
                binding.lectureDetailLiveIv.startAnimation(startAnim)
            }

            override fun onAnimationRepeat(p0: Animation?) {

            }
        })
    }

    //7.6.1 강의 리뷰 조회 api
    override fun onShowLectureReviewSuccess(code: Int, result: ShowLectureReview) {
        when(code){
            200->{
                binding.lectureDetailReviewRv.layoutManager =
                    LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
                lectureReviewRVAdapter = LectureReviewRVAdapter()
                binding.lectureDetailReviewRv.adapter = lectureReviewRVAdapter

                review.clear()

                totalPage = if(result.reviewCount %12==0) result.reviewCount /12 else result.reviewCount /12 + 1
                page = if(pageNum%5==0) pageNum/5 else pageNum/5+1

                Log.d("reviewCount",result.reviewCount.toString())

                pageButton()
                if(result.reviewCount == 0){
                    binding.lectureDetailReviewPagenum1Tv.visibility = View.INVISIBLE
                    binding.lectureDetailReviewPagenum2Tv.visibility = View.INVISIBLE
                    binding.lectureDetailReviewPagenum3Tv.visibility = View.INVISIBLE
                    binding.lectureDetailReviewPagenum4Tv.visibility = View.INVISIBLE
                    binding.lectureDetailReviewPagenum5Tv.visibility = View.INVISIBLE
                    binding.lectureDetailReviewPageNextIv.visibility = View.INVISIBLE
                }

                review.apply {
                    for (i in result.reviewList) {
                        review.add(Review(i.lectureIdx, i.authorName, i.content))
                    }

                    lectureReviewRVAdapter.datas = review
                    lectureReviewRVAdapter.notifyDataSetChanged()
                }
//                if(result.isNotEmpty()){
//                    var j = 0
//                    thread(start = true) {
//                        while(true){
//                            runOnUiThread{
//                                review.clear()
//                                review.apply {
//                                    for (cnt in 1..3){
//                                        review.add(Review(result[j].lectureIdx, result[j].authorName, result[j].content))
//
//                                        j++
//                                        if(j>=result.size){
//                                            j = 0
//                                        }
//                                    }
//
//                                    lectureReviewRVAdapter.datas = review
//                                    lectureReviewRVAdapter.notifyDataSetChanged()
//                                }
//                                binding.lectureDetailReviewRv.startLayoutAnimation()
//                            }
//                            Thread.sleep(5000)
//                        }
//                    }
//                }
            }
        }
    }

    override fun onShowLectureReviewFailure(code: Int) {

    }


    private fun pageButtonClick() {
        binding.lectureDetailReviewPagenum1Tv.setOnClickListener {
            pageNum = (page - 1) * 5 + 1
            pageButton()
            reviewService.showLectureReview(pageNum, lectureIdx)
        }
        binding.lectureDetailReviewPagenum2Tv.setOnClickListener {
            pageNum = (page - 1) * 5 + 2
            pageButton()
            reviewService.showLectureReview(pageNum, lectureIdx)
        }
        binding.lectureDetailReviewPagenum3Tv.setOnClickListener {
            pageNum = (page - 1) * 5 + 3
            pageButton()
            reviewService.showLectureReview(pageNum, lectureIdx)
        }
        binding.lectureDetailReviewPagenum4Tv.setOnClickListener {
            pageNum = (page - 1) * 5 + 4
            pageButton()
            reviewService.showLectureReview(pageNum, lectureIdx)
        }
        binding.lectureDetailReviewPagenum5Tv.setOnClickListener {
            pageNum = (page - 1) * 5 + 5
            pageButton()
            reviewService.showLectureReview(pageNum, lectureIdx)
        }
        binding.lectureDetailReviewPagePreviousIv.setOnClickListener {
            pageNum = (page-2)*5 + 1
            pageButton()
            reviewService.showLectureReview(pageNum, lectureIdx)
        }
        binding.lectureDetailReviewPageNextIv.setOnClickListener {
            pageNum = page * 5 + 1
            pageButton()
            reviewService.showLectureReview(pageNum, lectureIdx)
        }

    }



    private fun pageButton(){
        //페이지 번호
        binding.lectureDetailReviewPagenum1Tv.text = ((page-1)*5+1).toString()
        binding.lectureDetailReviewPagenum2Tv.text = ((page-1)*5+2).toString()
        binding.lectureDetailReviewPagenum3Tv.text = ((page-1)*5+3).toString()
        binding.lectureDetailReviewPagenum4Tv.text = ((page-1)*5+4).toString()
        binding.lectureDetailReviewPagenum5Tv.text = ((page-1)*5+5).toString()

        //화살표 visibility 설정
        if(page == 1)   binding.lectureDetailReviewPagePreviousIv.visibility = View.INVISIBLE
        else    binding.lectureDetailReviewPagePreviousIv.visibility = View.VISIBLE
        if(totalPage>=(page-1)*5+1 && totalPage<=(page-1)*5+5)
            binding.lectureDetailReviewPageNextIv.visibility = View.INVISIBLE
        else    binding.lectureDetailReviewPageNextIv.visibility = View.VISIBLE

        //숫자 버튼 visibility 설정
        if(totalPage-page*5 == -1){
            binding.lectureDetailReviewPagenum2Tv.visibility = View.VISIBLE
            binding.lectureDetailReviewPagenum3Tv.visibility = View.VISIBLE
            binding.lectureDetailReviewPagenum4Tv.visibility = View.VISIBLE
            binding.lectureDetailReviewPagenum5Tv.visibility = View.INVISIBLE
        }else if(totalPage-page*5 == -2){
            binding.lectureDetailReviewPagenum2Tv.visibility = View.VISIBLE
            binding.lectureDetailReviewPagenum3Tv.visibility = View.VISIBLE
            binding.lectureDetailReviewPagenum4Tv.visibility = View.INVISIBLE
            binding.lectureDetailReviewPagenum5Tv.visibility = View.INVISIBLE
        } else if(totalPage-page*5 == -3){
            binding.lectureDetailReviewPagenum2Tv.visibility = View.VISIBLE
            binding.lectureDetailReviewPagenum3Tv.visibility = View.INVISIBLE
            binding.lectureDetailReviewPagenum4Tv.visibility = View.INVISIBLE
            binding.lectureDetailReviewPagenum5Tv.visibility = View.INVISIBLE
        }
        else if(totalPage-page*5 == -4){
            binding.lectureDetailReviewPagenum2Tv.visibility = View.INVISIBLE
            binding.lectureDetailReviewPagenum3Tv.visibility = View.INVISIBLE
            binding.lectureDetailReviewPagenum4Tv.visibility = View.INVISIBLE
            binding.lectureDetailReviewPagenum5Tv.visibility = View.INVISIBLE
        }else{
            binding.lectureDetailReviewPagenum2Tv.visibility = View.VISIBLE
            binding.lectureDetailReviewPagenum3Tv.visibility = View.VISIBLE
            binding.lectureDetailReviewPagenum4Tv.visibility = View.VISIBLE
            binding.lectureDetailReviewPagenum5Tv.visibility = View.VISIBLE
        }


        //background circle 설정
        if(pageNum%5 == 1) {
            binding.lectureDetailReviewPagenum1Tv.setBackgroundResource(R.drawable.circle_debri_debri_8)
            binding.lectureDetailReviewPagenum2Tv.setBackgroundResource(R.color.transparent)
            binding.lectureDetailReviewPagenum3Tv.setBackgroundResource(R.color.transparent)
            binding.lectureDetailReviewPagenum4Tv.setBackgroundResource(R.color.transparent)
            binding.lectureDetailReviewPagenum5Tv.setBackgroundResource(R.color.transparent)
        }else if(pageNum%5 == 2){
            binding.lectureDetailReviewPagenum1Tv.setBackgroundResource(R.color.transparent)
            binding.lectureDetailReviewPagenum2Tv.setBackgroundResource(R.drawable.circle_debri_debri_8)
            binding.lectureDetailReviewPagenum3Tv.setBackgroundResource(R.color.transparent)
            binding.lectureDetailReviewPagenum4Tv.setBackgroundResource(R.color.transparent)
            binding.lectureDetailReviewPagenum5Tv.setBackgroundResource(R.color.transparent)
        }else if(pageNum%5 == 3){
            binding.lectureDetailReviewPagenum1Tv.setBackgroundResource(R.color.transparent)
            binding.lectureDetailReviewPagenum2Tv.setBackgroundResource(R.color.transparent)
            binding.lectureDetailReviewPagenum3Tv.setBackgroundResource(R.drawable.circle_debri_debri_8)
            binding.lectureDetailReviewPagenum4Tv.setBackgroundResource(R.color.transparent)
            binding.lectureDetailReviewPagenum5Tv.setBackgroundResource(R.color.transparent)
        }else if(pageNum%5 == 4){
            binding.lectureDetailReviewPagenum1Tv.setBackgroundResource(R.color.transparent)
            binding.lectureDetailReviewPagenum2Tv.setBackgroundResource(R.color.transparent)
            binding.lectureDetailReviewPagenum3Tv.setBackgroundResource(R.color.transparent)
            binding.lectureDetailReviewPagenum4Tv.setBackgroundResource(R.drawable.circle_debri_debri_8)
            binding.lectureDetailReviewPagenum5Tv.setBackgroundResource(R.color.transparent)
        }else if(pageNum%5 == 0){
            binding.lectureDetailReviewPagenum1Tv.setBackgroundResource(R.color.transparent)
            binding.lectureDetailReviewPagenum2Tv.setBackgroundResource(R.color.transparent)
            binding.lectureDetailReviewPagenum3Tv.setBackgroundResource(R.color.transparent)
            binding.lectureDetailReviewPagenum4Tv.setBackgroundResource(R.color.transparent)
            binding.lectureDetailReviewPagenum5Tv.setBackgroundResource(R.drawable.circle_debri_debri_8)
        }

    }
}