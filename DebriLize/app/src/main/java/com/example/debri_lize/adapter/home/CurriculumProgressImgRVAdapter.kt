package com.example.debri_lize.adapter.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.debri_lize.data.curriculum.CurriculumLectureImg
import com.example.debri_lize.databinding.ItemCurriculumLectureImgBinding

class CurriculumProgressImgRVAdapter : RecyclerView.Adapter<CurriculumProgressImgRVAdapter.ViewHolder>() {

    var datas = mutableListOf<CurriculumLectureImg>()

    inner class ViewHolder(val binding : ItemCurriculumLectureImgBinding) : RecyclerView.ViewHolder(binding.root){

        val lectureName : TextView = binding.itemCurriculumLectureImgTitleTv
        val lectureImg : ImageView = binding.itemCurriculumLectureImgIv

        fun bind(item: CurriculumLectureImg, position: Int) {
            lectureName.text = item.lectureName
            lectureImg.setImageResource(item.lectureImg)

            if(position%2==0){
                binding.paddingLeft.visibility = View.VISIBLE
                binding.paddingRight.visibility = View.GONE
            }else{
                binding.paddingLeft.visibility = View.GONE
                binding.paddingRight.visibility = View.VISIBLE
            }
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemCurriculumLectureImgBinding = ItemCurriculumLectureImgBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(datas[position], position)

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