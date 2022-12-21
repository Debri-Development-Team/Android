package com.example.debri_lize.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.debri_lize.activity.AddCurriculumDetailActivity
import com.example.debri_lize.activity.auth.LoginActivity
import com.example.debri_lize.adapter.home.CurriculumRVAdapter
import com.example.debri_lize.adapter.home.HomeVPAdapter
import com.example.debri_lize.data.curriculum.Curriculum
import com.example.debri_lize.databinding.FragmentHomeBinding
import com.example.debri_lize.databinding.FragmentProfileBinding
import com.example.debri_lize.service.CurriculumService
import com.example.debri_lize.utils.ApplicationClass
import com.example.debri_lize.utils.getUserID
import com.example.debri_lize.utils.getUserName
import com.example.debri_lize.view.curriculum.MyCurriculumListView

class ProfileFragment : Fragment(), MyCurriculumListView{

    lateinit var binding : FragmentProfileBinding
    lateinit var curriculumRVAdapter: CurriculumRVAdapter
    val datas = ArrayList<Curriculum>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(inflater, container, false)


        //수정할 것
        //click logout
        binding.profileMenuLogoutTv.setOnClickListener{
            logout()
            val intent = Intent(activity, LoginActivity::class.java)

            //모든 화면 초기화
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
            startActivity(intent)
        }

        return binding.root

    }

    override fun onStart() {
        super.onStart()

        binding.profileUserNameTv.text = getUserName()
        binding.profileUserIdTv.text = getUserID()

        //api 8.2
        val curriculumService = CurriculumService()
        curriculumService.setMyCurriculumListView(this)
        curriculumService.myCurriculumList()

    }

    private fun logout(){ //jwt=0으로 만들기 : 저장된 값X
        val editor = ApplicationClass.mSharedPreferences.edit()
        editor.remove("jwt")
        editor.apply()
    }

    override fun onMyCurriculumListSuccess(code: Int, result: List<Curriculum>) {
        when(code){
            200->{
                binding.profileCurriculumRv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                curriculumRVAdapter = CurriculumRVAdapter("ProfileActivity")
                binding.profileCurriculumRv.adapter = curriculumRVAdapter

                datas.clear()

                //data : 전체
                datas.apply {

                    for (i in result){
                        datas.add(Curriculum(i.curriculumIdx, i.curriculumName, i.curriculumAuthor, i.status, i.visibleStatus, i.curriDesc))
                    }


                    curriculumRVAdapter.datas = datas
                    curriculumRVAdapter.notifyDataSetChanged()



                    //click recyclerview item
                    curriculumRVAdapter.setItemClickListener(object : CurriculumRVAdapter.OnItemClickListener {
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

    override fun onMyCurriculumListFailure(code: Int) {
        Log.d("mycurrilistfail","$code")
    }

}