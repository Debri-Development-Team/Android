package com.example.debri_lize.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.debri_lize.adapter.home.CurriculumRVAdapter
import com.example.debri_lize.data.curriculum.Curriculum
import com.example.debri_lize.databinding.FragmentCurriculumBinding

class CurriculumFragment : Fragment() {

    lateinit var binding: FragmentCurriculumBinding
    lateinit var curriculumRVAdapter: CurriculumRVAdapter
    val datas = ArrayList<Curriculum>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCurriculumBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onStart() {
        super.onStart()

        initRecyclerView()
    }

    private fun initRecyclerView(){
        binding.curriculumRv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        curriculumRVAdapter = CurriculumRVAdapter()
        binding.curriculumRv.adapter = curriculumRVAdapter

        datas.clear()

        //data : 전체
        datas.apply {

            datas.add(Curriculum(1, "자바의 정석", "자바의 정석"))
            datas.add(Curriculum(1, "자바의 정석", "자바의 정석"))
            datas.add(Curriculum(1, "자바의 정석", "자바의 정석"))

            curriculumRVAdapter.datas = datas
            curriculumRVAdapter.notifyDataSetChanged()

            //click recyclerview item
            curriculumRVAdapter.setItemClickListener(object : CurriculumRVAdapter.OnItemClickListener {
                override fun onClick(v: View, position: Int) {


                }
            })
        }
    }
}