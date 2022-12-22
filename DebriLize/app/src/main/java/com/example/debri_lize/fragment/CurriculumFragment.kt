package com.example.debri_lize.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.debri_lize.R
import com.example.debri_lize.activity.AddCurriculumDetailActivity
import com.example.debri_lize.activity.MainActivity
import com.example.debri_lize.adapter.home.CurriculumRVAdapter
import com.example.debri_lize.data.curriculum.Curriculum
import com.example.debri_lize.data.curriculum.Top10
import com.example.debri_lize.databinding.FragmentCurriculumBinding
import com.example.debri_lize.service.CurriculumService
import com.example.debri_lize.view.curriculum.ShowGetNewCurriListView
import com.example.debri_lize.view.curriculum.ShowTop10ListView

class CurriculumFragment : Fragment(), ShowTop10ListView, ShowGetNewCurriListView {

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

        //CurriculumFragment -> ScrapCurriculumFragment
        binding.curriculumScrapLayout.setOnClickListener {
            (context as MainActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.main_frm, ScrapCurriculumFragment()).commitAllowingStateLoss()
        }


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
                        datas.add(Curriculum(i.curriIdx, i.curriName, i.curriAuthor, i.status))
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

    override fun onShowGetNewCurriListSuccess(code: Int, result: List<Top10>) {
        when(code){
            200->{
                binding.curriculumRecentRegisterRv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                curriculumRVAdapter = CurriculumRVAdapter("CurriculumFragment")
                binding.curriculumRecentRegisterRv.adapter = curriculumRVAdapter


                newdatas.clear()

                //data : 전체
                newdatas.apply {
                    var count = 0

                    for (i in result){
                        newdatas.add(Curriculum(i.curriIdx, i.curriName, i.curriAuthor, i.status, i.curriDesc))
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


}