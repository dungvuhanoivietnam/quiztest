package com.quiztest.quiztest.fragment

import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.quiztest.quiztest.MainActivity
import com.quiztest.quiztest.R
import com.quiztest.quiztest.base.BaseFragment
import com.quiztest.quiztest.custom.ExtEditTextApp
import com.quiztest.quiztest.databinding.FragmentLoginBinding
import com.quiztest.quiztest.fragment.register.RegisterFragment
import com.quiztest.quiztest.utils.Utils


class LoginFragment : BaseFragment() {
    private lateinit var binding: FragmentLoginBinding
    private var isSuccessEmail: Boolean = false
    private var isSuccessPass: Boolean = false


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

    override fun initView(v: View?) {

    }

    override fun initView() {
        val height = Utils.getHeight(activity) * 220 / 800
        var layoutParams: ViewGroup.LayoutParams =
            ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height)
        binding.imvThum.layoutParams = layoutParams

        binding.edtMail.initData(
            ExtEditTextApp.TYPE_VALIDATE.EMAIL,
            context?.getString(R.string.malformed_account) ?: "",
            InputType.TYPE_CLASS_TEXT,
            { t ->
                kotlin.run {
                    isSuccessEmail = t
                    initButtonLogin()
                }
            })

        binding.edtPass.initData(ExtEditTextApp.TYPE_VALIDATE.PASSWORD,
            context?.getString(R.string.incorrect_password)
                ?: "",
            InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD,
            { t ->
                kotlin.run {
                    isSuccessPass = t
                    initButtonLogin()
                }

            })
        binding.ivBack.setOnClickListener {
            backstackFragment()
        }

        binding.txtRegister.setOnClickListener {
            replaceFragment(RegisterFragment(), RegisterFragment::class.java.simpleName)
            (activity as MainActivity?)!!.hideOrShowBottomView(false)
        }
    }

    override fun initData() {
    }

    fun initButtonLogin() {
        if (isSuccessEmail and isSuccessPass)
            binding.btnLogin.setBackgroundResource(R.drawable.bg_blue_21)
        else
            binding.btnLogin.setBackgroundResource(R.drawable.bg_gray_b8)

    }


}