package com.example.debri

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.debri.activity.DailyCalendarActivity
import com.example.debri.databinding.FragmentHomeBinding



class HomeFragment : Fragment() {
    lateinit var binding: FragmentHomeBinding
    lateinit var scheduleRVAdapter: ScheduleRVAdapter
    val datas = ArrayList<ScheduleData>()

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
        initScheduleRecycler()
    }

    private fun initScheduleRecycler() {
        binding.homeScheduleRv.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        scheduleRVAdapter = ScheduleRVAdapter()
        binding.homeScheduleRv.adapter = scheduleRVAdapter

        //dummy
        datas.apply {
            add(ScheduleData("01:00", "SQL 스터디", "info"))
            add(ScheduleData("02:00", "Ruby on Rails 강의","info"))
            add(ScheduleData("03:00", "야호1", "info"))
            add(ScheduleData("04:00", "야호2", "info"))
            add(ScheduleData("05:00", "야호3", "info"))
            add(ScheduleData("06:00", "야호4", "info"))
            Log.d("datas", datas.toString())
            scheduleRVAdapter.datas = datas
            scheduleRVAdapter.notifyDataSetChanged()

            //recyclerview item 클릭하면 fragment
//            scheduleRVAdapter.setItemClickListener(object : ScheduleRVAdapter.OnItemClickListener {
//                override fun onClick(v: View, position: Int) {
//                    // 클릭 시 이벤트 작성
//                    activity?.let {
//
//                        //객체 자체를 보내는 방법 (data class)
//                        val intent = Intent(context, DailyCalendarActivity::class.java)
//                        intent.putExtra("schedule", datas[position])
//                        startActivity(intent)
//
//                    }
//
//                }
//            })

            binding.homeScheduleTv.setOnClickListener{
                activity?.let {
                    val intent = Intent(context, DailyCalendarActivity::class.java)
                    intent.putExtra("schedule", datas)
                    startActivity(intent)

                }
            }


        }


    }
}