package com.example.debri

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.debri.data.User
import com.example.debri.data.service.AuthService
import com.example.debri.data.view.LoginView
import com.example.debri.databinding.ActivityLoginBinding
import com.example.debri.data.response.Result

class LoginActivity:AppCompatActivity(), LoginView {
    lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //회원가입버튼 누르면 activity_login.xml -> activity_signup.xml로 이동
        binding.loginSignUpTv.setOnClickListener{
            startActivity(Intent(this, SignUpActivity::class.java))
        }

        //로그인 버튼을 누르면 아래 코드 실행
        binding.loginSignInBtn.setOnClickListener{
            login()
        }

        //text용
        binding.loginCloseIv.setOnClickListener{
            startActivity(Intent(this, MainActivity::class.java))
            finish()
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
        val id : String = binding.loginIdEt.text.toString()
        val password : String = binding.loginPasswordEt.text.toString()


        val authService = AuthService()
        authService.setLoginView(this)

        //만든 API 호출
        authService.login(User(id, "", "", password))

        //user가 null일 경우
        //Toast.makeText(this, "회원 정보가 존재하지 않습니다.", Toast.LENGTH_SHORT).show()

    }

    //JWT를 저장하는 함수 : 서버
    private fun saveJwt2(jwt: String) {
        val spf = getSharedPreferences("auth2" , MODE_PRIVATE)
        val editor = spf.edit()

        editor.putString("jwt", jwt)
        editor.apply()
    }

    //
    fun saveUserIdx(context: Context, userIdx : Int){
        val spf = context.getSharedPreferences("auth", MODE_PRIVATE)
        val editor = spf.edit()

        editor.putInt("userIdx", userIdx)
        editor.apply()
    }

    //로그인이 끝나면 MainActivity로 전환
    private fun startMainActivity(){
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    override fun onLoginSuccess(code:Int, result:Result) {
        Log.d("login", "success")
        when(code){
            //개발할 때는 userIdx 저장이 필요할수도
            200-> {
                //saveJwt2(result2.jwt)
                saveUserIdx(this, result.userIdx)
                startMainActivity()
            }
        }
    }

    override fun onLoginFailure() {
        //실패처리 코드추가
    }
}