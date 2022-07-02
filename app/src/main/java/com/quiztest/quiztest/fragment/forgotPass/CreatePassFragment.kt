package com.quiztest.quiztest.fragment.forgotPass

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.quiztest.quiztest.R
import com.quiztest.quiztest.base.BaseFragment
import com.quiztest.quiztest.databinding.FragmentCreatePassBinding


class CreatePassFragment : BaseFragment() {
private lateinit var binding: FragmentCreatePassBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
       binding= FragmentCreatePassBinding.inflate(inflater,container,false)
        initView(binding.root)
        return binding.root
    }

    override fun getLayoutId(): Int = R.layout.fragment_create_pass

    override fun initView(v: View?) {

    }

    override fun initData() {

    }


}