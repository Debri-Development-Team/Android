package com.example.debri_lize

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.debri_lize.data.board.Board
import com.example.debri_lize.databinding.ItemBoardFavoriteBinding

class BoardFavoriteRVAdapter : RecyclerView.Adapter<BoardFavoriteRVAdapter.ViewHolder>() {

    var datas_f = mutableListOf<Board>()

    inner class ViewHolder(val binding : ItemBoardFavoriteBinding) : RecyclerView.ViewHolder(binding.root){

        val boardName : TextView = binding.itemBoardFavoriteTv1

        fun bind(item: Board) {
            boardName.text = item.boardName
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemBoardFavoriteBinding = ItemBoardFavoriteBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(datas_f[position])

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
    override fun getItemCount(): Int = datas_f.size


}