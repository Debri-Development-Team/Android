package com.example.debri_lize.activity.auth

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.debri_lize.databinding.ActivitySignupEmailBinding

class SignUpEmailActivity : AppCompatActivity() {

    lateinit var binding: ActivitySignupEmailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupEmailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //이메일 인증 추가


        //인증 성공 시
        certificateSuccess()

        //back
        binding.signUpEmailBackIv.setOnClickListener{
            finish()
        }

    }

    //인증 성공
    private fun certificateSuccess(){
        SignUpActivity.Singleton.userID = binding.signUpIdEt.toString()
        finish()
    }
}