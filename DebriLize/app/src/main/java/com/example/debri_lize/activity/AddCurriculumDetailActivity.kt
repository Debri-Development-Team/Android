package com.example.debri_lize.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.debri_lize.ClassLectureRVAdapter
import com.example.debri_lize.adapter.start.ReviewRVAdapter
import com.example.debri_lize.data.class_.Lecture
import com.example.debri_lize.data.curriculum.Review
import com.example.debri_lize.databinding.ActivityAddCurriculumDetailBinding

class AddCurriculumDetailActivity : AppCompatActivity() {
    lateinit var binding : ActivityAddCurriculumDetailBinding

    lateinit var classLectureRVAdapter: ClassLectureRVAdapter
    lateinit var reviewRVAdapter: ReviewRVAdapter

    val datas = ArrayList<Lecture>()
    val review = ArrayList<Review>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddCurriculumDetailBinding.inflate(layoutInflater) //binding 초기화
        setContentView(binding.root)

        initRecyclerView()

    }

    private fun initRecyclerView(){
        binding.addCurriculumDetailLectureRv.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        classLectureRVAdapter = ClassLectureRVAdapter()
        binding.addCurriculumDetailLectureRv.adapter = classLectureRVAdapter

        datas.clear()

        //data : 전체
        datas.apply {

            datas.add(Lecture(1, "야호", 1, "야호", "야호", "야호"))
            datas.add(Lecture(1, "야호", 1, "야호", "야호", "야호"))
            datas.add(Lecture(1, "야호", 1, "야호", "야호", "야호"))

            classLectureRVAdapter.datas = datas
            classLectureRVAdapter.notifyDataSetChanged()

            //click recyclerview item
            classLectureRVAdapter.setItemClickListener(object :
                ClassLectureRVAdapter.OnItemClickListener {
                override fun onClick(v: View, position: Int) {


                }
            })
        }

        binding.addCurriculumDetailReviewRv.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        reviewRVAdapter = ReviewRVAdapter()
        binding.addCurriculumDetailReviewRv.adapter = reviewRVAdapter

        review.clear()

        //data : 전체
        review.apply {

            review.add(Review("자바가 너무 쉬워졌어요 어떡하죠?", "by 데브리짱짱걸"))
            review.add(Review("나쁘지 않습니다...저에겐 너무 쉽군요", "by 데브리짱짱걸"))
            review.add(Review("기본을 다지기에 좋은 커리큘럼 입니다.", "by 데브리짱짱걸"))

            reviewRVAdapter.datas = review
            reviewRVAdapter.notifyDataSetChanged()

            //click recyclerview item
            reviewRVAdapter.setItemClickListener(object :
                ReviewRVAdapter.OnItemClickListener {
                override fun onClick(v: View, position: Int) {


                }
            })
        }
    }




}