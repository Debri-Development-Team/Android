package com.example.debri.activity

import android.util.Log
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.debri.DailyCalendarRVAdapter
import com.example.debri.R
import com.example.debri.ScheduleData
import com.example.debri.databinding.ActivityDailyCalendarBinding

class DailyCalendarActivity : AppCompatActivity() {
    lateinit var binding: ActivityDailyCalendarBinding
    lateinit var dailyCalenderRVAdapter: DailyCalendarRVAdapter
    var datas = ArrayList<ScheduleData>()
    var timeList = arrayListOf("00:00", "01:00", "02:00", "03:00", "04:00", "05:00", "06:00"
        , "07:00", "08:00", "09:00", "10:00", "11:00", "12:00", "13:00", "14:00", "15:00"
        , "16:00", "17:00", "18:00", "19:00", "20:00", "21:00", "22:00", "23:00", "24:00")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_daily_calendar)

        binding = ActivityDailyCalendarBinding.inflate(layoutInflater) //binding 초기화
        setContentView(binding.root)

        //intent로 객체 받아오기
        val intent = intent
        datas = intent.getSerializableExtra("schedule") as ArrayList<ScheduleData>

    }

    override fun onStart() {
        super.onStart()
        initDailyCalendarRecycler()
    }

    private fun initDailyCalendarRecycler() {
        dailyCalenderRVAdapter = DailyCalendarRVAdapter()
        binding.dailyCalendarTimeScheduleRv.adapter = dailyCalenderRVAdapter
        Log.d("data", datas.toString())

        //data
        datas.apply {

            Log.d("data", datas.toString())
            dailyCalenderRVAdapter.datas = datas
            dailyCalenderRVAdapter.timeList = timeList
            dailyCalenderRVAdapter.notifyDataSetChanged()

            //recyclerview item 클릭하면
            dailyCalenderRVAdapter.setItemClickListener(object : DailyCalendarRVAdapter.OnItemClickListener {
                override fun onClick(v: View, position: Int) {
                    // 클릭 시 이벤트 작성


                }
            })
            dailyCalenderRVAdapter.notifyDataSetChanged()

        }


    }
}