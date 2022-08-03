package com.example.debri_lize.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.debri_lize.BoardFavoriteRVAdapter
import com.example.debri_lize.ClassFavoriteRVAdapter
import com.example.debri_lize.ClassLectureRVAdapter
import com.example.debri_lize.R
import com.example.debri_lize.data.Lecture
import com.example.debri_lize.data.board.Board
import com.example.debri_lize.databinding.FragmentClassBinding
import com.example.debri_lize.service.ClassService
import com.example.debri_lize.utils.getUserIdx
import com.example.debri_lize.view.`class`.LectureFavoriteView
import com.example.debri_lize.view.`class`.LectureListView

class ClassFragment : Fragment(), LectureFavoriteView {

    lateinit var binding: FragmentClassBinding
    lateinit var classfavoriteRVAdapter: ClassFavoriteRVAdapter
    lateinit var classLectureRVAdapter: ClassLectureRVAdapter
    val datas_f = ArrayList<Lecture>()
    val datas = ArrayList<Lecture>()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentClassBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onStart() {
        super.onStart()

        onRadioButtonClicked()

        //api - lectureFavorite
        val classService = ClassService()
        classService.setLectureFavoriteView(this)
        Log.d("useridx", getUserIdx().toString())
        classService.showLectureFavorite(getUserIdx()!!)

        //전체 강의
//        classService.setLectureListView(this)
//        classService.showLectureList()



    }

    private fun onRadioButtonClicked(){
        //언어
        //front
        binding.classCurriTagFrontCb.setOnCheckedChangeListener { button, checked ->
            if(checked){
                binding.classCurriTagBackCb.visibility = View.GONE
                binding.classCurriTagCCb.visibility = View.GONE
                binding.classCurriTagPythonCb.visibility = View.GONE
                //즐겨찾기 강의 view -> GONE
                binding.classFavoriteLayout.visibility = View.GONE
                //필터 강의 view -> VISIBLE
                binding.classLecturelistRv.visibility = View.VISIBLE
            }else{
                binding.classCurriTagBackCb.visibility = View.VISIBLE
                binding.classCurriTagCCb.visibility = View.VISIBLE
                binding.classCurriTagPythonCb.visibility = View.VISIBLE
                //즐겨찾기 강의 view -> VISIBLE
                binding.classFavoriteLayout.visibility = View.VISIBLE
                //필터 강의 리스트 view -> GONE
                binding.classLecturelistRv.visibility = View.GONE
            }
        }
        //back
        binding.classCurriTagBackCb.setOnCheckedChangeListener { button, checked ->
            if(checked){
                binding.classCurriTagFrontCb.visibility = View.GONE
                binding.classCurriTagCCb.visibility = View.GONE
                binding.classCurriTagPythonCb.visibility = View.GONE

                binding.classFavoriteLayout.visibility = View.GONE
                binding.classLecturelistRv.visibility = View.VISIBLE
            }else{
                binding.classCurriTagFrontCb.visibility = View.VISIBLE
                binding.classCurriTagCCb.visibility = View.VISIBLE
                binding.classCurriTagPythonCb.visibility = View.VISIBLE

                binding.classFavoriteLayout.visibility = View.VISIBLE
                binding.classLecturelistRv.visibility = View.GONE
            }
        }
        //C language
        binding.classCurriTagCCb.setOnCheckedChangeListener { button, checked ->
            if(checked){
                binding.classCurriTagBackCb.visibility = View.GONE
                binding.classCurriTagFrontCb.visibility = View.GONE
                binding.classCurriTagPythonCb.visibility = View.GONE

                binding.classFavoriteLayout.visibility = View.GONE
                binding.classLecturelistRv.visibility = View.VISIBLE
            }else{
                binding.classCurriTagBackCb.visibility = View.VISIBLE
                binding.classCurriTagFrontCb.visibility = View.VISIBLE
                binding.classCurriTagPythonCb.visibility = View.VISIBLE

                binding.classFavoriteLayout.visibility = View.VISIBLE
                binding.classLecturelistRv.visibility = View.GONE
            }
        }
        //python
        binding.classCurriTagPythonCb.setOnCheckedChangeListener { button, checked ->
            if(checked){
                binding.classCurriTagBackCb.visibility = View.GONE
                binding.classCurriTagCCb.visibility = View.GONE
                binding.classCurriTagFrontCb.visibility = View.GONE

                binding.classFavoriteLayout.visibility = View.GONE
                binding.classLecturelistRv.visibility = View.VISIBLE
            }else{
                binding.classCurriTagBackCb.visibility = View.VISIBLE
                binding.classCurriTagCCb.visibility = View.VISIBLE
                binding.classCurriTagFrontCb.visibility = View.VISIBLE

                binding.classFavoriteLayout.visibility = View.VISIBLE
                binding.classLecturelistRv.visibility = View.GONE
            }
        }

        //서적 or 영상
        //서적
        binding.classCurriTagBookCb.setOnCheckedChangeListener { button, checked ->
            if(checked){
                binding.classCurriTagVideoCb.visibility = View.GONE

                binding.classFavoriteLayout.visibility = View.GONE
                binding.classLecturelistRv.visibility = View.VISIBLE
            }else{
                binding.classCurriTagVideoCb.visibility = View.VISIBLE

                binding.classFavoriteLayout.visibility = View.VISIBLE
                binding.classLecturelistRv.visibility = View.GONE
            }
        }
        //영상
        binding.classCurriTagVideoCb.setOnCheckedChangeListener { button, checked ->
            if(checked){
                binding.classCurriTagBookCb.visibility = View.GONE

                binding.classFavoriteLayout.visibility = View.GONE
                binding.classLecturelistRv.visibility = View.VISIBLE
            }else{
                binding.classCurriTagBookCb.visibility = View.VISIBLE

                binding.classFavoriteLayout.visibility = View.VISIBLE
                binding.classLecturelistRv.visibility = View.GONE
            }
        }

        //가격
        //무료
        binding.classPriceTagForfreeCb.setOnCheckedChangeListener { button, checked ->
            if(checked){
                binding.classPriceTagNoForfreeCb.visibility = View.GONE

                binding.classFavoriteLayout.visibility = View.GONE
                binding.classLecturelistRv.visibility = View.VISIBLE
            }else{
                binding.classPriceTagNoForfreeCb.visibility = View.VISIBLE

                binding.classFavoriteLayout.visibility = View.VISIBLE
                binding.classLecturelistRv.visibility = View.GONE
            }
        }
        //유료
        binding.classPriceTagNoForfreeCb.setOnCheckedChangeListener { button, checked ->
            if(checked){
                binding.classPriceTagForfreeCb.visibility = View.GONE

                binding.classFavoriteLayout.visibility = View.GONE
                binding.classLecturelistRv.visibility = View.VISIBLE
            }else{
                binding.classPriceTagForfreeCb.visibility = View.VISIBLE

                binding.classFavoriteLayout.visibility = View.VISIBLE
                binding.classLecturelistRv.visibility = View.GONE
            }
        }

    }



    override fun onLectureFavoriteSuccess(code: Int, result: List<com.example.debri_lize.response.Lecture>) {
        when(code){
            200->{
                //즐겨찾기
                binding.classFavoriteRv.layoutManager =
                    LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                classfavoriteRVAdapter = ClassFavoriteRVAdapter()
                binding.classFavoriteRv.adapter = classfavoriteRVAdapter

                datas_f.apply {
                    for (i in result){
                        datas_f.add(Lecture(i.lectureIdx, i.lectureName, i.chapterNumber, i.langTag, i.media, i.price))
                    }

                    classfavoriteRVAdapter.datas_classf = datas_f
                    classfavoriteRVAdapter.notifyDataSetChanged()

                    //item 클릭 시 강의 상세 화면으로 전환?
                    classfavoriteRVAdapter.setItemClickListener(object : ClassFavoriteRVAdapter.OnItemClickListener {
                        override fun onClick(v: View, position: Int) {



                        }
                    })
                }
            }
        }
    }

    override fun onLectureFavoriteFailure(code: Int) {
        Log.d("lecturefavoritefail","$code")
    }

//    override fun onLectureListSuccess(code: Int, result: List<com.example.debri_lize.response.Lecture>) {
//        when(code){
//            200->{
//                //전체 강의 조회
//                binding.classLecturelistRv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
//                classLectureRVAdapter = ClassLectureRVAdapter()
//                binding.classLecturelistRv.adapter = classLectureRVAdapter
//
//                datas.apply {
//                    for (i in result){
//                        datas.add(Lecture(i.lectureIdx, i.lectureName, i.chapterNumber, i.langTag, i.media, i.price))
//                    }
//
//                    classLectureRVAdapter.datas = datas
//                    classLectureRVAdapter.notifyDataSetChanged()
//
//                    //item 클릭 시 강의 상세 화면으로 전환?
//                    classLectureRVAdapter.setItemClickListener(object : ClassLectureRVAdapter.OnItemClickListener {
//                        override fun onClick(v: View, position: Int) {
//
//
//
//                        }
//                    })
//                }
//            }
//        }
//    }
//
//    override fun onLectureListFailure(code: Int) {
//        Log.d("lecturelistfail","$code")
//    }


}