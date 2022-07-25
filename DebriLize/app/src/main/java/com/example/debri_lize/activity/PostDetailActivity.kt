package com.example.debri_lize.activity

import android.content.res.Resources
import android.os.Bundle
import android.view.Gravity
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.debri_lize.R
import com.example.debri_lize.databinding.ActivityPostDetailBinding
import com.google.android.material.bottomsheet.BottomSheetDialog


class PostDetailActivity : AppCompatActivity() {
    lateinit var binding : ActivityPostDetailBinding
    private var likeTF : Boolean = false
    private var scrapTF : Boolean = false

    private fun Int.toPx(): Int = (this * Resources.getSystem().displayMetrics.density).toInt()

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



        /* getBackground 함수가 없어서 현재 background 속성이 무엇인지 알 수 없음
        *   -> Boolean 변수 사용하여 해결
        *   -> 게시물 나갔다가 들어와도 유지되는지 확인 필요
        */
        //추천 버튼
        binding.postDetailMenuLikeLayout.setOnClickListener {
            likeTF = !likeTF
            if(likeTF) {
                binding.postDetailMenuLikeLayout.setBackgroundResource(R.drawable.border_round_white_transparent_10)
                binding.postDetailMenuLikeIv.setImageResource(R.drawable.ic_like_white)
            }else {
                binding.postDetailMenuLikeLayout.setBackgroundResource(R.drawable.border_round_debri_darkmode_10)
                binding.postDetailMenuLikeIv.setImageResource(R.drawable.ic_like_debri)
            }

        }


        //스크랩 버튼
        binding.postDetailMenuScrapLayout.setOnClickListener {
            scrapTF = !scrapTF
            if(scrapTF){
                binding.postDetailMenuScrapLayout.setBackgroundResource(R.drawable.border_round_white_transparent_10)
                binding.postDetailMenuScrapTv.setTextColor(ContextCompat.getColor(this@PostDetailActivity, R.color.white))
                binding.postDetailMenuScrapIv.setImageResource(R.drawable.ic_scrap_white)
            }else{
                binding.postDetailMenuScrapLayout.setBackgroundResource(R.drawable.border_round_transparent_debri_10)
                binding.postDetailMenuScrapTv.setTextColor(ContextCompat.getColor(this@PostDetailActivity, R.color.darkmode_background))
                binding.postDetailMenuScrapIv.setImageResource(R.drawable.ic_scrap_darkmode)



                //스크랩 확인 토스트메시지
                var scrapToast = layoutInflater.inflate(R.layout.toast_scrap,null)
                var toast = Toast(this)
                toast.view = scrapToast
                toast.setGravity(Gravity.CENTER_HORIZONTAL,0,0)
                toast.show()

            }

        }
    }

}