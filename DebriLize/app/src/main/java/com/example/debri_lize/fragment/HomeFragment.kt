package com.example.debri_lize.fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.debri_lize.CustomDialog
import com.example.debri_lize.R
import com.example.debri_lize.activity.MainActivity
import com.example.debri_lize.activity.PostCreateActivity
import com.example.debri_lize.activity.auth.ProfileActivity
import com.example.debri_lize.adapter.home.CurriculumProgressImgRVAdapter
import com.example.debri_lize.adapter.home.CurriculumProgressRVAdapter
import com.example.debri_lize.data.curriculum.CurriculumLecture
import com.example.debri_lize.data.curriculum.CurriculumLectureImg
import com.example.debri_lize.data.post.ReportPost
import com.example.debri_lize.databinding.FragmentHomeBinding
import com.example.debri_lize.utils.getUserIdx
import com.google.android.material.bottomsheet.BottomSheetDialog

class HomeFragment : Fragment() {

    lateinit var context: MainActivity

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

        //click userImg -> profile
        binding.homeDebriUserIv.setOnClickListener{
            val intent = Intent(context, ProfileActivity::class.java)
            startActivity(intent)
        }

        //bottomSheet
        bottomSheetSetting()

        //recycler view : 수정예정
        initRecyclerView()

        //click next -> AddCurriculumFragment
        //커리큘럼 리스트 개수가 끝났을 때 진행해야함 : 추후 조건문 추가
        binding.homeCurriculumNextIv.setOnClickListener{
            val passBundleBFragment = AddCurriculumFragment()
            //fragment to fragment
            activity?.supportFragmentManager!!.beginTransaction()
                .replace(R.id.main_frm, passBundleBFragment)
                .commit()
        }
    }

    //context 받아오기기
    override fun onAttach(context: Context) {
        super.onAttach(context)
        this.context = context as MainActivity
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

    //bottom sheet
    private fun bottomSheetSetting(){

        lateinit var bottomSheetView : View
        val bottomSheetDialog = BottomSheetDialog(context)
        bottomSheetView = layoutInflater.inflate(R.layout.fragment_bottom_sheet_four, null)
        bottomSheetDialog.setContentView(bottomSheetView)

        if(true){ //현재 커리큘럼 : 비공개

            bottomSheetView.findViewById<TextView>(R.id.bottom_sheet_four_tv1).text = "공개로 전환하기"

            //비공개 -> 공개
            bottomSheetView.findViewById<TextView>(R.id.bottom_sheet_four_tv1).setOnClickListener {
                binding.homeCurriculumHideTv.text = "공개 중"
                binding.homeCurriculumHideIv.setImageResource(R.drawable.ic_open)
                bottomSheetDialog.dismiss()
            }


        }
        else{ //현재 커리큘럼 : 공개
            bottomSheetView.findViewById<TextView>(R.id.bottom_sheet_four_tv1).text = "비공개로 전환하기"


        }

        //커리큘럼 이름 변경하기
        bottomSheetView.findViewById<TextView>(R.id.bottom_sheet_four_tv2).setOnClickListener {
            //add dialog code

            bottomSheetDialog.dismiss()
        }

        //커리큘럼 초기화하기
        bottomSheetView.findViewById<TextView>(R.id.bottom_sheet_four_tv3).setOnClickListener {
            //add dialog code

            bottomSheetDialog.dismiss()
        }

        //커리큘럼 삭제하기
        bottomSheetView.findViewById<TextView>(R.id.bottom_sheet_four_tv4).setOnClickListener {
            //add dialog code

            bottomSheetDialog.dismiss()
        }

        binding.homeCurriculumSettingIv.setOnClickListener {
            bottomSheetDialog.show()
        }

        //close button
        bottomSheetView.findViewById<TextView>(R.id.bottom_sheet_close_tv).setOnClickListener {
            bottomSheetDialog.dismiss()
        }

    }
}