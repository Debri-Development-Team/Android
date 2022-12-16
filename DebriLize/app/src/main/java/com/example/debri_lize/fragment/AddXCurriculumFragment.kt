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
import com.example.debri_lize.databinding.FragmentAddCurriculumBinding

class AddXCurriculumFragment : Fragment() {

    lateinit var binding: FragmentAddCurriculumBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddCurriculumBinding.inflate(inflater, container, false)
        return binding.root
    }


    }