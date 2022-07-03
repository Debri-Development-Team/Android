package com.example.debri

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.example.debri.databinding.ItemRecyclerLectureBinding
import java.nio.file.Files.size


class LectureAdapter(var items:ArrayList<LectureData>) : RecyclerView.Adapter<LectureAdapter.ViewHolder>(),
    Filterable {

    var TAG = "LectureAdapter"

    var filteredLecture = ArrayList<LectureData>()
    var itemFilter = ItemFilter()

    init {
        filteredLecture.addAll(items)
    }

    interface OnItemClickListener{
        fun onClick(v: View, position: Int)

    }

    override fun onCreateViewHolder(
        viewGroup: ViewGroup,
        viewType: Int
    ): LectureAdapter.ViewHolder {
        val binding: ItemRecyclerLectureBinding = ItemRecyclerLectureBinding.inflate(LayoutInflater.from(viewGroup.context),viewGroup,false)
        return ViewHolder(binding)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])

        //recyclerview item 클릭하면 fragment
        // (1) 리스트 내 항목 클릭 시 onClick() 호출
        holder.itemView.setOnClickListener {
            itemClickListener?.onClick(it, position)
        }

    }

    // (3) 외부에서 클릭 시 이벤트 설정
    fun setItemClickListener(onItemClickListener: OnItemClickListener) {
        this.itemClickListener = onItemClickListener
    }
    // (4) setItemClickListener로 설정한 함수 실행
    private lateinit var itemClickListener : OnItemClickListener

    override fun getItemCount(): Int = items.size

    // var itemClickListener:OnItemClickListener?=null

    inner class ViewHolder(val binding:ItemRecyclerLectureBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(data : LectureData){
            binding.lectureNameTv.text = data.lectureName
            binding.lectureDayofweekTv.text = data.dayofweek
            binding.lectureTimeTv.text = data.time
        }
    }

    override fun getFilter(): Filter {
        return itemFilter
    }

    inner class ItemFilter : Filter(){
        override fun performFiltering(charSequence: CharSequence?): FilterResults {
            val filterString = charSequence.toString()
            val results = FilterResults()

            //검색이 필요없을 경우를 위해 원본 배열을 복제
            val filteredList: ArrayList<LectureData> = ArrayList()
            //공백제외 아무런 값이 없을 경우 -> 원본 배열
            if (filterString.trim { it <= ' ' }.isEmpty()) {
                results.values = items
                results.count = items.size

                return results
            }else {
                for (item in items) {
                    if (item.lectureName.contains(filterString)) {
                        filteredList.add(item)
                    }
                }
            }
            results.values = filteredList
            results.count = filteredList.size

            return results
        }

        override fun publishResults(charSequence: CharSequence?, filterResults: FilterResults?) {
            filteredLecture.clear()
            filteredLecture.addAll(filterResults?.values as ArrayList<LectureData>)
            notifyDataSetChanged()
        }

    }
}