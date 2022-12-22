package com.example.debri_lize.fragment

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.*
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.debri_lize.adapter.class_.ClassFavoriteRVAdapter
import com.example.debri_lize.adapter.class_.ClassLectureRVAdapter
import com.example.debri_lize.R
import com.example.debri_lize.activity.LectureDetailActivity
import com.example.debri_lize.data.class_.Lecture
import com.example.debri_lize.data.class_.LectureFilter
import com.example.debri_lize.data.class_.SearchLecture
import com.example.debri_lize.databinding.FragmentClassBinding
import com.example.debri_lize.service.ClassService
import com.example.debri_lize.utils.getUserIdx
import com.example.debri_lize.view.class_.LectureFavoriteView
import com.example.debri_lize.view.class_.LectureFilterView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.tabs.TabLayout

class ClassFragment : Fragment(), LectureFavoriteView, LectureFilterView {

    lateinit var binding: FragmentClassBinding
    lateinit var classfavoriteRVAdapter: ClassFavoriteRVAdapter
    lateinit var classLectureRVAdapter: ClassLectureRVAdapter

    val classService = ClassService()
    val lectureFilter = LectureFilter()

    var filterNum : Int = 0
    var filterNum2 : Int = 0

    var category : LectureFilter? = null

    //강의 정렬 상태
    var sortStatus : String? = null

//    var datas = ArrayList<Lecture>()
//    var datas_f = ArrayList<Lecture>()

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

        //태그 클릭 효과
//        onRadioButtonClicked()

        //api - lectureFavorite, lecturSearch
        classService.setLectureFavoriteView(this)
        classService.setLectureFilterView(this)

        classService.showLectureSearch(lectureFilter)


        //click filter btn
//        binding.classFilterIv.setOnClickListener{
//            //bottom sheet
//            bottomSheet()
//        }

        //focus
        binding.classSearchEt.setOnFocusChangeListener(object : View.OnFocusChangeListener {
            override fun onFocusChange(view: View, hasFocus: Boolean) {
                if (hasFocus) {
                    //  포커스시
                    binding.classSearchLayout.setBackgroundResource(R.drawable.border_round_debri_transparent_10)
                    binding.classSearchIv.setImageResource(R.drawable.btm_nav_search_on)
                } else {
                    //  포커스 뺏겼을 때
                    binding.classSearchLayout.setBackgroundResource(R.drawable.border_round_white_transparent_10)
                    binding.classSearchIv.setImageResource(R.drawable.btm_nav_search)
                }
            }
        })


        //click userImg -> profile
//        binding.classDebriUserIv.setOnClickListener{
//            val intent = Intent(context, ProfileActivity::class.java)
//            startActivity(intent)
//        }


        //fragment to fragment : category select
        binding.classCurriTagBtn.setOnClickListener{
            activity?.supportFragmentManager!!.beginTransaction()
                .replace(R.id.main_frm, ClassSelectCategoryFragment())
                .commit()
        }
        binding.classMediaTagBtn.setOnClickListener{
            activity?.supportFragmentManager!!.beginTransaction()
                .replace(R.id.main_frm, ClassSelectCategoryFragment())
                .commit()
        }
        binding.classPriceTagBtn.setOnClickListener{
            activity?.supportFragmentManager!!.beginTransaction()
                .replace(R.id.main_frm, ClassSelectCategoryFragment())
                .commit()
        }

        //데이터 받아오기 by ClassSelectCategoryFragment
        category = arguments?.getSerializable("category") as LectureFilter?
        showFilter()

        //탭 클릭
        binding.classTablayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when(tab!!.position) {
                    0 -> {
                        binding.classLecturelistRv.visibility = View.VISIBLE
                        binding.classFavoriteRv.visibility = View.GONE
                        Log.d("탭","0")
                        Log.d("lecturefilter",lectureFilter.toString())
                        classService.showLectureSearch(lectureFilter)

                    }
                    1 -> {
                        binding.classLecturelistRv.visibility = View.GONE
                        binding.classFavoriteRv.visibility = View.VISIBLE
                        Log.d("탭","1")
                        Log.d("lecturefilter",lectureFilter.toString())
                        classService.showLectureFavorite(getUserIdx()!!)

                    }
                }

            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

        }

        )

        //sort 필터 아이콘 클릭
        binding.classFilterIv.setOnClickListener {
            //필터가 가나다 순 상태이면 좋아요 순으로 바꾸기
            if(binding.classSortAZTv.visibility == View.VISIBLE){
                binding.classSortAZTv.visibility = View.GONE
                binding.classSortLikeIv.visibility = View.VISIBLE

                //토스트 메세지
                var sortLectureLikeToast = layoutInflater.inflate(R.layout.toast_sort_lecture, null)
                sortLectureLikeToast.findViewById<ImageView>(R.id.toast_sort_mark_iv).visibility = View.VISIBLE
                sortLectureLikeToast.findViewById<TextView>(R.id.toast_sort_A_Z_tv).visibility = View.GONE
                var toast = Toast(context)
                toast.view = sortLectureLikeToast
                toast.setGravity(Gravity.CENTER_HORIZONTAL,0,0)
                toast.show()

                //TODO: 좋아요 순 정렬하기
                sortStatus = "like"
                classService.showLectureSearch(lectureFilter)
                classService.showLectureFavorite(getUserIdx()!!)
                
            }
            else{   //필터가 좋아요 순 상태이면 가나다 순으로 바꾸기
                binding.classSortAZTv.visibility = View.VISIBLE
                binding.classSortLikeIv.visibility = View.GONE

                //토스트 메세지
                var sortLectureAtoZToast = layoutInflater.inflate(R.layout.toast_sort_lecture, null)
                sortLectureAtoZToast.findViewById<TextView>(R.id.toast_sort_A_Z_tv).visibility = View.VISIBLE
                sortLectureAtoZToast.findViewById<ImageView>(R.id.toast_sort_mark_iv).visibility = View.GONE
                sortLectureAtoZToast.findViewById<TextView>(R.id.toast_sort_tv).setText("가나다 순")
                var toast = Toast(context)
                toast.view = sortLectureAtoZToast
                toast.setGravity(Gravity.CENTER_HORIZONTAL,0,0)
                toast.show()

                //TODO: 가나다 순 정렬하기
                sortStatus = "AtoZ"
                classService.showLectureSearch(lectureFilter)
                classService.showLectureFavorite(getUserIdx()!!)
            }
        }


        //검색어 입력 : search Lecture
        binding.classSearchEt.addTextChangedListener(object : TextWatcher{
            //입력이 끝날 때
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }
            //입력하기 전에
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }
            //타이핑되는 텍스트에 변화가 있을 때
            override fun afterTextChanged(p0: Editable?) {
                val searchText: String = binding.classSearchEt.text.toString()
                Log.d("editText","$searchText")
                lectureFilter.key = searchText
//                if(searchText=="")  filterNum2 = 0
//                else filterNum2 = 1
//                showList()
            }

        })

    }

    //bottom sheet
//    private fun bottomSheet(){
//
//        val bottomSheetDialog = BottomSheetDialog(requireContext())
//
//        var bottomSheetView : View = layoutInflater.inflate(R.layout.fragment_bottom_sheet_two, null)
//        bottomSheetDialog.setContentView(bottomSheetView)
//
//        bottomSheetView.findViewById<TextView>(R.id.bottom_sheet_two_tv).text = "강의 정렬하기"
//        bottomSheetView.findViewById<TextView>(R.id.bottom_sheet_two_tv1).text = "가나다 순 정렬"
//        bottomSheetView.findViewById<TextView>(R.id.bottom_sheet_two_tv2).text = "좋아요 순 정렬"
//
//        //가나다 순
//        touchEvent(bottomSheetView.findViewById(R.id.bottom_sheet_two_tv1))
//        bottomSheetView.findViewById<TextView>(R.id.bottom_sheet_two_tv1).setOnClickListener {
//
////            datas.sortBy { it.lectureName }
////            datas_f.sortBy { it.lectureName }
//
//
//            bottomSheetDialog.dismiss()
//        }
//        //좋아요 순
//        touchEvent(bottomSheetView.findViewById(R.id.bottom_sheet_two_tv2))
//        bottomSheetView.findViewById<TextView>(R.id.bottom_sheet_two_tv2).setOnClickListener {
//
////            datas.sortBy { it.likeNumber }
////            datas_f.sortBy { it.likeNumber }
//
//
//            bottomSheetDialog.dismiss()
//        }
//
//
//        binding.classFilterIv.setOnClickListener {
//            bottomSheetDialog.show()
//        }
//
//        //close button
//        bottomSheetView.findViewById<TextView>(R.id.bottom_sheet_close_tv).setOnClickListener {
//            bottomSheetDialog.dismiss()
//        }
//
//
//    }

    private fun touchEvent(bind : TextView){
        bind.setOnTouchListener(object : View.OnTouchListener {
            override fun onTouch(view: View?, event: MotionEvent?): Boolean {
                when (event?.action) {
                    MotionEvent.ACTION_DOWN -> {
                        bind.setTextColor(ContextCompat.getColor(context!!, R.color.white))
                    }
                    MotionEvent.ACTION_UP -> {
                        bind.setTextColor(ContextCompat.getColor(context!!, R.color.darkmode_background))
                        bind.performClick()
                    }
                }

                //리턴값이 false면 동작 안됨
                return true //or false
            }


        })
    }


    private fun showFilter(){
        //category로 받아온 데이터를 lectureFilter로 넣기
        if (category?.lang != null) lectureFilter.lang = category!!.lang
        if (category?.type != null) lectureFilter.type = category!!.type
        if (category?.price != null) lectureFilter.price = category!!.price

    }

    override fun onLectureFavoriteSuccess(code: Int, result: List<Lecture>) {
        when(code){
            200->{
                val datas_f = ArrayList<Lecture>()

                //즐겨찾기
                binding.classFavoriteRv.layoutManager =
                    LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                classfavoriteRVAdapter = ClassFavoriteRVAdapter()
                binding.classFavoriteRv.adapter = classfavoriteRVAdapter

                datas_f.apply {
                    for (i in result){
                       datas_f.add(Lecture(i.lectureIdx, i.lectureName, i.chapterNum, i.language, i.media, i.price, i.userScrap, i.scrapNumber, i.usedCount, i.likeNumber, i.userLike, i.lectureDesc, i.srcLink))
                    }
                    //강의 정렬
                    if(sortStatus == "like")    datas_f.sortByDescending { it.likeNumber }
                    else    datas_f.sortBy { it.lectureName }

                    classfavoriteRVAdapter.datas_classf = datas_f
                    classfavoriteRVAdapter.notifyDataSetChanged()

                    //item 클릭 시 강의 상세 화면으로 전환?
                    classfavoriteRVAdapter.setItemClickListener(object : ClassFavoriteRVAdapter.OnItemClickListener {
                        override fun onClick(v: View, position: Int) {

                            //LectureDetailActivity에 data보내기
                            val intent = Intent(context, LectureDetailActivity::class.java)
                            intent.putExtra("lectureIdx", datas_f[position].lectureIdx)
                            intent.putExtra("lectureName", datas_f[position].lectureName)
                            Log.d("lectureIdxClass", datas_f[position].lectureIdx.toString())
                            startActivity(intent)

                        }
                    })
                }
            }
        }
    }

    override fun onLectureFavoriteFailure(code: Int) {
        Log.d("lecturefavoritefail","$code")
        when(code){
            7305 -> {

            }
        }
    }

    override fun onLectureFilterSuccess(code: Int, result: SearchLecture) {
        when(code){
            200->{
                val datas = ArrayList<Lecture>()
                binding.classLecturelistRv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                classLectureRVAdapter = ClassLectureRVAdapter()
                binding.classLecturelistRv.adapter = classLectureRVAdapter

                datas.apply {
                    for (i in result.lectureList) {
                        datas.add(
                            Lecture(i.lectureIdx, i.lectureName, i.chapterNum, i.language, i.media, i.price, i.userScrap, i.scrapNumber, i.usedCount, i.likeNumber, i.userLike, i.lectureDesc, i.srcLink)
                        )
                    }
                    //강의 정렬
                    if(sortStatus == "like")    datas.sortByDescending { it.likeNumber }
                    else    datas.sortBy { it.lectureName }

                    classLectureRVAdapter.datas = datas
                    classLectureRVAdapter.notifyDataSetChanged()

                    //item 클릭 시 강의 상세 화면으로 전환?
                    classLectureRVAdapter.setItemClickListener(object :
                        ClassLectureRVAdapter.OnItemClickListener {
                        override fun onClick(v: View, position: Int) {

                            //LectureDetailActivity에 data보내기
                            val intent = Intent(context, LectureDetailActivity::class.java)
                            intent.putExtra("lectureIdx", datas[position].lectureIdx)
                            intent.putExtra("lectureName", datas[position].lectureName)
                            Log.d("lectureIdxClass", datas[position].lectureIdx.toString())
                            startActivity(intent)

                        }
                    })

                }


            }
        }
    }

    override fun onLectureFilterFailure(code: Int) {
        Log.d("lecturefilterfail","$code")
        when(code){
            //검색 결과 없을 때
            7307-> {
                binding.classLecturelistRv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                classLectureRVAdapter = ClassLectureRVAdapter()
                binding.classLecturelistRv.adapter = classLectureRVAdapter
            }
        }
    }


}

