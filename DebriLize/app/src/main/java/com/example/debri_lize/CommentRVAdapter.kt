package com.example.debri_lize

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.debri_lize.activity.MainActivity
import com.example.debri_lize.activity.PostDetailActivity
import com.example.debri_lize.data.post.Cocomment
import com.example.debri_lize.data.post.CommentList
import com.example.debri_lize.data.post.PostDetail
import com.example.debri_lize.databinding.ActivityPostDetailBinding
import com.example.debri_lize.databinding.ItemCommentBinding
import com.example.debri_lize.service.CommentService
import com.example.debri_lize.utils.getUserIdx
import com.example.debri_lize.utils.getUserName
import com.example.debri_lize.view.post.CocommentCreateView
import kotlin.properties.Delegates

class CommentRVAdapter(context: PostDetailActivity) : RecyclerView.Adapter<CommentRVAdapter.ViewHolder>(), CocommentCreateView {
    private lateinit var cocommentRVAdapter: CocommentRVAdapter
    private lateinit var binding : ActivityPostDetailBinding

    var parentItemArrayList = ArrayList<CommentList>()
    var childItemArrayListGroup = ArrayList<ArrayList<CommentList>>()

    //activity
    var postIdx by Delegates.notNull<Int>()
    var context = context

    fun build(parent: ArrayList<CommentList>, child : ArrayList<ArrayList<CommentList>>, binding: ActivityPostDetailBinding, postIdx : Int): CommentRVAdapter {
        parentItemArrayList = parent
        Log.d("parentItemArrayList", parentItemArrayList.toString())
        childItemArrayListGroup = child
        this.binding = binding
        this.postIdx = postIdx
        notifyDataSetChanged()
        return this
    }

    inner class ViewHolder(val binding : ItemCommentBinding) : RecyclerView.ViewHolder(binding.root){

        val commentContent : TextView = binding.itemCommentTv
        val authorName : TextView = binding.itemCommentProfileTv

        fun bind(item: CommentList) {
            commentContent.text = item.commentContent
            authorName.text = item.authorName
            Log.d("itemCommentList", item.toString())
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

        //write cocomment

        holder.binding.itemCommentWriteIv.setOnClickListener {
            Log.d("click write cocomment", "click")
            //on cocomment editText
            binding.postDetailWriteCommentEt.visibility = View.GONE
            binding.postDetailWriteCocommentEt.visibility = View.VISIBLE
            Log.d("click comment", binding.postDetailWriteCommentEt.visibility.toString())
            Log.d("click cocomment", binding.postDetailWriteCocommentEt.visibility.toString())
            binding.postDetailWriteCocommentEt.setOnKeyListener { v, keyCode, event ->
                if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                    createCocomment(
                        parentItemArrayList[position].commentIdx,
                        parentItemArrayList[position].postIdx
                    )
                    true
                }
                false
            }
            Log.d("commentIdx", parentItemArrayList[position].commentIdx.toString())
            Log.d("postIdx", parentItemArrayList[position].postIdx.toString())
        }

    }

    override fun getItemCount(): Int = parentItemArrayList.size

    private fun getCocomment(commentIdx : Int, postIdx : Int) : Cocomment {
        val cocommentContent : String = binding.postDetailWriteCocommentEt.text.toString()

        return Cocomment(getUserIdx(), postIdx,commentIdx, cocommentContent, getUserName())
    }

    private fun createCocomment(commentIdx : Int, postIdx : Int){
        //대댓글이 입력되지 않은 경우
        if(binding.postDetailWriteCocommentEt.text.toString().isEmpty()){
            //Toast.makeText(this, "대댓글을 입력해주세요", Toast.LENGTH_SHORT).show()
            return
        }

        val commentService = CommentService()
        commentService.setCocommentCreateView(this)
        commentService.createCocomment(getCocomment(commentIdx, postIdx))
        Log.d("createCocomment", getCocomment(commentIdx, postIdx).toString())
        return

    }


    override fun onCocommentCreateSuccess(code: Int) {
        when(code){
            200->{
                binding.postDetailWriteCocommentEt.text.clear()
                binding.postDetailWriteCommentEt.visibility = View.VISIBLE
                binding.postDetailWriteCocommentEt.visibility = View.GONE

                val activity: PostDetailActivity = context
                val commentService = CommentService()
                commentService.setShowCommentView(activity)
                commentService.showComment(postIdx)
                return
            }
        }
    }

    override fun onCocommentCreateFailure(code: Int) {

    }

}