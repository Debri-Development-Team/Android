package com.example.debri_lize.activity.auth

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.debri_lize.R
import com.example.debri_lize.data.auth.UserSignup
import com.example.debri_lize.service.AuthService
import com.example.debri_lize.view.auth.SignUpView
import com.example.debri_lize.databinding.ActivitySignupBinding

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

        binding.signUpBackLayout.setOnClickListener{
            finish()
        }

        //focus effect
        setFocus()


    }


    //회원가입
    //사용자가 입력한 값 가져오기
    private fun getUser() : UserSignup {
        val id : String = binding.signUpIdEt.text.toString()
        val password : String = binding.signUpPasswordEt.text.toString()
        val password2 : String = binding.signUpPasswordCheckEt.text.toString()
        var nickname : String = binding.signUpNicknameEt.text.toString()
        val birthday : String = binding.signUpBirthEt.text.toString()

        return UserSignup(id, password, password2, nickname, birthday)
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

    override fun onSignUpSuccess(code : Int) {
        when(code){
            //개발할 때는 userIdx 저장이 필요할수도
            200-> {
                Toast.makeText(this, "message", Toast.LENGTH_SHORT).show()
                finish()
            }
        }

    }

    override fun onSignUpFailure(code : Int) {
        when(code){
            //개발할 때는 userIdx 저장이 필요할수도
            3020, 3021, 3022, 3023, 3024, 3025, 3026, 3027, 1000-> {
                Toast.makeText(this, "message", Toast.LENGTH_SHORT).show()
            }
        }
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