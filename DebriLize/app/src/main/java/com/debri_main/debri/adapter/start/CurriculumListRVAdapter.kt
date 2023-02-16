package com.debri_main.debri.adapter.start

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.debri_main.debri.R
import com.debri_main.debri.data.curriculum.Curriculum
import com.debri_main.debri.databinding.ItemCurriculumBinding

class CurriculumListRVAdapter : RecyclerView.Adapter<CurriculumListRVAdapter.ViewHolder>() {

    var datas = mutableListOf<Curriculum>()

    inner class ViewHolder(val binding : ItemCurriculumBinding) : RecyclerView.ViewHolder(binding.root){

        val statusImg : ImageView = binding.itemCurriculumStatusIv
        val curriculumName : TextView = binding.itemCurriculumNameTv
        val curriculumAuthor : TextView = binding.itemCurriculumAuthorTv
        val language : TextView = binding.itemCurriculumLangTagTv

        fun bind(item: Curriculum) {
            Glide.with(itemView).load(R.raw.curriculum).into(statusImg)
            curriculumName.text = item.curriculumName
            curriculumAuthor.text = "by" + item.curriculumAuthor
            binding.itemCurriculumDetailTv.text = item.curriDesc

            //language tag
            language.text = item.langtag

            when(language.text){
                "Front" -> language.setBackgroundResource(R.drawable.border_round_transparent_front_10)
                "Back" -> language.setBackgroundResource(R.drawable.border_round_transparent_back_10)
                "C 언어" -> language.setBackgroundResource(R.drawable.border_round_transparent_c_10)
                "Python" -> language.setBackgroundResource(R.drawable.border_round_transparent_python_10)
            }
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemCurriculumBinding = ItemCurriculumBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
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