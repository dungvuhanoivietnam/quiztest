package com.quiztest.quiztest.fragment.forgotPass

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import com.quiztest.quiztest.MainActivity
import com.quiztest.quiztest.R
import com.quiztest.quiztest.base.BaseFragment
import com.quiztest.quiztest.databinding.FragmentCreatePassBinding
import com.quiztest.quiztest.fragment.login.LoginFragment


class CreatePassFragment : BaseFragment() {
    private lateinit var binding: FragmentCreatePassBinding
    private var isSuccessPass = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentCreatePassBinding.inflate(inflater, container, false)
        initView(binding.root)
        return binding.root
    }

    override fun getLayoutId(): Int = R.layout.fragment_create_pass

    override fun initView(v: View?) {
        bindingView()
    }

    private fun verifyPass() {
        if (binding.edtPass.equals(binding.edtConfirmPass)) {
            binding.txtErrorConfirmpass.isVisible = false
            binding.edtConfirmPass.setBackgroundResource(R.drawable.boarde_blue_21)
            binding.edtPass.setBackgroundResource(R.drawable.boarde_blue_21)
            isSuccessPass = true

        } else {
            binding.txtErrorConfirmpass.isVisible = true
            binding.txtErrorConfirmpass.setText(R.string.text_error_re_enter_pass)
            binding.edtConfirmPass.setBackgroundResource(R.drawable.boarde_red)
            isSuccessPass = false
        }
    }


    private fun checkButtonContinue() {
        binding.btnSave.isEnabled = isSuccessEmail
    }

    private fun bindingView() {
        binding.ivBack.setOnClickListener {
            backstackFragment()
        }

        binding.txtLogin.setOnClickListener {
            replaceFragment(LoginFragment(), LoginFragment::class.java.simpleName)
            (activity as MainActivity?)!!.hideOrShowBottomView(false)
        }

//        binding.edtPass.doOnTextChanged { text, start, before, count ->
//            password = text.toString().trim()
//            validatePassWord()
//            checkButtonLogin()
//        }
//        binding.edtMail.doOnTextChanged { text, start, before, count ->
//            email = text.toString().trim()
//            validateEmail()
//            checkButtonLogin()
//        }
    }

    override fun initData() {

    }


}