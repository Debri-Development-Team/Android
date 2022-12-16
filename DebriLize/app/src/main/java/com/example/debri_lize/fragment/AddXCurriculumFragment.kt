package com.example.debri_lize.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
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