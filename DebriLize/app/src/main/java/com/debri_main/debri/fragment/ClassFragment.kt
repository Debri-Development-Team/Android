package com.debri_main.debri.fragment



import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.*
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.debri_main.debri.adapter.class_.ClassFavoriteRVAdapter
import com.debri_main.debri.adapter.class_.ClassLectureRVAdapter
import com.debri_main.debri.R
import com.debri_main.debri.activity.LectureDetailActivity
import com.debri_main.debri.data.class_.Lecture
import com.debri_main.debri.data.class_.LectureFilter
import com.debri_main.debri.data.class_.SearchLecture
import com.debri_main.debri.databinding.FragmentClassBinding
import com.debri_main.debri.service.ClassService
import com.debri_main.debri.utils.getUserIdx
import com.debri_main.debri.view.class_.LectureFavoriteView
import com.debri_main.debri.view.class_.LectureFilterView
import com.google.android.material.tabs.TabLayout

class ClassFragment : androidx.fragment.app.Fragment(), LectureFavoriteView, LectureFilterView {

    lateinit var binding: FragmentClassBinding
    lateinit var classfavoriteRVAdapter: ClassFavoriteRVAdapter
    lateinit var classLectureRVAdapter: ClassLectureRVAdapter

    val classService = ClassService()
    val lectureFilter = LectureFilter()

    private var pageNum : Int = 1 //현재 페이지 번호
    var page : Int = 1      //현재 페이지가 속한 곳 pageNum이 1~5면 1, 6~10이면 2
    var totalPage : Int = 0
    var lectureCount : Int = 0

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

//        classService.showLectureSearch(lectureFilter)

        //페이징 버튼 클릭
        pageButtonClick()

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



        when(category?.lang){
            "Front"->{
                binding.classCurriTagBtn.setText("Front")
                binding.classCurriTagBtn.setBackgroundResource(R.drawable.border_round_transparent_front_18)

            }
            "Back"->{
                binding.classCurriTagBtn.setText("Back")
                binding.classCurriTagBtn.setBackgroundResource(R.drawable.border_round_transparent_back_18)
                binding.classCurriTagBtn.setTextColor(Color.parseColor("#0A1123"))

            }
            "Python"->{
                binding.classCurriTagBtn.setText("Python")
                binding.classCurriTagBtn.setBackgroundResource(R.drawable.border_round_transparent_python_18)
                binding.classCurriTagBtn.setTextColor(Color.parseColor("#0A1123"))

            }
            "C 언어"->{
                binding.classCurriTagBtn.setText("C 언어")
                binding.classCurriTagBtn.setBackgroundResource(R.drawable.border_round_transparent_c_18)
                binding.classCurriTagBtn.setTextColor(Color.parseColor("#0A1123"))

            }
            ""->{
                binding.classCurriTagBtn.setText("전체")
                binding.classCurriTagBtn.setBackgroundResource(R.drawable.border_round_gray_transparent_18)
                binding.classCurriTagBtn.setTextColor(Color.parseColor("#FFFFFFFF"))

            }

        }

        when(category?.type){
            "서적"->{
                binding.classMediaTagBtn.setText("서적")
            }
            "영상"->{
                binding.classMediaTagBtn.setText("영상")
            }
            ""->{
                binding.classMediaTagBtn.setText("전체")
                binding.classMediaTagBtn.setBackgroundResource(R.drawable.border_round_gray_transparent_18)
            }
        }
        when(category?.price){
            "무료"->{
                binding.classPriceTagBtn.setText("무료")
            }
            "유료"->{
                binding.classPriceTagBtn.setText("유료")
            }
            ""->{
                binding.classPriceTagBtn.setText("전체")
                binding.classPriceTagBtn.setBackgroundResource(R.drawable.border_round_gray_transparent_18)
            }
        }

        //탭 클릭
        binding.classTablayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when(tab!!.position) {
                    0 -> {
                        binding.classLecturelistRv.visibility = View.VISIBLE
                        binding.classFavoriteRv.visibility = View.GONE
                        binding.lecturePageLayout.visibility = View.VISIBLE
                        Log.d("탭","0")
                        Log.d("lecturefilter",lectureFilter.toString())
                        classService.showLectureSearch(lectureFilter)

                    }
                    1 -> {
                        binding.classLecturelistRv.visibility = View.GONE
                        binding.classFavoriteRv.visibility = View.VISIBLE
                        binding.lecturePageLayout.visibility = View.GONE
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

                //좋아요 순 정렬하기
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

                //가나다 순 정렬하기
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
                classService.showLectureSearch(lectureFilter)
//                if(searchText=="")  filterNum2 = 0
//                else filterNum2 = 1
//                showList()
            }

        })

    }

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
        Log.d("category",category.toString())
        if (category?.lang != null) lectureFilter.lang = category!!.lang
        if (category?.type != null) lectureFilter.type = category!!.type
        if (category?.price != null) lectureFilter.price = category!!.price
        classService.showLectureSearch(lectureFilter)
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

                totalPage = if(result.lectureCount%12==0) result.lectureCount/12 else result.lectureCount/12 + 1
                page = if(pageNum%5==0) pageNum/5 else pageNum/5+1


                lectureCount = result.lectureCount

                Log.d("lecturecount",lectureCount.toString())

                pageButton()



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

    private fun pageButtonClick() {
        binding.lecturePagenum1Tv.setOnClickListener {
            pageNum = (page - 1) * 5 + 1
            lectureFilter.pageNum = pageNum
            pageButton()
            classService.showLectureSearch(lectureFilter)
        }
        binding.lecturePagenum2Tv.setOnClickListener {
            pageNum = (page - 1) * 5 + 2
            lectureFilter.pageNum = pageNum
            pageButton()
            classService.showLectureSearch(lectureFilter)
        }
        binding.lecturePagenum3Tv.setOnClickListener {
            pageNum = (page - 1) * 5 + 3
            lectureFilter.pageNum = pageNum
            pageButton()
            classService.showLectureSearch(lectureFilter)
        }
        binding.lecturePagenum4Tv.setOnClickListener {
            pageNum = (page - 1) * 5 + 4
            lectureFilter.pageNum = pageNum
            pageButton()
            classService.showLectureSearch(lectureFilter)
        }
        binding.lecturePagenum5Tv.setOnClickListener {
            pageNum = (page - 1) * 5 + 5
            lectureFilter.pageNum = pageNum
            pageButton()
            classService.showLectureSearch(lectureFilter)
        }
        binding.lecturePagePreviousIv.setOnClickListener {
            pageNum = (page-2)*5 + 1
            lectureFilter.pageNum = pageNum
            pageButton()
            classService.showLectureSearch(lectureFilter)
        }
        binding.lecturePageNextIv.setOnClickListener {
            pageNum = page * 5 + 1
            lectureFilter.pageNum = pageNum
            pageButton()
            classService.showLectureSearch(lectureFilter)
        }

    }



    private fun pageButton(){
        //페이지 번호
        binding.lecturePagenum1Tv.text = ((page-1)*5+1).toString()
        binding.lecturePagenum2Tv.text = ((page-1)*5+2).toString()
        binding.lecturePagenum3Tv.text = ((page-1)*5+3).toString()
        binding.lecturePagenum4Tv.text = ((page-1)*5+4).toString()
        binding.lecturePagenum5Tv.text = ((page-1)*5+5).toString()

        //화살표 visibility 설정
        if(page == 1)   binding.lecturePagePreviousIv.visibility = View.INVISIBLE
        else    binding.lecturePagePreviousIv.visibility = View.VISIBLE
        if(totalPage>=(page-1)*5+1 && totalPage<=(page-1)*5+5)
            binding.lecturePageNextIv.visibility = View.INVISIBLE
        else    binding.lecturePageNextIv.visibility = View.VISIBLE

        //숫자 버튼 visibility 설정
        if(totalPage-page*5 == -1){
            binding.lecturePagenum2Tv.visibility = View.VISIBLE
            binding.lecturePagenum3Tv.visibility = View.VISIBLE
            binding.lecturePagenum4Tv.visibility = View.VISIBLE
            binding.lecturePagenum5Tv.visibility = View.INVISIBLE
        }else if(totalPage-page*5 == -2){
            binding.lecturePagenum2Tv.visibility = View.VISIBLE
            binding.lecturePagenum3Tv.visibility = View.VISIBLE
            binding.lecturePagenum4Tv.visibility = View.INVISIBLE
            binding.lecturePagenum5Tv.visibility = View.INVISIBLE
        } else if(totalPage-page*5 == -3){
            binding.lecturePagenum2Tv.visibility = View.VISIBLE
            binding.lecturePagenum3Tv.visibility = View.INVISIBLE
            binding.lecturePagenum4Tv.visibility = View.INVISIBLE
            binding.lecturePagenum5Tv.visibility = View.INVISIBLE
        }
        else if(totalPage-page*5 == -4){
            binding.lecturePagenum2Tv.visibility = View.INVISIBLE
            binding.lecturePagenum3Tv.visibility = View.INVISIBLE
            binding.lecturePagenum4Tv.visibility = View.INVISIBLE
            binding.lecturePagenum5Tv.visibility = View.INVISIBLE
        }else{
            binding.lecturePagenum2Tv.visibility = View.VISIBLE
            binding.lecturePagenum3Tv.visibility = View.VISIBLE
            binding.lecturePagenum4Tv.visibility = View.VISIBLE
            binding.lecturePagenum5Tv.visibility = View.VISIBLE
        }

        if(lectureCount == 0){
            binding.lecturePagenum1Tv.visibility = View.INVISIBLE
            binding.lecturePagenum2Tv.visibility = View.INVISIBLE
            binding.lecturePagenum3Tv.visibility = View.INVISIBLE
            binding.lecturePagenum4Tv.visibility = View.INVISIBLE
            binding.lecturePagenum5Tv.visibility = View.INVISIBLE
            binding.lecturePageNextIv.visibility = View.INVISIBLE
        }


        //background circle 설정
        if(pageNum%5 == 1) {
            binding.lecturePagenum1Tv.setBackgroundResource(R.drawable.circle_debri_debri_8)
            binding.lecturePagenum2Tv.setBackgroundResource(R.color.transparent)
            binding.lecturePagenum3Tv.setBackgroundResource(R.color.transparent)
            binding.lecturePagenum4Tv.setBackgroundResource(R.color.transparent)
            binding.lecturePagenum5Tv.setBackgroundResource(R.color.transparent)
        }else if(pageNum%5 == 2){
            binding.lecturePagenum1Tv.setBackgroundResource(R.color.transparent)
            binding.lecturePagenum2Tv.setBackgroundResource(R.drawable.circle_debri_debri_8)
            binding.lecturePagenum3Tv.setBackgroundResource(R.color.transparent)
            binding.lecturePagenum4Tv.setBackgroundResource(R.color.transparent)
            binding.lecturePagenum5Tv.setBackgroundResource(R.color.transparent)
        }else if(pageNum%5 == 3){
            binding.lecturePagenum1Tv.setBackgroundResource(R.color.transparent)
            binding.lecturePagenum2Tv.setBackgroundResource(R.color.transparent)
            binding.lecturePagenum3Tv.setBackgroundResource(R.drawable.circle_debri_debri_8)
            binding.lecturePagenum4Tv.setBackgroundResource(R.color.transparent)
            binding.lecturePagenum5Tv.setBackgroundResource(R.color.transparent)
        }else if(pageNum%5 == 4){
            binding.lecturePagenum1Tv.setBackgroundResource(R.color.transparent)
            binding.lecturePagenum2Tv.setBackgroundResource(R.color.transparent)
            binding.lecturePagenum3Tv.setBackgroundResource(R.color.transparent)
            binding.lecturePagenum4Tv.setBackgroundResource(R.drawable.circle_debri_debri_8)
            binding.lecturePagenum5Tv.setBackgroundResource(R.color.transparent)
        }else if(pageNum%5 == 0){
            binding.lecturePagenum1Tv.setBackgroundResource(R.color.transparent)
            binding.lecturePagenum2Tv.setBackgroundResource(R.color.transparent)
            binding.lecturePagenum3Tv.setBackgroundResource(R.color.transparent)
            binding.lecturePagenum4Tv.setBackgroundResource(R.color.transparent)
            binding.lecturePagenum5Tv.setBackgroundResource(R.drawable.circle_debri_debri_8)
        }

    }

}

