package com.example.debri_lize.activity

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.MenuItem
import android.view.View
import android.widget.PopupMenu
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.debri_lize.R
import com.example.debri_lize.data.post.Post
import com.example.debri_lize.service.PostService
import com.example.debri_lize.view.post.PostCreateView
import com.example.debri_lize.databinding.ActivityPostCreateBinding
import com.example.debri_lize.utils.getUserIdx


class PostCreateActivity : AppCompatActivity(), PostCreateView { //, CoroutineScope by MainScope()

    lateinit var binding : ActivityPostCreateBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPostCreateBinding.inflate(layoutInflater) //binding 초기화
        setContentView(binding.root)

        //board option menu
        binding.writeOptionMenuLayout.setOnClickListener {

        }


        //자동으로 줄이 바뀌고 enter 엔터키를 누르면 다음줄로 이동
        binding.writeContentEt.setHorizontallyScrolling(false)

        //게시글 글자수 count
        countLetter()

        // 실행
        binding.writeBtn.setOnClickListener{
            createPost()
            finish()
        }


    }

    //count letter
    private fun countLetter(){
        //count letter
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
        val boardIdx : Int = 1 //변경필요
        val userIdx = getUserIdx() //변경필요
        val postContent : String = binding.writeContentEt.text.toString()
        var postName : String = binding.writeTitleEt.text.toString()

        return Post(boardIdx, userIdx, postContent, postName)
    }


    private fun createPost(){
        //이메일 형식이 잘못된 경우
        if(binding.writeTitleEt.text.toString().isEmpty()){
            Toast.makeText(this, "제목을 입력해주세요", Toast.LENGTH_SHORT).show()
            return
        }


        //닉네임 형식이 맞지 않는 경우
        if(binding.writeContentEt.text.toString().isEmpty()){
            Toast.makeText(this, "내용을 입력해주세요", Toast.LENGTH_SHORT).show()
            return
        }

        val postService = PostService()
        postService.setPostCreateView(this)

        //만든 API 호출
        postService.createPost(getPost())

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




}