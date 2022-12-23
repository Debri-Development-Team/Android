package com.example.debri_lize.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.debri_lize.R
import com.example.debri_lize.data.class_.LectureFilter
import com.example.debri_lize.databinding.FragmentClassSelectCategoryBinding

class ClassSelectCategoryFragment : Fragment() {

    lateinit var binding : FragmentClassSelectCategoryBinding

    private var category = LectureFilter()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentClassSelectCategoryBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onStart() {
        super.onStart()

        //카테고리 설정
        onTagChecked()

        //확정 버튼
        binding.classCategoryBtn.setOnClickListener {
            val passBundleBFragment = ClassFragment()
            // 데이터 보내기
            Log.d("category",category.toString())
            val bundle = Bundle()
            bundle.putSerializable("category", category)
            passBundleBFragment.arguments = bundle


            activity?.supportFragmentManager!!.beginTransaction()
                .replace(R.id.main_frm, passBundleBFragment)
                .commit()
        }
    }


    private fun onTagChecked(){
        //front
        binding.classCurriTagFrontCb.setOnCheckedChangeListener { button, checked ->
            if(checked){
                binding.classCurriTagBackCb.isChecked = false
                binding.classCurriTagCCb.isChecked = false
                binding.classCurriTagPythonCb.isChecked = false
                category.lang = "Front"
            }else{
                category.lang = ""
            }
        }

        //back
        binding.classCurriTagBackCb.setOnCheckedChangeListener { button, checked ->
            if(checked){
                binding.classCurriTagFrontCb.isChecked = false
                binding.classCurriTagCCb.isChecked = false
                binding.classCurriTagPythonCb.isChecked = false
                category.lang = "Back"
            }else{
                category.lang = ""
            }
        }

        //python
        binding.classCurriTagPythonCb.setOnCheckedChangeListener { button, checked ->
            if(checked){
                binding.classCurriTagFrontCb.isChecked = false
                binding.classCurriTagCCb.isChecked = false
                binding.classCurriTagBackCb.isChecked = false
                category.lang = "Python"
            }else{
                category.lang = ""
            }
        }

        //c
        binding.classCurriTagCCb.setOnCheckedChangeListener { button, checked ->
            if(checked){
                binding.classCurriTagFrontCb.isChecked = false
                binding.classCurriTagBackCb.isChecked = false
                binding.classCurriTagPythonCb.isChecked = false
                category.lang = "C 언어"
            }else{
                category.lang = ""
            }
        }

        //서적,영상
        binding.classCategoryTypeRg.setOnCheckedChangeListener { group, checkedId ->
            when(checkedId){
                R.id.class_category_book_rb -> category.type = "서적"
                R.id.class_category_video_rb -> category.type = "영상"
            }
        }

        //유료,무료
        binding.classCategoryPriceRg.setOnCheckedChangeListener { group, checkedId ->
            when(checkedId){
                R.id.class_category_free_rb -> category.price = "무료"
                R.id.class_category_nofree_rb -> category.price = "유료"
            }
        }
    }
}