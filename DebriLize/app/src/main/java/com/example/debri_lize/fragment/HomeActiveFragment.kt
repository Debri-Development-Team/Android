package com.example.debri_lize.fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.debri_lize.CustomDialog
import com.example.debri_lize.R
import com.example.debri_lize.activity.LectureDetailActivity
import com.example.debri_lize.activity.MainActivity
import com.example.debri_lize.activity.auth.ProfileActivity
import com.example.debri_lize.adapter.home.ChapterRVAdapter
import com.example.debri_lize.adapter.home.LectureRVAdapter
import com.example.debri_lize.data.curriculum.*
import com.example.debri_lize.databinding.FragmentHomeActiveBinding

import com.example.debri_lize.service.CurriculumService
import com.example.debri_lize.utils.*
import com.example.debri_lize.view.curriculum.*
import com.google.android.material.bottomsheet.BottomSheetDialog
import java.sql.Timestamp
import java.text.SimpleDateFormat
import kotlin.properties.Delegates

class HomeActiveFragment(

    private var curriculumIdx: Int,
    private var index: Int //my curriculum index

    ) : Fragment(), ShowCurriculumDetailView, EditCurriculumNameView,
    EditCurriculumVisibleView, EditCurriculumStatusView, DeleteCurriculumView, ResetCurriculumView {

    lateinit var context: MainActivity

    lateinit var binding: FragmentHomeActiveBinding

    lateinit var chapterRVAdapter: ChapterRVAdapter
    lateinit var lectureRVAdapter: LectureRVAdapter

    var arrayImg = arrayOf(R.raw.green, R.raw.red, R.raw.purple)

    val chapter = ArrayList<ChapterList>()
    val lecture = ArrayList<LectureList>()


    //api
    val curriculumService = CurriculumService()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeActiveBinding.inflate(inflater, container, false)

        //click userImg -> profile
        binding.homeDebriUserIv.setOnClickListener{
            val intent = Intent(context, ProfileActivity::class.java)
            startActivity(intent)
        }

        //click add lecture -> ClassFragment
        binding.homeCurriculumAddLectureLayout.setOnClickListener{
            context.binding.mainBnv.selectedItemId = R.id.classFragment
            val passBundleBFragment = ClassFragment()

            context.binding.mainBnv.selectedItemId = R.id.classFragment

            //fragment to fragment
            activity?.supportFragmentManager!!.beginTransaction()
                .replace(R.id.main_frm, passBundleBFragment)
                .commit()
        }

        //chapter
        binding.homeCurriculumLectureImgRv.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        chapterRVAdapter = ChapterRVAdapter(this)
        binding.homeCurriculumLectureImgRv.adapter = chapterRVAdapter

        //lecture
        binding.homeCurriculumLectureRv.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        lectureRVAdapter = LectureRVAdapter()
        binding.homeCurriculumLectureRv.adapter = lectureRVAdapter

        //api - 8.3 커리큘럼 상세 조회 api : 홈
        curriculumService.setShowCurriculumDetailView(this)
        Log.d("fragment cu", curriculumIdx.toString())
        curriculumService.showCurriculumDetail(curriculumIdx)

        return binding.root
    }

    //context 받아오기
    override fun onAttach(context: Context) {
        super.onAttach(context)
        this.context = context as MainActivity
    }

    //bottom sheet
    private fun bottomSheetSetting(visibleStatus : String){
        lateinit var bottomSheetView : View
        val bottomSheetDialog = BottomSheetDialog(context)
        bottomSheetView = layoutInflater.inflate(R.layout.fragment_bottom_sheet_four, null)
        bottomSheetDialog.setContentView(bottomSheetView)


        //toast message
        var publicToast = layoutInflater.inflate(R.layout.toast_curri_public,null)
        var toast = Toast(context)
        toast.view = publicToast
        toast.setGravity(Gravity.CENTER_HORIZONTAL,0,0)

       if(visibleStatus=="INACTIVE"){ //현재 커리큘럼 : 비공개
           bottomSheetView.findViewById<TextView>(R.id.bottom_sheet_four_tv1).text = "공개로 전환하기"

            //비공개 -> 공개
           touchEvent(bottomSheetView.findViewById(R.id.bottom_sheet_four_tv1))
            bottomSheetView.findViewById<TextView>(R.id.bottom_sheet_four_tv1).setOnClickListener {
                //공개 완료 토스트메세지
                publicToast.findViewById<TextView>(R.id.toast_curri_public_tv).text = "커리큘럼이 공개로 변경되었습니다!"
                publicToast.findViewById<ImageView>(R.id.toast_curri_public_mark_iv).setImageResource(R.drawable.ic_open)
                toast.show()

                //api - 8.4.2 커리큘럼 공유 상태 수정 api
                curriculumService.setEditCurriculumVisibleView(this)
                curriculumService.editCurriculumVisible(EditCurriculumVisible(curriculumIdx, "ACTIVE"))

                bottomSheetDialog.dismiss()
            }


        }
       else{ //현재 커리큘럼 : 공개
           bottomSheetView.findViewById<TextView>(R.id.bottom_sheet_four_tv1).text = "비공개로 전환하기"

           //공개 -> 비공개
           touchEvent(bottomSheetView.findViewById(R.id.bottom_sheet_four_tv1))
           bottomSheetView.findViewById<TextView>(R.id.bottom_sheet_four_tv1).setOnClickListener{
               //비공개 완료 토스트메세지
               publicToast.findViewById<TextView>(R.id.toast_curri_public_tv).text = "커리큘럼이 비공개로 변경되었습니다!"
               publicToast.findViewById<TextView>(R.id.toast_curri_public_tv).setTextSize(15F)
               publicToast.findViewById<ImageView>(R.id.toast_curri_public_mark_iv).setImageResource(R.drawable.ic_hide)
               toast.show()

               //api - 8.4.2 커리큘럼 공유 상태 수정 api
               curriculumService.setEditCurriculumVisibleView(this)
               curriculumService.editCurriculumVisible(EditCurriculumVisible(curriculumIdx, "INACTIVE"))

               bottomSheetDialog.dismiss()
           }


        }

        //커리큘럼 이름 변경하기
        touchEvent(bottomSheetView.findViewById(R.id.bottom_sheet_four_tv2))
        bottomSheetView.findViewById<TextView>(R.id.bottom_sheet_four_tv2).setOnClickListener {
            curriculumService.setEditCurriculumNameView(this)

            //add dialog code
            val dialog = CustomDialog(context)
            dialog.changeCurriNameDlg()
            //이름 적은 후 ok 버튼 클릭 시
            dialog.setOnClickListenerETC(object:CustomDialog.ButtonClickListenerETC{
                override fun onClicked(TF: Boolean, reason : String) {

                    //api - 8.4.1 커리큘럼 제목 수정 api
                    curriculumService.editCurriculumName(EditCurriculumName(curriculumIdx, reason))
                }

            })
            bottomSheetDialog.dismiss()
        }

        //커리큘럼 초기화하기
        touchEvent(bottomSheetView.findViewById(R.id.bottom_sheet_four_tv3))
        bottomSheetView.findViewById<TextView>(R.id.bottom_sheet_four_tv3).setOnClickListener {
            curriculumService.setResetCurriculumView(this)
            //add dialog code
            val dialog = CustomDialog(context)
            dialog.initializeCurriDlg()
            //yes 버튼 클릭시
            dialog.setOnClickListener(object:CustomDialog.ButtonClickListener{
                override fun onClicked(TF: Boolean) {
                    //api
                    curriculumService.resetCurriculum(curriculumIdx)
                }

            })
            bottomSheetDialog.dismiss()
        }

        //커리큘럼 삭제하기
        touchEvent(bottomSheetView.findViewById(R.id.bottom_sheet_four_tv4))
        bottomSheetView.findViewById<TextView>(R.id.bottom_sheet_four_tv4).setOnClickListener {
            curriculumService.setDeleteCurriculumView(this)

            val dialog = CustomDialog(context)
            dialog.deleteCurriDlg()
            //yes 버튼 클릭시
            dialog.setOnClickListener(object:CustomDialog.ButtonClickListener{
                override fun onClicked(TF: Boolean) {
                    //api - 8.6 커리큘럼 삭제 api
                    Log.d("deleteCurriIdx", curriculumIdx.toString())
                    curriculumService.deleteCurriculum(curriculumIdx)

                }
            })
            bottomSheetDialog.dismiss()
        }

        binding.homeCurriculumSettingLayout.setOnClickListener {
            bottomSheetDialog.show()
        }

        //close button
        bottomSheetView.findViewById<TextView>(R.id.bottom_sheet_close_tv).setOnClickListener {
            bottomSheetDialog.dismiss()
        }

    }

    private fun touchEvent(bind : TextView){
        bind.setOnTouchListener(object : View.OnTouchListener {
            override fun onTouch(view: View?, event: MotionEvent?): Boolean {
                when (event?.action) {
                    MotionEvent.ACTION_DOWN -> {
                        bind.setTextColor(ContextCompat.getColor(context, R.color.white))
                    }
                    MotionEvent.ACTION_UP -> {
                        bind.setTextColor(ContextCompat.getColor(context, R.color.darkmode_background))
                        bind.performClick()
                    }
                }

                //리턴값이 false면 동작 안됨
                return true //or false
            }


        })
    }

    //timestamp to date
    private fun timestampToDate(timestamp: Timestamp): String? {
        val sdf = SimpleDateFormat("yyyy년 MM월 dd일")
        val date = sdf.format(timestamp)

        return date+"에 완성함"
    }

    private fun waveAnimation(progressRate : Int){
        binding.waveLoadingView.setProgressValue(progressRate)
        binding.waveLoadingView.setAmplitudeRatio(50)
        binding.waveLoadingView.setAnimDuration(8000)
        binding.waveLoadingView.startAnimation()
    }

    //8.3 커리큘럼 상세 조회 api : 홈
    override fun onShowCurriculumDetailSuccess(code: Int, result: CurriculumDetail) {
        when(code){
            200->{
                //활성화

                //chapter
                //동적으로 화면 크기 지정
                if(result.chapterListResList.isEmpty()){
                    //recycler view size
                    binding.homeCurriculumLectureImgRv.layoutParams = binding.homeCurriculumLectureImgRv.layoutParams.apply {
                        this.height = (381 * getUISize("dpi"))
                    }

                    binding.homeCurriculumActiveCircle.setBackgroundResource(R.drawable.circle_border_red_opacity_30)
                    binding.homeNoLectureTv.visibility = View.VISIBLE
                    binding.waveLoadingView.visibility = View.GONE

                }else if(result.chapterListResList.size == 1){

                    //recycler view size
                    binding.homeCurriculumLectureImgRv.layoutParams = binding.homeCurriculumLectureImgRv.layoutParams.apply {
                        this.height = (250 * getUISize("dpi"))
                    }

                    val param = binding.homeCurriculumLectureImgRv.layoutParams as ViewGroup.MarginLayoutParams
                    param.setMargins(30* getUISize("dpi"),131* getUISize("dpi"),30* getUISize("dpi"),0)
                    binding.homeCurriculumLectureImgRv.layoutParams = param

                    binding.homeCurriculumActiveCircle.setBackgroundResource(R.drawable.circle_debri_opacity_30)
                    binding.homeNoLectureTv.visibility = View.GONE
                    binding.waveLoadingView.visibility = View.VISIBLE

                }else{
                    //recycler view size
                    binding.homeCurriculumLectureImgRv.layoutParams = binding.homeCurriculumLectureImgRv.layoutParams.apply {
                        this.height = WRAP_CONTENT
                    }

                    val param = binding.homeCurriculumLectureImgRv.layoutParams as ViewGroup.MarginLayoutParams
                    param.setMargins(30* getUISize("dpi"),15* getUISize("dpi"),30* getUISize("dpi"),0)
                    binding.homeCurriculumLectureImgRv.layoutParams = param

                    binding.homeCurriculumActiveCircle.setBackgroundResource(R.drawable.circle_debri_opacity_30)
                    binding.homeNoLectureTv.visibility = View.GONE
                    binding.waveLoadingView.visibility = View.VISIBLE
                }

                //chapter recycler view
                chapter.clear()

                var j = 0
                chapter.apply {

                    for(i in result.chapterListResList!!){
                        //else if로 변경
                        if(j%3==0){
                            chapter.add(ChapterList(i.chIdx,i.chName,i.chNum,i.language,i.chComplete,i.progressOrder,i.completeChNum,arrayImg[0], i.lectureIdx, i.curriIdx))
                        }
                        if(j%3==1){
                            chapter.add(ChapterList(i.chIdx,i.chName,i.chNum,i.language,i.chComplete,i.progressOrder,i.completeChNum,arrayImg[1], i.lectureIdx, i.curriIdx))
                        }
                        if(j%3==2){
                            chapter.add(ChapterList(i.chIdx,i.chName,i.chNum,i.language,i.chComplete,i.progressOrder,i.completeChNum,arrayImg[2], i.lectureIdx, i.curriIdx))
                        }
                        j++
                    }

                    chapterRVAdapter.datas = chapter
                    chapterRVAdapter.notifyDataSetChanged()
                }

                //move
                if(index==0){
                    //1번째 커리큘럼
                        binding.homeCurriculumPreviousIv.visibility = View.INVISIBLE
                        binding.homeCurriculumNextIv.setOnClickListener{
                            //다음 커리로 이동

                        }
                }else{

                    binding.homeCurriculumPreviousIv.visibility = View.VISIBLE
                    binding.homeCurriculumPreviousIv.setOnClickListener{
                        //이전 커리로 이동
                    }
                    binding.homeCurriculumNextIv.setOnClickListener {
                        //다음 커리로 이동
                    }
                }

                //홈 화면
                curriculumIdx = result.curriculumIdx
                binding.homeCurriculumTitleTv.text = result.curriculumName //커리큘럼 이름
                binding.homeCurriculumDateTv.text = timestampToDate(result.createdAt) //커리큘럼 생성 날짜

                //공개 or 비공개
                if(result.visibleStatus=="ACTIVE"){ //공개
                    binding.homeCurriculumHideIv.setImageResource(R.drawable.ic_open)
                    binding.homeCurriculumHideTv.text = "공개 중"
                }else{ //비공개
                    binding.homeCurriculumHideIv.setImageResource(R.drawable.ic_hide)
                    binding.homeCurriculumHideTv.text = "비공개"
                }

                //dday
                binding.homeCurriculumDdayTv.text = "D-"+result.dday.toString()

                //progress rate
                waveAnimation(result.progressRate.toInt())
                binding.homeCurriculumProgressTv2.text = result.progressRate.toInt().toString()

                //lecture
                lecture.clear()

                lecture.apply {
                    for(i in result.lectureListResList){
                        lecture.add(LectureList(i.lectureIdx,i.lectureName,i.language,i.chNum,i.progressRate,i.type,i.price,i.usedCnt,i.scrapStatus,i.likeStatus))
                    }

                    lectureRVAdapter.datas = lecture
                    lectureRVAdapter.notifyDataSetChanged()

                    //click recyclerview item
                    lectureRVAdapter.setItemClickListener(object : LectureRVAdapter.OnItemClickListener {
                        override fun onClick(v: View, position: Int) {
                            val intent = Intent(context, LectureDetailActivity::class.java)
                            intent.putExtra("lectureIdx", lecture[position].lectureIdx)
                            intent.putExtra("lectureName", lecture[position].lectureName)
                            startActivity(intent)

                        }
                    })
                }

                //bottomSheet
                bottomSheetSetting(result.visibleStatus)

            }
        }
    }

    override fun onShowCurriculumDetailFailure(code: Int) {

    }

    //8.4.1 커리큘럼 제목 수정 api
    override fun onEditCurriculumNameSuccess(code: Int) {
        when(code){
            200->{
                //api - 8.3 커리큘럼 상세 조회 api : 홈
                curriculumService.setShowCurriculumDetailView(this)
                curriculumService.showCurriculumDetail(curriculumIdx)
            }
        }
    }

    override fun onEditCurriculumNameFailure(code: Int) {

    }

    //8.4.2 커리큘럼 공유 상태 수정 api
    override fun onEditCurriculumVisibleSuccess(code: Int) {
        when(code){
            200->{
                //api - 8.3 커리큘럼 상세 조회 api : 홈
                curriculumService.setShowCurriculumDetailView(this)
                curriculumService.showCurriculumDetail(curriculumIdx)
            }
        }
    }

    override fun onEditCurriculumVisibleFailure(code: Int) {

    }

    //8.4.3 커리큘럼 활성 상태 수정 api
    override fun onEditCurriculumStatusSuccess(code: Int) {
        when(code){
            200->{

            }
        }
    }

    override fun onEditCurriculumStatusFailure(code: Int) {

    }

    //8.6 커리큘럼 삭제 api
    override fun onDeleteCurriculumViewSuccess(code: Int) {
        when(code){
            200->{
                Toast.makeText(context, "커리큘럼 삭제", Toast.LENGTH_SHORT).show()
                //8.2 조회
            }
        }
    }

    override fun onDeleteCurriculumViewFailure(code: Int) {

    }

    //8.11 커리큘럼 리셋 api
    override fun onResetCurriculumSuccess(code: Int) {
        when(code){
            200->{
                Toast.makeText(context, "커리큘럼 리셋", Toast.LENGTH_SHORT).show()
                curriculumService.showCurriculumDetail(curriculumIdx)
            }
        }
    }

    override fun onResetCurriculumFailure(code: Int) {

    }


}