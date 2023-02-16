package com.debri_main.debri.adapter.start

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.debri_main.debri.data.curriculum.Review
import com.debri_main.debri.databinding.ItemReviewBinding

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