package com.example.debri_lize.activity.auth

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.debri_lize.R
import com.example.debri_lize.data.User
import com.example.debri_lize.data.service.AuthService
import com.example.debri_lize.data.view.SignUpView
import com.example.debri_lize.databinding.ActivitySignupBinding
import com.example.debri_lize.data.response.Result

class SignUpActivity:AppCompatActivity(), SignUpView {
    lateinit var binding: ActivitySignupBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //가입완료 버튼을 누르면 회원가입 끝
        binding.signUpSignUpBtn.setOnClickListener{
            signUp()
            finish()
        }

        setFocus() //focus effect


    }


    //회원가입
    //사용자가 입력한 값 가져오기
    private fun getUser() : User {
        val id : String = binding.signUpIdEt.text.toString()
        var nickname : String = binding.signUpNicknameEt.text.toString()
        val birthday : String = binding.signUpBirthEt.text.toString()
        val password : String = binding.signUpPasswordEt.text.toString()

        return User(id, nickname, birthday, password)
    }

    //회원가입 진행(서버이용)
    private fun signUp(){
        //이메일 형식이 잘못된 경우
        if(binding.signUpIdEt.text.toString().isEmpty()){
            Toast.makeText(this, "아이디 형식이 잘못되었습니다.", Toast.LENGTH_SHORT).show()
            return
        }

        //비밀번호와 비밀번호 확인이 일치하지 않는 경우
        if(binding.signUpPasswordEt.text.toString().isEmpty() != binding.signUpPasswordCheckEt.text.toString().isEmpty()){
            Toast.makeText(this, "비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show()
            return
        }

        //닉네임 형식이 맞지 않는 경우
        if(binding.signUpNicknameEt.text.toString().isEmpty()){
            Toast.makeText(this, "닉네임 형식이 잘못되었습니다.", Toast.LENGTH_SHORT).show()
            return
        }

        //생일 형식이 맞지 않는 경우
        if(binding.signUpBirthEt.text.toString().isEmpty()){
            Toast.makeText(this, "생일 형식이 잘못되었습니다.", Toast.LENGTH_SHORT).show()
            return
        }


        val authService = AuthService()
        authService.setSignUpView(this)

        //만든 API 호출
        authService.signUp(getUser())

    }

    override fun onSignUpSuccess(result : Result) {
        Log.d("success", "success")

        finish()
    }

    override fun onSignUpFailure() {
        Log.d("fail", "fail")
    }

    //focus effect
    private fun setFocus(){
        //focus on ID
        binding.signUpIdEt.setOnFocusChangeListener(object : View.OnFocusChangeListener {
            override fun onFocusChange(view: View, hasFocus: Boolean) {
                if (hasFocus) {
                    //  포커스시
                    binding.signUpIdLayout.setBackgroundResource(R.drawable.border_round_debri_transparent_10)
                    binding.signUpIdBarV.setBackgroundResource(R.drawable.vertical_line_debri_2)
                    binding.signUpIdTv.setTextColor(ContextCompat.getColor(this@SignUpActivity, R.color.debri))
                } else {
                    //  포커스 뺏겼을 때
                    binding.signUpIdLayout.setBackgroundResource(R.drawable.border_round_white_transparent_10)
                    binding.signUpIdBarV.setBackgroundResource(R.drawable.vertical_line_white_1)
                    binding.signUpIdTv.setTextColor(ContextCompat.getColor(this@SignUpActivity, R.color.white))
                }
            }
        })

        //focus on password
        binding.signUpPasswordEt.setOnFocusChangeListener(object : View.OnFocusChangeListener {
            override fun onFocusChange(view: View, hasFocus: Boolean) {
                if (hasFocus) {
                    //  포커스시
                    binding.signUpPasswordLayout.setBackgroundResource(R.drawable.border_round_debri_transparent_10)
                    binding.signUpPasswordBarV.setBackgroundResource(R.drawable.vertical_line_debri_2)
                    binding.signUpPasswordTv.setTextColor(ContextCompat.getColor(this@SignUpActivity, R.color.debri))
                } else {
                    //  포커스 뺏겼을 때
                    binding.signUpPasswordLayout.setBackgroundResource(R.drawable.border_round_white_transparent_10)
                    binding.signUpPasswordBarV.setBackgroundResource(R.drawable.vertical_line_white_1)
                    binding.signUpPasswordTv.setTextColor(ContextCompat.getColor(this@SignUpActivity, R.color.white))
                }
            }
        })

        //focus on passwordCheck
        binding.signUpPasswordCheckEt.setOnFocusChangeListener(object : View.OnFocusChangeListener {
            override fun onFocusChange(view: View, hasFocus: Boolean) {
                if (hasFocus) {
                    //  포커스시
                    binding.signUpPasswordCheckLayout.setBackgroundResource(R.drawable.border_round_debri_transparent_10)
                    binding.signUpPasswordCheckBarV.setBackgroundResource(R.drawable.vertical_line_debri_2)
                    binding.signUpPasswordCheckTv.setTextColor(ContextCompat.getColor(this@SignUpActivity, R.color.debri))
                } else {
                    //  포커스 뺏겼을 때
                    binding.signUpPasswordCheckLayout.setBackgroundResource(R.drawable.border_round_white_transparent_10)
                    binding.signUpPasswordCheckBarV.setBackgroundResource(R.drawable.vertical_line_white_1)
                    binding.signUpPasswordCheckTv.setTextColor(ContextCompat.getColor(this@SignUpActivity, R.color.white))
                }
            }
        })

        //focus on birth
        binding.signUpBirthEt.setOnFocusChangeListener(object : View.OnFocusChangeListener {
            override fun onFocusChange(view: View, hasFocus: Boolean) {
                if (hasFocus) {
                    //  포커스시
                    binding.signUpBirthLayout.setBackgroundResource(R.drawable.border_round_debri_transparent_10)
                    binding.signUpBirthBarV.setBackgroundResource(R.drawable.vertical_line_debri_2)
                    binding.signUpBirthTv.setTextColor(ContextCompat.getColor(this@SignUpActivity, R.color.debri))
                } else {
                    //  포커스 뺏겼을 때
                    binding.signUpBirthLayout.setBackgroundResource(R.drawable.border_round_white_transparent_10)
                    binding.signUpBirthBarV.setBackgroundResource(R.drawable.vertical_line_white_1)
                    binding.signUpBirthTv.setTextColor(ContextCompat.getColor(this@SignUpActivity, R.color.white))
                }
            }
        })

        //focus on nickname
        binding.signUpNicknameEt.setOnFocusChangeListener(object : View.OnFocusChangeListener {
            override fun onFocusChange(view: View, hasFocus: Boolean) {
                if (hasFocus) {
                    //  포커스시
                    binding.signUpNicknameLayout.setBackgroundResource(R.drawable.border_round_debri_transparent_10)
                    binding.signUpNicknameBarV.setBackgroundResource(R.drawable.vertical_line_debri_2)
                    binding.signUpNicknameTv.setTextColor(ContextCompat.getColor(this@SignUpActivity, R.color.debri))
                } else {
                    //  포커스 뺏겼을 때
                    binding.signUpNicknameLayout.setBackgroundResource(R.drawable.border_round_white_transparent_10)
                    binding.signUpNicknameBarV.setBackgroundResource(R.drawable.vertical_line_white_1)
                    binding.signUpNicknameTv.setTextColor(ContextCompat.getColor(this@SignUpActivity, R.color.white))
                }
            }
        })

    }
}