package com.example.debri_lize.activity

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.debri_lize.R
import com.example.debri_lize.data.PostList
import com.example.debri_lize.data.response.PostDetail
import com.example.debri_lize.data.service.PostService
import com.example.debri_lize.data.view.post.PostDetailView
import com.example.debri_lize.databinding.ActivityPostDetailBinding
import com.google.android.material.bottomsheet.BottomSheetDialog


class PostDetailActivity : AppCompatActivity(), PostDetailView {
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


        //api
        val postService = PostService()
        postService.setPostDetailView(this)

        val intent = intent //전달할 데이터를 받을 Intent
        val postIdx = intent.getIntExtra("postIdx", 0)
        postService.showPostDetail(postIdx)


    }

    override fun onPostDetailSuccess(code: Int, result: PostDetail) {
        when(code){
            200->{
                binding.postDetailTitleTv.text = result.postName
                binding.postDetailTimeTv.text = result.timeAfterCreated.toString()+"분 전"
                binding.postDetailAuthorTv.text = result.authorName
                binding.postDetailContentTv.text = result.postContents
            }
        }
    }

    override fun onPostDetailFailure(code: Int) {
        Log.d("postdetail", "ohohoho")
    }


}