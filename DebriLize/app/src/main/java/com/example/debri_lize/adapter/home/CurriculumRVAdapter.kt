package com.example.debri_lize.adapter.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.debri_lize.R
import com.example.debri_lize.data.curriculum.Curriculum
import com.example.debri_lize.databinding.ItemCurriculumBinding
import com.example.debri_lize.databinding.ItemCurriculumProfileBinding

class CurriculumRVAdapter : RecyclerView.Adapter<CurriculumRVAdapter.ViewHolder>() {

    var datas = mutableListOf<Curriculum>()

    inner class ViewHolder(val binding : ItemCurriculumProfileBinding) : RecyclerView.ViewHolder(binding.root){

        val statusImg : ImageView = binding.profileCurriculumStatusIv
        val curriculumName : TextView = binding.profileCurriculumNameTv

        fun bind(item: Curriculum) {
            Glide.with(itemView).load(R.raw.curriculum).into(statusImg)
            curriculumName.text = item.curriculumName
            binding.profileCurriculumDateTv.text = item.curriDesc
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemCurriculumProfileBinding = ItemCurriculumProfileBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
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