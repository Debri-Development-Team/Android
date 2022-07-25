package com.example.debri_lize.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.debri_lize.BoardFavoriteRVAdapter
import com.example.debri_lize.ClassFavoriteRVAdapter
import com.example.debri_lize.R
import com.example.debri_lize.data.Lecture
import com.example.debri_lize.databinding.FragmentClassBinding

class ClassFragment : Fragment() {

    lateinit var binding: FragmentClassBinding
    lateinit var classfavoriteRVAdapter: ClassFavoriteRVAdapter
    val datas = ArrayList<Lecture>()


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

        initClassFavoriteRecycler()
        onRadioButtonClicked()

    }

    private fun onRadioButtonClicked(){
        //언어
        //front
        binding.classCurriTagFrontCb.setOnCheckedChangeListener { button, checked ->
            if(checked){
                binding.classCurriTagBackCb.visibility = View.GONE
                binding.classCurriTagCCb.visibility = View.GONE
                binding.classCurriTagPythonCb.visibility = View.GONE
            }else{
                binding.classCurriTagBackCb.visibility = View.VISIBLE
                binding.classCurriTagCCb.visibility = View.VISIBLE
                binding.classCurriTagPythonCb.visibility = View.VISIBLE
            }
        }
        //back
        binding.classCurriTagBackCb.setOnCheckedChangeListener { button, checked ->
            if(checked){
                binding.classCurriTagFrontCb.visibility = View.GONE
                binding.classCurriTagCCb.visibility = View.GONE
                binding.classCurriTagPythonCb.visibility = View.GONE
            }else{
                binding.classCurriTagFrontCb.visibility = View.VISIBLE
                binding.classCurriTagCCb.visibility = View.VISIBLE
                binding.classCurriTagPythonCb.visibility = View.VISIBLE
            }
        }
        //C language
        binding.classCurriTagCCb.setOnCheckedChangeListener { button, checked ->
            if(checked){
                binding.classCurriTagBackCb.visibility = View.GONE
                binding.classCurriTagFrontCb.visibility = View.GONE
                binding.classCurriTagPythonCb.visibility = View.GONE
            }else{
                binding.classCurriTagBackCb.visibility = View.VISIBLE
                binding.classCurriTagFrontCb.visibility = View.VISIBLE
                binding.classCurriTagPythonCb.visibility = View.VISIBLE
            }
        }
        //python
        binding.classCurriTagPythonCb.setOnCheckedChangeListener { button, checked ->
            if(checked){
                binding.classCurriTagBackCb.visibility = View.GONE
                binding.classCurriTagCCb.visibility = View.GONE
                binding.classCurriTagFrontCb.visibility = View.GONE
            }else{
                binding.classCurriTagBackCb.visibility = View.VISIBLE
                binding.classCurriTagCCb.visibility = View.VISIBLE
                binding.classCurriTagFrontCb.visibility = View.VISIBLE
            }
        }

        //서적 or 영상
        //서적
        binding.classCurriTagBookCb.setOnCheckedChangeListener { button, checked ->
            if(checked){
                binding.classCurriTagVideoCb.visibility = View.GONE
            }else{
                binding.classCurriTagVideoCb.visibility = View.VISIBLE
            }
        }
        //영상
        binding.classCurriTagVideoCb.setOnCheckedChangeListener { button, checked ->
            if(checked){
                binding.classCurriTagBookCb.visibility = View.GONE
            }else{
                binding.classCurriTagBookCb.visibility = View.VISIBLE
            }
        }

        //가격
        //무료
        binding.classPriceTagForfreeCb.setOnCheckedChangeListener { button, checked ->
            if(checked){
                binding.classPriceTagNoForfreeCb.visibility = View.GONE
            }else{
                binding.classPriceTagNoForfreeCb.visibility = View.VISIBLE
            }
        }
        //유료
        binding.classPriceTagNoForfreeCb.setOnCheckedChangeListener { button, checked ->
            if(checked){
                binding.classPriceTagForfreeCb.visibility = View.GONE
            }else{
                binding.classPriceTagForfreeCb.visibility = View.VISIBLE
            }
        }

    }

    private fun closeBtnClick(view:View){
        view.setOnClickListener{

        }
    }

    private fun initClassFavoriteRecycler() {
        binding.classFavoriteRv.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        classfavoriteRVAdapter = ClassFavoriteRVAdapter()
        binding.classFavoriteRv.adapter = classfavoriteRVAdapter

        datas.apply {
            //더미
            add(Lecture("C 짱",10,"C언어","영상","무료"))


            classfavoriteRVAdapter.datas_classf = datas
            classfavoriteRVAdapter.notifyDataSetChanged()

            //item 클릭 시 강의 상세 화면으로 전환?
            classfavoriteRVAdapter.setItemClickListener(object : ClassFavoriteRVAdapter.OnItemClickListener {
                override fun onClick(v: View, position: Int) {



                }
            })
        }
    }
}