package com.example.debri_lize.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.debri_lize.adapter.home.HomeVPAdapter
import com.example.debri_lize.data.curriculum.Curriculum
import com.example.debri_lize.databinding.FragmentHomeBinding
import com.example.debri_lize.service.CurriculumService
import com.example.debri_lize.view.curriculum.MyCurriculumListView
import com.google.android.material.tabs.TabLayoutMediator

class HomeFragment : Fragment(), MyCurriculumListView {

    lateinit var binding: FragmentHomeBinding
    private lateinit var homeVPAdapter: HomeVPAdapter

    private var fragmentList = ArrayList<Fragment>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        //api - 8.2 커리큘럼 리스트 조회 api
        val curriculumService = CurriculumService()
        curriculumService.setMyCurriculumListView(this)
        curriculumService.myCurriculumList()
    }

    //api
    //8.2 커리큘럼 리스트 조회 api : 내가 추가한 커리큘럼들 (데이터만 사용)
    override fun onMyCurriculumListSuccess(code: Int, result: List<Curriculum>) {
        when(code){
            200->{
                var j = 0
                for(i in result){
                    if(i.status=="ACTIVE") { //활성 상태
                        fragmentList.add(HomeActiveFragment(i.curriculumIdx!!, j))
                    }else { //비활성 상태
                        fragmentList.add(HomeInactiveFragment(i.curriculumIdx!!, j))
                    }
                    j++
                }

                if(j<=20){ //20 : 커리큘럼 최대 개수
                    fragmentList.add(AddCurriculumFragment())
                }else{
                    fragmentList.add(AddXCurriculumFragment())
                }


                //ViewPagerAdapter 초기화
                homeVPAdapter = HomeVPAdapter(this)
                homeVPAdapter.fragments.addAll(fragmentList)

                //ViewPager2와 Adapter 연동
                binding.homeContentVp.adapter = homeVPAdapter
                Log.d("fragment", fragmentList.toString())
                Log.d("fragment", homeVPAdapter.fragments.toString())
                Log.d("fragment", homeVPAdapter.fragments.size.toString())

                TabLayoutMediator(binding.homeContentTl, binding.homeContentVp){tab, position ->
                    //implementation
                }.attach()

            }
        }
    }

    override fun onMyCurriculumListFailure(code: Int) {

    }


}