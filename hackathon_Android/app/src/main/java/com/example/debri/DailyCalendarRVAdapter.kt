package com.example.debri

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.debri.databinding.ItemHourBinding

class DailyCalendarRVAdapter : RecyclerView.Adapter<DailyCalendarRVAdapter.ViewHolder>(){
    var datas = ArrayList<ScheduleData>()
    var timeList = ArrayList<String>()

    inner class ViewHolder(val binding : ItemHourBinding) : RecyclerView.ViewHolder(binding.root){

        val schedule_time : TextView = binding.itemHourScheduleTimeTv
        val title : TextView = binding.itemHourScheduleTitleTv
        val detail : TextView = binding.itemHourScheduleDetailTv

        fun bind(item: ScheduleData) {
            //viewpager만들고 item.coverImg[position]
            schedule_time.text = item.time
            title.text = item.title
            detail.text = item.detail
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemHourBinding = ItemHourBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(datas[position])

        //recyclerview item 클릭하면 fragment
        // (1) 리스트 내 항목 클릭 시 onClick() 호출
        holder.itemView.setOnClickListener {
            itemClickListener.onClick(it, position)
        }
    }

    // (2) 리스너 인터페이스
    interface OnItemClickListener {
        fun onClick(v: View, position: Int)
    }
    // (3) 외부에서 클릭 시 이벤트 설정
    fun setItemClickListener(onItemClickListener: OnItemClickListener) {
        this.itemClickListener = onItemClickListener
    }
    // (4) setItemClickListener로 설정한 함수 실행
    private lateinit var itemClickListener : OnItemClickListener



    //
    override fun getItemCount(): Int = datas.size
}