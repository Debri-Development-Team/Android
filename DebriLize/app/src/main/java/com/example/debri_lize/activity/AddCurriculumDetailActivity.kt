package com.example.debri_lize.activity

import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.debri_lize.adapter.class_.ClassLectureRVAdapter
import com.example.debri_lize.adapter.start.ReviewRVAdapter
import com.example.debri_lize.data.class_.Lecture
import com.example.debri_lize.data.curriculum.Review
import com.example.debri_lize.databinding.ActivityAddCurriculumDetailBinding
import com.example.debri_lize.service.ReviewService
import com.example.debri_lize.utils.getUserName
import com.example.debri_lize.view.curriculum.CreateReviewView
import com.example.debri_lize.view.curriculum.ShowReviewView
import kotlin.concurrent.thread

class AddCurriculumDetailActivity : AppCompatActivity(), CreateReviewView, ShowReviewView {
    lateinit var binding : ActivityAddCurriculumDetailBinding

    lateinit var classLectureRVAdapter: ClassLectureRVAdapter
    lateinit var reviewRVAdapter: ReviewRVAdapter

    val datas = ArrayList<Lecture>()
    val review = ArrayList<Review>()

    //api
    var reviewService = ReviewService()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddCurriculumDetailBinding.inflate(layoutInflater) //binding 초기화
        setContentView(binding.root)

        initRecyclerView()

        //review recycler view
        binding.addCurriculumDetailReviewRv.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        reviewRVAdapter = ReviewRVAdapter()
        binding.addCurriculumDetailReviewRv.adapter = reviewRVAdapter

        //8.12.1 커리큘럼 리뷰 조회 api
        reviewService.setShowReviewView(this)
        reviewService.showReview(59)

    }

    override fun onStart() {
        super.onStart()

        //write comment <- enter
        binding.addCurriculumDetailWriteReviewEt.setOnKeyListener { v, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                createReview()
                true
            }
            false
        }
    }

    private fun initRecyclerView(){
        binding.addCurriculumDetailLectureRv.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        classLectureRVAdapter = ClassLectureRVAdapter()
        binding.addCurriculumDetailLectureRv.adapter = classLectureRVAdapter

        datas.clear()

        //data : 전체
        datas.apply {

//            datas.add(Lecture(1, "야호", 1, "야호", "야호", "야호",false))
//            datas.add(Lecture(1, "야호", 1, "야호", "야호", "야호",false))
//            datas.add(Lecture(1, "야호", 1, "야호", "야호", "야호",false))

            classLectureRVAdapter.datas = datas
            classLectureRVAdapter.notifyDataSetChanged()

            //click recyclerview item
            classLectureRVAdapter.setItemClickListener(object :
                ClassLectureRVAdapter.OnItemClickListener {
                override fun onClick(v: View, position: Int) {


                }
            })
        }


    }

    //comment
    //사용자가 입력한 값 가져오기
    private fun getReview() : Review {
        val content : String = binding.addCurriculumDetailWriteReviewEt.text.toString()

        return Review(59, getUserName(), content)
    }

    private fun createReview(){
        //리뷰가 입력되지 않은 경우
        if(binding.addCurriculumDetailWriteReviewEt.text.toString().isEmpty()){
            Toast.makeText(this, "한 줄 평을 입력해주세요", Toast.LENGTH_SHORT).show()
            return
        }

        //8.12.1 커리큘럼 리뷰 조회 api
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
                thread(start = true) {

                    while(true){
                        runOnUiThread{
                            review.clear()
                            review.apply {
                                for (i in result){
                                    review.add(Review(i.curriculumIdx, i.authorName, i.content))
                                }
                            }
                            reviewRVAdapter.datas = review
                            reviewRVAdapter.notifyDataSetChanged()
                            binding.addCurriculumDetailReviewRv.startLayoutAnimation()
                        }
                        Thread.sleep(5000)
                    }
                }
            }
        }
    }

    override fun onShowReviewFailure(code: Int) {

    }

}