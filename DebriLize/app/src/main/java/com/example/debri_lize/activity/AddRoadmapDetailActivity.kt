package com.example.debri_lize.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.debri_lize.ClassLectureRVAdapter
import com.example.debri_lize.R
import com.example.debri_lize.adapter.start.RoadmapRVAdapter
import com.example.debri_lize.data.class_.Lecture
import com.example.debri_lize.data.curriculum.Curriculum
import com.example.debri_lize.databinding.ActivityAddCurriculumBinding
import com.example.debri_lize.databinding.ActivityAddRoadmapDetailBinding

class AddRoadmapDetailActivity : AppCompatActivity() {
    lateinit var binding : ActivityAddRoadmapDetailBinding

    lateinit var classLectureRVAdapter: ClassLectureRVAdapter

    val datas = ArrayList<Lecture>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddRoadmapDetailBinding.inflate(layoutInflater) //binding 초기화
        setContentView(binding.root)

        initRecyclerView()

    }

    private fun initRecyclerView(){
        binding.addRoadmapLectureRv.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        classLectureRVAdapter = ClassLectureRVAdapter()
        binding.addRoadmapLectureRv.adapter = classLectureRVAdapter

        datas.clear()

        //data : 전체
        datas.apply {

            datas.add(Lecture(1, "야호", 1, "야호", "야호", "야호",false))
            datas.add(Lecture(1, "야호", 1, "야호", "야호", "야호",false))
            datas.add(Lecture(1, "야호", 1, "야호", "야호", "야호",false))

            classLectureRVAdapter.datas = datas
            classLectureRVAdapter.notifyDataSetChanged()

            //click recyclerview item
            classLectureRVAdapter.setItemClickListener(object : ClassLectureRVAdapter.OnItemClickListener {
                override fun onClick(v: View, position: Int) {


                }
            })
        }
    }




}