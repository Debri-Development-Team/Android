package com.example.debri_lize.activity


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.debri_lize.adapter.home.CurriculumRVAdapter
import com.example.debri_lize.data.curriculum.AddLecture
import com.example.debri_lize.data.curriculum.Curriculum
import com.example.debri_lize.databinding.ActivityChooseMyCurriculumBinding
import com.example.debri_lize.service.CurriculumService
import com.example.debri_lize.view.curriculum.AddLectureInCurriculumView
import com.example.debri_lize.view.curriculum.MyCurriculumListView
import kotlin.properties.Delegates

class ChooseMyCurriculumActivity : AppCompatActivity(), MyCurriculumListView, AddLectureInCurriculumView {
    lateinit var binding : ActivityChooseMyCurriculumBinding

    lateinit var curriculumRVAdapter: CurriculumRVAdapter
    val datas = ArrayList<Curriculum>()

    private var lectureIdx by Delegates.notNull<Int>()
    private lateinit var lectureName : String

    //api
    val curriculumService = CurriculumService()

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityChooseMyCurriculumBinding.inflate(layoutInflater) //binding 초기화
        setContentView(binding.root)

        //LectureDetailActivity -> ChooseMyCurriculumActivity
        val intent = intent //전달할 데이터를 받을 Intent
        lectureIdx = intent.getIntExtra("lectureIdx", 0)
        lectureName = intent.getStringExtra("lectureName").toString()

        //screen
        binding.chooseMyCurriLectureNameTv.text = lectureName

        //backbtn
        binding.postPreviousIv.setOnClickListener{
            finish()
        }

        //api - 8.2 커리큘럼 리스트 조회 api
        curriculumService.setMyCurriculumListView(this)
        curriculumService.myCurriculumList()

        //api - 8.5 강의자료 추가 api : 홈 > 새로운 강의자료 추가하기
        curriculumService.setAddLectureInCurriculumView(this)

    }

    override fun onMyCurriculumListSuccess(code: Int, result: List<Curriculum>) {
        when(code){
            200->{
                binding.profileCurriculumRv.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
                curriculumRVAdapter = CurriculumRVAdapter()
                binding.profileCurriculumRv.adapter = curriculumRVAdapter

                datas.clear()

                //data : 전체
                datas.apply {

                    for(i in result){
                        datas.add(Curriculum(i.curriculumIdx, i.curriculumName, i.curriculumAuthor, i.status))
                    }

                    curriculumRVAdapter.datas = datas
                    curriculumRVAdapter.notifyDataSetChanged()

                    //click recyclerview item
                    curriculumRVAdapter.setItemClickListener(object : CurriculumRVAdapter.OnItemClickListener {
                        override fun onClick(v: View, position: Int) {
                            //api - 8.5 강의자료 추가 api : 홈 > 새로운 강의자료 추가하기
                            curriculumService.addLectureInCurriculum(AddLecture(datas[position].curriculumIdx, 3)) //lectureIdx 변경
                            Log.d("addLecture", AddLecture(datas[position].curriculumIdx, lectureIdx).toString())
                        }
                    })
                }
            }
        }
    }

    override fun onMyCurriculumListFailure(code: Int) {

    }

    override fun onAddLectureInCurriculumSuccess(code: Int) {
        when(code){
            200->{
                //add toast

                finish()
            }
        }
    }

    override fun onAddLectureInCurriculumFailure(code: Int) {

    }


}