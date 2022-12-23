package com.example.debri_lize.activity.auth

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.debri_lize.R
import com.example.debri_lize.activity.AddCurriculumActivity
import com.example.debri_lize.activity.MainActivity
import com.example.debri_lize.data.auth.Email
import com.example.debri_lize.data.auth.UserLogin
import com.example.debri_lize.databinding.ActivitySignupEmailBinding
import com.example.debri_lize.service.AuthService
import com.example.debri_lize.utils.*
import com.example.debri_lize.utils.ApplicationClass.Companion.TAG
import com.example.debri_lize.view.auth.EmailView
import io.reactivex.Completable.timer
import io.reactivex.Single
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.*
import kotlin.concurrent.thread
import kotlin.properties.Delegates

class SignUpEmailActivity : AppCompatActivity(), EmailView {

    lateinit var binding: ActivitySignupEmailBinding

    var emailAddress : String? = ""
    var emailCode : Int = 0
    var timeout : Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupEmailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //인증 코드 보내기
        binding.signUpEmailSendCodeBtn.setOnClickListener {

            //인증 코드 받기
            emailAddress = binding.signUpEmailIdEt.text.toString()

            //이메일 입력 안한 경우
            if(emailAddress == "")  {
                binding.signUpEmailIdLayout.setBackgroundResource(R.drawable.border_round_red_transparent_10)
                binding.signUpEmailIdTv.setBackgroundResource(R.drawable.border_line_left_red_2)

                //1초 후 효과 없애기
                thread(start = true){
                    Thread.sleep(1300)
                    runOnUiThread{
                        binding.signUpEmailIdLayout.setBackgroundResource(R.drawable.border_round_white_transparent_10)
                        binding.signUpEmailIdTv.setBackgroundResource(R.drawable.border_line_left)
                    }
                }
            }else{
                //1.5 이메일 인증 API
                val authService = AuthService()
                authService.setEmailView(this)
                authService.getCode(emailAddress!!)
            }




        }

        //인증하기
        binding.signUpEmailCodeCheckBtn.setOnClickListener {

            if(emailAddress == "")  {
                binding.signUpEmailIdLayout.setBackgroundResource(R.drawable.border_round_red_transparent_10)
                binding.signUpEmailIdTv.setBackgroundResource(R.drawable.border_line_left_red_2)

                //1초 후 효과 없애기
                thread(start = true){
                    Thread.sleep(1300)
                    runOnUiThread{
                        binding.signUpEmailIdLayout.setBackgroundResource(R.drawable.border_round_white_transparent_10)
                        binding.signUpEmailIdTv.setBackgroundResource(R.drawable.border_line_left)
                    }
                }
            }

            var inputCode : String = binding.signUpEmailCodeEt.text.toString()
            val itCode : Int = if(inputCode=="") -1 else inputCode.toInt()

            if(emailCode == itCode && emailCode!=0){
                //인증 성공
                SignUpActivity.Singleton.userID = binding.signUpEmailIdEt.text.toString()
                SignUpActivity.Singleton.certificationTF = true
                finish()
            }else{
                //인증 실패
                binding.signUpEmailCodeLayout.setBackgroundResource(R.drawable.border_round_red_transparent_10)
                binding.signUpEmailCodeTv.setBackgroundResource(R.drawable.border_line_left_red_2)

                //1초 후 효과 없애기
                thread(start = true){
                    Thread.sleep(1300)
                    runOnUiThread{
                        binding.signUpEmailCodeLayout.setBackgroundResource(R.drawable.border_round_white_transparent_10)
                        binding.signUpEmailCodeTv.setBackgroundResource(R.drawable.border_line_left)
                    }
                }

                //toast message
                var codexToast = layoutInflater.inflate(R.layout.toast_prepare,null)
                codexToast.findViewById<TextView>(R.id.toast_prepare_roadmap_tv).text = "인증코드가 일치하지 않습니다!"
                codexToast.findViewById<TextView>(R.id.toast_prepare_tv).text = ""
                codexToast.findViewById<TextView>(R.id.toast_update_tv).text = "다시 입력하거나, 코드를 다시 받아주세요"
                var toast = Toast(this)
                toast.view = codexToast
                toast.setGravity(Gravity.CENTER_HORIZONTAL,0,0)
                toast.show()

            }

        }

        //back
        binding.signUpEmailBackIv.setOnClickListener{
            finish()
            saveSendEmailTF(false)
        }


        //focus effect
        setFocus()

    }

    override fun onStart() {
        super.onStart()
        Log.d("sendEmail", getSendEmailTF().toString())
        if(getSendEmailTF()==true){
            binding.signUpEmailSendCodeBtn.isEnabled = false //이메일 전송 버튼 비활성화
        }
    }

    //인증 코드 받기
    override fun onEmailSuccess(code: Int, result: Email) {
        when(code){
            200-> {
                emailCode = result.code
                timeout = result.timeout

                //인증 코드 이메일 전송
                sendEmail(emailAddress!!)

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

        //timer
        startTimer()
    }

    //timer
    private fun startTimer(){
        saveSendEmailTF(sendEmailTF = true)

        var mSecond: Long = timeout.toLong()

        val sdf = SimpleDateFormat("mm:ss")
        val mTimer = Timer()
        // 반복적으로 사용할 TimerTask
        var mTimerTask = object : TimerTask() {
            override fun run() {
                val mHandler = Handler(Looper.getMainLooper())
                mHandler.postDelayed({
                    // 반복실행할 구문
                    mSecond = mSecond - 1000
                    Log.d(TAG,"$mSecond")

                    if (mSecond <= 0) {
                        //타이머 종료
                        mTimer.cancel()
                        binding.signUpEmailCodeTimeTv2.text = "00:00"

                        emailCode = 0 //인증코드 비활성화

                        saveSendEmailTF(false)
                        binding.signUpEmailSendCodeBtn.isEnabled = true //이메일 전송 버튼 활성화
                    }

                    val time = sdf.format(mSecond)
                    binding.signUpEmailCodeTimeTv2.text = time


                }, 0)
            }
        }
        mTimer.schedule(mTimerTask, 0, 1000)
    }

    private fun setFocus(){
        //email id focus
        binding.signUpEmailIdEt.setOnFocusChangeListener(object : View.OnFocusChangeListener{
            override fun onFocusChange(view: View, hasFocus: Boolean) {
                if(hasFocus){
                    //포커스 시
                    binding.signUpEmailIdLayout.setBackgroundResource(R.drawable.border_round_debri_transparent_10)
                    binding.signUpEmailIdTv.setBackgroundResource(R.drawable.border_line_left_debri)
                }else{
                    //포커스 뺏겼을 떄
                    binding.signUpEmailIdLayout.setBackgroundResource(R.drawable.border_round_white_transparent_10)
                    binding.signUpEmailIdTv.setBackgroundResource(R.drawable.border_line_left)
                }
            }
        })
        //email code focus
        binding.signUpEmailCodeEt.setOnFocusChangeListener(object : View.OnFocusChangeListener{
            override fun onFocusChange(view: View, hasFocus: Boolean) {
                if(hasFocus){
                    //포커스 시
                    binding.signUpEmailCodeLayout.setBackgroundResource(R.drawable.border_round_debri_transparent_10)
                    binding.signUpEmailCodeTv.setBackgroundResource(R.drawable.border_line_left_debri)
                }else{
                    //포커스 뺏겼을 떄
                    binding.signUpEmailCodeLayout.setBackgroundResource(R.drawable.border_round_white_transparent_10)
                    binding.signUpEmailCodeTv.setBackgroundResource(R.drawable.border_line_left)
                }
            }
        })
    }

}