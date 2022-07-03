package com.example.debri

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.debri.databinding.ItemRecyclerLectureBinding

class UserLectureRVAdapter(var datas:ArrayList<LectureData>) : RecyclerView.Adapter<UserLectureRVAdapter.ViewHolder>() {

    interface OnItemClickListener{
        fun onClick(v: View, position: Int)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): UserLectureRVAdapter.ViewHolder {
        val binding: ItemRecyclerLectureBinding = ItemRecyclerLectureBinding.inflate(LayoutInflater.from(viewGroup.context),viewGroup,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserLectureRVAdapter.ViewHolder, position: Int) {
        holder.bind(datas[position])

        // (1) 리스트 내 항목 클릭 시 onClick() 호출
        holder.itemView.setOnClickListener {
            itemClickListener?.onClick(it, position)
        }
    }

    // (3) 외부에서 클릭 시 이벤트 설정
    fun setItemClickListener(onItemClickListener: UserLectureRVAdapter.OnItemClickListener) {
        this.itemClickListener = onItemClickListener
    }
    // (4) setItemClickListener로 설정한 함수 실행
    private lateinit var itemClickListener : UserLectureRVAdapter.OnItemClickListener

    override fun getItemCount(): Int = datas.size

    inner class ViewHolder(val binding:ItemRecyclerLectureBinding): RecyclerView.ViewHolder(binding.root){

        fun bind(data : LectureData){
            binding.lectureNameTv.text = data.lectureName
            binding.lectureDayofweekTv.text = data.dayofweek
            binding.lectureTimeTv.text = data.time
        }

    }

}