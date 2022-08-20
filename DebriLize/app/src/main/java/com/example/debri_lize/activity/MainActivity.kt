package com.example.debri_lize.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.debri_lize.R
import com.example.debri_lize.databinding.ActivityMainBinding
import com.example.debri_lize.fragment.BoardFragment
import com.example.debri_lize.fragment.ClassFragment
import com.example.debri_lize.fragment.HomeFragment
import com.example.debri_lize.fragment.CurriculumFragment

class MainActivity : AppCompatActivity() {
    lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater) //binding 초기화
        setContentView(binding.root)

        initBottomNavigation()

    }


    //fragment를 전환해 각각의 화면을 보여줌
    private fun initBottomNavigation(){

        supportFragmentManager.beginTransaction()
            .replace(R.id.main_frm, HomeFragment())
            .commitAllowingStateLoss()

        binding.mainBnv.setOnItemSelectedListener{ item ->
            when (item.itemId) {

                R.id.homeFragment -> { //homeFragment를 눌렀을 때
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frm, HomeFragment()) //homeFragment보여지기
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }

                R.id.classFragment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frm, ClassFragment())
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }
                R.id.boardFragment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frm, BoardFragment())
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }
                R.id.curriculumFragment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frm, CurriculumFragment())
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }

            }
            false
        }
    }
}