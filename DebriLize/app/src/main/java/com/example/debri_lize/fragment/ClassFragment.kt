package com.example.debri_lize.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.debri_lize.BoardFavoriteRVAdapter
import com.example.debri_lize.ClassFavoriteRVAdapter
import com.example.debri_lize.data.Lecture
import com.example.debri_lize.databinding.FragmentClassBinding

class ClassFragment : Fragment() {

    lateinit var binding: FragmentClassBinding
    lateinit var classfavoriteRVAdapter: ClassFavoriteRVAdapter
    val datas = ArrayList<Lecture>()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentClassBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onStart() {
        super.onStart()

        initClassFavoriteRecycler()
    }

    private fun initClassFavoriteRecycler() {
        binding.classFavoriteRv.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        classfavoriteRVAdapter = ClassFavoriteRVAdapter()
        binding.classFavoriteRv.adapter = classfavoriteRVAdapter

        datas.apply {
            //더미
            add(Lecture("C 짱",10,"C언어","영상","무료"))


            classfavoriteRVAdapter.datas_classf = datas
            classfavoriteRVAdapter.notifyDataSetChanged()

            //item 클릭 시 강의 상세 화면으로 전환?
            classfavoriteRVAdapter.setItemClickListener(object : ClassFavoriteRVAdapter.OnItemClickListener {
                override fun onClick(v: View, position: Int) {



                }
            })
        }
    }
}