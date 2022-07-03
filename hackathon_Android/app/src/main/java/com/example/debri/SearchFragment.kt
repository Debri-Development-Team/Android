package com.example.debri

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
//import com.example.debri.data.service.LectureService
//import com.example.debri.data.view.LectureSearchView
import com.example.debri.databinding.FragmentSearchBinding


class SearchFragment : Fragment() { //, LectureSearchView
    lateinit var binding:FragmentSearchBinding
    val data:ArrayList<LectureData> = ArrayList()
    lateinit var adapter: LectureAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()

        initRecyclerView()
    }


    var searchViewTextListener: SearchView.OnQueryTextListener =
        object : SearchView.OnQueryTextListener {
            //검색버튼 입력시 호출, 검색버튼이 없으므로 사용하지 않음
            override fun onQueryTextSubmit(s: String): Boolean {
                return false
            }

            //텍스트 입력/수정시에 호출
            override fun onQueryTextChange(s: String): Boolean {
                adapter.getFilter().filter(s)
                return false
            }
        }

    private fun initRecyclerView(){
        binding.searchFragmentLectureRv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        adapter = LectureAdapter(data)
        binding.searchFragmentLectureRv.adapter = adapter

        //더미데이터
        data.apply{
            add(LectureData("강의1","월/수","02:00 - 03:00"))
            add(LectureData("강의2","화/목","12:00 - 13:00"))
            add(LectureData("강의3","수/목","12:00 - 14:00"))

            adapter.items = data
            adapter.notifyDataSetChanged()

            adapter.setItemClickListener(object: LectureAdapter.OnItemClickListener{
                override fun onClick(v: View, position: Int) {

                    //SearchDetailFragment에 data보내기
                    val bundle = Bundle()
                    bundle.putSerializable("lecture", data[position])
                    val passBundleBFragment = SearchDetailFragment()
                    passBundleBFragment.arguments = bundle

                    //fragment to fragment
                    activity?.supportFragmentManager!!.beginTransaction()
                        .replace(R.id.main_frm, passBundleBFragment)
                        .commit()
//                    (context as MainActivity).supportFragmentManager.beginTransaction()
//                        .replace(R.id.main_frm, SearchDetailFragment()).commitAllowingStateLoss()
                }
            })
            adapter.notifyDataSetChanged()

        }

    }


}