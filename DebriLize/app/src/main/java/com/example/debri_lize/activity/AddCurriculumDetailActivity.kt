package com.example.debri_lize.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.annotation.UiThread
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.debri_lize.R
import com.example.debri_lize.activity.auth.ProfileActivity
import com.example.debri_lize.adapter.class_.ClassLectureRVAdapter
import com.example.debri_lize.adapter.start.ReviewRVAdapter
import com.example.debri_lize.data.class_.Lecture
import com.example.debri_lize.data.curriculum.CurriculumDetail
import com.example.debri_lize.data.curriculum.Review
import com.example.debri_lize.databinding.ActivityAddCurriculumDetailBinding
import com.example.debri_lize.service.CurriculumService
import com.example.debri_lize.service.ReviewService
import com.example.debri_lize.utils.getUserName
import com.example.debri_lize.view.curriculum.*
import kotlin.concurrent.thread
import kotlin.properties.Delegates

class AddCurriculumDetailActivity : AppCompatActivity(), CreateReviewView, ShowReviewView, ShowCurriculumDetailView,
    CreateCurriLikeView, CancelCurriLikeView {
    lateinit var binding : ActivityAddCurriculumDetailBinding

    lateinit var classLectureRVAdapter: ClassLectureRVAdapter
    lateinit var reviewRVAdapter: ReviewRVAdapter

    var curriculumIdx by Delegates.notNull<Int>()
    val lecture = ArrayList<Lecture>()

    //review
    val review = ArrayList<Review>()

    //api
    var reviewService = ReviewService()
    var curriculumService = CurriculumService()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddCurriculumDetailBinding.inflate(layoutInflater) //binding 초기화
        setContentView(binding.root)

        Glide.with(this).load(R.raw.curriculum).into(binding.addCurriculumDetailStatusIv)

        //click userImg
        binding.addCurriculumDetailDebriUserIv.setOnClickListener{
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
        }

        //backbtn
        binding.addCurriculumDetailNextIv.setOnClickListener{
            finish()
        }


        //review recycler view
        binding.addCurriculumDetailReviewRv.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        reviewRVAdapter = ReviewRVAdapter()
        binding.addCurriculumDetailReviewRv.adapter = reviewRVAdapter

        //data : CurriculumFragment -> AddCurriculumDetailActivity
        val intent = intent //전달할 데이터를 받을 Intent
        curriculumIdx = intent.getIntExtra("curriculumIdx", 0)

    }

    override fun onStart() {
        super.onStart()

        liveAnimation()

        //write review <- enter
        binding.addCurriculumDetailWriteReviewEt.setOnKeyListener { v, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                createReview()
                true
            }
            false
        }

        //lecture recycler view
        binding.addCurriculumDetailLectureRv.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        classLectureRVAdapter = ClassLectureRVAdapter()
        binding.addCurriculumDetailLectureRv.adapter = classLectureRVAdapter

        //AddCurriculumActivity -> AddCurriculumDetailActivity
        val intent = intent //전달할 데이터를 받을 Intent
        curriculumIdx = intent.getIntExtra("curriculumIdx", 0)

        //8.3 커리큘럼 상세 조회 api
        curriculumService.setShowCurriculumDetailView(this)
        curriculumService.showCurriculumDetail(curriculumIdx)

        //8.8(9) 커리큘럼 좋아요 생성(취소)
        curriculumService.setCreateCurriLikeView(this)
        curriculumService.setCancelCurriLikeView(this)

        //8.12.1 커리큘럼 리뷰 조회 api
        reviewService.setShowReviewView(this)
        reviewService.showReview(curriculumIdx)
    }

    //live animation
    @UiThread
    private fun liveAnimation(){
        val startAnim : Animation = AnimationUtils.loadAnimation(this, R.anim.anim_appear)
        val endAnim : Animation = AnimationUtils.loadAnimation(this, R.anim.anim_disappear)
        binding.addCurriculumDetailLiveIv.startAnimation(startAnim)

        startAnim.setAnimationListener(object : Animation.AnimationListener{
            override fun onAnimationStart(p0: Animation?) {

            }

            override fun onAnimationEnd(p0: Animation?) {
                binding.addCurriculumDetailLiveIv.startAnimation(endAnim)
            }

            override fun onAnimationRepeat(p0: Animation?) {

            }
        })

        endAnim.setAnimationListener(object : Animation.AnimationListener{
            override fun onAnimationStart(p0: Animation?) {

            }

            override fun onAnimationEnd(p0: Animation?) {
                binding.addCurriculumDetailLiveIv.startAnimation(startAnim)
            }

            override fun onAnimationRepeat(p0: Animation?) {

            }
        })
    }

    //8.3 커리큘럼 상세 조회 api
    override fun onShowCurriculumDetailSuccess(code: Int, result: CurriculumDetail) {
        when(code){
            200->{
                //screen
                binding.addCurriculumDetailNameTv.text = result.curriculumName
                binding.addCurriculumDetailDetailTv.text = result.curriDesc
                binding.addCurriculumDetailAuthorTv.text = result.curriculumAuthor
                //binding.addCurriculumDetailDdayTv2.text = result.

                //커리큘럼 좋아요 상태 api 시트에 추가되면 수정
                if(true)  {
                    binding.addCurriculumLikeIv.setImageResource(R.drawable.ic_like_on)
                    binding.addCurriculumLikeLayout.setBackgroundResource(R.drawable.border_round_debri_darkmode_10)
                }else{
                    binding.addCurriculumLikeIv.setImageResource(R.drawable.ic_like_off)
                    binding.addCurriculumLikeLayout.setBackgroundResource(R.drawable.border_round_white_transparent_10)
                }

                //lecture recycler view
                lecture.clear()

                //data : 전체
                lecture.apply {

                    for(i in result.lectureListResList){
                        if(i.scrapStatus=="ACTIVE"){ //likeCnt추가
                            i.lectureIdx?.let { i.lectureName?.let { it1 ->
                                i.language?.let { it2 ->
                                    Lecture(it,
                                        it1,i.chNum,
                                        it2,i.type,i.price,true,0,i.usedCnt,0,true, "야호", "야호", "야호")
                                }
                            } }
                                ?.let { lecture.add(it) }
                        }else{ //likeCnt추가
                            i.lectureIdx?.let { i.lectureName?.let { it1 ->
                                i.language?.let { it2 ->
                                    Lecture(it,
                                        it1,i.chNum,
                                        it2,i.type,i.price,false,0,i.usedCnt,0,true, "야호", "야호", "야호")
                                }
                            } }
                                ?.let { lecture.add(it) }
                        }

                    }


                    classLectureRVAdapter.datas = lecture
                    classLectureRVAdapter.notifyDataSetChanged()

                    //click recyclerview item
                    classLectureRVAdapter.setItemClickListener(object :
                        ClassLectureRVAdapter.OnItemClickListener {
                        override fun onClick(v: View, position: Int) {


                        }
                    })




                    //click like btn
                    binding.addCurriculumLikeLayout.setOnClickListener {
                        //커리 좋아요 상태 api시트에 추가되면 수정
                        if(false){
                            //api - delete curri like
                            curriculumService.cancelCurriLike(curriculumIdx)
                        }else{
                            //api - create curri like
                            curriculumService.createCurriLike(curriculumIdx)
                        }
                    }
                }
            }
        }
    }

    override fun onShowCurriculumDetailFailure(code: Int) {

    }

    //review
    //사용자가 입력한 값 가져오기
    private fun getReview() : Review {
        val content : String = binding.addCurriculumDetailWriteReviewEt.text.toString()

        return Review(curriculumIdx, getUserName(), content)
    }

    private fun createReview(){
        //리뷰가 입력되지 않은 경우
        if(binding.addCurriculumDetailWriteReviewEt.text.toString().isEmpty()){
            Toast.makeText(this, "한 줄 평을 입력해주세요", Toast.LENGTH_SHORT).show()
            return
        }

        ////8.12 커리큘럼 리뷰 작성 api
        reviewService.setCreateReviewView(this)
        reviewService.createReview(getReview())

    }

    //8.12 커리큘럼 리뷰 작성 api
    override fun onCreateReviewSuccess(code: Int) {
        when(code){
            200->{
                Toast.makeText(this, "review ok", Toast.LENGTH_SHORT).show()
                binding.addCurriculumDetailWriteReviewEt.text.clear()
                reviewService.setShowReviewView(this)
                reviewService.showReview(59)
            }
        }
    }

    override fun onCreateReviewFailure(code: Int) {

    }

    override fun onShowReviewSuccess(code: Int, result: List<Review>) {
        when(code){
            200->{
                if(result.isNotEmpty()){
                    var j = 0
                    thread(start = true) {
                        while(true){
                            runOnUiThread{
                                review.clear()
                                review.apply {
                                    for (cnt in 1..3){
                                        review.add(Review(result[j].curriculumIdx, result[j].authorName, result[j].content))

                                        j++
                                        if(j>=result.size){
                                            j = 0
                                        }
                                    }
                                    reviewRVAdapter.datas = review
                                    reviewRVAdapter.notifyDataSetChanged()
                                }
                                binding.addCurriculumDetailReviewRv.startLayoutAnimation()
                            }
                            Thread.sleep(5000)
                        }
                    }
                }
            }
        }
    }

    override fun onShowReviewFailure(code: Int) {

    }

    override fun onCreateCurriLikeSuccess(code: Int) {
        when(code){
            200->{
                curriculumService.showCurriculumDetail(curriculumIdx)
            }
        }
    }

    override fun onCreateCurriLikeFailure(code: Int) {
        Log.d("createCurriLikeFail","$code")
    }

    override fun onDeleteCurriLikeSuccess(code: Int) {
        when(code){
            200->{
                curriculumService.showCurriculumDetail(curriculumIdx)
            }
        }
    }

    override fun onDeleteCurriLikeFailure(code: Int) {
        Log.d("deleteCurriLikeFail","$code")
    }


}