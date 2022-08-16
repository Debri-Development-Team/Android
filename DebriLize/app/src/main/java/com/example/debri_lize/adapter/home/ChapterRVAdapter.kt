package com.example.debri_lize.adapter.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.debri_lize.R
import com.example.debri_lize.data.curriculum.ChapterList
import com.example.debri_lize.databinding.ItemCurriculumLectureImgBinding
import org.w3c.dom.Text

class ChapterRVAdapter : RecyclerView.Adapter<ChapterRVAdapter.ViewHolder>() {

    var datas = ArrayList<ChapterList>()

    inner class ViewHolder(val binding : ItemCurriculumLectureImgBinding) : RecyclerView.ViewHolder(binding.root){

        val chapterName : TextView = binding.itemCurriculumLectureImgTitleTv
        val chapterImg : ImageView = binding.itemCurriculumLectureImgIv
        val chapterNum : TextView = binding.itemCurriculumLectureImgProgressTv3
        val completeChapterNum : TextView = binding.itemCurriculumLectureImgProgressTv1
        val language : TextView = binding.itemCurriculumLectureImgTagTv

        fun bind(item: ChapterList, position: Int) {
            chapterName.text = item.chName
            item.chapterImg?.let { chapterImg.setImageResource(it) }
            chapterNum.text = item.chNum.toString() //현재 진행된 chapter 수
            completeChapterNum.text = item.completeChNum.toString() //총 chapter 수
            language.text = item.language

            when(language.text){
                "Front" -> language.setBackgroundResource(R.drawable.border_round_transparent_front_10)
                "Back" -> language.setBackgroundResource(R.drawable.border_round_transparent_back_10)
                "C 언어" -> language.setBackgroundResource(R.drawable.border_round_transparent_c_10)
                "Python" -> language.setBackgroundResource(R.drawable.border_round_transparent_python_10)
            }

            if(position%2==0){
                binding.paddingLeft.visibility = View.VISIBLE
                binding.paddingRight.visibility = View.GONE
            }else{
                binding.paddingLeft.visibility = View.GONE
                binding.paddingRight.visibility = View.VISIBLE
            }

            if(item.chComplete=="TRUE"){ //완료된 상태
                binding.itemCurriculumLectureImgCheckboxIv.setImageResource(R.drawable.ic_checkbox_on)
            }else{ //미완료인 상태
                binding.itemCurriculumLectureImgCheckboxIv.setImageResource(R.drawable.ic_check_box)
            }
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemCurriculumLectureImgBinding = ItemCurriculumLectureImgBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(datas[position], position)

        holder.binding.itemCurriculumLectureImgLayout.setOnClickListener{
            //api 추가필요
            if(datas[position].chComplete=="TRUE"){ //완료된 상태
                holder.binding.itemCurriculumLectureImgCheckboxIv.setImageResource(R.drawable.ic_check_box)

            }else{ //미완료인 상태
                holder.binding.itemCurriculumLectureImgCheckboxIv.setImageResource(R.drawable.ic_checkbox_on)

            }

        }
    }

    //
    override fun getItemCount(): Int = datas.size

}