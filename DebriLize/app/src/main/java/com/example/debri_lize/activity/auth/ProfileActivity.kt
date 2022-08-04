package com.example.debri_lize.activity.auth

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.debri_lize.databinding.ActivityProfileBinding
import com.example.debri_lize.utils.ApplicationClass

class ProfileActivity : AppCompatActivity() {
    lateinit var binding : ActivityProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater) //binding 초기화
        setContentView(binding.root)

        //click logout
        binding.profileMenuLogoutTv.setOnClickListener{
            logout()
            val intent = Intent(this, LoginActivity::class.java)
            //모든 화면 초기화
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
            startActivity(intent)
        }
    }

    private fun logout(){ //jwt=0으로 만들기 : 저장된 값X
        val editor = ApplicationClass.mSharedPreferences.edit()
        editor.remove("jwt")
        editor.apply()
    }
}