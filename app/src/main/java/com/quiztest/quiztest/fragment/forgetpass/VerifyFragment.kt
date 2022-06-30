package com.quiztest.quiztest.fragment.forgetpass

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.quiztest.quiztest.R
import com.quiztest.quiztest.base.BaseFragment
import com.quiztest.quiztest.databinding.FragmentForgetBinding
import com.quiztest.quiztest.databinding.FragmentVerifyBinding


class VerifyFragment : BaseFragment() {
    private lateinit var binding: FragmentVerifyBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentVerifyBinding.inflate(inflater, container, false)
        initView(binding.root)
        return binding.root
    }

    override fun getLayoutId(): Int = R.layout.fragment_verify

    override fun initView(v: View?) {

    }

    override fun initData() {

    }

}