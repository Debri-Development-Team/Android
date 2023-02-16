package com.debri_main.debri.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.debri_main.debri.activity.AddCurriculumDetailActivity
import com.debri_main.debri.adapter.home.CurriculumRVAdapter
import com.debri_main.debri.data.curriculum.*
import com.debri_main.debri.databinding.FragmentCurriculumBinding
import com.debri_main.debri.service.CurriculumService
import com.debri_main.debri.view.curriculum.ShowGetNewCurriListView
import com.debri_main.debri.view.curriculum.ShowScrapCurriListView
import com.debri_main.debri.view.curriculum.ShowTop10ListView
import com.google.android.material.tabs.TabLayout

class CurriculumFragment : Fragment(), ShowTop10ListView, ShowGetNewCurriListView,
    ShowScrapCurriListView {

    lateinit var binding: FragmentCurriculumBinding
    lateinit var curriculumRVAdapter: CurriculumRVAdapter
    val datas = ArrayList<Curriculum>()
    val newdatas = ArrayList<Curriculum>()

    val curriculumService = CurriculumService()

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

        //api - top5
        curriculumService.setShowTop10ListView(this)
        curriculumService.showTop10List()

        //api - 최신 등록
        curriculumService.setShowGetNewCurriListView(this)
        curriculumService.showGetNewCurriList()

        curriculumService.setShowScrapCurriListView(this)


        //탭 클릭
        binding.curriculumTablayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when(tab!!.position) {
                    0 -> {
                        binding.curriculumTotalRv.visibility = View.VISIBLE
                        binding.curriculumFavoriteRv.visibility = View.GONE
                        curriculumService.showGetNewCurriList()

                    }
                    1 -> {
                        binding.curriculumTotalRv.visibility = View.GONE
                        binding.curriculumFavoriteRv.visibility = View.VISIBLE
                        curriculumService.showScrapCurriList()
                    }
                }

            }
            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }
            override fun onTabReselected(tab: TabLayout.Tab?) {
            }
        }
        )
    }



    override fun onShowTop10ListSuccess(code: Int, result: List<Top10>) {
        when(code){
            200->{
                binding.curriculumRv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                curriculumRVAdapter = CurriculumRVAdapter("CurriculumFragment")
                binding.curriculumRv.adapter = curriculumRVAdapter


                datas.clear()

                //data : 전체
                datas.apply {
                    var count = 0

                    for (i in result){
//                        Log.d("curri", i.toString())
                        datas.add(Curriculum(i.curriIdx, i.curriName, i.curriAuthor, i.status, i.visibleStatus, i.curriDesc, i.createdAt, i.langtag))
                        count++
                        if(count==5) break
                    }

                    curriculumRVAdapter.datas = datas
                    curriculumRVAdapter.notifyDataSetChanged()

                    //click recyclerview item
                    curriculumRVAdapter.setItemClickListener(object : CurriculumRVAdapter.OnItemClickListener{
                        override fun onClick(v: View, position: Int) {
                            val intent = Intent(context, AddCurriculumDetailActivity::class.java)
                            intent.putExtra("curriculumIdx", datas[position].curriculumIdx)
                            startActivity(intent)

                        }
                    })
                }
            }
        }
    }

    override fun onShowTop10ListFailure(code: Int) {
        Log.d("showtop5listfail","$code")
    }

    override fun onShowGetNewCurriListSuccess(code: Int, result: List<RecentCurriculum>) {
        when(code){
            200->{
                binding.curriculumTotalRv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                curriculumRVAdapter = CurriculumRVAdapter("CurriculumFragment")
                binding.curriculumTotalRv.adapter = curriculumRVAdapter


                newdatas.clear()

                //data : 전체
                newdatas.apply {
                    var count = 0

                    for (i in result){
                        Log.d("total lang",i.toString())
                        newdatas.add(Curriculum(i.curriIdx, i.curriName, i.curriAuthor, i.visibleStatus, i.status, i.curriDesc, i.createdAt, i.langtag))
                        count++
                        if(count==5) break
                    }

                    curriculumRVAdapter.datas = newdatas
                    curriculumRVAdapter.notifyDataSetChanged()

                    //click recyclerview item
                    curriculumRVAdapter.setItemClickListener(object : CurriculumRVAdapter.OnItemClickListener{
                        override fun onClick(v: View, position: Int) {
                            val intent = Intent(context, AddCurriculumDetailActivity::class.java)
                            intent.putExtra("curriculumIdx", newdatas[position].curriculumIdx)
                            startActivity(intent)

                        }
                    })
                }
            }
        }
    }

    override fun onShowGetNewCurriListFailure(code: Int) {
        Log.d("showgetnewcurrilistfail","$code")
    }

    override fun onShowScrapCurriListSuccess(code: Int, result: List<Curriculum>) {
        when(code){
            200->{
                binding.curriculumFavoriteRv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                curriculumRVAdapter = CurriculumRVAdapter("CurriculumFragment")
                binding.curriculumFavoriteRv.adapter = curriculumRVAdapter

                newdatas.clear()

                newdatas.apply {
//                    var count = 0

                    for (i in result){
                        newdatas.add(Curriculum(i.curriculumIdx, i.curriculumName, i.curriculumAuthor, i.status, i.visibleStatus, i.curriDesc, i.createdAt, i.langtag))
//                        count++
//                        if(count==5) break
                    }

                    curriculumRVAdapter.datas = newdatas
                    curriculumRVAdapter.notifyDataSetChanged()

                    //click recyclerview item
                    curriculumRVAdapter.setItemClickListener(object : CurriculumRVAdapter.OnItemClickListener{
                        override fun onClick(v: View, position: Int) {
                            val intent = Intent(context, AddCurriculumDetailActivity::class.java)
                            intent.putExtra("curriculumIdx", newdatas[position].curriculumIdx)
                            startActivity(intent)

                        }
                    })
                }
            }
        }
    }

    override fun onShowScrapCurriListFailure(code: Int) {
        Log.d("showscrapcurrilistfailure","$code")
    }


}