package com.example.debri_lize.activity

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.debri_lize.CommentRVAdapter
import com.example.debri_lize.R
import com.example.debri_lize.data.post.Comment
import com.example.debri_lize.data.post.CommentList
import com.example.debri_lize.response.PostDetail
import com.example.debri_lize.service.PostService
import com.example.debri_lize.view.post.PostDetailView
import com.example.debri_lize.databinding.ActivityPostDetailBinding
import com.example.debri_lize.service.CommentService
import com.example.debri_lize.utils.getUserIdx
import com.example.debri_lize.view.post.CommentCreateView
import com.example.debri_lize.view.post.ShowCommentView
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlin.properties.Delegates


class PostDetailActivity : AppCompatActivity(), PostDetailView, CommentCreateView, ShowCommentView {
    lateinit var binding : ActivityPostDetailBinding
    var postIdx by Delegates.notNull<Int>()

    private lateinit var commentRVAdapter: CommentRVAdapter
    private val comments = ArrayList<CommentList>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPostDetailBinding.inflate(layoutInflater) //binding 초기화
        setContentView(binding.root)

        //bottom sheet complain
        val bottomSheetView = layoutInflater.inflate(com.example.debri_lize.R.layout.fragment_bottom_sheet_complain, null)
        val bottomSheetDialog = BottomSheetDialog(this)
        bottomSheetDialog.setContentView(bottomSheetView)

        binding.postDetailComplainTv.setOnClickListener {
            bottomSheetDialog.show()
        }

        bottomSheetView.findViewById<TextView>(R.id.bottom_sheet_close_tv).setOnClickListener {
            bottomSheetDialog.dismiss()
        }


        //api - post
        val postService = PostService()
        postService.setPostDetailView(this)

        val intent = intent //전달할 데이터를 받을 Intent
        postIdx = intent.getIntExtra("postIdx", 0)
        postService.showPostDetail(postIdx)

        //api - comment
        val commentService = CommentService()
        commentService.setShowCommentView(this)
        commentService.showComment(postIdx)

        //write comment - enter키로 변경할 것
        binding.postDetailWriteCommentIv.setOnClickListener{
            createComment()
        }
//        binding.postDetailWriteCommentEt.setOnEditorActionListener { textView, action, event ->
//            var handled = false
//            if (action == EditorInfo.IME_ACTION_DONE) {
//                createComment()
//                //binding.postDetailWriteCommentEt.text = null
//                handled = true
//            }
//            handled
//        }

    }

    //api
    //postDetail
    override fun onPostDetailSuccess(code: Int, result: PostDetail) {
        when(code){
            200->{
                binding.postDetailTitleTv.text = result.postName
                binding.postDetailTimeTv.text = result.timeAfterCreated.toString()+"분 전"
                binding.postDetailAuthorTv.text = result.authorName
                binding.postDetailContentTv.text = result.postContents
                binding.postDetailCountCommentTv.text = "("+result.commentCnt.toString()+")"
            }
        }
    }

    override fun onPostDetailFailure(code: Int) {
        Log.d("postdetail", "ohohoho")
    }


    //comment
    //사용자가 입력한 값 가져오기
    private fun getComment() : Comment {
        val commentContent : String = binding.postDetailWriteCommentEt.text.toString()
        var authorName : String = binding.postDetailAuthorTv.text.toString()

        return Comment(getUserIdx(), postIdx, commentContent, authorName)
    }

    private fun createComment(){
        //댓글이 입력되지 않은 경우
        if(binding.postDetailWriteCommentEt.text.toString().isEmpty()){
            Toast.makeText(this, "댓글을 입력해주세요", Toast.LENGTH_SHORT).show()
            return
        }

        val commentService = CommentService()
        commentService.setCommentCreateView(this)
        //만든 API 호출
        commentService.createComment(getComment())

    }

    override fun onCommentCreateSuccess(code: Int) {
        when(code){
            200->Toast.makeText(this, "comment ok", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCommentCreateFailure(code: Int) {

    }

    //show comment
    override fun onShowCommentSuccess(code: Int, result: List<com.example.debri_lize.response.Comment>) {
        when(code){
            200->{
                binding.postDetailCommentRv.layoutManager =
                    LinearLayoutManager(this@PostDetailActivity, LinearLayoutManager.VERTICAL, false)
                commentRVAdapter = CommentRVAdapter()
                binding.postDetailCommentRv.adapter = commentRVAdapter

                //data
                comments.apply {
                    Log.d("commentSize", result.size.toString())
                    for (i in result){
                        //댓글일 경우
                        if(i.commentLevel == 0){
                            comments.add(CommentList(i.commentIdx, i.authorIdx, i.postIdx, i.commentLevel, i.commentOrder, i.commentGroup, i.commentContent, i.authorName))
                        }
                        
                    }
                    commentRVAdapter.datas = comments
                    commentRVAdapter.notifyDataSetChanged()
                }
            }
        }
    }

    override fun onShowCommentFailure(code: Int) {

    }
}