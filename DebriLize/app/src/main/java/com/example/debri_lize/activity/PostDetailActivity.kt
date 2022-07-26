package com.example.debri_lize.activity

import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.debri_lize.CommentRVAdapter
import com.example.debri_lize.R
import com.example.debri_lize.data.post.Comment
import com.example.debri_lize.data.post.CommentList
import com.example.debri_lize.databinding.ActivityPostDetailBinding
import com.example.debri_lize.response.PostDetail
import com.example.debri_lize.service.CommentService
import com.example.debri_lize.service.PostService
import com.example.debri_lize.utils.getUserIdx
import com.example.debri_lize.utils.getUserName
import com.example.debri_lize.view.post.CommentCreateView
import com.example.debri_lize.view.post.PostDetailView
import com.example.debri_lize.view.post.ShowCommentView
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlin.properties.Delegates


class PostDetailActivity : AppCompatActivity(), PostDetailView, CommentCreateView, ShowCommentView {
    lateinit var binding : ActivityPostDetailBinding
    var postIdx by Delegates.notNull<Int>()
    var authorIdx by Delegates.notNull<Int>()

    private lateinit var commentRVAdapter: CommentRVAdapter //Myadapter
    lateinit var parentItemArrayList: ArrayList<CommentList>
    lateinit var childItemArrayList: ArrayList<CommentList>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPostDetailBinding.inflate(layoutInflater) //binding 초기화
        setContentView(binding.root)

        //api - post
        val postService = PostService()
        postService.setPostDetailView(this)

        val intent = intent //전달할 데이터를 받을 Intent
        postIdx = intent.getIntExtra("postIdx", 0)
        binding.postDetailBoardNameTv.text = intent.getStringExtra("boardName")
        postService.showPostDetail(postIdx)

        //api - comment
        val commentService = CommentService()
        commentService.setShowCommentView(this)
        commentService.showComment(postIdx)

        //write comment <- enter
        binding.postDetailWriteCommentEt.setOnKeyListener { v, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                createComment()
                true
            }
            false
        }

    }

    //bottom sheet
    private fun bottomSheet(){

        lateinit var bottomSheetView : View
        val bottomSheetDialog = BottomSheetDialog(this)


        if(getUserIdx()==authorIdx){ //본인 글
            bottomSheetView = layoutInflater.inflate(R.layout.fragment_bottom_sheet_edit_delete, null)
            bottomSheetDialog.setContentView(bottomSheetView)
        }
        else{ //타인 글
            bottomSheetView = layoutInflater.inflate(R.layout.fragment_bottom_sheet_complain, null)
            bottomSheetDialog.setContentView(bottomSheetView)

            bottomSheetView.findViewById<TextView>(R.id.bottom_sheet_complain_tv).setOnClickListener {
                val bottomSheetComplainDetailView = layoutInflater.inflate(R.layout.fragment_bottom_sheet_complain_detail, null)
                val bottomSheetComplainDetailDialog = BottomSheetDialog(this)
                bottomSheetComplainDetailDialog.setContentView(bottomSheetComplainDetailView)

                bottomSheetComplainDetailDialog.show()

                //close button
                bottomSheetComplainDetailView.findViewById<TextView>(R.id.bottom_sheet_close_tv).setOnClickListener {
                    bottomSheetComplainDetailDialog.dismiss()
                }
            }
        }

        binding.postDetailAuthorMenuIv.setOnClickListener {
            bottomSheetDialog.show()
        }

        //close button
        bottomSheetView.findViewById<TextView>(R.id.bottom_sheet_close_tv).setOnClickListener {
            bottomSheetDialog.dismiss()
        }

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
                authorIdx = result.authorIdx

                //bottom sheet
                bottomSheet()
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

        return Comment(getUserIdx(), postIdx, commentContent, getUserName())
    }

    private fun createComment(){
        //댓글이 입력되지 않은 경우
        if(binding.postDetailWriteCommentEt.text.toString().isEmpty()){
            Toast.makeText(this, "댓글을 입력해주세요", Toast.LENGTH_SHORT).show()
            return
        }

        val commentService = CommentService()
        commentService.setCommentCreateView(this)
        commentService.createComment(getComment())

    }

    override fun onCommentCreateSuccess(code: Int) {
        when(code){
            200->Toast.makeText(this, "comment ok", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCommentCreateFailure(code: Int) {

    }

    override fun onShowCommentSuccess(code: Int, result: List<com.example.debri_lize.response.CommentList>
    ) {
        when(code){
            //개발할 때는 userIdx 저장이 필요할수도
            200-> {

                parentItemArrayList = ArrayList<CommentList>()
                childItemArrayList = ArrayList<CommentList>()
                var commentGroup by Delegates.notNull<Int>()

                for (i in result) {

                    if(i.commentLevel==0){
                        commentGroup = i.commentGroup
                        parentItemArrayList.add(CommentList(i.commentIdx, i.authorIdx, i.postIdx, i.commentLevel, i.commentOrder, i.commentGroup, i.commentContent, i.authorName))
                    }

                    //Child Item Object
                    for(j in result){
                        if (j.commentLevel==1 && j.commentGroup==commentGroup) {
                            childItemArrayList.add(CommentList(j.commentIdx, j.authorIdx, j.postIdx, j.commentLevel, j.commentOrder, j.commentGroup, j.commentContent, j.authorName))
                        }
                    }
                }

                binding.postDetailCommentRv.layoutManager =
                    LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
                commentRVAdapter = CommentRVAdapter().build(parentItemArrayList, childItemArrayList)
                binding.postDetailCommentRv.adapter = commentRVAdapter

                commentRVAdapter.notifyDataSetChanged()
            }
        }
    }

    override fun onShowCommentFailure(code: Int) {

    }

}