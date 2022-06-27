package com.quiztest.quiztest.fragment.register

import android.os.Bundle
import android.text.TextPaint
import android.text.style.ClickableSpan

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.quiztest.quiztest.R
import com.quiztest.quiztest.base.BaseFragment
import com.quiztest.quiztest.databinding.FragmentRegisterBinding
import com.quiztest.quiztest.utils.setClickSpan


class RegisterFragment : BaseFragment() {
    private lateinit var binding: FragmentRegisterBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegisterBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun getLayoutId(): Int = R.layout.fragment_register

    override fun initView() {

    }

    override fun initData() {

    }

    fun hotlineDescriptionText(textView: TextView, boolean: Boolean){
        val pattern1 = requireContext().getString(R.string.when_you_click_register_you_agree_to)+ " "
        val pattern2 = requireContext().getString(R.string.our_terms_and_conditions_of_use)


        val clickableSpan = object : ClickableSpan(){
            override fun onClick(widget: View) {

            }

            override fun updateDrawState(ds: TextPaint) {
                super.updateDrawState(ds)
                ds.isUnderlineText = true
            }
        }

        textView.setClickSpan(
            clickableSpan,
            pattern1 + pattern2,
            pattern2
        )

    }

}