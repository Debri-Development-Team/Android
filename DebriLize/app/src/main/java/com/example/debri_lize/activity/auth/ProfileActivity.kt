package com.example.debri_lize.activity.auth

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.debri_lize.R
import com.example.debri_lize.activity.AddCurriculumDetailActivity
import com.example.debri_lize.adapter.home.CurriculumRVAdapter
import com.example.debri_lize.data.curriculum.Curriculum
import com.example.debri_lize.databinding.ActivityProfileBinding
import com.example.debri_lize.fragment.ScrapCurriculumFragment
import com.example.debri_lize.fragment.ScrapPostFragment
import com.example.debri_lize.service.CurriculumService
import com.example.debri_lize.utils.ApplicationClass
import com.example.debri_lize.utils.getUserID
import com.example.debri_lize.utils.getUserName
import com.example.debri_lize.view.curriculum.MyCurriculumListView

class ProfileActivity : AppCompatActivity(), MyCurriculumListView {
    lateinit var binding : ActivityProfileBinding
    lateinit var curriculumRVAdapter: CurriculumRVAdapter
    val datas = ArrayList<Curriculum>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater) //binding 초기화
        setContentView(binding.root)

        //click logout
        binding.profileMenuLogoutTv.setOnClickListener{
            logout()
            val intent = Intent(this, LoginActivity::class.java)
            //모든 화면 초기화
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
            startActivity(intent)
        }
    }

    override fun onStart() {
        super.onStart()
        binding.profileUserNameTv.text = getUserName()
        binding.profileUserIdTv.text = getUserID()

        //api 8.2
        val curriculumService = CurriculumService()
        curriculumService.setMyCurriculumListView(this)
        curriculumService.myCurriculumList()

        binding.profilePreviousIv.setOnClickListener {
            finish()
        }

        //ProfileActivity -> ScrapPostFragment
//        binding.profileScrapPostLayout.setOnClickListener{
//
//            //activity to fragment
//            supportFragmentManager.beginTransaction()
//                .replace(R.id.profile, ScrapPostFragment())
//                .commit()
//        }
//
//        //ProfileActivity -> ScrapCurriculumFragment
//        binding.profileScrapCurriLayout.setOnClickListener {
//            //activity to fragment
//            supportFragmentManager.beginTransaction()
//                .replace(R.id.profile, ScrapCurriculumFragment())
//                .commit()
//        }
    }


    private fun logout(){ //jwt=0으로 만들기 : 저장된 값X
        val editor = ApplicationClass.mSharedPreferences.edit()
        editor.remove("jwt")
        editor.apply()
    }

    override fun onMyCurriculumListSuccess(code: Int, result: List<Curriculum>) {
        when(code){
            200->{
                binding.profileCurriculumRv.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
                curriculumRVAdapter = CurriculumRVAdapter()
                binding.profileCurriculumRv.adapter = curriculumRVAdapter

                datas.clear()

                //data : 전체
                datas.apply {

                    for (i in result){
                        datas.add(Curriculum(i.curriculumIdx, i.curriculumName, i.curriculumAuthor, i.status))
                    }

                    curriculumRVAdapter.datas = datas
                    curriculumRVAdapter.notifyDataSetChanged()

                    //click recyclerview item
                    curriculumRVAdapter.setItemClickListener(object : CurriculumRVAdapter.OnItemClickListener {
                        override fun onClick(v: View, position: Int) {
                            val intent = Intent(this@ProfileActivity, AddCurriculumDetailActivity::class.java)
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