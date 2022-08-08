package com.example.debri_lize

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText

class CustomDialog(context: Context) {
    private val dialog = Dialog(context)

    fun showWriteDlg(){
        dialog.setContentView(R.layout.dialog_write)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT)) //뒤 여백 없애기
        dialog.window!!.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT)
        dialog.setCanceledOnTouchOutside(false)  //다이얼로그 바깥쪽 클릭 시 팝업창 꺼지지않게
        dialog.setCancelable(false)

        //yes
        dialog.findViewById<Button>(R.id.dialog_write_yes_btn).setOnClickListener {
            //게시물 작성하기
            onClickListener.onClicked(true)
            dialog.dismiss()
        }

        //no
        dialog.findViewById<Button>(R.id.dialog_write_no_btn).setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }

    fun showCancelDlg(){
        dialog.setContentView(R.layout.dialog_cancel)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.window!!.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT)
        dialog.setCanceledOnTouchOutside(false)
        dialog.setCancelable(false)

        //yes
        dialog.findViewById<Button>(R.id.dialog_cancel_yes_btn).setOnClickListener {
            //게시물 작성 취소 : writeActivity 종료하기
            onClickListener.onClicked(true)
            dialog.dismiss()
        }

        //no
        dialog.findViewById<Button>(R.id.dialog_cancel_no_btn).setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }

    fun showEditDlg() {
        dialog.setContentView(R.layout.dialog_edit_check)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.window!!.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT)
        dialog.setCanceledOnTouchOutside(false)
        dialog.setCancelable(false)

        //yes
        dialog.findViewById<Button>(R.id.dialog_edit_check_yes_btn).setOnClickListener {
            //게시물 수정하기
            onClickListener.onClicked(true)
            dialog.dismiss()
        }

        //no
        dialog.findViewById<Button>(R.id.dialog_edit_check_no_btn).setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }

    fun showEditCancelDlg() {
        dialog.setContentView(R.layout.dialog_edit_cancel)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.window!!.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT)
        dialog.setCanceledOnTouchOutside(false)
        dialog.setCancelable(false)

        //yes
        dialog.findViewById<Button>(R.id.dialog_edit_cancel_yes_btn).setOnClickListener {
            //게시물 수정 취소
            onClickListener.onClicked(true)
            dialog.dismiss()
        }

        //no
        dialog.findViewById<Button>(R.id.dialog_edit_cancel_no_btn).setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }

    fun showReportDlg(){
        dialog.setContentView(R.layout.dialog_report_text)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.window!!.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT)
        dialog.setCanceledOnTouchOutside(false)
        dialog.setCancelable(false)

        //yes
        dialog.findViewById<Button>(R.id.dialog_report_yes_btn).setOnClickListener {
            //신고 사유
            onClickListenerETC.onClicked(true, dialog.findViewById<EditText>(R.id.dialog_report_text_et).text.toString())
            dialog.dismiss()
        }

        //no
        dialog.findViewById<Button>(R.id.dialog_report_no_btn).setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }


    interface ButtonClickListener{
        fun onClicked(TF: Boolean)
    }

    interface ButtonClickListenerETC{
        fun onClicked(TF: Boolean, reason : String)
    }


    private lateinit var onClickListener: ButtonClickListener
    private lateinit var onClickListenerETC: ButtonClickListenerETC

    fun setOnClickListener(listener: ButtonClickListener){
        onClickListener = listener
    }

    fun setOnClickListenerETC(listener: ButtonClickListenerETC){
        onClickListenerETC = listener
    }




}