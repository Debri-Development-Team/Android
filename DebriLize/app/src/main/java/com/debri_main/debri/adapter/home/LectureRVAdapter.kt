package com.debri_main.debri.adapter.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.debri_main.debri.R
import com.debri_main.debri.data.curriculum.LectureList
import com.debri_main.debri.databinding.ItemCurriculumLectureBinding

class LectureRVAdapter : RecyclerView.Adapter<LectureRVAdapter.ViewHolder>() {

    var datas = ArrayList<LectureList>()

    inner class ViewHolder(val binding : ItemCurriculumLectureBinding) : RecyclerView.ViewHolder(binding.root){

        val lectureName : TextView = binding.homeCurriculumTitleTv
        val language : TextView = binding.itemCurriculumLectureImgTagTv
        val progress : TextView = binding.itemCurriculumLectureProgressTv
        val progressBar : ProgressBar = binding.homeLectureProgressBar
        val chapterNum : TextView = binding.homeLectureChapterNumTv

        fun bind(item: LectureList) {
            lectureName.text = item.lectureName
            language.text = item.language
            progress.text = item.progressRate.toInt().toString() + "%"
            progressBar.progress = item.progressRate.toInt()
            chapterNum.text = "(1강~"+item.chNum.toString()+"강)"

            when(language.text){
                "Front" -> language.setBackgroundResource(R.drawable.border_round_transparent_front_10)
                "Back" -> language.setBackgroundResource(R.drawable.border_round_transparent_back_10)
                "C 언어" -> language.setBackgroundResource(R.drawable.border_round_transparent_c_10)
                "Python" -> language.setBackgroundResource(R.drawable.border_round_transparent_python_10)
            }
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemCurriculumLectureBinding = ItemCurriculumLectureBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(datas[position])

        //recyclerview item 클릭하면 fragment
        // (1) 리스트 내 항목 클릭 시 onClick() 호출
        holder.itemView.setOnClickListener {
            itemClickListener.onClick(it, position)
        }
    }

    // (2) 리스너 인터페이스
    interface OnItemClickListener {
        fun onClick(v: View, position: Int)
    }
    // (3) 외부에서 클릭 시 이벤트 설정
    fun setItemClickListener(onItemClickListener: OnItemClickListener) {
        this.itemClickListener = onItemClickListener
    }
    // (4) setItemClickListener로 설정한 함수 실행
    private lateinit var itemClickListener : OnItemClickListener



    //
    override fun getItemCount(): Int = datas.size

}