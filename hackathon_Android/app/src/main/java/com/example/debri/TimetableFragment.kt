package com.example.debri

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.debri.databinding.FragmentTimetableBinding

class TimetableFragment : Fragment() {
    lateinit var binding:FragmentTimetableBinding
    val checkdata:ArrayList<UserChecklist> = ArrayList()
    val userlecturedata:ArrayList<LectureData> = ArrayList()
    lateinit var checkadapter: UserCheckRVAdapter
    lateinit var lectureadapter: UserLectureRVAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTimetableBinding.inflate(inflater, container, false)
        initRecyclerView()

        return binding.root
    }



    private fun initRecyclerView(){
        //체크리스트 어댑터연결
        binding.userChecklistRv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        checkadapter = UserCheckRVAdapter(checkdata)
        binding.userChecklistRv.adapter = checkadapter

        //더미데이터
        checkdata.apply{
            add(UserChecklist("java",false))
            add(UserChecklist("spring",false))
            add(UserChecklist("udemy",false))
            add(UserChecklist("강의",false))

            checkadapter.datas = checkdata
            checkadapter.notifyDataSetChanged()

             checkadapter.setItemClickListener(object:UserCheckRVAdapter.OnItemClickListener{
                override fun onClick(v: View, position: Int) {
                    checkdata[position].TF = !checkdata[position].TF
                    Log.d("${checkdata[position].lectureName}","${checkdata[position].TF}")
                }
            })

            //유저 강의 목록 어댑터 연결
            binding.userLecturelistRv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            lectureadapter = UserLectureRVAdapter(userlecturedata)
            binding.userLecturelistRv.adapter = lectureadapter

            //dummy
            userlecturedata.apply{
                add(LectureData("java","월/수","02:00 - 03:00"))
                add(LectureData("spring","화/목","12:00 - 13:00"))
                add(LectureData("udemy","수/목","12:00 - 14:00"))

                lectureadapter.datas = userlecturedata
                lectureadapter.notifyDataSetChanged()

               lectureadapter.setItemClickListener(object: UserLectureRVAdapter.OnItemClickListener{
                    override fun onClick(v: View, position: Int) {
                        //SearchDetailFragment에 data보내기
                        val bundle = Bundle()
                        bundle.putSerializable("lecture", userlecturedata[position])
                        //Log.d("data[position]","$bundle")
                        val passBundleBFragment = SearchDetailFragment()
                        passBundleBFragment.arguments = bundle

                        //fragment to fragment
                        activity?.supportFragmentManager!!.beginTransaction()
                            .replace(R.id.main_frm, passBundleBFragment)
                            .commit()

                    }
                })

            }

        }
    }



}