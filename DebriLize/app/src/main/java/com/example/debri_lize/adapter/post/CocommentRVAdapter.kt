package com.example.debri_lize.adapter.post

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.debri_lize.R
import com.example.debri_lize.activity.PostDetailActivity
import com.example.debri_lize.data.post.CommentList
import com.example.debri_lize.databinding.ItemCocommentBinding
import com.example.debri_lize.service.CommentService
import com.example.debri_lize.view.post.CreateCommentLikeView
import com.example.debri_lize.view.post.DeleteCommentLikeView
import kotlin.properties.Delegates

class CocommentRVAdapter(context: PostDetailActivity) : RecyclerView.Adapter<CocommentRVAdapter.ViewHolder>(),
    CreateCommentLikeView, DeleteCommentLikeView {

    var childItemArrayList = ArrayList<CommentList>()
    var activity = context
    val commentService = CommentService()

    var postIdx by Delegates.notNull<Int>()

    fun build(child : ArrayList<CommentList>): CocommentRVAdapter {

        childItemArrayList = child
        return this
    }

    inner class ViewHolder(val binding : ItemCocommentBinding) : RecyclerView.ViewHolder(binding.root){

        val cocommentContent : TextView = binding.itemCocommentTv
        val authorName : TextView = binding.itemCocommentProfileTv
        val time : TextView = binding.itemCocommentTimeTv
        val cocommentLikeNum : TextView = binding.itemCocommentLikeTv

        fun bind(item: CommentList) {
            cocommentContent.text = item.commentContent
            authorName.text = item.authorName + " >"
            cocommentLikeNum.text = item.likeCount.toString()

            if(item.timeAfterCreated == 0){
                time.text = "방금 전"
            }else if (item.timeAfterCreated < 60) {
                time.text = item.timeAfterCreated.toString() + "분 전"
            }else if (item.timeAfterCreated < 1440){
                var hour = item.timeAfterCreated/60
                time.text = hour.toString() + "시간 전"
            }else if (item.timeAfterCreated < 43200){
                var day = item.timeAfterCreated/1440
                time.text = day.toString() + "일 전"
            }else if (item.timeAfterCreated < 525600){
                var month = item.timeAfterCreated/43200
                time.text = month.toString() + "달 전"
            }else{
                var year = item.timeAfterCreated/525600
                time.text = year.toString() + "년 전"
            }

            if(item.likeStatus)  binding.itemCocommentLikeIv.setImageResource(R.drawable.ic_comment_like_on)
            else binding.itemCocommentLikeIv.setImageResource(R.drawable.ic_comment_like_off)

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

        commentService.setCreateCommentLikeView(this)
        commentService.setDeleteCommentLikeView(this)

        //like cocomment
        holder.binding.itemCocommentLikeIv.setOnClickListener {
            postIdx = childItemArrayList[position].postIdx
            if(childItemArrayList[position].likeStatus){
                holder.binding.itemCocommentLikeIv.setImageResource(R.drawable.ic_comment_like_off)
                //api - delete like
                commentService.deleteCommentLike(childItemArrayList[position].commentIdx)
            }else{
                holder.binding.itemCocommentLikeIv.setImageResource(R.drawable.ic_comment_like_on)
                //api - create like
                commentService.createCommentLike(childItemArrayList[position].commentIdx)
            }

        }
    }


    override fun getItemCount(): Int = childItemArrayList.size

    override fun onCreateCommentLikeSuccess(code: Int) {
        when(code){
            200->{
                val activity: PostDetailActivity = activity
                val commentService = CommentService()
                commentService.setShowCommentView(activity)
                commentService.showComment(postIdx)
            }
        }
    }

    override fun onCreateCommentLikeFailure(code: Int) {

    }

    override fun onDeleteCommentLikeSuccess(code: Int) {
        when(code){
            200->{
                val activity: PostDetailActivity = activity
                val commentService = CommentService()
                commentService.setShowCommentView(activity)
                commentService.showComment(postIdx)
            }
        }
    }

    override fun onDeleteCommentLikeFailure(code: Int) {

    }

}