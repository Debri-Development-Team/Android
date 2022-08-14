package com.example.debri_lize.adapter.start

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.debri_lize.R
import com.example.debri_lize.activity.AddCurriculumDetailActivity
import com.example.debri_lize.data.curriculum.Curriculum
import com.example.debri_lize.data.curriculum.Review
import com.example.debri_lize.databinding.ItemCurriculumBinding
import com.example.debri_lize.databinding.ItemReviewBinding

class ReviewRVAdapter() : RecyclerView.Adapter<ReviewRVAdapter.ViewHolder>() {

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
    }

    //
    override fun getItemCount(): Int = datas.size


}