package com.example.debri_lize.activity.auth

import android.app.Activity
import android.content.Intent
import android.graphics.Insets
import android.os.Build
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.debri_lize.R
import com.example.debri_lize.activity.AddCurriculumActivity
import com.example.debri_lize.activity.AddCurriculumDetailActivity
import com.example.debri_lize.activity.MainActivity
import com.example.debri_lize.data.auth.Token
import com.example.debri_lize.data.auth.User
import com.example.debri_lize.data.auth.UserLogin
import com.example.debri_lize.databinding.ActivityLoginBinding
import com.example.debri_lize.service.AuthService
import com.example.debri_lize.service.TokenService
import com.example.debri_lize.utils.*
import com.example.debri_lize.view.auth.LoginView
import com.example.debri_lize.view.auth.TokenView


class LoginActivity:AppCompatActivity(), LoginView, TokenView {
    lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //set UI size
        var screenWidth = getScreenWidth(this) / getDeviceDpi() // px to dp
        Log.d("screenWidth", screenWidth.toString())
        saveSize(screenWidth)
        setSize()

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

    //get screenWidth (pixel)
    private fun getScreenWidth(activity: Activity): Int {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            val windowMetrics = activity.windowManager.currentWindowMetrics
            val insets: Insets = windowMetrics.windowInsets
                .getInsetsIgnoringVisibility(WindowInsets.Type.systemBars())
            windowMetrics.bounds.width() - insets.left - insets.right
        } else {
            val displayMetrics = DisplayMetrics()
            activity.windowManager.defaultDisplay.getMetrics(displayMetrics)
            displayMetrics.widthPixels
        }
    }

    //get screen density (DPI)
    private fun getDeviceDpi(): Int {

        val density = resources.displayMetrics.density.toInt()
        val result = when {
            density >= 4.0 -> "xxxhdpi"
            density >= 3.0 -> "xxhdpi"
            density >= 2.0 -> "xhdpi"
            density >= 1.5 -> "hdpi"
            density >= 1.0 -> "mdpi"
            else -> "ldpi"
        }
        Log.d("density", density.toString())
        saveUISize("dpi", density)

        return density
    }

    //save UI Size (dp)
    private fun saveSize(screenWidth : Int){

        //dp to px : px = dp * density
        //px to dp : dp = px / density
        //signUp, login button
        saveUISize("authButton", ((screenWidth-60)/2 - 15) * getDeviceDpi()) //dimens.xml에서 margin, padding값 가져올 것
    }

    //set UI Size (px)
    private fun setSize(){
        //signUp button
        binding.loginSignUpBtn.layoutParams = binding.loginSignUpBtn.layoutParams.apply {
            this.width = getUISize("authButton")
        }

        //login button
        binding.loginBtn.layoutParams = binding.loginBtn.layoutParams.apply {
            this.width = getUISize("authButton")
        }
    }

    //set UI Size

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


    }

    override fun onLoginSuccess(code:Int, result: User?) {
        when(code){
            200-> {

                saveJwt(result!!.jwt)
                saveUserIdx(result!!.userIdx)
                saveUserName(result!!.userName)
                saveRefreshToken(result!!.refreshToken)
                saveUserID(result!!.userID)

                saveIsFirst(true)

                finish()
                startActivity(Intent(this, MainActivity::class.java))

                if(result!!.firstLogin){ //최초 로그인
                    startActivity(Intent(this, AddCurriculumActivity::class.java))
                }

            }
        }
    }

    override fun onLoginFailure(code:Int, message: String) {
        when(code){
            5001->{
                val tokenService = TokenService()
                tokenService.setTokenView(this)

                //만든 API 호출
                tokenService.token(getRefreshToken()!!)
            }
        }
    }

    //token
    override fun onTokenSuccess(code: Int, result: Token?) {
        when(code){
            200->{
                saveJwt(result!!.accessToken)
                saveRefreshToken(result!!.refreshToken)

            }
        }
    }

    override fun onTokenFailure(code: Int) {

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

        /*//google
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
        })*/
    }


}