package com.example.debri_lize

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.debri_lize.data.post.CommentList
import com.example.debri_lize.databinding.ItemCommentBinding

class CommentRVAdapter : RecyclerView.Adapter<CommentRVAdapter.ViewHolder>() {
    private lateinit var cocommentRVAdapter: CocommentRVAdapter

    var parentItemArrayList = ArrayList<CommentList>()
    var childItemArrayListGroup = ArrayList<ArrayList<CommentList>>()

    fun build(parent: ArrayList<CommentList>, child : ArrayList<ArrayList<CommentList>>): CommentRVAdapter {
        parentItemArrayList = parent
        childItemArrayListGroup = child
        return this
    }

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
        holder.bind(parentItemArrayList[position])

        holder.binding.itemCommentCocommentRv.apply {
            layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            cocommentRVAdapter = CocommentRVAdapter().build(childItemArrayListGroup[position])
            adapter = cocommentRVAdapter
        }

        holder.binding.itemCommentMenuIv.setOnClickListener{
            //bottom sheet
            //bottomSheet()
        }

    }

    override fun getItemCount(): Int = parentItemArrayList.size

}