package com.example.debri_lize

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.debri_lize.data.Lecture
import com.example.debri_lize.databinding.ItemClassFavoriteBinding

class ClassFavoriteRVAdapter : RecyclerView.Adapter<ClassFavoriteRVAdapter.ViewHolder>() {

    var datas_classf = mutableListOf<Lecture>()

    inner class ViewHolder(val binding:ItemClassFavoriteBinding) : RecyclerView.ViewHolder(binding.root){

        val lectureName : TextView = binding.itemClassFavTitleTv
        val chapterNum : TextView = binding.itemClassFavChapterTv
        val language : TextView = binding.itemClassFavTagLanguageTv
        val media : TextView = binding.itemClassFavMediaTagTv
        val price : TextView = binding.itemClassFavPriceTagTv

        fun bind(item: Lecture){
            lectureName.text = item.lectureName
            chapterNum.text = "(" + item.chapterNum.toString()+"챕터)"
            language.text = item.language
            media.text = "#" + item.media
            price.text = "#" + item.price
        }
    }


    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemClassFavoriteBinding = ItemClassFavoriteBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(datas_classf[position])

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

    override fun getItemCount(): Int = datas_classf.size

}