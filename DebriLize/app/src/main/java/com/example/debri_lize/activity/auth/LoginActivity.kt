package com.example.debri_lize.activity.auth

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.debri_lize.R
import com.example.debri_lize.activity.AddCurriculumActivity
import com.example.debri_lize.activity.MainActivity
import com.example.debri_lize.data.UserLogin
import com.example.debri_lize.data.response.Result
import com.example.debri_lize.data.service.AuthService
import com.example.debri_lize.data.view.LoginView
import com.example.debri_lize.databinding.ActivityLoginBinding
import com.example.debri_lize.utils.saveJwt
import com.example.debri_lize.utils.saveUserIdx

public class LoginActivity:AppCompatActivity(), LoginView {
    lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setFocus() //focus effect
        setMouseEvent() //mouse event

        //click signup btn
        binding.loginSignUpBtn.setOnClickListener{
            startActivity(Intent(this, SignUpActivity::class.java))
        }

        //click login btn
        binding.loginBtn.setOnClickListener{
            login()
        }
    }

    private fun login(){
        //ID가 입력되지 않은 경우
        if(binding.loginIdEt.text.toString().isEmpty()){
            Toast.makeText(this, "아이디를 입력해주세요.", Toast.LENGTH_SHORT).show()
            return
        }

        //비밀번호가 입력되지 않은 경우
        if(binding.loginPasswordEt.text.toString().isEmpty()){
            Toast.makeText(this, "비밀번호를 입력해주세요.", Toast.LENGTH_SHORT).show()
            return
        }

        //정상적으로 입력된 경우
        val email : String = binding.loginIdEt.text.toString()
        val pwd : String = binding.loginPasswordEt.text.toString()


        val authService = AuthService()
        authService.setLoginView(this)

        //만든 API 호출
        authService.login(UserLogin(email, pwd))

        //user가 null일 경우
        //Toast.makeText(this, "회원 정보가 존재하지 않습니다.", Toast.LENGTH_SHORT).show()

    }

    override fun onLoginSuccess(code:Int, result: Result?) {

        when(code){
            //개발할 때는 userIdx 저장이 필요할수도
            200-> {

                saveJwt(result!!.jwt)
                saveUserIdx(result!!.userIdx)
                Log.d("save", "success")
                //startActivity(Intent(this, AddCurriculumActivity::class.java))
                //test
                startActivity(Intent(this, MainActivity::class.java))
            }
        }
    }

    override fun onLoginFailure(code:Int, message: String) {
        when(code){
            3020->{ //이메일을 입력해주세요
                }
            3021->{ //비밀번호를 입력해주세요

            }
            3022->{ //이메일 형식을 확인해주세요

            }
            4010->{ //없는 아이디거나 비밀버호가 틀렸습니다

            }
        }
    }

    //focus effect
    private fun setFocus() {
        //focus on ID
        binding.loginIdEt.setOnFocusChangeListener(object : View.OnFocusChangeListener {
            override fun onFocusChange(view: View, hasFocus: Boolean) {
                if (hasFocus) {
                    //  포커스시
                    binding.loginIdLayout.setBackgroundResource(R.drawable.border_round_debri_transparent_10)
                    binding.loginIdBarV.setBackgroundResource(R.drawable.vertical_line_debri_2)
                    binding.loginIdTv.setTextColor(ContextCompat.getColor(this@LoginActivity, R.color.debri))
                } else {
                    //  포커스 뺏겼을 때
                    binding.loginIdLayout.setBackgroundResource(R.drawable.border_round_white_transparent_10)
                    binding.loginIdBarV.setBackgroundResource(R.drawable.vertical_line_white_1)
                    binding.loginIdTv.setTextColor(ContextCompat.getColor(this@LoginActivity, R.color.white))
                }
            }
        })

        //focus on password
        binding.loginPasswordEt.setOnFocusChangeListener(object : View.OnFocusChangeListener {
            override fun onFocusChange(view: View, hasFocus: Boolean) {
                if (hasFocus) {
                    //  포커스시
                    binding.loginPasswordLayout.setBackgroundResource(R.drawable.border_round_debri_transparent_10)
                    binding.loginPasswordBarV.setBackgroundResource(R.drawable.vertical_line_debri_2)
                    binding.loginPasswordTv.setTextColor(ContextCompat.getColor(this@LoginActivity, R.color.debri))
                } else {
                    //  포커스 뺏겼을 때
                    binding.loginPasswordLayout.setBackgroundResource(R.drawable.border_round_white_transparent_10)
                    binding.loginPasswordBarV.setBackgroundResource(R.drawable.vertical_line_white_1)
                    binding.loginPasswordTv.setTextColor(ContextCompat.getColor(this@LoginActivity, R.color.white))
                }
            }
        })
    }

    private fun setMouseEvent(){
        //signup
        binding.loginSignUpBtn.setOnTouchListener(object : View.OnTouchListener {
            override fun onTouch(view: View?, event: MotionEvent?): Boolean {
                when (event?.action) {
                    MotionEvent.ACTION_DOWN -> {
                        binding.loginSignUpBtn.setBackgroundResource(R.drawable.border_round_debri_transparent_6)
                        binding.loginSignUpBtn.setTextColor(ContextCompat.getColor(this@LoginActivity, R.color.white))
                    }
                    MotionEvent.ACTION_UP -> {
                        binding.loginSignUpBtn.setBackgroundResource(R.drawable.border_round_transparent_white_6)
                        binding.loginSignUpBtn.setTextColor(ContextCompat.getColor(this@LoginActivity, R.color.darkmode_background))
                        binding.loginSignUpBtn.performClick()
                    }
                }


                //리턴값이 false면 동작 안됨
                return true //or false
            }


        })

        //login
        binding.loginBtn.setOnTouchListener(object : View.OnTouchListener {
            override fun onTouch(view: View?, event: MotionEvent?): Boolean {
                when (event?.action) {
                    MotionEvent.ACTION_DOWN -> {
                        binding.loginBtn.setBackgroundResource(R.drawable.border_round_debri_transparent_6)
                        binding.loginBtn.setTextColor(ContextCompat.getColor(this@LoginActivity, R.color.white))
                    }
                    MotionEvent.ACTION_UP -> {
                        binding.loginBtn.setBackgroundResource(R.drawable.border_round_transparent_debri_6)
                        binding.loginBtn.setTextColor(ContextCompat.getColor(this@LoginActivity, R.color.darkmode_background))
                        binding.loginBtn.performClick()
                    }
                }
                //리턴값이 false면 동작 안됨
                return true //or false
            }
        })

        //google
        binding.loginGoogleLayout.setOnTouchListener(object : View.OnTouchListener {
            override fun onTouch(view: View?, event: MotionEvent?): Boolean {
                when (event?.action) {
                    MotionEvent.ACTION_DOWN -> {
                        binding.loginGoogleLayout.setBackgroundResource(R.drawable.border_round_debri_transparent_6)
                        binding.loginGoogleTv.setTextColor(ContextCompat.getColor(this@LoginActivity, R.color.debri))
                    }
                    MotionEvent.ACTION_UP -> {
                        binding.loginGoogleLayout.setBackgroundResource(R.drawable.border_round_white_transparent_6)
                        binding.loginGoogleTv.setTextColor(ContextCompat.getColor(this@LoginActivity, R.color.white))
                    }
                }
                //리턴값이 false면 동작 안됨
                return true //or false
            }
        })

        //kakao
        binding.loginKakaoLayout.setOnTouchListener(object : View.OnTouchListener {
            override fun onTouch(view: View?, event: MotionEvent?): Boolean {
                when (event?.action) {
                    MotionEvent.ACTION_DOWN -> {
                        binding.loginKakaoLayout.setBackgroundResource(R.drawable.border_round_debri_transparent_6)
                        binding.loginKakaoTv.setTextColor(ContextCompat.getColor(this@LoginActivity, R.color.debri))
                    }
                    MotionEvent.ACTION_UP -> {
                        binding.loginKakaoLayout.setBackgroundResource(R.drawable.border_round_white_transparent_6)
                        binding.loginKakaoTv.setTextColor(ContextCompat.getColor(this@LoginActivity, R.color.white))
                    }
                }
                //리턴값이 false면 동작 안됨
                return true //or false
            }
        })

        //naver
        binding.loginNaverLayout.setOnTouchListener(object : View.OnTouchListener {
            override fun onTouch(view: View?, event: MotionEvent?): Boolean {
                when (event?.action) {
                    MotionEvent.ACTION_DOWN -> {
                        binding.loginNaverLayout.setBackgroundResource(R.drawable.border_round_debri_transparent_6)
                        binding.loginNaverTv.setTextColor(ContextCompat.getColor(this@LoginActivity, R.color.debri))
                    }
                    MotionEvent.ACTION_UP -> {
                        binding.loginNaverLayout.setBackgroundResource(R.drawable.border_round_white_transparent_6)
                        binding.loginNaverTv.setTextColor(ContextCompat.getColor(this@LoginActivity, R.color.white))
                    }
                }
                //리턴값이 false면 동작 안됨
                return true //or false
            }
        })
    }
}