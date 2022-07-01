package com.quiztest.quiztest.fragment.forgotPass

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.quiztest.quiztest.R
import com.quiztest.quiztest.base.BaseFragment
import com.quiztest.quiztest.databinding.FragmentVerifyMailBinding


class VerifyMailFragment : BaseFragment() {

    private lateinit var binding: FragmentVerifyMailBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentVerifyMailBinding.inflate(inflater, container, false)
        initView(binding.root)
        return binding.root
    }

    override fun getLayoutId(): Int = R.layout.fragment_verify_mail

    override fun initView(v: View?) {

    }

    override fun initData() {

    }

}