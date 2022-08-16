package com.example.debri_lize.adapter.class_

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.debri_lize.R
import com.example.debri_lize.data.curriculum.Review
import com.example.debri_lize.databinding.ItemLectureReviewBinding

class LectureReviewRVAdapter() : RecyclerView.Adapter<LectureReviewRVAdapter.ViewHolder>() {
    var datas = mutableListOf<Review>()

    inner class ViewHolder(val binding : ItemLectureReviewBinding) : RecyclerView.ViewHolder(binding.root){

        val content : TextView = binding.itemLectureReviewContentTv
        val authorName : TextView = binding.itemLectureReviewAuthorTv


        fun bind(item: Review) {
            content.text = item.content
            authorName.text = item.authorName
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): LectureReviewRVAdapter.ViewHolder {
        val binding: ItemLectureReviewBinding = ItemLectureReviewBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(datas[position])
    }

    override fun getItemCount(): Int = datas.size

}