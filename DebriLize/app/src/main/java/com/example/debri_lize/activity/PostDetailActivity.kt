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
import com.example.debri_lize.adapter.post.CommentRVAdapter
import android.view.Gravity
import android.view.MotionEvent
import androidx.core.content.ContextCompat
import com.example.debri_lize.CustomDialog
import com.example.debri_lize.R
import com.example.debri_lize.data.post.*
import com.example.debri_lize.databinding.ActivityPostDetailBinding
import com.example.debri_lize.service.CommentService
import com.example.debri_lize.service.PostService
import com.example.debri_lize.service.ReportService
import com.example.debri_lize.utils.*
import com.example.debri_lize.view.post.*
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlin.properties.Delegates


class PostDetailActivity : AppCompatActivity(), PostDetailView, CommentCreateView, ShowCommentView, DeletePostView,
    CreatePostLikeView, CancelPostLikeView, CreatePostScrapView, CancelPostScrapView, ReportPostView, DeleteCommentView, ReportCommentView,
    ReportUserView {
    lateinit var binding : ActivityPostDetailBinding

    var postIdx by Delegates.notNull<Int>()
    var authorIdx by Delegates.notNull<Int>()
    var commentIdx by Delegates.notNull<Int>()

    //data
    lateinit var postDetail : PostDetail

    //api
    val postService = PostService()
    val commentService = CommentService()
    val reportService = ReportService()

    //comment
    private lateinit var commentRVAdapter: CommentRVAdapter //Myadapter
    lateinit var parentItemArrayList: ArrayList<CommentList>
    lateinit var childItemArrayList: ArrayList<CommentList>
    private var temp = ArrayList<CommentList>()

    //like, scrap
    var likeTF by Delegates.notNull<Boolean>()
    private var scrapTF : Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPostDetailBinding.inflate(layoutInflater) //binding 초기화
        setContentView(binding.root)

        //api - post
        postService.setPostDetailView(this)

        val intent = intent //전달할 데이터를 받을 Intent
        postIdx = intent.getIntExtra("postIdx", 0)
        var boardName = intent.getStringExtra("boardName")
        binding.postDetailBoardNameTv.text = boardName
        postService.showPostDetail(postIdx)

        //api - comment
        val commentService = CommentService()
        commentService.setShowCommentView(this)
        commentService.showComment(postIdx)

        reportService.setReportUserView(this)

        //write comment <- enter
        binding.postDetailWriteCommentEt.setOnKeyListener { v, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                createComment()
                true
            }
            false
        }


        //추천 버튼
        binding.postDetailMenuLikeLayout.setOnClickListener {
            if(!likeTF) {
                //api - createPostLike
                postService.setCreatePostLikeView(this)
                postService.createPostLike(getLikePost())

            }else {
                //api - cancelPostLike
                postService.setCancelPostLikeView(this)
                postService.cancelPostLike(getLikePostCancel())
            }
        }


        //스크랩 버튼
        binding.postDetailMenuScrapLayout.setOnClickListener {
            if(!scrapTF){
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
                //api - cancelPostScrap
                postService.setCancelPostScrapView(this)
                postService.cancelPostScrap(getPostIdx()!!)
            }

        }

    }

    override fun onResume() {
        super.onResume()
        //api - post
        postService.setPostDetailView(this)
        postService.showPostDetail(postIdx)
    }

    //bottom sheet
    private fun bottomSheetPost(){

        lateinit var bottomSheetView : View
        val bottomSheetDialog = BottomSheetDialog(this)

        if(getUserIdx()==authorIdx){ //본인 글
            bottomSheetView = layoutInflater.inflate(R.layout.fragment_bottom_sheet_two, null)
            bottomSheetDialog.setContentView(bottomSheetView)

            bottomSheetView.findViewById<TextView>(R.id.bottom_sheet_two_tv).text = "게시물 관리"
            bottomSheetView.findViewById<TextView>(R.id.bottom_sheet_two_tv1).text = "수정하기"
            bottomSheetView.findViewById<TextView>(R.id.bottom_sheet_two_tv2).text = "삭제하기"

            //click edit button
            touchEvent(bottomSheetView.findViewById(R.id.bottom_sheet_two_tv1))
            bottomSheetView.findViewById<TextView>(R.id.bottom_sheet_two_tv1).setOnClickListener {
                //PostCreateActivity에 값 전달
                val intent = Intent(this, PostCreateActivity::class.java)
                intent.putExtra("postDetail", postDetail)
                startActivity(intent)
                bottomSheetDialog.dismiss()
                //finish()
            }
            //click delete button
            touchEvent(bottomSheetView.findViewById(R.id.bottom_sheet_two_tv2))
            bottomSheetView.findViewById<TextView>(R.id.bottom_sheet_two_tv2).setOnClickListener {

                bottomSheetDialog.dismiss()
                //add dialog code
                val dialog = CustomDialog(this)
                dialog.showDeletePostDlg()
                dialog.setOnClickListener(object : CustomDialog.ButtonClickListener{
                    override fun onClicked(TF: Boolean) {
                        //게시물 삭제
                        postService.deletePost(postIdx)
                    }

                })


                finish()
            }
        }
        else{ //타인 글
            bottomSheetView = layoutInflater.inflate(R.layout.fragment_bottom_sheet_complain, null)
            bottomSheetDialog.setContentView(bottomSheetView)

            //click complain button
            touchEvent(bottomSheetView.findViewById(R.id.bottom_sheet_complain_tv))
            bottomSheetView.findViewById<TextView>(R.id.bottom_sheet_complain_tv).setOnClickListener {
                val bottomSheetComplainDetailView = layoutInflater.inflate(R.layout.fragment_bottom_sheet_complain_detail, null)
                val bottomSheetComplainDetailDialog = BottomSheetDialog(this)
                bottomSheetComplainDetailDialog.setContentView(bottomSheetComplainDetailView)

                reportService.setReportPostView(this)
                bottomSheetComplainDetailDialog.show()

                //complain button
                //광고,스팸
                touchEvent(bottomSheetComplainDetailDialog.findViewById(R.id.bottom_sheet_complain_page_tv1)!!)
                bottomSheetComplainDetailDialog.findViewById<TextView>(R.id.bottom_sheet_complain_page_tv1)!!
                    .setOnClickListener {
                        //api
                        reportService.reportPost(ReportPost(postIdx, "광고, 스팸"))

                        //다이얼로그 닫기
                        bottomSheetComplainDetailDialog.dismiss()
                        bottomSheetDialog.dismiss()
                    }
                //낚시,도배
                touchEvent(bottomSheetComplainDetailDialog.findViewById(R.id.bottom_sheet_complain_page_tv2)!!)
                bottomSheetComplainDetailDialog.findViewById<TextView>(R.id.bottom_sheet_complain_page_tv2)!!
                    .setOnClickListener {
                        //api
                        reportService.reportPost(ReportPost(postIdx, "낚시, 도배"))

                        //다이얼로그 닫기
                        bottomSheetComplainDetailDialog.dismiss()
                        bottomSheetDialog.dismiss()
                    }
                //개발과 무관한 게시물
                touchEvent(bottomSheetComplainDetailDialog.findViewById(R.id.bottom_sheet_complain_page_tv3)!!)
                bottomSheetComplainDetailDialog.findViewById<TextView>(R.id.bottom_sheet_complain_page_tv3)!!
                    .setOnClickListener {
                        //api
                        reportService.reportPost(ReportPost(postIdx, "개발과 무관한 게시물"))

                        //다이얼로그 닫기
                        bottomSheetComplainDetailDialog.dismiss()
                        bottomSheetDialog.dismiss()
                    }
                //욕설,비하
                touchEvent(bottomSheetComplainDetailDialog.findViewById(R.id.bottom_sheet_complain_page_tv4)!!)
                bottomSheetComplainDetailDialog.findViewById<TextView>(R.id.bottom_sheet_complain_page_tv4)!!
                    .setOnClickListener {
                        //api
                        reportService.reportPost(ReportPost(postIdx, "욕설, 비하"))

                        //다이얼로그 닫기
                        bottomSheetComplainDetailDialog.dismiss()
                        bottomSheetDialog.dismiss()
                    }
                //기타
                touchEvent(bottomSheetComplainDetailDialog.findViewById(R.id.bottom_sheet_complain_page_tv5)!!)
                bottomSheetComplainDetailDialog.findViewById<TextView>(R.id.bottom_sheet_complain_page_tv5)!!
                    .setOnClickListener {

                        //신고 사유 다이얼로그
                        val dialog = CustomDialog(this)
                        dialog.showReportDlg()
                        //사유 적은 후 ok 버튼 클릭 시
                        dialog.setOnClickListenerETC(object:CustomDialog.ButtonClickListenerETC{
                            override fun onClicked(TF: Boolean, reason : String) {
                                //텍스트 받아 넘기기
                                //api
                                reportService.reportPost(ReportPost(postIdx, reason))

                                finish()
                            }

                        })

                        //bottom 다이얼로그 닫기
                        bottomSheetComplainDetailDialog.dismiss()
                        bottomSheetDialog.dismiss()

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

    //bottom sheet comment, cocomment
    fun bottomSheetComment(authorIdx : Int, commentIdx : Int){

        lateinit var bottomSheetView : View
        val bottomSheetDialog = BottomSheetDialog(this)

        if(getUserIdx()==authorIdx){ //본인 댓글
            bottomSheetView = layoutInflater.inflate(R.layout.fragment_bottom_sheet_one, null)
            bottomSheetDialog.setContentView(bottomSheetView)

            bottomSheetView.findViewById<TextView>(R.id.bottom_sheet_two_tv).text = "댓글 관리"
            bottomSheetView.findViewById<TextView>(R.id.bottom_sheet_two_tv1).text = "삭제하기"

            bottomSheetDialog.show()
            //click delete button
            touchEvent(bottomSheetView.findViewById(R.id.bottom_sheet_two_tv1))
            bottomSheetView.findViewById<TextView>(R.id.bottom_sheet_two_tv1).setOnClickListener {
                commentService.setDeleteCommentView(this)
                commentService.deleteComment(commentIdx)
                bottomSheetDialog.dismiss()
                //add dialog code


            }
        }
        else{ //타인 댓글
            bottomSheetView = layoutInflater.inflate(R.layout.fragment_bottom_sheet_complain, null)
            bottomSheetDialog.setContentView(bottomSheetView)
            bottomSheetDialog.show()

            //글자색 white로
            touchEvent(bottomSheetView.findViewById(R.id.bottom_sheet_complain_tv))

            //click complain button
            bottomSheetView.findViewById<TextView>(R.id.bottom_sheet_complain_tv).setOnClickListener {

                val bottomSheetComplainDetailView = layoutInflater.inflate(R.layout.fragment_bottom_sheet_complain_detail, null)
                val bottomSheetComplainDetailDialog = BottomSheetDialog(this)
                bottomSheetComplainDetailDialog.setContentView(bottomSheetComplainDetailView)

                reportService.setReportCommentView(this)
                bottomSheetComplainDetailDialog.show()

                //complain button
                //광고,스팸
                touchEvent(bottomSheetComplainDetailDialog.findViewById(R.id.bottom_sheet_complain_page_tv1)!!)
                bottomSheetComplainDetailDialog.findViewById<TextView>(R.id.bottom_sheet_complain_page_tv1)!!
                    .setOnClickListener {
                        //api
                        reportService.reportComment(ReportComment(commentIdx, "광고, 스팸"))

                        //다이얼로그 닫기
                        bottomSheetComplainDetailDialog.dismiss()
                        bottomSheetDialog.dismiss()
                    }
                //낚시,도배
                touchEvent(bottomSheetComplainDetailDialog.findViewById(R.id.bottom_sheet_complain_page_tv2)!!)
                bottomSheetComplainDetailDialog.findViewById<TextView>(R.id.bottom_sheet_complain_page_tv2)!!
                    .setOnClickListener {
                        //api
                        reportService.reportComment(ReportComment(commentIdx, "낚시, 도배"))

                        //다이얼로그 닫기
                        bottomSheetComplainDetailDialog.dismiss()
                        bottomSheetDialog.dismiss()
                    }
                //개발과 무관한 게시물
                touchEvent(bottomSheetComplainDetailDialog.findViewById(R.id.bottom_sheet_complain_page_tv3)!!)
                bottomSheetComplainDetailDialog.findViewById<TextView>(R.id.bottom_sheet_complain_page_tv3)!!
                    .setOnClickListener {
                        //api
                        reportService.reportComment(ReportComment(commentIdx, "개발과 무관한 게시물"))

                        //다이얼로그 닫기
                        bottomSheetComplainDetailDialog.dismiss()
                        bottomSheetDialog.dismiss()
                    }
                //욕설,비하
                touchEvent(bottomSheetComplainDetailDialog.findViewById(R.id.bottom_sheet_complain_page_tv4)!!)
                bottomSheetComplainDetailDialog.findViewById<TextView>(R.id.bottom_sheet_complain_page_tv4)!!
                    .setOnClickListener {
                        //api
                        reportService.reportComment(ReportComment(commentIdx, "욕설, 비하"))

                        //다이얼로그 닫기
                        bottomSheetComplainDetailDialog.dismiss()
                        bottomSheetDialog.dismiss()
                    }
                //기타
                touchEvent(bottomSheetComplainDetailDialog.findViewById(R.id.bottom_sheet_complain_page_tv5)!!)
                bottomSheetComplainDetailDialog.findViewById<TextView>(R.id.bottom_sheet_complain_page_tv5)!!
                    .setOnClickListener {

                        //신고 사유 다이얼로그
                        val dialog = CustomDialog(this)
                        dialog.showReportDlg()
                        //사유 적은 후 ok 버튼 클릭 시
                        dialog.setOnClickListenerETC(object:CustomDialog.ButtonClickListenerETC{
                            override fun onClicked(TF: Boolean, reason : String) {
                                //텍스트 받아 넘기기
                                //api
                                reportService.reportComment(ReportComment(commentIdx, reason))

                                finish()
                            }

                        })

                        //bottom 다이얼로그 닫기
                        bottomSheetComplainDetailDialog.dismiss()
                        bottomSheetDialog.dismiss()

                    }

                //close button
                bottomSheetComplainDetailView.findViewById<TextView>(R.id.bottom_sheet_close_tv).setOnClickListener {
                    bottomSheetComplainDetailDialog.dismiss()
                }
            }
        }

        //close button
        bottomSheetView.findViewById<TextView>(R.id.bottom_sheet_close_tv).setOnClickListener {
            bottomSheetDialog.dismiss()
        }

    }

    private fun touchEvent(bind : TextView){
        bind.setOnTouchListener(object : View.OnTouchListener {
            override fun onTouch(view: View?, event: MotionEvent?): Boolean {
                when (event?.action) {
                    MotionEvent.ACTION_DOWN -> {
                        bind.setTextColor(ContextCompat.getColor(this@PostDetailActivity, R.color.white))
                    }
                    MotionEvent.ACTION_UP -> {
                        bind.setTextColor(ContextCompat.getColor(this@PostDetailActivity, R.color.darkmode_background))
                        bind.performClick()
                    }
                }

                //리턴값이 false면 동작 안됨
                return true //or false
            }


        })
    }

    //api
    //postDetail
    override fun onPostDetailSuccess(code: Int, result: PostDetail) {
        when(code){
            200->{
                Log.d("PostDetailresult","$result")
                postDetail = result
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
                bottomSheetPost()
                postService.setDeletePostView(this@PostDetailActivity)

                likeTF = result.likeStatus!!
                scrapTF = result.scrapStatus!!

                //postdetail 새로 들어와도 likeStatus 상태 저장
                if(result.likeStatus!!){
                    binding.postDetailMenuLikeLayout.setBackgroundResource(R.drawable.border_round_debri_darkmode_10)
                    binding.postDetailMenuLikeIv.setImageResource(R.drawable.ic_like_debri)
                }else{
                    binding.postDetailMenuLikeLayout.setBackgroundResource(R.drawable.border_round_white_transparent_10)
                    binding.postDetailMenuLikeIv.setImageResource(R.drawable.ic_like_white)
                }

                //postdeatil 새로 들어와도 scrapStatus 상태 저장
                if(result.scrapStatus!!){
                    binding.postDetailMenuScrapLayout.setBackgroundResource(R.drawable.border_round_transparent_debri_10)
                    binding.postDetailMenuScrapTv.setTextColor(ContextCompat.getColor(this@PostDetailActivity, R.color.darkmode_background))
                    binding.postDetailMenuScrapIv.setImageResource(R.drawable.ic_scrap_darkmode)
                }else{
                    binding.postDetailMenuScrapLayout.setBackgroundResource(R.drawable.border_round_white_transparent_10)
                    binding.postDetailMenuScrapTv.setTextColor(ContextCompat.getColor(this@PostDetailActivity, R.color.white))
                    binding.postDetailMenuScrapIv.setImageResource(R.drawable.ic_scrap_white)
                }

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

        commentService.setCommentCreateView(this)
        commentService.createComment(getComment())

    }



    override fun onCommentCreateSuccess(code: Int) {
        when(code){
            200->{
                Toast.makeText(this, "comment ok", Toast.LENGTH_SHORT).show()
                binding.postDetailWriteCommentEt.text.clear()
                commentService.setShowCommentView(this)
                commentService.showComment(postIdx)

            }
        }
    }

    override fun onCommentCreateFailure(code: Int) {

    }

    override fun onShowCommentSuccess(code: Int, result: List<CommentList>) {
        when(code){
            200-> {
                temp.clear()
                for(i in result){
                    temp.add(CommentList(i.commentIdx, i.authorIdx, i.postIdx, i.commentLevel, i.commentOrder, i.commentGroup, i.commentContent, i.authorName, i.timeAfterCreated, i.likeStatus, i.likeCount))
                }


                parentItemArrayList = ArrayList<CommentList>()
                var childItemArrayListGroup = ArrayList<ArrayList<CommentList>>()
                var commentGroup by Delegates.notNull<Int>()

                for (i in result) {
                    childItemArrayList = ArrayList<CommentList>()

                    if(i.commentLevel==0){
                        commentGroup = i.commentGroup
                        parentItemArrayList.add(CommentList(i.commentIdx, i.authorIdx, i.postIdx, i.commentLevel, i.commentOrder, i.commentGroup, i.commentContent, i.authorName, i.timeAfterCreated, i.likeStatus, i.likeCount))
                    }

                    //Child Item Object
                    val iterator = temp.iterator()
                    while(iterator.hasNext()){
                        val item = iterator.next()
                        if (item.commentLevel==1 && item.commentGroup==commentGroup) {
                            childItemArrayList.add(CommentList(item.commentIdx, item.authorIdx, item.postIdx, item.commentLevel, item.commentOrder, item.commentGroup, item.commentContent, item.authorName, item.timeAfterCreated, item.likeStatus, item.likeCount))

                            Log.d("childList", childItemArrayList.toString())
                        }
                    }

                    if(i.commentLevel==0){
                        childItemArrayListGroup.add(childItemArrayList)
                        Log.d("childGroup", childItemArrayListGroup.toString())
                    }


                }
                Log.d("child", childItemArrayListGroup.toString())

                binding.postDetailCommentRv.layoutManager =
                    LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
                commentRVAdapter = CommentRVAdapter(this)
                commentRVAdapter.build(parentItemArrayList, childItemArrayListGroup, binding, postIdx)
                binding.postDetailCommentRv.adapter = commentRVAdapter



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
                postService.showPostDetail(postIdx)

//                finish()
//                overridePendingTransition(0, 0)
//                startActivity(intent)
//                overridePendingTransition(0, 0)

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
                postService.showPostDetail(postIdx)

//                finish()
//                overridePendingTransition(0, 0)
//                startActivity(intent)
//                overridePendingTransition(0, 0)
            }
        }
    }

    override fun onCancelPostLikeFailure(code: Int) {
        Log.d("postlikecancelfail","$code")
    }

    override fun onCreatePostScrapSuccess(code: Int) {
        when(code){
            200 -> {
                postService.showPostDetail(postIdx)
            }
        }
    }

    override fun onCreatePostScrapFailure(code: Int) {
        Log.d("postscrapcreatefail","$code")
    }

    override fun onCancelPostScrapSuccess(code: Int) {
        when(code){
            200 -> {
                postService.showPostDetail(postIdx)
            }
        }
    }

    override fun onCancelPostScrapFailure(code: Int) {
        Log.d("postscrapcancelfail","$code")
    }

    //신고하기 api
    override fun onReportPostSuccess(code: Int) {
        when(code){
            200->{
                //신고 다이얼로그
                val dialog = CustomDialog(this)
                dialog.reportPostUserDlg()
                val dialogETC = CustomDialog(this)
                dialog.setOnClickListener(object:CustomDialog.ButtonClickListener {
                    override fun onClicked(TF: Boolean) {
                        //신고 사유 : 유저 신고 시 reason 필요

                        dialogETC.showReportDlg()
                        dialogETC.setOnClickListenerETC(object:CustomDialog.ButtonClickListenerETC {
                            override fun onClicked(TF: Boolean, reason: String) {
                                //유저 신고&차단 api
                                reportService.reportUser(reason, postIdx)
                            }

                        })
                    }

                })

            }
        }
    }

    override fun onReportPostFailure(code: Int) {

    }

    override fun onDeleteCommentSuccess(code: Int) {
        when(code){
            200->{
                commentService.setShowCommentView(this)
                commentService.showComment(postIdx)
            }
        }

    }

    override fun onDeleteCommentFailure(code: Int) {

    }

    override fun onReportCommentSuccess(code: Int) {
        when(code){
            200->{
                //토스트메세지 띄우기
                var reportToast = layoutInflater.inflate(R.layout.toast_report,null)
                var toast = Toast(this)
                toast.view = reportToast
                toast.setGravity(Gravity.CENTER_HORIZONTAL,0,0)
                toast.show()
            }
        }
    }

    override fun onReportCommentFailure(code: Int) {

    }

    override fun onReportUserSuccess(code: Int) {
        when(code){
            200->{

            }
        }
    }

    override fun onReportUserFailure(code: Int) {
        Log.d("reportuserfail","$code")
    }


}