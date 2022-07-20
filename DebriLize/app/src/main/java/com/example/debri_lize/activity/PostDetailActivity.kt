package com.example.debri_lize.activity

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.debri_lize.R
import com.example.debri_lize.databinding.ActivityPostDetailBinding
import com.google.android.material.bottomsheet.BottomSheetDialog


class PostDetailActivity : AppCompatActivity() {
    lateinit var binding : ActivityPostDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPostDetailBinding.inflate(layoutInflater) //binding 초기화
        setContentView(binding.root)

        //bottom sheet complain
        val bottomSheetView = layoutInflater.inflate(com.example.debri_lize.R.layout.fragment_bottom_sheet_complain, null)
        val bottomSheetDialog = BottomSheetDialog(this)
        bottomSheetDialog.setContentView(bottomSheetView)

        binding.postDetailComplainTv.setOnClickListener {
            bottomSheetDialog.show()
        }

        bottomSheetView.findViewById<TextView>(R.id.bottom_sheet_close_tv).setOnClickListener {
            bottomSheetDialog.dismiss()
        }


    }

}