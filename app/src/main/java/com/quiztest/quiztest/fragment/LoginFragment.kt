package com.quiztest.quiztest.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.quiztest.quiztest.R
import com.quiztest.quiztest.base.BaseFragment
import com.quiztest.quiztest.databinding.FragmentLoginBinding
import com.quiztest.quiztest.utils.Utils


class LoginFragment : BaseFragment() {
    private lateinit var binding: FragmentLoginBinding
    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        initView()
        return binding.root
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_login
    }

    override fun initView() {
        val height = Utils.getHeight(activity) * 240 / 800
        var layoutParams: ViewGroup.LayoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height)
        binding.imvThum.layoutParams = layoutParams
    }

    override fun initData() {

    }


}