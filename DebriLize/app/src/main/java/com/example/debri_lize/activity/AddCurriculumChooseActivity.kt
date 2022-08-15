package com.example.debri_lize.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.debri_lize.adapter.start.CurriculumListRVAdapter
import com.example.debri_lize.data.curriculum.Curriculum
import com.example.debri_lize.data.curriculum.NewCurriculum
import com.example.debri_lize.databinding.ActivityAddCurriculumChooseBinding
import com.example.debri_lize.service.CurriculumService
import com.example.debri_lize.view.curriculum.CreateCurriculumView
import com.example.debri_lize.view.curriculum.MyCurriculumListView

class AddCurriculumChooseActivity : AppCompatActivity(), CreateCurriculumView{
    lateinit var binding : ActivityAddCurriculumChooseBinding

    lateinit var roadmapRVAdapter : CurriculumListRVAdapter
    lateinit var roadmapTopRVAdapter : CurriculumListRVAdapter

    val datas = ArrayList<Curriculum>()
    val top10 = ArrayList<Curriculum>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddCurriculumChooseBinding.inflate(layoutInflater) //binding 초기화
        setContentView(binding.root)

        initRecyclerView()
    }

    override fun onStart() {
        super.onStart()
        binding.addCurriculumChooseNewIv.setOnClickListener{
            //add dialog code

            //api
            var curriculumService = CurriculumService()
            curriculumService.setCreateCurriculumView(this)
            curriculumService.createCurriculum(NewCurriculum("curriName", "curriAuthor", "visible", "language"))
        }
    }

    override fun onResume() {
        super.onResume()
    }

    private fun initRecyclerView(){
        binding.addCurriculumChooseRoadmapAdminRv.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        roadmapRVAdapter = CurriculumListRVAdapter()
        binding.addCurriculumChooseRoadmapAdminRv.adapter = roadmapRVAdapter

        datas.clear()

        //data : 전체
        datas.apply {

            datas.add(Curriculum(1, "서버 로드맵", "야호"))
            datas.add(Curriculum(1, "안드로이드 로드맵", "야호"))


            roadmapRVAdapter.datas = datas
            roadmapRVAdapter.notifyDataSetChanged()

            //click recyclerview item
            roadmapRVAdapter.setItemClickListener(object : CurriculumListRVAdapter.OnItemClickListener {
                override fun onClick(v: View, position: Int) {
                    val intent = Intent(this@AddCurriculumChooseActivity, AddRoadmapDetailActivity::class.java)
                    startActivity(intent)
                }
            })
        }

        binding.addCurriculumChooseTopRv.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        roadmapTopRVAdapter = CurriculumListRVAdapter()
        binding.addCurriculumChooseTopRv.adapter = roadmapTopRVAdapter

        top10.clear()

        //data : 전체
        top10.apply {

            top10.add(Curriculum(1,"야호","야호"))
            top10.add(Curriculum(1,"야호","야호"))
            top10.add(Curriculum(1,"야호","야호"))

            roadmapTopRVAdapter.datas = top10
            roadmapTopRVAdapter.notifyDataSetChanged()

            //click recyclerview item
            roadmapTopRVAdapter.setItemClickListener(object : CurriculumListRVAdapter.OnItemClickListener {
                override fun onClick(v: View, position: Int) {
                    val intent = Intent(this@AddCurriculumChooseActivity, AddCurriculumDetailActivity::class.java)
                    startActivity(intent)
                }
            })
        }


    }

    override fun onCreateCurriculumSuccess(code: Int) {
        when(code){
            200->{

            }
        }
    }

    override fun onCreateCurriculumFailure(code: Int) {

    }

}