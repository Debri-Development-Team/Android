package com.debri_main.debri.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.debri_main.debri.R
import com.debri_main.debri.activity.AddCurriculumChooseActivity
import com.debri_main.debri.databinding.FragmentAddCurriculumBinding

class AddCurriculumFragment : Fragment() {

    lateinit var binding: FragmentAddCurriculumBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddCurriculumBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()

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