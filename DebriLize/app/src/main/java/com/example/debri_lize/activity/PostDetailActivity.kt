package com.example.debri_lize.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.debri_lize.CommentRVAdapter
import android.view.Gravity
import androidx.core.content.ContextCompat
import com.example.debri_lize.CustomDialog
import com.example.debri_lize.R
import com.example.debri_lize.data.post.*
import com.example.debri_lize.databinding.ActivityPostDetailBinding
import com.example.debri_lize.databinding.ItemCommentBinding
import com.example.debri_lize.response.PostDetail
import com.example.debri_lize.service.CommentService
import com.example.debri_lize.service.PostService
import com.example.debri_lize.utils.*
import com.example.debri_lize.view.post.*
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlin.properties.Delegates


class PostDetailActivity : AppCompatActivity(), PostDetailView, CommentCreateView, ShowCommentView, DeletePostView,
    CreatePostLikeView, CancelPostLikeView, CreatePostScrapView, CancelPostScrapView {
    lateinit var binding : ActivityPostDetailBinding

    var postIdx by Delegates.notNull<Int>()
    var authorIdx by Delegates.notNull<Int>()

    //data
    lateinit var postDetail : com.example.debri_lize.data.post.PostDetail

    //api
    val postService = PostService()

    //comment
    private lateinit var commentRVAdapter: CommentRVAdapter //Myadapter
    lateinit var parentItemArrayList: ArrayList<CommentList>
    lateinit var childItemArrayList: ArrayList<CommentList>

    //like, scrap
    private var likeTF : Boolean = false
    private var scrapTF : Boolean = false

    private var reportOK : Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPostDetailBinding.inflate(layoutInflater) //binding 초기화
        setContentView(binding.root)

        //api - post
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

        //postdetail 새로 들어와도 likeStatus 상태 저장
        Log.d("likestatus", likeTF.toString())
        if(getLikeStatus()?.likeStatus =="LIKE" && getLikeStatus()?.postIdx == getPostIdx()){
            likeTF = true
            Log.d("likestatus","${getLikeStatus()}")
            binding.postDetailMenuLikeLayout.setBackgroundResource(R.drawable.border_round_debri_darkmode_10)
            binding.postDetailMenuLikeIv.setImageResource(R.drawable.ic_like_debri)
        }else{
            likeTF = false
            binding.postDetailMenuLikeLayout.setBackgroundResource(R.drawable.border_round_white_transparent_10)
            binding.postDetailMenuLikeIv.setImageResource(R.drawable.ic_like_white)
        }

        //추천 버튼
        binding.postDetailMenuLikeLayout.setOnClickListener {
            likeTF = !likeTF
            if(likeTF) {
                binding.postDetailMenuLikeLayout.setBackgroundResource(R.drawable.border_round_debri_darkmode_10)
                binding.postDetailMenuLikeIv.setImageResource(R.drawable.ic_like_debri)

                saveLikeStatus("LIKE", getPostIdx()!!, getUserIdx()!!)

                //api - createPostLike
                postService.setCreatePostLikeView(this)
                postService.createPostLike(getLikePost())

            }else {
                binding.postDetailMenuLikeLayout.setBackgroundResource(R.drawable.border_round_white_transparent_10)
                binding.postDetailMenuLikeIv.setImageResource(R.drawable.ic_like_white)

                saveLikeStatus("UNLIKE", getPostIdx()!!, getUserIdx()!!)

                //api - cancelPostLike
                postService.setCancelPostLikeView(this)
                postService.cancelPostLike(getLikePostCancel())
            }

        }


        //스크랩 버튼
        binding.postDetailMenuScrapLayout.setOnClickListener {
            scrapTF = !scrapTF
            if(scrapTF){
                binding.postDetailMenuScrapLayout.setBackgroundResource(R.drawable.border_round_transparent_debri_10)
                binding.postDetailMenuScrapTv.setTextColor(ContextCompat.getColor(this@PostDetailActivity, R.color.darkmode_background))
                binding.postDetailMenuScrapIv.setImageResource(R.drawable.ic_scrap_darkmode)

                //api - createPostScrap
                postService.setCreatePostScrapView(this)
                postService.createPostScrap(getPostIdx()!!)

                //스크랩 확인 토스트메시지
                var scrapToast = layoutInflater.inflate(R.layout.toast_scrap,null)
                var toast = Toast(this)
                toast.view = scrapToast
                toast.setGravity(Gravity.CENTER_HORIZONTAL,0,0)
                toast.show()
            }else{
                binding.postDetailMenuScrapLayout.setBackgroundResource(R.drawable.border_round_white_transparent_10)
                binding.postDetailMenuScrapTv.setTextColor(ContextCompat.getColor(this@PostDetailActivity, R.color.white))
                binding.postDetailMenuScrapIv.setImageResource(R.drawable.ic_scrap_white)

                //api - cancelPostScrap
                postService.setCancelPostScrapView(this)
                postService.cancelPostScrap(getPostIdx()!!)
            }

        }

    }

    //bottom sheet
    private fun bottomSheet(){

        lateinit var bottomSheetView : View
        val bottomSheetDialog = BottomSheetDialog(this)

        if(getUserIdx()==authorIdx){ //본인 글
            bottomSheetView = layoutInflater.inflate(R.layout.fragment_bottom_sheet_two, null)
            bottomSheetDialog.setContentView(bottomSheetView)

            bottomSheetView.findViewById<TextView>(R.id.bottom_sheet_two_tv).text = "게시물 관리"
            bottomSheetView.findViewById<TextView>(R.id.bottom_sheet_two_tv1).text = "수정하기"
            bottomSheetView.findViewById<TextView>(R.id.bottom_sheet_two_tv2).text = "삭제하기"

            //click edit button
            bottomSheetView.findViewById<TextView>(R.id.bottom_sheet_two_tv1).setOnClickListener {
                //PostCreateActivity에 값 전달
                val intent = Intent(this, PostCreateActivity::class.java)
                intent.putExtra("postDetail", postDetail)
                startActivity(intent)
                bottomSheetDialog.dismiss()
                finish()
            }
            //click delete button
            bottomSheetView.findViewById<TextView>(R.id.bottom_sheet_two_tv2).setOnClickListener {
                postService.deletePost(postIdx)
                bottomSheetDialog.dismiss()
                //add dialog code


                finish()
            }
        }
        else{ //타인 글
            bottomSheetView = layoutInflater.inflate(R.layout.fragment_bottom_sheet_complain, null)
            bottomSheetDialog.setContentView(bottomSheetView)

            //click complain button
            bottomSheetView.findViewById<TextView>(R.id.bottom_sheet_complain_tv).setOnClickListener {
                val bottomSheetComplainDetailView = layoutInflater.inflate(R.layout.fragment_bottom_sheet_complain_detail, null)
                val bottomSheetComplainDetailDialog = BottomSheetDialog(this)
                bottomSheetComplainDetailDialog.setContentView(bottomSheetComplainDetailView)

                bottomSheetComplainDetailDialog.show()

                //complain button
                //광고,스팸
                bottomSheetComplainDetailDialog.findViewById<TextView>(R.id.bottom_sheet_complain_page_tv1)!!
                    .setOnClickListener {
                        //토스트메세지 띄우기
                        var reportToast = layoutInflater.inflate(R.layout.toast_report,null)
                        var toast = Toast(this)
                        toast.view = reportToast
                        toast.setGravity(Gravity.CENTER_HORIZONTAL,0,0)
                        toast.show()

                        //다이얼로그 닫기
                        bottomSheetComplainDetailDialog.dismiss()
                        bottomSheetDialog.dismiss()
                    }
                //낚시,도배
                bottomSheetComplainDetailDialog.findViewById<TextView>(R.id.bottom_sheet_complain_page_tv2)!!
                    .setOnClickListener {
                        //토스트메세지 띄우기
                        var reportToast = layoutInflater.inflate(R.layout.toast_report,null)
                        var toast = Toast(this)
                        toast.view = reportToast
                        toast.setGravity(Gravity.CENTER_HORIZONTAL,0,0)
                        toast.show()

                        //다이얼로그 닫기
                        bottomSheetComplainDetailDialog.dismiss()
                        bottomSheetDialog.dismiss()
                    }
                //개발과 무관한 게시물
                bottomSheetComplainDetailDialog.findViewById<TextView>(R.id.bottom_sheet_complain_page_tv3)!!
                    .setOnClickListener {
                        //토스트메세지 띄우기
                        var reportToast = layoutInflater.inflate(R.layout.toast_report,null)
                        var toast = Toast(this)
                        toast.view = reportToast
                        toast.setGravity(Gravity.CENTER_HORIZONTAL,0,0)
                        toast.show()

                        //다이얼로그 닫기
                        bottomSheetComplainDetailDialog.dismiss()
                        bottomSheetDialog.dismiss()
                    }
                //욕설,비하
                bottomSheetComplainDetailDialog.findViewById<TextView>(R.id.bottom_sheet_complain_page_tv4)!!
                    .setOnClickListener {
                        //토스트메세지 띄우기
                        var reportToast = layoutInflater.inflate(R.layout.toast_report,null)
                        var toast = Toast(this)
                        toast.view = reportToast
                        toast.setGravity(Gravity.CENTER_HORIZONTAL,0,0)
                        toast.show()

                        //다이얼로그 닫기
                        bottomSheetComplainDetailDialog.dismiss()
                        bottomSheetDialog.dismiss()
                    }
                //기타
                bottomSheetComplainDetailDialog.findViewById<TextView>(R.id.bottom_sheet_complain_page_tv5)!!
                    .setOnClickListener {

                        //신고 사유 다이얼로그
                        val dialog = CustomDialog(this)
                        dialog.showReportDlg()
                        //사유 적은 후 ok 버튼 클릭 시
                        dialog.setOnClickListener(object:CustomDialog.ButtonClickListener{
                            override fun onClicked(TF: Boolean) {
                                reportOK = true
                                //텍스트 받아 넘기기


                                finish()
                            }

                        })

                        //토스트메세지 띄우기
                        if(reportOK){
                            var reportToast = layoutInflater.inflate(R.layout.toast_report,null)
                            var toast = Toast(this)
                            toast.view = reportToast
                            toast.setGravity(Gravity.CENTER_HORIZONTAL,0,0)
                            toast.show()

                            //bottom 다이얼로그 닫기
                            bottomSheetComplainDetailDialog.dismiss()
                            bottomSheetDialog.dismiss()
                        }

                    }

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
                Log.d("PostDetailresult","$result")
                postDetail = com.example.debri_lize.data.post.PostDetail(result.boardIdx, result.postIdx, result.authorIdx, result.authorName, result.postName, result.likeCnt,
                    result.likeStatus.toString(), result.scrapStatus.toString(), result.commentCnt, result.timeAfterCreated, result.postContents)
                binding.postDetailTitleTv.text = result.postName
                binding.postDetailTimeTv.text = result.timeAfterCreated.toString()+"분 전"
                binding.postDetailAuthorTv.text = result.authorName
                binding.postDetailContentTv.text = result.postContents
                binding.postDetailCountCommentTv.text = "("+result.commentCnt.toString()+")"
                authorIdx = result.authorIdx

                if(result.likeCnt < 100)
                    binding.postDetailMenuLikeNumTv.text = result.likeCnt.toString()
                else
                    binding.postDetailMenuLikeNumTv.text = "99+"


                //bottom sheet
                bottomSheet()
                postService.setDeletePostView(this@PostDetailActivity)
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
                var childItemArrayListGroup = ArrayList<ArrayList<CommentList>>()
                var commentGroup by Delegates.notNull<Int>()

                for (i in result) {
                    childItemArrayList = ArrayList<CommentList>()

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

                    childItemArrayListGroup.add(childItemArrayList)
                }
                Log.d("parent", parentItemArrayList.toString())
                Log.d("child", childItemArrayListGroup.toString())

                binding.postDetailCommentRv.layoutManager =
                    LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
                commentRVAdapter = CommentRVAdapter().build(parentItemArrayList, childItemArrayListGroup, binding)
                binding.postDetailCommentRv.adapter = commentRVAdapter

                commentRVAdapter.notifyDataSetChanged()
            }
        }
    }

    override fun onShowCommentFailure(code: Int) {

    }

    override fun onDeletePostSuccess(code: Int) {
        when(code){
            200->{
                //게시글 삭제 성공 시 코드
            }
        }
    }

    override fun onDeletePostFailure(code: Int) {

    }

    private fun getLikePost() : PostLikeCreate {
        val postIdx = getPostIdx()
        val userIdx = getUserIdx() //변경필요
        val likeStatus = "LIKE"
        return PostLikeCreate(postIdx, userIdx, likeStatus)
    }

    override fun onCreatePostLikeSuccess(code: Int) {
        when(code){
            200->{
                //좋아요 생성 성공
                postDetail.likeCnt++
                Log.d("postdetaillike","$postDetail")
                if(postDetail.likeCnt < 100)
                    binding.postDetailMenuLikeNumTv.text = postDetail.likeCnt.toString()
                else
                    binding.postDetailMenuLikeNumTv.text = "99+"

            }
        }
    }

    override fun onCreatePostLikeFailure(code: Int) {
        Log.d("postlikefail","$code")
    }

    private fun getLikePostCancel() : PostLikeCancel {
        val postIdx = getPostIdx()
        val userIdx = getUserIdx() //변경필요
        return PostLikeCancel(postIdx, userIdx)
    }

    override fun onCancelPostLikeSuccess(code: Int) {
        when(code){
            200->{
                //좋아요 취소 성공
                postDetail.likeCnt--
                Log.d("postdetaillike","$postDetail")
                if(postDetail.likeCnt < 100)
                    binding.postDetailMenuLikeNumTv.text = postDetail.likeCnt.toString()
                else
                    binding.postDetailMenuLikeNumTv.text = "99+"

            }
        }
    }

    override fun onCancelPostLikeFailure(code: Int) {
        Log.d("postlikecancelfail","$code")
    }

    override fun onCreatePostScrapSuccess(code: Int) {
        when(code){
            200 -> {

            }
        }
    }

    override fun onCreatePostScrapFailure(code: Int) {
        Log.d("postscrapcreatefail","$code")
    }

    override fun onCancelPostScrapSuccess(code: Int) {
        when(code){
            200 -> {

            }
        }
    }

    override fun onCancelPostScrapFailure(code: Int) {
        Log.d("postscrapcancelfail","$code")
    }




}