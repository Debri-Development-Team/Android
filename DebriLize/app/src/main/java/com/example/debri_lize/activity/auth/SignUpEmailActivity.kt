package com.example.debri_lize.activity.auth

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.debri_lize.activity.AddCurriculumActivity
import com.example.debri_lize.activity.MainActivity
import com.example.debri_lize.data.auth.Email
import com.example.debri_lize.data.auth.UserLogin
import com.example.debri_lize.databinding.ActivitySignupEmailBinding
import com.example.debri_lize.service.AuthService
import com.example.debri_lize.utils.*
import com.example.debri_lize.view.auth.EmailView
import kotlin.properties.Delegates

class SignUpEmailActivity : AppCompatActivity(), EmailView {

    lateinit var binding: ActivitySignupEmailBinding
    var emailCode : Int = 0
    var timeout : Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupEmailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //인증 코드 보내기
        binding.signUpEmailSendCodeBtn.setOnClickListener {
            //인증 코드 받기
            var emailAddress : String = binding.signUpEmailIdEt.text.toString()

            //1.5 이메일 인증 API
            val authService = AuthService()
            authService.setEmailView(this)
            authService.getCode(emailAddress)

            //인증 코드 이메일 전송
            sendEmail(emailAddress)

            //timeout으로 타이머 기능 추가

        }

        //인증 코드 재전송
        binding.signUpEmailResendCodeBtn.setOnClickListener {
            //인증 코드 받기
            var emailAddress : String = binding.signUpEmailIdEt.text.toString()

            //1.5 이메일 인증 API
            val authService = AuthService()
            authService.setEmailView(this)
            authService.getCode(emailAddress)

            //인증 코드 이메일 전송
            sendEmail(emailAddress)

            //timeout으로 타이머 기능 추가
        }

        //인증하기
        binding.signUpEmailCodeCheckBtn.setOnClickListener {
            var inputCode : String = binding.signUpEmailCodeEt.text.toString()
            if(emailCode == inputCode.toInt()){
                //인증 성공
                SignUpActivity.Singleton.userID = binding.signUpEmailIdEt.text.toString()
                SignUpActivity.Singleton.certificationTF = true
                finish()
            }else{
                //인증 실패

            }
        }


        //back
        binding.signUpEmailBackIv.setOnClickListener{
            finish()
        }

    }



    //인증 코드 받기
    override fun onEmailSuccess(code: Int, result: Email) {
        when(code){
            200-> {
                emailCode = result.code
                timeout = result.timeout
            }
        }
    }

    override fun onEmailFailure(code: Int, message: String) {

    }

    //인증 코드 이메일 보내기
    @SuppressLint("QueryPermissionsNeeded")
    private fun sendEmail(emailAddress: String) {
        val emailTitle = "메일 제목입니다"
        val emailContent = "메일 내용입니다" + emailCode.toString()

        val intent = Intent(Intent.ACTION_SENDTO) // 메일 전송 설정
            .apply {
                type = "text/plain" // 데이터 타입 설정
                data = Uri.parse("mailto:") // 이메일 앱에서만 인텐트 처리되도록 설정

                putExtra(Intent.EXTRA_EMAIL, arrayOf(emailAddress)) // 메일 수신 주소 목록
                putExtra(Intent.EXTRA_SUBJECT, emailTitle) // 메일 제목 설정
                putExtra(Intent.EXTRA_TEXT, emailContent) // 메일 본문 설정
            }

        if (intent.resolveActivity(packageManager) != null) {
            startActivity(Intent.createChooser(intent, "메일 전송하기"))
        } else {
            Toast.makeText(this, "메일을 전송할 수 없습니다", Toast.LENGTH_LONG).show()
        }
    }
}