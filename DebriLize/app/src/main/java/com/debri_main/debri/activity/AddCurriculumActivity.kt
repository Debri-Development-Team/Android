package com.debri_main.debri.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import com.debri_main.debri.databinding.ActivityAddCurriculumBinding

class AddCurriculumActivity : AppCompatActivity() {
    lateinit var binding : ActivityAddCurriculumBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddCurriculumBinding.inflate(layoutInflater) //binding 초기화
        setContentView(binding.root)

        binding.addCurriculumCircleIv.setOnClickListener{
            val intent = Intent(this, AddCurriculumChooseActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.addCurriculumCircleIv.setOnTouchListener(object : View.OnTouchListener {
            override fun onTouch(view: View?, event: MotionEvent?): Boolean {
                when (event?.action) {
                    MotionEvent.ACTION_DOWN -> {
                        binding.addCurriculumCircleIv.visibility = View.GONE
                        binding.addCurriculumCircleTouchIv.visibility = View.VISIBLE
                    }
                    MotionEvent.ACTION_UP -> {
                        binding.addCurriculumCircleIv.visibility = View.VISIBLE
                        binding.addCurriculumCircleTouchIv.visibility = View.GONE
                        binding.addCurriculumCircleIv.performClick()
                    }
                }


                //리턴값이 false면 동작 안됨
                return true //or false
            }


        })

    }




}