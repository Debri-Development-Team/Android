package com.example.debri_lize.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.example.debri_lize.R
import com.example.debri_lize.data.curriculum.NewCurriculum
import com.example.debri_lize.databinding.ActivityCurriculumSettingBinding
import com.example.debri_lize.service.CurriculumService
import com.example.debri_lize.utils.getUserName
import com.example.debri_lize.view.curriculum.CreateCurriculumView
import kotlin.concurrent.thread

class CurriculumSettingActivity : AppCompatActivity(), CreateCurriculumView {
    lateinit var binding: ActivityCurriculumSettingBinding

    var langText: String = ""
    var publicText: String = ""

    var curriName: String = ""
    var curriExplain: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCurriculumSettingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //버튼 클릭한 텍스트 받아오기
        radioBtnClick()
        //focus effect
        editTextFocus()
        //입력 text 받아오기
        textTyping()

        //back
        binding.curriculumSettingPreviousIv.setOnClickListener{
            finish()
        }

        //시작하기 버튼 클릭 시 입력하지 않은 것이 있는지 체크
        inputCheck()


    }

    //시작하기 버튼 클릭 시 입력하지 않은 것이 있는지 체크
    private fun inputCheck(){
        //start btn
        binding.curriculumSettingStartBtn.setOnClickListener {
            if(curriName.isNotBlank() && curriExplain.isNotBlank() && langText.isNotBlank() && publicText.isNotBlank()){
                //api - 8.1 커리큘럼 생성 api
                var curriculumService = CurriculumService()
                curriculumService.setCreateCurriculumView(this)
                if(publicText=="공개")    publicText="ACTIVE"
                else if(publicText=="비공개")  publicText="INACTIVE"
                curriculumService.createCurriculum(NewCurriculum(curriName, getUserName()!!, publicText, langText, curriExplain))
            }else{
                binding.curriculumSettingStartBtn.setTextColor(ContextCompat.getColor(this@CurriculumSettingActivity, R.color.red))
                binding.curriculumSettingStartBtn.setBackgroundResource(R.drawable.border_round_red_transparent_6)
                thread(start = true){
                    Thread.sleep(1300)
                    runOnUiThread{
                        binding.curriculumSettingStartBtn.setTextColor(ContextCompat.getColor(this@CurriculumSettingActivity, R.color.darkmode_background))
                        binding.curriculumSettingStartBtn.setBackgroundResource(R.drawable.border_round_transparent_debri_10)

                    }
                }
            }

            if(curriName.isBlank()){
                binding.curriculumSettingCurrinameLayout.setBackgroundResource(R.drawable.border_round_red_transparent_6)
                thread(start = true){
                    Thread.sleep(1300)
                    runOnUiThread{
                        binding.curriculumSettingCurrinameLayout.setBackgroundResource(R.drawable.border_round_white_transparent_10)

                    }
                }
            }
            if(curriExplain.isBlank()){
                binding.curriculumSettingCurriExplainLayout.setBackgroundResource(R.drawable.border_round_red_transparent_6)
                thread(start = true){
                    Thread.sleep(1300)
                    runOnUiThread{
                        binding.curriculumSettingCurriExplainLayout.setBackgroundResource(R.drawable.border_round_white_transparent_10)

                    }
                }
            }
            if(langText.isBlank()){
                binding.curriculumSettingLangTagTv.setTextColor(ContextCompat.getColor(this@CurriculumSettingActivity, R.color.red))
                thread(start = true){
                    Thread.sleep(1300)
                    runOnUiThread{
                        binding.curriculumSettingLangTagTv.setTextColor(ContextCompat.getColor(this@CurriculumSettingActivity, R.color.white))

                    }
                }
            }
            if(publicText.isBlank()){
                binding.curriculumSettingVisibleTagTv.setTextColor(ContextCompat.getColor(this@CurriculumSettingActivity, R.color.red))
                thread(start = true){
                    Thread.sleep(1300)
                    runOnUiThread{
                        binding.curriculumSettingVisibleTagTv.setTextColor(ContextCompat.getColor(this@CurriculumSettingActivity, R.color.white))

                    }
                }
            }
        }
    }


    private fun radioBtnClick(){
        //언어
        //front
        binding.curriculumSettingFrontCb.setOnCheckedChangeListener { button, ischecked ->
            if (ischecked){
                binding.curriculumSettingBackCb.isChecked = false
                binding.curriculumSettingPythonCb.isChecked = false
                binding.curriculumSettingCCb.isChecked = false
                langText = button.text.toString()
            }else{
                if(langText==button.text.toString()){
                    langText = ""
                }
            }
        }
        //Back
        binding.curriculumSettingBackCb.setOnCheckedChangeListener { button, ischecked ->
            if (ischecked){
                binding.curriculumSettingFrontCb.isChecked = false
                binding.curriculumSettingPythonCb.isChecked = false
                binding.curriculumSettingCCb.isChecked = false
                langText = button.text.toString()
            }else{
                if(langText==button.text.toString()){
                    langText = ""
                }
            }
        }
        //python
        binding.curriculumSettingPythonCb.setOnCheckedChangeListener { button, ischecked ->
            if (ischecked){
                binding.curriculumSettingBackCb.isChecked = false
                binding.curriculumSettingFrontCb.isChecked = false
                binding.curriculumSettingCCb.isChecked = false
                langText = button.text.toString()
            }else{
                if(langText==button.text.toString()){
                    langText = ""
                }
            }
        }
        //C lang
        binding.curriculumSettingCCb.setOnCheckedChangeListener { button, ischecked ->
            if (ischecked){
                binding.curriculumSettingBackCb.isChecked = false
                binding.curriculumSettingPythonCb.isChecked = false
                binding.curriculumSettingFrontCb.isChecked = false
                langText = button.text.toString()
            }else{
                if(langText==button.text.toString()){
                    langText = ""
                }
            }
        }

        //공개 여부
        //공개
        binding.classCurriTagPublicCb.setOnCheckedChangeListener { button, ischecked ->
            if (ischecked){
                binding.classCurriTagPrivateCb.isChecked = false
                publicText = button.text.toString()
            }else{
                if(publicText==button.text.toString()){
                    publicText = ""
                }
            }
        }

        //비공개
        binding.classCurriTagPrivateCb.setOnCheckedChangeListener { button, ischecked ->
            if (ischecked){
                binding.classCurriTagPublicCb.isChecked = false
                publicText = button.text.toString()
            }else{
                if(publicText==button.text.toString()){
                    publicText = ""
                }
            }
        }

    }

    private fun textTyping(){
        //curriculum name
        binding.curriculumSettingCurrinameEt.addTextChangedListener(object : TextWatcher {
            //입력이 끝날 때
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            //입력하기 전에
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            //타이핑되는 텍스트에 변화가 있을 때
            override fun afterTextChanged(p0: Editable?) {
                curriName = binding.curriculumSettingCurrinameEt.text.toString()

            }

        })

        //curriculum explain
        binding.curriculumSettingExplainEt.addTextChangedListener(object : TextWatcher {
            //입력이 끝날 때
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            //입력하기 전에
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            //타이핑되는 텍스트에 변화가 있을 때
            override fun afterTextChanged(p0: Editable?) {
                curriExplain = binding.curriculumSettingExplainEt.text.toString()

            }

        })
    }


    private fun editTextFocus(){
        //커리 이름
        binding.curriculumSettingCurrinameEt.setOnFocusChangeListener(object : View.OnFocusChangeListener {
            override fun onFocusChange(view: View, hasFocus: Boolean) {
                if (hasFocus) {
                    //  포커스시
                    binding.curriculumSettingCurrinameLayout.setBackgroundResource(R.drawable.border_round_debri_transparent_10)
                } else {
                    //  포커스 뺏겼을 때
                    binding.curriculumSettingCurrinameLayout.setBackgroundResource(R.drawable.border_round_white_transparent_10)
                }
            }
        })
        //커리 설명
        binding.curriculumSettingExplainEt.setOnFocusChangeListener(object : View.OnFocusChangeListener {
            override fun onFocusChange(view: View, hasFocus: Boolean) {
                if (hasFocus) {
                    //  포커스시
                    binding.curriculumSettingCurriExplainLayout.setBackgroundResource(R.drawable.border_round_debri_transparent_10)
                } else {
                    //  포커스 뺏겼을 때
                    binding.curriculumSettingCurriExplainLayout.setBackgroundResource(R.drawable.border_round_white_transparent_10)
                }
            }
        })
    }

    override fun onCreateCurriculumSuccess(code: Int) {
        when(code){
            200->{
                //커리 생성 확인 토스트메시지
                var addLectureToast = layoutInflater.inflate(R.layout.toast_add_lecture_to_curri,null)
                addLectureToast.findViewById<TextView>(R.id.toast_add_lecture_tv).text = "커리큘럼이 생성되었습니다!"
                var toast = Toast(this)
                toast.view = addLectureToast
                toast.setGravity(Gravity.CENTER_HORIZONTAL,0,0)
                toast.show()
                finish()
            }
        }
    }

    override fun onCreateCurriculumFailure(code: Int) {
        Log.d("createcurriculumfail","$code")
    }

}