package com.debri_main.debri.adapter.class_

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.debri_main.debri.data.curriculum.Review
import com.debri_main.debri.databinding.ItemLectureReviewBinding

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