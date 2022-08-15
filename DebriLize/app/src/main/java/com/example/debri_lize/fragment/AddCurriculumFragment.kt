package com.example.debri_lize.fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.debri_lize.R
import com.example.debri_lize.activity.AddCurriculumChooseActivity
import com.example.debri_lize.activity.MainActivity
import com.example.debri_lize.activity.auth.ProfileActivity
import com.example.debri_lize.data.board.Board
import com.example.debri_lize.data.board.BoardFavorite
import com.example.debri_lize.databinding.FragmentAddCurriculumBinding
import com.example.debri_lize.service.CurriculumService

class AddCurriculumFragment : Fragment() {

    lateinit var context: MainActivity

    lateinit var binding: FragmentAddCurriculumBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddCurriculumBinding.inflate(inflater, container, false)
        return binding.root
    }

    //context 받아오기기
    override fun onAttach(context: Context) {
        super.onAttach(context)
        this.context = context as MainActivity
    }

    override fun onStart() {
        super.onStart()

        //click userImg -> profile
        binding.addCurriculumDebriUserIv.setOnClickListener{
            val intent = Intent(context, ProfileActivity::class.java)
            startActivity(intent)
        }

        binding.homeCurriculumPreviousIv.setOnClickListener{

            //fragment to fragment
            val passBundleBFragment = HomeFragment()
            activity?.supportFragmentManager!!.beginTransaction()
                .replace(R.id.main_frm, passBundleBFragment)
                .commit()
        }

        binding.addCurriculumCircleLayout.setOnClickListener{
            val intent = Intent(context, AddCurriculumChooseActivity::class.java)
            startActivity(intent)
        }

        binding.addCurriculumCircleLayout.setOnTouchListener(object : View.OnTouchListener {
            override fun onTouch(view: View?, event: MotionEvent?): Boolean {
                when (event?.action) {
                    MotionEvent.ACTION_DOWN -> {
                        binding.addCurriculumCircleLayout.setBackgroundResource(R.drawable.circle_debri_debri_8)
                    }
                    MotionEvent.ACTION_UP -> {
                        binding.addCurriculumCircleLayout.setBackgroundResource(R.drawable.circle_debri_transparent_8)
                        binding.addCurriculumCircleLayout.performClick()
                    }
                }
                //리턴값이 false면 동작 안됨
                return true //or false
            }
        })

    }

    }