package com.example.debri_lize.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.debri_lize.adapter.start.RoadmapRVAdapter
import com.example.debri_lize.data.curriculum.Curriculum
import com.example.debri_lize.databinding.ActivityAddCurriculumChooseBinding

class AddCurriculumChooseActivity : AppCompatActivity() {
    lateinit var binding : ActivityAddCurriculumChooseBinding

    lateinit var roadmapRVAdapter : RoadmapRVAdapter
    lateinit var roadmapTopRVAdapter : RoadmapRVAdapter

    val datas = ArrayList<Curriculum>()
    val tops = ArrayList<Curriculum>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddCurriculumChooseBinding.inflate(layoutInflater) //binding 초기화
        setContentView(binding.root)

        initRecyclerView()
    }

    private fun initRecyclerView(){
        binding.addCurriculumChooseRoadmapAdminRv.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        roadmapRVAdapter = RoadmapRVAdapter()
        binding.addCurriculumChooseRoadmapAdminRv.adapter = roadmapRVAdapter

        datas.clear()

        //data : 전체
        datas.apply {

            datas.add(Curriculum("서버 로드맵"))
            datas.add(Curriculum("안드로이드 로드맵"))


            roadmapRVAdapter.datas = datas
            roadmapRVAdapter.notifyDataSetChanged()

            //click recyclerview item
            roadmapRVAdapter.setItemClickListener(object : RoadmapRVAdapter.OnItemClickListener {
                override fun onClick(v: View, position: Int) {
                    val intent = Intent(this@AddCurriculumChooseActivity, AddRoadmapDetailActivity::class.java)
                    startActivity(intent)
                }
            })
        }

        binding.addCurriculumChooseTopRv.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        roadmapTopRVAdapter = RoadmapRVAdapter()
        binding.addCurriculumChooseTopRv.adapter = roadmapTopRVAdapter

        tops.clear()

        //data : 전체
        tops.apply {


            tops.add(Curriculum("자바의 정석"))
            tops.add(Curriculum("자바의 정석"))
            tops.add(Curriculum("자바의 정석"))

            roadmapTopRVAdapter.datas = tops
            roadmapTopRVAdapter.notifyDataSetChanged()

            //click recyclerview item
            roadmapTopRVAdapter.setItemClickListener(object : RoadmapRVAdapter.OnItemClickListener {
                override fun onClick(v: View, position: Int) {
                    val intent = Intent(this@AddCurriculumChooseActivity, AddCurriculumDetailActivity::class.java)
                    startActivity(intent)
                }
            })
        }
    }



}