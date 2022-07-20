package com.example.debri_lize.activity

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.ContextThemeWrapper
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.PopupMenu
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.debri_lize.R
import com.example.debri_lize.data.Post
import com.example.debri_lize.databinding.ActivityWriteBinding


class WriteActivity : AppCompatActivity() { //, CoroutineScope by MainScope()

    lateinit var binding : ActivityWriteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWriteBinding.inflate(layoutInflater) //binding 초기화
        setContentView(binding.root)

        //board option menu
        binding.writeOptionMenuLayout.setOnClickListener {
            showPopup(binding.writeOptionMenuListIv)
        }


        //자동으로 줄이 바뀌고 enter 엔터키를 누르면 다음줄로 이동
        binding.writeContentEt.setHorizontallyScrolling(false)

        //게시글 글자수 count
        countLetter()

        //게시글 등록
        // component
        val title : EditText = binding.writeTitleEt
        val content : EditText = binding.writeContentEt

        // 유저아이디
        //val userIdx : Int = getIntent().getIntExtra("userid")

        // 실행
        binding.writeBtn.setOnClickListener{

            //BoardDetailFragment에 data보내기
//            val bundle = Bundle()
//            bundle.putSerializable("boardDetail", boardDetaildata)
//            val passBundleBFragment = PostFragment()
//            passBundleBFragment.arguments = bundle
//            Log.d("bundle", bundle.toString())
//
//            supportFragmentManager.beginTransaction()
//                .replace(R.id.write_activity, passBundleBFragment)
//                .commit()

            finish()

        }


    }

    //popup menu custom method
    private fun showPopup(v: View) {

        //val wrapper = ContextThemeWrapper(this, com.example.debri_lize.R.style.MyPopupMenu)
        //val popup = PopupMenu(wrapper, v) // PopupMenu 객체 선언

        val popup = PopupMenu(this, v) // PopupMenu 객체 선언
        popup.menuInflater.inflate(R.menu.post_menu, popup.menu) // 메뉴 레이아웃 inflate
        popup.setOnMenuItemClickListener(PopupListener())
        popup.show() // 팝업 보여주기
    }

    inner class PopupListener : PopupMenu.OnMenuItemClickListener {
        override fun onMenuItemClick(item: MenuItem?): Boolean {
            when (item?.itemId) { // 메뉴 아이템에 따라 동작 다르게 하기
                R.id.c -> {
                    binding.writeOptionMenuListTv1.text = "C언어"
                    binding.writeOptionMenuListTv1.setTextColor(ContextCompat.getColor(applicationContext!!, R.color.c))
                    binding.writeOptionMenuListTv2.text = "게시판"
                }

                R.id.c_qna -> {
                    binding.writeOptionMenuListTv1.text = "C언어"
                    binding.writeOptionMenuListTv1.setTextColor(ContextCompat.getColor(applicationContext!!, R.color.c))
                    binding.writeOptionMenuListTv2.text = "질문 게시판"
                }

                R.id.java -> {
                    binding.writeOptionMenuListTv1.text = "JAVA"
                    binding.writeOptionMenuListTv1.setTextColor(ContextCompat.getColor(applicationContext!!, R.color.java))
                    binding.writeOptionMenuListTv2.text = "게시판"
                }

                R.id.java_qna -> {
                    binding.writeOptionMenuListTv1.text = "JAVA"
                    binding.writeOptionMenuListTv1.setTextColor(ContextCompat.getColor(applicationContext!!, R.color.java))
                    binding.writeOptionMenuListTv2.text = "질문 게시판"
                }

                R.id.python -> {
                    binding.writeOptionMenuListTv1.text = "Python"
                    binding.writeOptionMenuListTv1.setTextColor(ContextCompat.getColor(applicationContext!!, R.color.python))
                    binding.writeOptionMenuListTv2.text = "게시판"
                }

                R.id.python_qna -> {
                    binding.writeOptionMenuListTv1.text = "Python"
                    binding.writeOptionMenuListTv1.setTextColor(ContextCompat.getColor(applicationContext!!, R.color.python))
                    binding.writeOptionMenuListTv2.text = "질문 게시판"
                }

                R.id.news_issue -> {
                    binding.writeOptionMenuListTv1.text = null
                    binding.writeOptionMenuListTv2.text = "개발 뉴스 &amp; 이슈"
                }

                R.id.news_issue -> {
                    binding.writeOptionMenuListTv1.text = null
                    binding.writeOptionMenuListTv2.text = "개발 뉴스 &amp; 이슈"
                }

                R.id.qna -> {
                    binding.writeOptionMenuListTv1.text = null
                    binding.writeOptionMenuListTv2.text = "질문 게시판"
                }

                R.id.curriculum_qna -> {
                    binding.writeOptionMenuListTv1.text = null
                    binding.writeOptionMenuListTv2.text = "커리큘럼 질문 게시판"
                }

            }

            return item != null // 아이템이 null이 아닌 경우 true, null인 경우 false 리턴
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




}