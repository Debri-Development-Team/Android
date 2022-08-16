package com.example.debri_lize.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.debri_lize.adapter.start.CurriculumListRVAdapter
import com.example.debri_lize.adapter.start.RoadMapListRVAdapter
import com.example.debri_lize.data.curriculum.Curriculum
import com.example.debri_lize.data.curriculum.NewCurriculum
import com.example.debri_lize.data.curriculum.RoadMapList
import com.example.debri_lize.databinding.ActivityAddCurriculumChooseBinding
import com.example.debri_lize.service.CurriculumService
import com.example.debri_lize.service.RoadMapService
import com.example.debri_lize.view.curriculum.CreateCurriculumView
import com.example.debri_lize.view.curriculum.MyCurriculumListView
import com.example.debri_lize.view.curriculum.ShowRoadMapListView

class AddCurriculumChooseActivity : AppCompatActivity(), CreateCurriculumView, ShowRoadMapListView{
    lateinit var binding : ActivityAddCurriculumChooseBinding

    lateinit var roadmapRVAdapter : RoadMapListRVAdapter
    lateinit var roadmapTopRVAdapter : CurriculumListRVAdapter

    val roadMap = ArrayList<RoadMapList>()
    val top10 = ArrayList<Curriculum>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddCurriculumChooseBinding.inflate(layoutInflater) //binding 초기화
        setContentView(binding.root)
    }

    override fun onStart() {
        super.onStart()
        binding.addCurriculumChooseNewIv.setOnClickListener{
            //add dialog code

            //api - 8.1 커리큘럼 생성 api
            var curriculumService = CurriculumService()
            curriculumService.setCreateCurriculumView(this)
            curriculumService.createCurriculum(NewCurriculum("curriName", "curriAuthor", "visible", "language"))

        }

        //api - 7.5 로드맵 리스트 조회 api
        var roadMapService = RoadMapService()
        roadMapService.setShowRoadMapListView(this)
        roadMapService.showRoadMapList()

        initRecyclerView()
    }

    override fun onResume() {
        super.onResume()
    }

    private fun initRecyclerView(){


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

    //7.5 로드맵 리스트 조회 api
    override fun onShowRoadMapListSuccess(code: Int, result: List<RoadMapList>) {
        when(code){
            200->{
                binding.addCurriculumChooseRoadmapAdminRv.layoutManager =
                    LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
                roadmapRVAdapter = RoadMapListRVAdapter()
                binding.addCurriculumChooseRoadmapAdminRv.adapter = roadmapRVAdapter

                roadMap.clear()

                roadMap.apply {
                    for(i in result){
                        roadMap.add(RoadMapList(i.roadmapIdx, i.roadmapName, i.roadmapExplain, i.roadmapAuthor))
                    }

                    roadmapRVAdapter.datas = roadMap
                    roadmapRVAdapter.notifyDataSetChanged()

                    //click recyclerview item
                    roadmapRVAdapter.setItemClickListener(object : RoadMapListRVAdapter.OnItemClickListener {
                        override fun onClick(v: View, position: Int) {
                            val intent = Intent(this@AddCurriculumChooseActivity, AddRoadmapDetailActivity::class.java)
                            intent.putExtra("roadMapIdx", roadMap[position].roadmapIdx)
                            startActivity(intent)
                        }
                    })
                }
            }
        }
    }

    override fun onShowRoadMapListFailure(code: Int) {

    }

}