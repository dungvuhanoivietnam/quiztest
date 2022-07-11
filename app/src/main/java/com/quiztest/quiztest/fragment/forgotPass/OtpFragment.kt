package com.quiztest.quiztest.fragment.forgotPass

import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.testiq.ui.DialogShowMessageApi
import com.quiztest.quiztest.MainActivity
import com.quiztest.quiztest.R
import com.quiztest.quiztest.base.BaseFragment
import com.quiztest.quiztest.custom.code_otp_view.OTPListener
import com.quiztest.quiztest.databinding.FragmentOtpBinding
import com.quiztest.quiztest.utils.Const


class OtpFragment : BaseFragment() {
    private val viewModel by lazy {
        ViewModelProvider(this)[OtpViewModel::class.java]
    }

    private lateinit var binding: FragmentOtpBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentOtpBinding.inflate(inflater, container, false)
        initView(binding.root)
        return binding.root
    }

    override fun getLayoutId(): Int = R.layout.fragment_otp

    override fun initView(v: View?) {
        val email = arguments?.getString(Const.KEY_EMAIL)
        binding.txtMailVerify.text = email
        email?.let { sendCountDown(it) }
        bindingData(email)
        setupObserver()
    }

    private fun sendCountDown(email: String) {
        val cTimer = object : CountDownTimer(80000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                binding.txtTimeResend.text =
                    String.format("%ss", (millisUntilFinished / 1000).toString())
            }

            override fun onFinish() {
                binding.txtTimeResend.text = context?.resources?.getString(R.string.text_done)
                viewModel.resendOtp(
                    requireContext(),
                    email = email,
                    Const.KEY_VERIFY_STYLE,
                    Const.KEY_VERIFY_TYPE
                )
            }
        }
        cTimer.start()
    }

    private fun bindingData(email: String?) {
        binding.otpView.otpListener = object : OTPListener {
            override fun onInteractionListener() {

            }

            override fun onOTPComplete(otp: String) {
                Log.e("tuan", otp)
                if (email != null) {
                    context?.let { viewModel.verifyOtp(it, email, Const.KEY_VERIFY_TYPE, otp) }
                }

            }
        }

        binding.txtSendAgain.setOnClickListener {
            if (email != null) {
                viewModel.resendOtp(
                    requireContext(),
                    email = email,
                    Const.KEY_VERIFY_STYLE,
                    Const.KEY_VERIFY_TYPE
                )
            }
        }

        binding.ivBack.setOnClickListener {
            backstackFragment()
        }

    }

    private fun setupObserver() {

        viewModel.isLoading.observe(viewLifecycleOwner) { isShowLoading ->
            if (isShowLoading) {
                showLoading()
            } else {
                cancelLoading()
            }
        }

        viewModel.otpResLiveData.observe(viewLifecycleOwner) {
            if (it != null) {
                Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
            }
        }


        viewModel.errorMessage.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        }

        viewModel.verifyOtpResLiveData.observe(viewLifecycleOwner) {
            if (it != null) {
                if (it.success == true) {
                    it.message?.let { it1 ->
                        context?.let { it2 ->
                            DialogShowMessageApi(it2, it1) {
                                replaceFragment(
                                    CreatePassFragment(),
                                    CreatePassFragment::class.java.simpleName
                                )
                                (activity as MainActivity?)!!.hideOrShowBottomView(false)
                            }.show()
                        }
                    }

                } else {
                    it.message?.let { it1 ->
                        context?.let { it2 ->
                            DialogShowMessageApi(it2, it1) {}.show()
                        }
                    }
                }
            }
        }

    }

    override fun initData() {

    }


}