package com.example.debri_lize.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.debri_lize.ClassFavoriteRVAdapter
import com.example.debri_lize.ClassLectureRVAdapter
import com.example.debri_lize.data.class_.Lecture
import com.example.debri_lize.data.class_.LectureFilter
import com.example.debri_lize.databinding.FragmentClassBinding
import com.example.debri_lize.service.ClassService
import com.example.debri_lize.utils.getUserIdx
import com.example.debri_lize.view.class_.LectureFavoriteView
import com.example.debri_lize.view.class_.LectureFilterView

class ClassFragment : Fragment(), LectureFavoriteView, LectureFilterView {

    lateinit var binding: FragmentClassBinding
    lateinit var classfavoriteRVAdapter: ClassFavoriteRVAdapter
    lateinit var classLectureRVAdapter: ClassLectureRVAdapter

    val classService = ClassService()
    val lectureFilter = LectureFilter()

    var filterNum : Int = 0

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

        //태그 클릭 효과
        onRadioButtonClicked()

        //api - lectureFavorite
        classService.setLectureFavoriteView(this)
        classService.showLectureFavorite(getUserIdx()!!)


        //강의 필터
        classService.setLectureFilterView(this)


    }

    private fun showList(){
        if(filterNum==0){
            //즐겨찾기 강의 view -> VISIBLE
            binding.classFavoriteLayout.visibility = View.VISIBLE
            //필터 강의 리스트 view -> GONE
            binding.classLecturelistRv.visibility = View.GONE
        }else{
            //즐겨찾기 강의 view -> GONE
            binding.classFavoriteLayout.visibility = View.GONE
            //필터 강의 view -> VISIBLE
            binding.classLecturelistRv.visibility = View.VISIBLE
        }
        classService.showLectureSearch(lectureFilter)
    }

    private fun onRadioButtonClicked(){
        //언어
        //front
        binding.classCurriTagFrontCb.setOnCheckedChangeListener { button, checked ->
            if(checked){
                binding.classCurriTagBackCb.visibility = View.GONE
                binding.classCurriTagCCb.visibility = View.GONE
                binding.classCurriTagPythonCb.visibility = View.GONE


                filterNum++
                lectureFilter.lang = button.text.toString()
            }else{
                binding.classCurriTagBackCb.visibility = View.VISIBLE
                binding.classCurriTagCCb.visibility = View.VISIBLE
                binding.classCurriTagPythonCb.visibility = View.VISIBLE

                filterNum--
                lectureFilter.lang = ""
            }
            showList()

        }
        //back
        binding.classCurriTagBackCb.setOnCheckedChangeListener { button, checked ->
            if(checked){
                binding.classCurriTagFrontCb.visibility = View.GONE
                binding.classCurriTagCCb.visibility = View.GONE
                binding.classCurriTagPythonCb.visibility = View.GONE

                filterNum++
                lectureFilter.lang = button.text.toString()

            }else{
                binding.classCurriTagFrontCb.visibility = View.VISIBLE
                binding.classCurriTagCCb.visibility = View.VISIBLE
                binding.classCurriTagPythonCb.visibility = View.VISIBLE

                filterNum--
                lectureFilter.lang = ""
            }
            showList()
        }
        //C language
        binding.classCurriTagCCb.setOnCheckedChangeListener { button, checked ->
            if(checked){
                binding.classCurriTagBackCb.visibility = View.GONE
                binding.classCurriTagFrontCb.visibility = View.GONE
                binding.classCurriTagPythonCb.visibility = View.GONE

                filterNum++
                lectureFilter.lang = button.text.toString()

            }else{
                binding.classCurriTagBackCb.visibility = View.VISIBLE
                binding.classCurriTagFrontCb.visibility = View.VISIBLE
                binding.classCurriTagPythonCb.visibility = View.VISIBLE

                filterNum--
                lectureFilter.lang = ""
            }
            showList()
        }
        //python
        binding.classCurriTagPythonCb.setOnCheckedChangeListener { button, checked ->
            if(checked){
                binding.classCurriTagBackCb.visibility = View.GONE
                binding.classCurriTagCCb.visibility = View.GONE
                binding.classCurriTagFrontCb.visibility = View.GONE

                filterNum++
                lectureFilter.lang = button.text.toString()

            }else{
                binding.classCurriTagBackCb.visibility = View.VISIBLE
                binding.classCurriTagCCb.visibility = View.VISIBLE
                binding.classCurriTagFrontCb.visibility = View.VISIBLE

                filterNum--
                lectureFilter.lang = ""
            }
            showList()
        }

        //서적 or 영상
        //서적
        binding.classCurriTagBookCb.setOnCheckedChangeListener { button, checked ->
            if(checked){
                binding.classCurriTagVideoCb.visibility = View.GONE

                filterNum++
                lectureFilter.type = button.text.toString()

            }else{
                binding.classCurriTagVideoCb.visibility = View.VISIBLE

                filterNum--
                lectureFilter.type = ""
            }
            showList()
        }
        //영상
        binding.classCurriTagVideoCb.setOnCheckedChangeListener { button, checked ->
            if(checked){
                binding.classCurriTagBookCb.visibility = View.GONE

                filterNum++
                lectureFilter.type = button.text.toString()

            }else{
                binding.classCurriTagBookCb.visibility = View.VISIBLE

                filterNum--
                lectureFilter.type = ""

            }
            showList()
        }

        //가격
        //무료
        binding.classPriceTagForfreeCb.setOnCheckedChangeListener { button, checked ->
            if(checked){
                binding.classPriceTagNoForfreeCb.visibility = View.GONE

                filterNum++
                lectureFilter.price = button.text.toString()
            }else{
                binding.classPriceTagNoForfreeCb.visibility = View.VISIBLE

                filterNum--
                lectureFilter.price = ""
            }
            showList()
        }
        //유료
        binding.classPriceTagNoForfreeCb.setOnCheckedChangeListener { button, checked ->
            if(checked){
                binding.classPriceTagForfreeCb.visibility = View.GONE

                filterNum++
                lectureFilter.price = button.text.toString()
            }else{
                binding.classPriceTagForfreeCb.visibility = View.VISIBLE

                filterNum--
                lectureFilter.price = ""
            }
            showList()
        }



    }



    override fun onLectureFavoriteSuccess(code: Int, result: List<com.example.debri_lize.response.Lecture>) {
        when(code){
            200->{
                val datas_f = ArrayList<Lecture>()

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

    override fun onLectureFilterSuccess(code: Int, result: List<com.example.debri_lize.response.Lecture>) {
        when(code){
            200->{
                val datas = ArrayList<Lecture>()
                binding.classLecturelistRv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                classLectureRVAdapter = ClassLectureRVAdapter()
                binding.classLecturelistRv.adapter = classLectureRVAdapter

                datas.apply {
                    for (i in result) {
                        datas.add(
                            Lecture(i.lectureIdx, i.lectureName, i.chapterNumber, i.langTag, i.media, i.price)
                        )
                    }

                    classLectureRVAdapter.datas = datas
                    classLectureRVAdapter.notifyDataSetChanged()

                    //item 클릭 시 강의 상세 화면으로 전환?
                    classLectureRVAdapter.setItemClickListener(object :
                        ClassLectureRVAdapter.OnItemClickListener {
                        override fun onClick(v: View, position: Int) {


                        }
                    })

                }
            }
        }
    }

    override fun onLectureFilterFailure(code: Int) {
        Log.d("lecturefilterfail","$code")
        when(code){
            //검색 결과 없을 때
            7307-> {
                binding.classLecturelistRv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                classLectureRVAdapter = ClassLectureRVAdapter()
                binding.classLecturelistRv.adapter = classLectureRVAdapter
            }
        }
    }




}