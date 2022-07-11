package com.quiztest.quiztest.fragment.forgotPass

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import com.quiztest.quiztest.MainActivity
import com.quiztest.quiztest.R
import com.quiztest.quiztest.base.BaseFragment
import com.quiztest.quiztest.databinding.FragmentVerifyMailBinding
import com.quiztest.quiztest.fragment.login.LoginFragment
import com.quiztest.quiztest.utils.Const
import java.util.regex.Pattern


class VerifyMailFragment : BaseFragment() {
    private val viewModel by lazy {
        ViewModelProvider(this)[VerifyMailViewModel::class.java]
    }

    private var email = ""
    private var isSuccessEmail = false
    val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
    val message = ""

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
        bindingView()
        setupObserver()
    }

    private fun bindingView() {
        binding.edtVeriMail.doOnTextChanged { text, start, before, count ->
            email = text.toString().trim()
            validMail()
            checkButtonContinue()

        }

        binding.btnContinue.setOnClickListener {
            if (isSuccessEmail) {
                binding.btnContinue.setBackgroundResource(R.drawable.btn_background_done)
                viewModel.validEmail(requireContext(), email)
            } else {
                showErrorOTP(message)
            }
        }

        binding.ivBack.setOnClickListener {
            backstackFragment()
        }

        binding.txtLogin.setOnClickListener {
            replaceFragment(LoginFragment(), LoginFragment::class.java.simpleName)
            (activity as MainActivity?)!!.hideOrShowBottomView(false)
        }

    }

    private fun validMail() {

        if (email.matches(emailPattern) && email.length < 100) {
            binding.txtErrorEmail.isVisible = false
            binding.edtVeriMail.setBackgroundResource(R.drawable.boarde_blue_21)
            isSuccessEmail = true

        } else {
            binding.txtErrorEmail.isVisible = true
            binding.txtErrorEmail.setText(R.string.malformed_account)
            binding.edtVeriMail.setBackgroundResource(R.drawable.boarde_red)
            isSuccessEmail = false
        }
    }

    override fun initData() {

    }

    private fun String.matches(regex: String): Boolean {
        return Pattern.matches(emailPattern, this)

    }

    private fun setupObserver() {

        viewModel.isLoading.observe(viewLifecycleOwner) { isShowLoading ->
            if (isShowLoading) {
                showLoading()
            } else {
                cancelLoading()
            }
        }

        viewModel.validEmailResLiveData.observe(viewLifecycleOwner) {
            if (viewLifecycleOwner.lifecycle.currentState == Lifecycle.State.RESUMED) {
                if (it != null) {
                    if (it.success) {
                    val bundle = Bundle()
                    bundle.putString(Const.KEY_EMAIL, binding.edtVeriMail.text.toString())
                    val otpFragment = OtpFragment()
                    otpFragment.arguments = bundle
                        replaceFragment(otpFragment, OtpFragment::class.java.simpleName)
                        (activity as MainActivity?)!!.hideOrShowBottomView(false)
                    } else {
                        Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }

        }


        viewModel.errorMessage.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        }


    }

    private fun checkButtonContinue() {
        binding.btnContinue.isEnabled = isSuccessEmail
    }

    private fun showErrorOTP(message: String) {
        binding.txtErrorEmail.apply {
            isVisible = true
            text = message
        }

    }
}