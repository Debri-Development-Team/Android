package com.debri_main.debri.fragment

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.debri_main.debri.CustomDialog
import com.debri_main.debri.R
import com.debri_main.debri.activity.LectureDetailActivity
import com.debri_main.debri.activity.MainActivity
import com.debri_main.debri.adapter.home.LectureRVAdapter
import com.debri_main.debri.data.curriculum.*
import com.debri_main.debri.databinding.FragmentHomeInactiveBinding
import com.debri_main.debri.service.CurriculumService
import com.debri_main.debri.view.curriculum.*
import com.google.android.material.bottomsheet.BottomSheetDialog
import java.sql.Timestamp
import java.text.SimpleDateFormat

class HomeInactiveFragment(
    private var curriculumIdx: Int,
    private var index: Int //my curriculum index
) : Fragment(), ShowCurriculumDetailView, EditCurriculumNameView,
    EditCurriculumVisibleView, EditCurriculumStatusView, DeleteCurriculumView {

    lateinit var context: MainActivity
    lateinit var binding: FragmentHomeInactiveBinding

    lateinit var lectureRVAdapter: LectureRVAdapter

    val lecture = ArrayList<LectureList>()

    //api
    val curriculumService = CurriculumService()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeInactiveBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onStart() {
        super.onStart()

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

        //api - 8.3 커리큘럼 상세 조회 api : 홈
        curriculumService.setShowCurriculumDetailView(this)
        curriculumService.showCurriculumDetail(curriculumIdx)

        //lecture
        binding.homeCurriculumLectureRv.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        lectureRVAdapter = LectureRVAdapter()
        binding.homeCurriculumLectureRv.adapter = lectureRVAdapter

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

        //toast
        var publicToast = layoutInflater.inflate(R.layout.toast_curri_public,null)
        var toast = Toast(context)
        toast.view = publicToast
        toast.setGravity(Gravity.CENTER_HORIZONTAL,0,0)

       if(visibleStatus=="INACTIVE"){ //현재 커리큘럼 : 비공개
           bottomSheetView.findViewById<TextView>(R.id.bottom_sheet_four_tv1).text = "공개로 전환하기"

            //비공개 -> 공개
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
           bottomSheetView.findViewById<TextView>(R.id.bottom_sheet_four_tv1).setOnClickListener{
               //비공개 완료 토스트메세지
               publicToast.findViewById<TextView>(R.id.toast_curri_public_tv).text = "커리큘럼이 비공개로 변경되었습니다!"
               publicToast.findViewById<ImageView>(R.id.toast_curri_public_mark_iv).setImageResource(R.drawable.ic_hide)
               toast.show()

               //api - 8.4.2 커리큘럼 공유 상태 수정 api
               curriculumService.setEditCurriculumVisibleView(this)
               curriculumService.editCurriculumVisible(EditCurriculumVisible(curriculumIdx, "INACTIVE"))

               bottomSheetDialog.dismiss()
           }


        }

        //커리큘럼 이름 변경하기
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
        bottomSheetView.findViewById<TextView>(R.id.bottom_sheet_four_tv3).setOnClickListener {
            //add dialog code
            val dialog = CustomDialog(context)
            dialog.initializeCurriDlg()
            //yes 버튼 클릭시
            dialog.setOnClickListener(object:CustomDialog.ButtonClickListener{
                override fun onClicked(TF: Boolean) {

                //api

                }

            })
            bottomSheetDialog.dismiss()
        }

        //커리큘럼 삭제하기
        bottomSheetView.findViewById<TextView>(R.id.bottom_sheet_four_tv4).setOnClickListener {
            curriculumService.setDeleteCurriculumView(this)

            val dialog = CustomDialog(context)
            dialog.deleteCurriDlg()
            //yes 버튼 클릭시
            dialog.setOnClickListener(object:CustomDialog.ButtonClickListener{
                override fun onClicked(TF: Boolean) {
                    //api - 8.6 커리큘럼 삭제 api
                    curriculumService.deleteCurriculum(curriculumIdx)
                }
            })
            bottomSheetDialog.dismiss()
        }

        binding.homeCurriculumSettingIv.setOnClickListener {
            bottomSheetDialog.show()
        }

        //close button
        bottomSheetView.findViewById<TextView>(R.id.bottom_sheet_close_tv).setOnClickListener {
            bottomSheetDialog.dismiss()
        }

    }

    //timestamp to date
    private fun timestampToDate(timestamp: Timestamp): String? {
        val sdf = SimpleDateFormat("yyyy년 MM월 dd일")
        val date = sdf.format(timestamp)

        return date
    }

    //8.3 커리큘럼 상세 조회 api : 홈
    override fun onShowCurriculumDetailSuccess(code: Int, result: CurriculumDetail) {
        when(code){
            200->{
                //8.4.3 커리큘럼 활성 상태 수정 api
                binding.homeCurriculumActiveLayout.setOnClickListener{
                    curriculumService.setEditCurriculumStatusView(this)
                    curriculumService.editCurriculumStatus(EditCurriculumStatus(curriculumIdx, "ACTIVE"))
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
                if(result.dday<0){ //dday 지남
                    binding.homeCurriculumDdayInfoTv.text = "D+"
                    binding.homeCurriculumDdayTv.text = (-result.dday).toString()
                    binding.homeCurriculumDateTv.text = timestampToDate(result.createdAt) + "\n완성함" //커리큘럼 생성 날짜
                }else if(result.dday == 0){ //dday 당일
                    binding.homeCurriculumDdayTv.text = "day"
                    //색상 빨간색으로 변경
                    binding.homeCurriculumDdayTv.setTextColor(Color.parseColor("#FF0000"))
                    binding.homeCurriculumDateTv.text = timestampToDate(result.createdAt) + "\n완성함" //커리큘럼 생성 날짜
                }else{ //dday
                    binding.homeCurriculumDdayTv.text = result.dday.toString()
                    //색상 초록색으로 변경
                    binding.homeCurriculumDdayTv.setTextColor(Color.parseColor("#66CC66"))
                    binding.homeCurriculumDateTv.text = timestampToDate(result.createdAt) + "\n시작함" //커리큘럼 생성 날짜
                }

                //progress rate
                binding.homeCurriculumProgressTv2.text = result.progressRate.toInt().toString()
                if(result.progressRate.toInt()<100){
                    //색상 빨간색으로 변경
                    binding.homeCurriculumProgressTv2.setTextColor(Color.parseColor("#FF0000"))
                }else{
                    //색상 초록색으로 변경
                    binding.homeCurriculumProgressTv2.setTextColor(Color.parseColor("#66CC66"))
                }

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
            }
        }
    }

    override fun onDeleteCurriculumViewFailure(code: Int) {

    }




}