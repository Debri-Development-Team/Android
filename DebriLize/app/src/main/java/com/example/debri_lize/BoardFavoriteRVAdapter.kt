package com.example.debri_lize

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.debri_lize.data.board.Board
import com.example.debri_lize.databinding.ItemBoardBinding

class BoardFavoriteRVAdapter : RecyclerView.Adapter<BoardFavoriteRVAdapter.ViewHolder>() {

    var datas_f = mutableListOf<Board>()

    inner class ViewHolder(val binding : ItemBoardBinding) : RecyclerView.ViewHolder(binding.root){


        val boardName : TextView = binding.itemBoardNameTv

        fun bind(item: Board) {
            boardName.text = item.boardName
            binding.itemBoardIv.setImageResource(R.drawable.ic_favorite_on)
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemBoardBinding = ItemBoardBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(datas_f[position])

        //recyclerview item 클릭하면 fragment
        // (1) 리스트 내 항목 클릭 시 onClick() 호출
        holder.itemView.setOnClickListener {
            itemClickListener.onClick(it, position)
        }

        holder.binding.itemBoardIv.setOnClickListener{
            holder.binding.itemBoardIv.setImageResource(R.drawable.ic_favorite_off)
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
    override fun getItemCount(): Int = datas_f.size

    //검색어 입력시 필터
    fun filterList(filteredList: ArrayList<Board>) {
        datas_f = filteredList
        notifyDataSetChanged()
    }

}