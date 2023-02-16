package com.debri_main.debri.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.debri_main.debri.R
import com.debri_main.debri.databinding.ActivityMainBinding
import com.debri_main.debri.fragment.*
import com.debri_main.debri.fragment.BoardFragment
import com.debri_main.debri.fragment.ClassFragment
import com.debri_main.debri.fragment.HomeFragment
import com.debri_main.debri.fragment.CurriculumFragment

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

                R.id.profileFragment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frm, ProfileFragment())
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }

            }
            false
        }
    }
}