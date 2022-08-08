package com.example.debri_lize

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.debri_lize.activity.PostDetailActivity
import com.example.debri_lize.data.post.CommentList
import com.example.debri_lize.databinding.ItemCocommentBinding
import com.example.debri_lize.databinding.ItemCommentBinding

class CocommentRVAdapter(context: PostDetailActivity) : RecyclerView.Adapter<CocommentRVAdapter.ViewHolder>() {

    var childItemArrayList = ArrayList<CommentList>()
    var activity = context

    fun build(child : ArrayList<CommentList>): CocommentRVAdapter {

        childItemArrayList = child
        return this
    }

    inner class ViewHolder(val binding : ItemCocommentBinding) : RecyclerView.ViewHolder(binding.root){

        val cocommentContent : TextView = binding.itemCocommentTv
        val authorName : TextView = binding.itemCocommentProfileTv
        val time : TextView = binding.itemCocommentTimeTv //연결필요

        fun bind(item: CommentList) {
            cocommentContent.text = item.commentContent
            authorName.text = item.authorName + " >"
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemCocommentBinding = ItemCocommentBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(childItemArrayList[position])

        holder.binding.itemCocommentMenuIv.setOnClickListener{
            //bottom sheet
            activity.bottomSheetComment(childItemArrayList[position].authorIdx, childItemArrayList[position].commentIdx)
        }
    }

    override fun getItemCount(): Int = childItemArrayList.size

}