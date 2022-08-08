package com.example.debri_lize.activity

import SpinnerAdapter
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.debri_lize.CustomDialog
import com.example.debri_lize.activity.auth.ProfileActivity
import com.example.debri_lize.data.post.Post
import com.example.debri_lize.service.PostService
import com.example.debri_lize.view.post.PostCreateView
import com.example.debri_lize.databinding.ActivityPostCreateBinding
import com.example.debri_lize.utils.getUserIdx
import com.example.debri_lize.data.post.EditPost
import com.example.debri_lize.data.post.PostDetail
import com.example.debri_lize.response.Board
import com.example.debri_lize.service.BoardService
import com.example.debri_lize.view.board.BoardListView
import com.example.debri_lize.view.post.EditPostView
import kotlin.properties.Delegates


class PostCreateActivity : AppCompatActivity(), PostCreateView, EditPostView, BoardListView {

    lateinit var binding : ActivityPostCreateBinding

    //spinner
    private val items = ArrayList<com.example.debri_lize.data.board.Board>()

    //api
    lateinit var postDetail : PostDetail

    var boardIdx by Delegates.notNull<Int>()
    var edit by Delegates.notNull<Boolean>() //edit or write 구분

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPostCreateBinding.inflate(layoutInflater) //binding 초기화
        setContentView(binding.root)

        //click userImg -> profile
        binding.writeDebriUserIv.setOnClickListener{
            val intent = Intent(this, ProfileActivity::class.java)
            finish()
            startActivity(intent)
        }

        binding.writeBtn.text = "작성하기"

        //자동으로 줄이 바뀌고 enter 엔터키를 누르면 다음줄로 이동
        binding.writeContentEt.setHorizontallyScrolling(false)

        //게시글 글자수 count
        countLetter()

        //edit 초기화
        edit = true

        //PostFragment에서 boardIdx받기 (write)
        val intent = intent //전달할 데이터를 받을 Intent
        boardIdx = intent.getIntExtra("boardIdx", 0)
        edit = intent.getBooleanExtra("edit", true)

        //api - spinner
        val boardService = BoardService()
        boardService.setBoardListView(this)
        boardService.showBoardList()

        //PostDetailAcitivy : edit -> data받아오기 (edit)
        if(edit==true){
            val intent = intent //전달할 데이터를 받을 Intent
            postDetail = intent.getSerializableExtra("postDetail") as PostDetail
            if(postDetail!=null){
                binding.writeTitleEt.text = Editable.Factory.getInstance().newEditable(postDetail.postName)
                binding.writeContentEt.text = Editable.Factory.getInstance().newEditable(postDetail.postContents)
                binding.writeBtn.text = "수정하기"
            }
        }

        // 실행
        binding.writeBtn.setOnClickListener{
            //글 작성 다이얼로그
            val dialog = CustomDialog(this)

            if(edit) dialog.showEditDlg()   //edit==true 이면 editDialog 보여주기
            else dialog.showWriteDlg()     //else writeDialog

            //작성 ok 버튼 클릭시
            dialog.setOnClickListener(object:CustomDialog.ButtonClickListener{
                override fun onClicked(TF: Boolean) {

                    if(binding.writeBtn.text == "작성하기"){
                        createPost()
                    }else{
                        editPost()
                    }

                    finish()
                }

            })
        }

        //back 버튼 클릭
        binding.writePreviousIv.setOnClickListener {
            //다이얼로그 생성
            val dialog = CustomDialog(this)

            if(edit) dialog.showEditCancelDlg()
            else dialog.showCancelDlg()
            dialog.setOnClickListener(object:CustomDialog.ButtonClickListener{
                override fun onClicked(TF: Boolean) {
                    finish()
                }

            })
        }


    }

    //count letter
    private fun countLetter(){
        binding.writeContentEt.addTextChangedListener(object : TextWatcher {

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                binding.writeCountLetterTv.text = "0/5000"
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                var userinput = binding.writeContentEt.text.toString()
                binding.writeCountLetterTv.text = userinput.length.toString() + "/5000"
            }

            override fun afterTextChanged(s: Editable?) {
                var userinput = binding.writeContentEt.text.toString()
                binding.writeCountLetterTv.text = userinput.length.toString() + "/5000"
            }
        })
    }

    //api

    //사용자가 입력한 값 가져오기
    private fun getPost() : Post {

        val userIdx = getUserIdx() //변경필요
        val postContent : String = binding.writeContentEt.text.toString()
        var postName : String = binding.writeTitleEt.text.toString()

        return Post(boardIdx, userIdx, postContent, postName)

    }

    private fun getEditPost() : EditPost{
        val userIdx = getUserIdx() //변경필요
        val postContent : String = binding.writeContentEt.text.toString()
        return EditPost(userIdx, postContent)
    }


    private fun createPost(){
        //제목이 null인 경우
        if(binding.writeTitleEt.text.toString().isEmpty()){
            Toast.makeText(this, "제목을 입력해주세요", Toast.LENGTH_SHORT).show()
            return
        }


        //내용이 null인 경우
        if(binding.writeContentEt.text.toString().isEmpty()){
            Toast.makeText(this, "내용을 입력해주세요", Toast.LENGTH_SHORT).show()
            return
        }

        val postService = PostService()
        postService.setPostCreateView(this)

        //만든 API 호출
        postService.createPost(getPost())

    }

    private fun editPost(){
        //제목이 null인 경우
        if(binding.writeTitleEt.text.toString().isEmpty()){
            Toast.makeText(this, "제목을 입력해주세요", Toast.LENGTH_SHORT).show()
            return
        }


        //내용이 null인 경우
        if(binding.writeContentEt.text.toString().isEmpty()){
            Toast.makeText(this, "내용을 입력해주세요", Toast.LENGTH_SHORT).show()
            return
        }

        val postService = PostService()
        postService.setEditPostView(this)

        //만든 API 호출
        postService.editPost(getEditPost(), postDetail.postIdx)

    }

    override fun onPostCreateSuccess(code : Int) {
        when(code){
            //개발할 때는 userIdx 저장이 필요할수도
            200-> {
                Toast.makeText(this, "ok", Toast.LENGTH_SHORT).show()
                finish()
            }
        }

    }

    override fun onPostCreateFailure(code : Int) {
        when(code){
            //개발할 때는 userIdx 저장이 필요할수도
            3030, 1000, 5000, 5001, 5002, 5003, 5004, 5005-> {
                Toast.makeText(this, "fail", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onEditPostSuccess(code: Int) {
        when(code){
            200-> {

            }
        }
    }

    override fun onEditPostFailure(code: Int) {

    }

    //spinner
    override fun onBoardListSuccess(code: Int, result: List<Board>) {
        when(code){
            200->{
                //spinner : boardList
                items.clear()

                for (i in result){
                    items.add(com.example.debri_lize.data.board.Board(i.boardIdx, i.boardName))
                }

                val myAdapter = SpinnerAdapter(this@PostCreateActivity,items)
                binding.writeBoardListSpinner.adapter = myAdapter

                //초기화
               binding.writeBoardListSpinner.setSelection(boardIdx-1)

                //click item
                binding.writeBoardListSpinner.onItemSelectedListener =
                    object : AdapterView.OnItemSelectedListener {
                        override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {

                            //아이템이 클릭 되면 맨 위부터 position 0번부터 순서대로 동작
                            when (position) {
                                0, 1 -> {
                                    boardIdx = items[position].boardIdx
                                }

                                else -> {

                                }
                            }
                        }

                        override fun onNothingSelected(parent: AdapterView<*>) {

                        }

                    }
            }
        }
    }

    override fun onBoardListFailure(code: Int) {

    }


}