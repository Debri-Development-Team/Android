package com.example.debri_lize.adapter.start

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.debri_lize.R
import com.example.debri_lize.data.curriculum.Curriculum
import com.example.debri_lize.data.curriculum.Review
import com.example.debri_lize.databinding.ItemCurriculumBinding
import com.example.debri_lize.databinding.ItemReviewBinding

class ReviewRVAdapter : RecyclerView.Adapter<ReviewRVAdapter.ViewHolder>() {

    var datas = mutableListOf<Review>()

    inner class ViewHolder(val binding : ItemReviewBinding) : RecyclerView.ViewHolder(binding.root){

        val content : TextView = binding.itemReviewContentTv
        val authorName : TextView = binding.itemReviewAuthorTv

        fun bind(item: Review) {
            content.text = item.content
            authorName.text = item.authorName
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemReviewBinding = ItemReviewBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
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