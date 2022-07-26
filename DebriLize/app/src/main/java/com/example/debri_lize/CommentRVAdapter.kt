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
    var childItemArrayList = ArrayList<CommentList>()

    fun build(parent: ArrayList<CommentList>, chile : ArrayList<CommentList>): CommentRVAdapter {
        parentItemArrayList = parent
        childItemArrayList = chile
        return this
    }

    inner class ViewHolder(val binding : ItemCommentBinding) : RecyclerView.ViewHolder(binding.root){

        val commentContent : TextView = binding.itemCommentTv
        val authorName : TextView = binding.itemCommentProfileTv

        fun bind(item: CommentList) {
            commentContent.text = item.commentContent
            authorName.text = item.authorName

            binding.itemCommentCocommentRv.apply {
                layoutManager =
                    LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                cocommentRVAdapter = CocommentRVAdapter().build(childItemArrayList)
                adapter = cocommentRVAdapter
            }
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemCommentBinding = ItemCommentBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(parentItemArrayList[position])

        holder.binding.itemCommentMenuIv.setOnClickListener{
            //bottom sheet
            //bottomSheet()
        }

    }

    override fun getItemCount(): Int = parentItemArrayList.size

}