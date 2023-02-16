package com.debri_main.debri.adapter.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.debri_main.debri.R
import com.debri_main.debri.data.curriculum.ChapterList
import com.debri_main.debri.data.curriculum.CompleteChapter
import com.debri_main.debri.databinding.ItemCurriculumLectureImgBinding
import com.debri_main.debri.fragment.HomeActiveFragment
import com.debri_main.debri.service.CurriculumService
import com.debri_main.debri.view.curriculum.CompleteChapterView
import kotlin.properties.Delegates

class ChapterRVAdapter(context: HomeActiveFragment) : RecyclerView.Adapter<ChapterRVAdapter.ViewHolder>(), CompleteChapterView {

    var datas = ArrayList<ChapterList>()
    var fragment = context
    var curriIdx by Delegates.notNull<Int>()

    inner class ViewHolder(val binding : ItemCurriculumLectureImgBinding) : RecyclerView.ViewHolder(binding.root){

        val chapterName : TextView = binding.itemCurriculumLectureImgTitleTv
        val chapterImg : ImageView = binding.itemCurriculumLectureImgIv
        val chapterNum : TextView = binding.itemCurriculumLectureImgProgressTv3
        val completeChapterNum : TextView = binding.itemCurriculumLectureImgProgressTv1
        val language : TextView = binding.itemCurriculumLectureImgTagTv

        fun bind(item: ChapterList, position: Int) {
            curriIdx = item.curriIdx

            chapterName.text = item.chName
            Glide.with(fragment).load(item.chapterImg).into(chapterImg)
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
            //api - 8.7 챕터 수강 완료 및 취소 api
            var curriculumService = CurriculumService()
            curriculumService.setCompleteChapterView(this)
            curriculumService.completeChapter(CompleteChapter(datas[position].chIdx, datas[position].curriIdx, datas[position].lectureIdx))

        }
    }

    //
    override fun getItemCount(): Int = datas.size

    //8.7 챕터 수강 완료 및 취소 api
    override fun onCompleteChapterSuccess(code: Int) {
        when(code){
            200 ->{
                //api - 8.3 커리큘럼 상세 조회 api : 홈
                val curriculumService = CurriculumService()
                curriculumService.setShowCurriculumDetailView(fragment)
                curriculumService.showCurriculumDetail(curriIdx)
            }
        }
    }

    override fun onCompleteChapterFailure(code: Int) {

    }

}