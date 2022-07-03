package com.example.debri

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.debri.data.User
import com.example.debri.data.service.AuthService
import com.example.debri.data.view.SignUpView
import com.example.debri.databinding.ActivitySignupBinding
import com.example.debri.data.response.Result

class SignUpActivity:AppCompatActivity(), SignUpView {
    lateinit var binding: ActivitySignupBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //가입완료 버튼을 누르면 회원가입 끝
        binding.signUpSignUpBtn.setOnClickListener{
            signUp()

            //finish()
        }

        //약정 동의1
        binding.signUpBox1OffIv.setOnClickListener{
            setBox1Status(false)
        }
        binding.signUpBox1OnIv.setOnClickListener{
            setBox1Status(true)
        }

        //약정 동의2
        binding.signUpBox2OffIv.setOnClickListener{
            setBox2Status(false)
        }
        binding.signUpBox2OnIv.setOnClickListener{
            setBox2Status(true)
        }

        //약정 동의3
        binding.signUpBox3OffIv.setOnClickListener{
            setBox3Status(false)
        }
        binding.signUpBox3OnIv.setOnClickListener{
            setBox3Status(true)
        }
    }

    fun setBox1Status(isBox : Boolean){
        if(isBox){
            binding.signUpBox1OffIv.visibility = View.VISIBLE
            binding.signUpBox1OnIv.visibility = View.GONE
        }else{
            binding.signUpBox1OffIv.visibility = View.GONE
            binding.signUpBox1OnIv.visibility = View.VISIBLE
        }
    }

    fun setBox2Status(isBox : Boolean){
        if(isBox){
            binding.signUpBox2OffIv.visibility = View.VISIBLE
            binding.signUpBox2OnIv.visibility = View.GONE
        }else{
            binding.signUpBox2OffIv.visibility = View.GONE
            binding.signUpBox2OnIv.visibility = View.VISIBLE
        }
    }

    fun setBox3Status(isBox : Boolean){
        if(isBox){
            binding.signUpBox3OffIv.visibility = View.VISIBLE
            binding.signUpBox3OnIv.visibility = View.GONE
        }else{
            binding.signUpBox3OffIv.visibility = View.GONE
            binding.signUpBox3OnIv.visibility = View.VISIBLE
        }
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
        result.signUpResult = true

        finish()
    }

    override fun onSignUpFailure() {
        Log.d("fail", "fail")
    }
}