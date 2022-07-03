package com.example.debri

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.debri.databinding.FragmentSearchDetailBinding

class SearchDetailFragment : Fragment() {
    lateinit var binding:FragmentSearchDetailBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchDetailBinding.inflate(layoutInflater)

        //data 받아오기
        var lecture = arguments?.getSerializable("lecture") as LectureData?
        //받아온 data로 변경
        Log.d("lecture", lecture.toString())
        if (lecture != null) {
            //레이아웃에 있는 text를 변경
            binding.lectureNameTv.text = lecture.lectureName
            binding.lectureDayofweekTv.text = lecture.dayofweek
            binding.lectureTimeTv.text = lecture.time
        }//

        //return inflater.inflate(R.layout.activity_main, container, false)

        binding.arrowRightIv.setOnClickListener{
            (context as MainActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.main_frm, SearchFragment()).commitAllowingStateLoss()
        }
        return binding.root
    }


}