package com.example.debri

import android.graphics.Color
import android.graphics.Color.YELLOW
import android.graphics.Color.blue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.debri.databinding.ItemRecyclerChecklistBinding

class UserCheckRVAdapter(var datas:ArrayList<UserChecklist>) : RecyclerView.Adapter<UserCheckRVAdapter.ViewHolder>() {

    interface OnItemClickListener{
        fun onClick(v: View, position: Int)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): UserCheckRVAdapter.ViewHolder {
        val binding: ItemRecyclerChecklistBinding = ItemRecyclerChecklistBinding.inflate(LayoutInflater.from(viewGroup.context),viewGroup,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(datas[position])

        holder.itemView.setOnClickListener {
            itemClickListener?.onClick(it, position)
            if(datas[position].TF) holder.itemView.setBackgroundColor(Color.parseColor("#87CEEB"))
            else holder.itemView.setBackgroundColor(Color.parseColor("#D3D3D3"))

        }

    }

    fun setItemClickListener(onItemClickListener: OnItemClickListener) {
        this.itemClickListener = onItemClickListener
    }

    private lateinit var itemClickListener : UserCheckRVAdapter.OnItemClickListener

    override fun getItemCount(): Int = datas.size

    inner class ViewHolder(val binding: ItemRecyclerChecklistBinding): RecyclerView.ViewHolder(binding.root){

        fun bind(data : UserChecklist){
            binding.lectureTitleTv.text = data.lectureName

        }

    }




}