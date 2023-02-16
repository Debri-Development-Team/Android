package com.debri_main.debri.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.debri_main.debri.R
import com.debri_main.debri.activity.AddCurriculumDetailActivity
import com.debri_main.debri.activity.MainActivity
import com.debri_main.debri.adapter.home.CurriculumRVAdapter
import com.debri_main.debri.data.curriculum.Curriculum
import com.debri_main.debri.databinding.FragmentScrapCurriculumBinding
import com.debri_main.debri.service.CurriculumService
import com.debri_main.debri.view.curriculum.ShowScrapCurriListView

class ScrapCurriculumFragment : Fragment(), ShowScrapCurriListView {

    lateinit var binding: FragmentScrapCurriculumBinding
    private val datas = ArrayList<Curriculum>()
    lateinit var curriculumRVAdapter: CurriculumRVAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentScrapCurriculumBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onStart() {
        super.onStart()

        //api - scrap curri list
        val curriculumService = CurriculumService()
        curriculumService.setShowScrapCurriListView(this)
        curriculumService.showScrapCurriList()

        //fragment to fragment
        binding.curriculumScrapPreviousIv.setOnClickListener{
            (context as MainActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.main_frm, CurriculumFragment()).commitAllowingStateLoss()
        }
    }

    override fun onShowScrapCurriListSuccess(code: Int, result: List<Curriculum>) {
        when(code){
            200->{
                binding.curriculumRv.layoutManager =
                    LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                curriculumRVAdapter = CurriculumRVAdapter("ScrapCurriculumFragment")
                binding.curriculumRv.adapter = curriculumRVAdapter

                datas.clear()

                datas.apply {
                    for (i in result){
                        datas.add(Curriculum(i.curriculumIdx, i.curriculumName, i.curriculumAuthor, i.status, i.visibleStatus, i.curriDesc))
                    }

                    curriculumRVAdapter.datas = datas
                    curriculumRVAdapter.notifyDataSetChanged()

                    curriculumRVAdapter.setItemClickListener(object: CurriculumRVAdapter.OnItemClickListener{
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

    override fun onShowScrapCurriListFailure(code: Int) {
        Log.d("showscrapcurrilistfail","$code")
    }
}