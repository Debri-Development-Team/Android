package com.example.debri_lize.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.debri_lize.adapter.class_.ClassLectureRVAdapter
import com.example.debri_lize.data.class_.Lecture
import com.example.debri_lize.data.curriculum.RoadMap
import com.example.debri_lize.databinding.ActivityAddRoadmapDetailBinding
import com.example.debri_lize.service.RoadMapService
import com.example.debri_lize.view.curriculum.ShowRoadMapDetailView
import kotlin.properties.Delegates

class AddRoadmapDetailActivity : AppCompatActivity(), ShowRoadMapDetailView {
    lateinit var binding : ActivityAddRoadmapDetailBinding

    lateinit var classLectureRVAdapter: ClassLectureRVAdapter

    var roadMapIdx by Delegates.notNull<Int>()
    val lecture = ArrayList<Lecture>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddRoadmapDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //lecture recycler view
        binding.addRoadmapLectureRv.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        classLectureRVAdapter = ClassLectureRVAdapter()
        binding.addRoadmapLectureRv.adapter = classLectureRVAdapter

        //data : AddCurriculumChooseActivity -> AddRoadMapDetailActivity
        val intent = intent //전달할 데이터를 받을 Intent
        roadMapIdx = intent.getIntExtra("roadMapIdx", 0)

        //api - 7.5.1 로드맵 상세 조회 api
        var roadMapService = RoadMapService()
        roadMapService.setShowRoadMapDetailView(this)
        if(roadMapIdx==1){ //server
            roadMapService.showRoadMapDetail("server")
        }else{ //front
            roadMapService.showRoadMapDetail("front")
        }

        //수정사항
        //classLectureRVadapter 하나로 합치고 즐찾 후, api 7.5.1 재조회
    }

    override fun onResume() {
        super.onResume()

    }

    //7.5.1 로드맵 상세 조회 api
    override fun onShowRoadMapDetailSuccess(code: Int, result: List<RoadMap>) {
        when(code){
            200->{
                //screen
                binding.itemCurriculumNameTv.text = result[0].roadmapName
                binding.itemCurriculumDetailTv.text = result[0].roadmapExplain
                binding.itemCurriculumAuthorTv.text = "by" + result[0].authorName
                binding.itemCurriculumDetailDdayTv2.text = result[0].requireDay.toString()

                //lecture recycler view
                lecture.clear()
                lecture.apply {
                    for(i in result){
                        for(j in i.roadmapChildCurriList){
                            for(k in j.roadmapChildLectureList){
                                lecture.add(Lecture(k.childLectureIdx, k.childLectureName,k.childChapterNumber,k.childLangTag, k.childMaterialType, k.childPricing, k.userScrap, k.scrapNumber,k.usedCount, k.likeNumber, k.userLike))
                            }
                        }
                    }

                    classLectureRVAdapter.datas = lecture
                    classLectureRVAdapter.notifyDataSetChanged()

                    //click recyclerview item
                    classLectureRVAdapter.setItemClickListener(object : ClassLectureRVAdapter.OnItemClickListener {
                        override fun onClick(v: View, position: Int) {


                        }
                    })
                }

            }
        }
    }

    override fun onShowRoadMapDetailFailure(code: Int) {

    }


}