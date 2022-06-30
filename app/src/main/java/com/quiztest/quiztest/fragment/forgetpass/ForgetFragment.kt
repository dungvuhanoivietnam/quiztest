package com.quiztest.quiztest.fragment.forgetpass

import android.os.Bundle
import android.text.InputType
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.quiztest.quiztest.R
import com.quiztest.quiztest.base.BaseFragment
import com.quiztest.quiztest.custom.ExtEditTextApp
import com.quiztest.quiztest.databinding.FragmentForgetBinding
import com.quiztest.quiztest.databinding.FragmentLoginBinding


class ForgetFragment : BaseFragment() {
    private lateinit var binding: FragmentForgetBinding
    private var isSuccessForgetEmail = false
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentForgetBinding.inflate(inflater, container, false)
        initView(binding.root)
        return binding.root
    }

    override fun getLayoutId(): Int = R.layout.fragment_forget

    override fun initView(v: View?) {
        binding.edtForgetEmail.initData(
            ExtEditTextApp.TYPE_VALIDATE.EMAIL, context?.getString(R.string.malformed_account)
                ?: "", InputType.TYPE_CLASS_TEXT
        ) { t ->
            kotlin.run {
                isSuccessForgetEmail = t
                initButtonContinue()
            }
        }
    }

    override fun initData() {

    }

    private fun initButtonContinue() {
        binding.btnContinue.setBackgroundResource(
            isSuccessForgetEmail? R : R.drawable.bg_gray_b8)

    }

}