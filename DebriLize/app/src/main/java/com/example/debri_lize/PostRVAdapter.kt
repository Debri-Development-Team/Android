package com.example.debri_lize

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.debri_lize.data.Post
import com.example.debri_lize.databinding.ItemPostBinding

class PostRVAdapter : RecyclerView.Adapter<PostRVAdapter.ViewHolder>() {

    var datas = mutableListOf<Post>()

    inner class ViewHolder(val binding : ItemPostBinding) : RecyclerView.ViewHolder(binding.root){

        val title : TextView = binding.itemPostTitle
        val time : TextView = binding.itemPostTitle
        var comment : TextView = binding.itemPostCountCommentTv

        fun bind(item: Post) {
            //viewpager만들고 item.coverImg[position]
            title.text = item.postName
            //time.text = item.time
            //comment.text = item.comment
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemPostBinding = ItemPostBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
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

    //검색어 입력시 필터
    fun filterList(filteredList: ArrayList<Post>) {
        datas = filteredList
        notifyDataSetChanged()
    }

}