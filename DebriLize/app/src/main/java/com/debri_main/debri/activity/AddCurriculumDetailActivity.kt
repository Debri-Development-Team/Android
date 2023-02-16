package com.debri_main.debri.activity

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.UiThread
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.debri_main.debri.R
import com.debri_main.debri.adapter.class_.ClassLectureRVAdapter
import com.debri_main.debri.adapter.start.ReviewRVAdapter
import com.debri_main.debri.data.class_.Lecture
import com.debri_main.debri.data.curriculum.CopyCurriculum
import com.debri_main.debri.data.curriculum.CurriculumDetail
import com.debri_main.debri.data.curriculum.Review
import com.debri_main.debri.data.curriculum.ShowReview
import com.debri_main.debri.databinding.ActivityAddCurriculumDetailBinding
import com.debri_main.debri.service.CurriculumService
import com.debri_main.debri.service.ReviewService
import com.debri_main.debri.utils.getUserName
import com.debri_main.debri.view.curriculum.*
import kotlin.properties.Delegates

class AddCurriculumDetailActivity : AppCompatActivity(), CreateReviewView, ShowReviewView, ShowCurriculumDetailView,
    CreateCurriLikeView, CancelCurriLikeView, CopyCurriculumView {
    lateinit var binding : ActivityAddCurriculumDetailBinding

    lateinit var classLectureRVAdapter: ClassLectureRVAdapter
    lateinit var reviewRVAdapter: ReviewRVAdapter

    var curriculumIdx by Delegates.notNull<Int>()
    val lecture = ArrayList<Lecture>()

    //review
    val review = ArrayList<Review>()
    private var pageNum : Int = 1 //현재 페이지 번호
    var page : Int = 1      //현재 페이지가 속한 곳 pageNum이 1~5면 1, 6~10이면 2
    var totalPage : Int = 0

    //api
    var reviewService = ReviewService()
    var curriculumService = CurriculumService()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddCurriculumDetailBinding.inflate(layoutInflater) //binding 초기화
        setContentView(binding.root)

        Glide.with(this).load(R.raw.curriculum).into(binding.addCurriculumDetailStatusIv)

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

        //리뷰 페이징 버튼
        pageButtonClick()

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
        reviewService.showReview(curriculumIdx, pageNum)
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
                binding.itemCurriculumAuthorTv1.text = result.curriculumAuthor
                binding.addCurriculumDetailCntTv.text = result.curriLikeCount.toString()
                binding.addCurriculumDetailDdayTv2.text = result.totalDday.toString()

                val language : TextView = binding.itemCurriculumLangTagTv
                //language tag
                language.text = result.language

                when(language.text){
                    "Front" -> language.setBackgroundResource(R.drawable.border_round_transparent_front_10)
                    "Back" -> language.setBackgroundResource(R.drawable.border_round_transparent_back_10)
                    "C 언어" -> language.setBackgroundResource(R.drawable.border_round_transparent_c_10)
                    "Python" -> language.setBackgroundResource(R.drawable.border_round_transparent_python_10)
                }

                Log.d("currilikestatus","${result}")

                //커리큘럼 좋아요 상태 api 시트에 추가되면 수정

                if(result.curriLikeStatus=="ACTIVE")  {
                    binding.addCurriculumLikeIv.setImageResource(R.drawable.ic_like_on)
                    binding.addCurriculumLikeLayout.setBackgroundResource(R.drawable.border_round_debri_darkmode_10)
                    binding.addCurriculumLikeTv.setTextColor(Color.parseColor("#66CC66"))
                }else{
                    binding.addCurriculumLikeIv.setImageResource(R.drawable.ic_like_dark)
                    binding.addCurriculumLikeLayout.setBackgroundResource(R.drawable.border_round_transparent_debri_10)
                    binding.addCurriculumLikeTv.setTextColor(Color.parseColor("#0A1123"))
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
                        Log.d("curri",result.toString())
                        //커리 좋아요 상태 api시트에 추가되면 수정
                        if(result.curriLikeStatus=="ACTIVE"){
                            //api - delete curri like
                            curriculumService.cancelCurriLike(result.scrapIdx)
                        }else{
                            //api - create curri like
                            curriculumService.createCurriLike(curriculumIdx)
                        }
                    }
                }

                //add btn - 8.13 코드추가
                binding.addCurriculumDetailAddBtn.setOnClickListener{
                    curriculumService.setCopyCurriculumView(this)
                    curriculumService.copyCurriculum(CopyCurriculum(curriculumIdx, result.curriculumAuthor))
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
                reviewService.showReview(curriculumIdx, pageNum)
            }
        }
    }

    override fun onCreateReviewFailure(code: Int) {

    }

    override fun onShowReviewSuccess(code: Int, result: ShowReview) {
        when(code){
            200->{
                binding.addCurriculumDetailReviewRv.layoutManager =
                    LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
                reviewRVAdapter = ReviewRVAdapter()
                binding.addCurriculumDetailReviewRv.adapter = reviewRVAdapter

                review.clear()


                totalPage = if(result.reviewCount!!%12==0) result.reviewCount!!/12 else result.reviewCount!!/12 + 1
                page = if(pageNum%5==0) pageNum/5 else pageNum/5+1

                Log.d("reviewCount",result.reviewCount.toString())
                pageButton()

                if(result.reviewCount == 0){
                    binding.addCurriDetailReviewPagenum1Tv.visibility = View.INVISIBLE
                    binding.addCurriDetailReviewPagenum2Tv.visibility = View.INVISIBLE
                    binding.addCurriDetailReviewPagenum3Tv.visibility = View.INVISIBLE
                    binding.addCurriDetailReviewPagenum4Tv.visibility = View.INVISIBLE
                    binding.addCurriDetailReviewPagenum5Tv.visibility = View.INVISIBLE
                    binding.addCurriDetailReviewPageNextIv.visibility = View.INVISIBLE
                }

                review.apply {
                    for (i in result.reviewList) {
                          review.add(Review(i.curriculumIdx, i.authorName, i.content))
                    }

                    reviewRVAdapter.datas = review
                    reviewRVAdapter.notifyDataSetChanged()
                }
//                if(result.isNotEmpty()){
//                    var j = 0
//                    thread(start = true) {
//                        while(true){
//                            runOnUiThread{
//                                review.clear()
//                                review.apply {
//                                    for (cnt in 1..3){
//                                        review.add(Review(result[j].curriculumIdx, result[j].authorName, result[j].content))
//
//                                        j++
//                                        if(j>=result.size){
//                                            j = 0
//                                        }
//                                    }
//                                    reviewRVAdapter.datas = review
//                                    reviewRVAdapter.notifyDataSetChanged()
//                                }
//                                binding.addCurriculumDetailReviewRv.startLayoutAnimation()
//                            }
//                            Thread.sleep(5000)
//                        }
//                    }
//                }
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

    override fun onCopyCurriculumSuccess(code: Int) {
        when(code){
            200->{

            }
        }
    }

    override fun onCopyCurriculumFailure(code: Int) {

    }

    private fun pageButtonClick() {
        binding.addCurriDetailReviewPagenum1Tv.setOnClickListener {
            pageNum = (page - 1) * 5 + 1
            pageButton()
            reviewService.showReview(curriculumIdx, pageNum)
        }
        binding.addCurriDetailReviewPagenum2Tv.setOnClickListener {
            pageNum = (page - 1) * 5 + 2
            pageButton()
            reviewService.showReview(curriculumIdx, pageNum)
        }
        binding.addCurriDetailReviewPagenum3Tv.setOnClickListener {
            pageNum = (page - 1) * 5 + 3
            pageButton()
            reviewService.showReview(curriculumIdx, pageNum)
        }
        binding.addCurriDetailReviewPagenum4Tv.setOnClickListener {
            pageNum = (page - 1) * 5 + 4
            pageButton()
            reviewService.showReview(curriculumIdx, pageNum)
        }
        binding.addCurriDetailReviewPagenum5Tv.setOnClickListener {
            pageNum = (page - 1) * 5 + 5
            pageButton()
            reviewService.showReview(curriculumIdx, pageNum)
        }
        binding.addCurriDetailReviewPagePreviousIv.setOnClickListener {
            pageNum = (page-2)*5 + 1
            pageButton()
            reviewService.showReview(curriculumIdx, pageNum)
        }
        binding.addCurriDetailReviewPageNextIv.setOnClickListener {
            pageNum = page * 5 + 1
            pageButton()
            reviewService.showReview(curriculumIdx, pageNum)
        }

    }



    private fun pageButton(){
        //페이지 번호
        binding.addCurriDetailReviewPagenum1Tv.text = ((page-1)*5+1).toString()
        binding.addCurriDetailReviewPagenum2Tv.text = ((page-1)*5+2).toString()
        binding.addCurriDetailReviewPagenum3Tv.text = ((page-1)*5+3).toString()
        binding.addCurriDetailReviewPagenum4Tv.text = ((page-1)*5+4).toString()
        binding.addCurriDetailReviewPagenum5Tv.text = ((page-1)*5+5).toString()

        //화살표 visibility 설정
        if(page == 1)   binding.addCurriDetailReviewPagePreviousIv.visibility = View.INVISIBLE
        else    binding.addCurriDetailReviewPagePreviousIv.visibility = View.VISIBLE
        if(totalPage>=(page-1)*5+1 && totalPage<=(page-1)*5+5)
            binding.addCurriDetailReviewPageNextIv.visibility = View.INVISIBLE
        else    binding.addCurriDetailReviewPageNextIv.visibility = View.VISIBLE

        //숫자 버튼 visibility 설정
        if(totalPage-page*5 == -1){
            binding.addCurriDetailReviewPagenum2Tv.visibility = View.VISIBLE
            binding.addCurriDetailReviewPagenum3Tv.visibility = View.VISIBLE
            binding.addCurriDetailReviewPagenum4Tv.visibility = View.VISIBLE
            binding.addCurriDetailReviewPagenum5Tv.visibility = View.INVISIBLE
        }else if(totalPage-page*5 == -2){
            binding.addCurriDetailReviewPagenum2Tv.visibility = View.VISIBLE
            binding.addCurriDetailReviewPagenum3Tv.visibility = View.VISIBLE
            binding.addCurriDetailReviewPagenum4Tv.visibility = View.INVISIBLE
            binding.addCurriDetailReviewPagenum5Tv.visibility = View.INVISIBLE
        } else if(totalPage-page*5 == -3){
            binding.addCurriDetailReviewPagenum2Tv.visibility = View.VISIBLE
            binding.addCurriDetailReviewPagenum3Tv.visibility = View.INVISIBLE
            binding.addCurriDetailReviewPagenum4Tv.visibility = View.INVISIBLE
            binding.addCurriDetailReviewPagenum5Tv.visibility = View.INVISIBLE
        }
        else if(totalPage-page*5 == -4){
            binding.addCurriDetailReviewPagenum2Tv.visibility = View.INVISIBLE
            binding.addCurriDetailReviewPagenum3Tv.visibility = View.INVISIBLE
            binding.addCurriDetailReviewPagenum4Tv.visibility = View.INVISIBLE
            binding.addCurriDetailReviewPagenum5Tv.visibility = View.INVISIBLE
        }else{
            binding.addCurriDetailReviewPagenum2Tv.visibility = View.VISIBLE
            binding.addCurriDetailReviewPagenum3Tv.visibility = View.VISIBLE
            binding.addCurriDetailReviewPagenum4Tv.visibility = View.VISIBLE
            binding.addCurriDetailReviewPagenum5Tv.visibility = View.VISIBLE
        }

        //background circle 설정
        if(pageNum%5 == 1) {
            binding.addCurriDetailReviewPagenum1Tv.setBackgroundResource(R.drawable.circle_debri_debri_8)
            binding.addCurriDetailReviewPagenum2Tv.setBackgroundResource(R.color.transparent)
            binding.addCurriDetailReviewPagenum3Tv.setBackgroundResource(R.color.transparent)
            binding.addCurriDetailReviewPagenum4Tv.setBackgroundResource(R.color.transparent)
            binding.addCurriDetailReviewPagenum5Tv.setBackgroundResource(R.color.transparent)
        }else if(pageNum%5 == 2){
            binding.addCurriDetailReviewPagenum1Tv.setBackgroundResource(R.color.transparent)
            binding.addCurriDetailReviewPagenum2Tv.setBackgroundResource(R.drawable.circle_debri_debri_8)
            binding.addCurriDetailReviewPagenum3Tv.setBackgroundResource(R.color.transparent)
            binding.addCurriDetailReviewPagenum4Tv.setBackgroundResource(R.color.transparent)
            binding.addCurriDetailReviewPagenum5Tv.setBackgroundResource(R.color.transparent)
        }else if(pageNum%5 == 3){
            binding.addCurriDetailReviewPagenum1Tv.setBackgroundResource(R.color.transparent)
            binding.addCurriDetailReviewPagenum2Tv.setBackgroundResource(R.color.transparent)
            binding.addCurriDetailReviewPagenum3Tv.setBackgroundResource(R.drawable.circle_debri_debri_8)
            binding.addCurriDetailReviewPagenum4Tv.setBackgroundResource(R.color.transparent)
            binding.addCurriDetailReviewPagenum5Tv.setBackgroundResource(R.color.transparent)
        }else if(pageNum%5 == 4){
            binding.addCurriDetailReviewPagenum1Tv.setBackgroundResource(R.color.transparent)
            binding.addCurriDetailReviewPagenum2Tv.setBackgroundResource(R.color.transparent)
            binding.addCurriDetailReviewPagenum3Tv.setBackgroundResource(R.color.transparent)
            binding.addCurriDetailReviewPagenum4Tv.setBackgroundResource(R.drawable.circle_debri_debri_8)
            binding.addCurriDetailReviewPagenum5Tv.setBackgroundResource(R.color.transparent)
        }else if(pageNum%5 == 0){
            binding.addCurriDetailReviewPagenum1Tv.setBackgroundResource(R.color.transparent)
            binding.addCurriDetailReviewPagenum2Tv.setBackgroundResource(R.color.transparent)
            binding.addCurriDetailReviewPagenum3Tv.setBackgroundResource(R.color.transparent)
            binding.addCurriDetailReviewPagenum4Tv.setBackgroundResource(R.color.transparent)
            binding.addCurriDetailReviewPagenum5Tv.setBackgroundResource(R.drawable.circle_debri_debri_8)
        }

    }
}