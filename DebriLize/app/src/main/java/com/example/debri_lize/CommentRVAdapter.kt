package com.example.debri_lize

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.debri_lize.data.post.Comment
import com.example.debri_lize.data.post.CommentList
import com.example.debri_lize.data.post.PostList
import com.example.debri_lize.databinding.ItemCommentBinding
import com.example.debri_lize.databinding.ItemPostBinding

class CommentRVAdapter : RecyclerView.Adapter<CommentRVAdapter.ViewHolder>() {

    var datas = mutableListOf<CommentList>()

    inner class ViewHolder(val binding : ItemCommentBinding) : RecyclerView.ViewHolder(binding.root){

        val commentContent : TextView = binding.itemCommentTv
        val authorName : TextView = binding.itemCommentProfileTv

        fun bind(item: CommentList) {
            commentContent.text = item.commentContent
            authorName.text = item.authorName
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemCommentBinding = ItemCommentBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(datas[position])
    }

    override fun getItemCount(): Int = datas.size

}