package com.example.debri_lize

import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.debri_lize.activity.PostDetailActivity
import com.example.debri_lize.data.post.Cocomment
import com.example.debri_lize.data.post.CommentList
import com.example.debri_lize.databinding.ActivityPostDetailBinding
import com.example.debri_lize.databinding.ItemCommentBinding
import com.example.debri_lize.service.CommentService
import com.example.debri_lize.utils.getUserIdx
import com.example.debri_lize.utils.getUserName
import com.example.debri_lize.view.post.CocommentCreateView
import com.example.debri_lize.view.post.CreateCommentLikeView
import com.example.debri_lize.view.post.DeleteCommentLikeView
import kotlin.properties.Delegates

class CommentRVAdapter(context: PostDetailActivity) : RecyclerView.Adapter<CommentRVAdapter.ViewHolder>(), CocommentCreateView,
    CreateCommentLikeView, DeleteCommentLikeView {
    private lateinit var cocommentRVAdapter: CocommentRVAdapter
    private lateinit var binding : ActivityPostDetailBinding

    var parentItemArrayList = ArrayList<CommentList>()
    var childItemArrayListGroup = ArrayList<ArrayList<CommentList>>()

    //activity
    var postIdx by Delegates.notNull<Int>()
    var activity = context

    val commentService = CommentService()

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
        val commentLikeNum : TextView = binding.itemCommentLikeTv

        fun bind(item: CommentList) {
            commentContent.text = item.commentContent
            authorName.text = item.authorName + " >"
            commentLikeNum.text = item.likeCount.toString()
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
            cocommentRVAdapter = CocommentRVAdapter(activity).build(childItemArrayListGroup[position])
            adapter = cocommentRVAdapter
        }

        holder.binding.itemCommentMenuIv.setOnClickListener{
            //bottom sheet
            Log.d("click menu", "click")
            activity.bottomSheetComment(parentItemArrayList[position].authorIdx, parentItemArrayList[position].commentIdx)
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


        commentService.setCreateCommentLikeView(this)
        commentService.setDeleteCommentLikeView(this)

        //댓글 좋아요
        holder.binding.itemCommentLikeIv.setOnClickListener {
            if(parentItemArrayList[position].likeStatus){
                holder.binding.itemCommentLikeIv.setImageResource(R.drawable.ic_comment_like_off)

                //api - delete comment like
//                commentService.deleteCommentLike(parentItemArrayList[position].commentIdx)
            }else{
                holder.binding.itemCommentLikeIv.setImageResource(R.drawable.ic_comment_like_on)

                //api - create comment like
//                commentService.createCommentLike(parentItemArrayList[position].commentIdx)
            }

            likeItemClickListener.onClick(it, position)
        }

    }

    // (2) 리스너 인터페이스
    interface OnLikeItemClickListener {
        fun onClick(v: View, position: Int)
    }
    // (3) 외부에서 클릭 시 이벤트 설정
    fun setLikeItemClickListener(onLikeItemClickListener: OnLikeItemClickListener) {
        this.likeItemClickListener = onLikeItemClickListener
    }
    // (4) setFavItemClickListener로 설정한 함수 실행
    private lateinit var likeItemClickListener : OnLikeItemClickListener



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

                val activity: PostDetailActivity = activity
                val commentService = CommentService()
                commentService.setShowCommentView(activity)
                commentService.showComment(postIdx)
                return
            }
        }
    }

    override fun onCocommentCreateFailure(code: Int) {

    }

    override fun onCreateCommentLikeSuccess(code: Int) {
        when(code){
            200->{
                commentService.showComment(postIdx)
            }
        }
    }

    override fun onCreateCommentLikeFailure(code: Int) {
        Log.d("createcommentlikefail","$code")
    }

    override fun onDeleteCommentLikeSuccess(code: Int) {
        when(code){
            200->{
                commentService.showComment(postIdx)
            }
        }
    }

    override fun onDeleteCommentLikeFailure(code: Int) {
        Log.d("deletecommentlikefail","$code")
    }


}