package com.example.debri

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentTransaction
import com.example.debri.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity(){
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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

                R.id.timetableFragment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frm, TimetableFragment())
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }
                R.id.searchFragment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frm, SearchFragment())
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }
                R.id.menuFragment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frm, MenuFragment())
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }

            }
            false
        }
    }

    fun changeFragment(index: Int){
        when(index){
            1 -> {
//                supportFragmentManager()
//                    .beginTransaction()
//                    .replace(R.id.framelayout, fragment_main)
//                    .commit()
            }

            2 -> {
//                supportFragmentManager()
//                    .beginTransaction()
//                    .replace(R.id.framelayout, fragment_menu)
//                    .commit()
            }
        }
    }
}