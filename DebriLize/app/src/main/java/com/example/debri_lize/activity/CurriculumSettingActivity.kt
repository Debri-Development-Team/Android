package com.example.debri_lize.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.debri_lize.databinding.ActivityCurriculumSettingBinding

class CurriculumSettingActivity : AppCompatActivity() {
    lateinit var binding: ActivityCurriculumSettingBinding

    var langText: String = ""
    var publicText: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCurriculumSettingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        radioBtnClick()

        //back
        binding.curriculumSettingPreviousIv.setOnClickListener{
            finish()
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

}