package com.example.debri_lize.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.debri_lize.R
import com.example.debri_lize.adapter.home.CurriculumProgressImgRVAdapter
import com.example.debri_lize.adapter.home.CurriculumProgressRVAdapter
import com.example.debri_lize.data.curriculum.CurriculumLecture
import com.example.debri_lize.data.curriculum.CurriculumLectureImg
import com.example.debri_lize.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    lateinit var binding: FragmentHomeBinding
    lateinit var curriculumProgressImgRVAdapter: CurriculumProgressImgRVAdapter
    lateinit var curriculumProgressRVAdapter: CurriculumProgressRVAdapter

    var arrayImg = arrayOf(R.drawable.ic_lecture_green, R.drawable.ic_lecture_purple, R.drawable.ic_lecture_red)

    val datasImg = ArrayList<CurriculumLectureImg>()
    val datas = ArrayList<CurriculumLecture>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        initRecyclerView()
    }

    private fun initRecyclerView(){
        //위에 강의 리스트 이미지
        binding.homeCurriculumLectureImgRv.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        curriculumProgressImgRVAdapter = CurriculumProgressImgRVAdapter()
        binding.homeCurriculumLectureImgRv.adapter = curriculumProgressImgRVAdapter

        datasImg.clear()

        //data : 전체
        datasImg.apply {

            for(i in 0 until 3){
                datasImg.add(CurriculumLectureImg(i,arrayImg[i],"자바의 정석(1강-4강)"))
            }

            curriculumProgressImgRVAdapter.datas = datasImg
            curriculumProgressImgRVAdapter.notifyDataSetChanged()

            //click recyclerview item
            curriculumProgressImgRVAdapter.setItemClickListener(object : CurriculumProgressImgRVAdapter.OnItemClickListener {
                override fun onClick(v: View, position: Int) {


                }
            })
        }

        //아래 강의 리스트
        binding.homeCurriculumLectureRv.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        curriculumProgressRVAdapter = CurriculumProgressRVAdapter()
        binding.homeCurriculumLectureRv.adapter = curriculumProgressRVAdapter

        datas.clear()

        //data : 전체
        datas.apply {


            datas.add(CurriculumLecture("자바의 정석"))
            datas.add(CurriculumLecture("자바의 정석"))
            datas.add(CurriculumLecture("자바의 정석"))

            curriculumProgressRVAdapter.datas = datas
            curriculumProgressRVAdapter.notifyDataSetChanged()

            //click recyclerview item
            curriculumProgressRVAdapter.setItemClickListener(object : CurriculumProgressRVAdapter.OnItemClickListener {
                override fun onClick(v: View, position: Int) {


                }
            })
        }
    }
}