package com.example.debri_lize.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.debri_lize.R
import com.example.debri_lize.adapter.start.CurriculumListRVAdapter
import com.example.debri_lize.adapter.start.RoadMapListRVAdapter
import com.example.debri_lize.data.curriculum.Curriculum
import com.example.debri_lize.data.curriculum.RoadMapList
import com.example.debri_lize.data.curriculum.Top10
import com.example.debri_lize.databinding.ActivityAddCurriculumChooseBinding
import com.example.debri_lize.service.CurriculumService
import com.example.debri_lize.service.RoadMapService
import com.example.debri_lize.utils.getIsFirst
import com.example.debri_lize.view.curriculum.CreateCurriculumView
import com.example.debri_lize.view.curriculum.ShowRoadMapListView
import com.example.debri_lize.view.curriculum.ShowTop10ListView

class AddCurriculumChooseActivity : AppCompatActivity(), CreateCurriculumView, ShowRoadMapListView, ShowTop10ListView{
    lateinit var binding : ActivityAddCurriculumChooseBinding

    lateinit var roadmapRVAdapter : RoadMapListRVAdapter
    lateinit var roadmapTopRVAdapter : CurriculumListRVAdapter

    val roadMap = ArrayList<RoadMapList>()
    val top10 = ArrayList<Curriculum>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddCurriculumChooseBinding.inflate(layoutInflater) //binding 초기화
        setContentView(binding.root)

        //backbtn

            binding.homeCurriculumPreviousIv.setOnClickListener{
                finish()
            }


        binding.addCurriculumChooseNewIv.setOnClickListener{
            //add dialog code

            //data : AddCurriculumChooseActivity -> CurriculumSettingActivity
            val intent = Intent(this, CurriculumSettingActivity::class.java)

            startActivity(intent)


        }

        //api - 7.5 로드맵 리스트 조회 api
        var roadMapService = RoadMapService()
        roadMapService.setShowRoadMapListView(this)
        roadMapService.showRoadMapList()

        binding.addCurriculumChooseRoadmapAdminRv.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        roadmapRVAdapter = RoadMapListRVAdapter()
        binding.addCurriculumChooseRoadmapAdminRv.adapter = roadmapRVAdapter

        //api - 8.10 커리큘럼 좋아요(추천) TOP 10 리스트 조회 api
        var curriculumService = CurriculumService()
        curriculumService.setShowTop10ListView(this)
        curriculumService.showTop10List()

        binding.addCurriculumChooseTopRv.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        roadmapTopRVAdapter = CurriculumListRVAdapter()
        binding.addCurriculumChooseTopRv.adapter = roadmapTopRVAdapter
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

                            if(roadMap[position].roadmapIdx==1){ //서버 로드맵
                                val intent = Intent(this@AddCurriculumChooseActivity, AddRoadmapDetailActivity::class.java)
                                intent.putExtra("roadMapIdx", roadMap[position].roadmapIdx)
                                startActivity(intent)
                            }else{ //안드로이드 로드맵
                                //준비중입니다 팝업창 추가
                                var prepareToast = layoutInflater.inflate(R.layout.toast_prepare,null)
                                var toast = Toast(this@AddCurriculumChooseActivity)
                                toast.view = prepareToast
                                toast.setGravity(Gravity.CENTER_HORIZONTAL,0,0)
                                toast.show()

                            }

                        }
                    })
                }
            }
        }
    }

    override fun onShowRoadMapListFailure(code: Int) {

    }

    //8.10 커리큘럼 좋아요(추천) TOP 10 리스트 조회 api
    override fun onShowTop10ListSuccess(code: Int, result: List<Top10>) {
        when(code){
            200->{

                top10.clear()

                //data : 전체
                top10.apply {

                    //나중에 랭킹순으로 뜨도록 변경
                    for(i in result){
                        top10.add(Curriculum(i.curriIdx,i.curriName,i.curriAuthor, i.status))
                    }

                    roadmapTopRVAdapter.datas = top10
                    roadmapTopRVAdapter.notifyDataSetChanged()

                    //click recyclerview item
                    roadmapTopRVAdapter.setItemClickListener(object : CurriculumListRVAdapter.OnItemClickListener {
                        override fun onClick(v: View, position: Int) {
                            val intent = Intent(this@AddCurriculumChooseActivity, AddCurriculumDetailActivity::class.java)
                            intent.putExtra("curriculumIdx", top10[position].curriculumIdx)
                            startActivity(intent)
                        }
                    })
                }
            }
        }
    }

    override fun onShowTop10ListFailure(code: Int) {

    }

}