package com.example.debri_lize.activity

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.debri_lize.CustomDialog
import com.example.debri_lize.R
import com.example.debri_lize.data.SpinnerModel
import com.example.debri_lize.data.post.Post
import com.example.debri_lize.service.PostService
import com.example.debri_lize.view.post.PostCreateView
import com.example.debri_lize.databinding.ActivityPostCreateBinding
import com.example.debri_lize.utils.getUserIdx
import com.example.debri_lize.SpinnerAdapter


class PostCreateActivity : AppCompatActivity(), PostCreateView { //, CoroutineScope by MainScope()

    lateinit var binding : ActivityPostCreateBinding

    //spinner
    private lateinit var spinnerAdapterYear: SpinnerAdapter
    private val listOfYear = ArrayList<SpinnerModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPostCreateBinding.inflate(layoutInflater) //binding 초기화
        setContentView(binding.root)

        //spinner : boardList
        binding.writeOptionMenuLayout.setOnClickListener{
            setupSpinnerYear()
            setupSpinnerHandler()
        }


        //자동으로 줄이 바뀌고 enter 엔터키를 누르면 다음줄로 이동
        binding.writeContentEt.setHorizontallyScrolling(false)

        //게시글 글자수 count
        countLetter()

        // 실행
        binding.writeBtn.setOnClickListener{
            //글 작성 다이얼로그
            val dialog = CustomDialog(this)

            dialog.showWriteDlg()
            //작성 ok 버튼 클릭시
            dialog.setOnClickListener(object:CustomDialog.ButtonClickListener{
                override fun onClicked(TF: Boolean) {
                    createPost()
                    finish()
                }

            })
        }

        //back 버튼 클릭
        binding.writePreviousIv.setOnClickListener {
            //다이얼로그 생성
            val dialog = CustomDialog(this)
            dialog.showCancelDlg()
            dialog.setOnClickListener(object:CustomDialog.ButtonClickListener{
                override fun onClicked(TF: Boolean) {
                    finish()
                }

            })
        }


    }

    private fun setupSpinnerYear() {
        val years = resources.getStringArray(R.array.spinner_year)

        for (i in years.indices) {
            val year = SpinnerModel(R.drawable.ic_favorite_on, years[i])
            listOfYear.add(year)
        }
        spinnerAdapterYear = SpinnerAdapter(this, R.layout.item_spinner, listOfYear)
        binding.spinnerYear.adapter = spinnerAdapterYear
    }


    private fun setupSpinnerHandler() {
        binding.spinnerYear.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val year = binding.spinnerYear.getItemAtPosition(position) as SpinnerModel
                if (!year.name.equals("연도")) {
                    binding.txtYear.text = "Selected: ${year.name}"
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
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