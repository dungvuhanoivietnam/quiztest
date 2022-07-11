package com.quiztest.quiztest.fragment.forgotPass

import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import com.example.testiq.ui.DialogShowMessageApi
import com.quiztest.quiztest.MainActivity
import com.quiztest.quiztest.R
import com.quiztest.quiztest.base.BaseFragment
import com.quiztest.quiztest.databinding.FragmentCreatePassBinding
import com.quiztest.quiztest.fragment.login.LoginFragment


class CreatePassFragment : BaseFragment() {
    private lateinit var binding: FragmentCreatePassBinding
    private var isSuccessPass = false
    private var ishowPassword: Boolean = false

    private val viewModel by lazy {
        ViewModelProvider(this)[CreatePassViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentCreatePassBinding.inflate(inflater, container, false)
        initView(binding.root)
        return binding.root
    }

    override fun getLayoutId(): Int = R.layout.fragment_create_pass

    override fun initView(v: View?) {
        bindingView()
        setupObserver()
    }

    private fun validateConfirmPass() {
        if (binding.edtPass == binding.edtConfirmPass) {
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

    private fun validatePassWord() {
        if (binding.edtPass.text.length < 6) {
            binding.txtErrorPass.isVisible = true
            binding.txtErrorPass.setText(R.string.password_cannot_less_6_characters)
            binding.edtPass.setBackgroundResource(R.drawable.boarde_red)
            isSuccessPass = false
            return
        }
        if (binding.edtPass.text.length >= 6) {
            binding.txtErrorPass.isVisible = false
            binding.edtPass.setBackgroundResource(R.drawable.boarde_blue_21)
            isSuccessPass = true

        }

    }


    private fun checkButtonSave() {
        binding.btnSave.isEnabled = isSuccessPass
    }

    private fun bindingView() {
        binding.ivBack.setOnClickListener {
            backstackFragment()
        }

        binding.txtLogin.setOnClickListener {
            replaceFragment(LoginFragment(), LoginFragment::class.java.simpleName)
            (activity as MainActivity?)!!.hideOrShowBottomView(false)
        }

        binding.edtPass.doOnTextChanged { text, start, before, count ->
            validatePassWord()
            checkButtonSave()
        }
        binding.edtConfirmPass.doOnTextChanged { text, start, before, count ->
            validateConfirmPass()
            checkButtonSave()
        }

        binding.imvEyePass.setOnClickListener {
            if (ishowPassword) {
                binding.edtPass.transformationMethod = HideReturnsTransformationMethod.getInstance()
                binding.imvEyePass.setImageResource(R.drawable.ic_open_eye)
                Log.e("->>>>>>>", "setupView: ")


            } else {
                binding.edtPass.transformationMethod = PasswordTransformationMethod.getInstance()
                binding.imvEyePass.setImageResource(R.drawable.ic_close_eye)

            }
            ishowPassword = !ishowPassword
        }
    }

    private fun setupObserver() {
//        viewModel.isLoading.observe(viewLifecycleOwner) { isShowLoading ->
//            if (isShowLoading) {
//                showLoading()
//            } else {
//                cancelLoading()
//            }
//        }

        viewModel.errorMessage.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        }

        viewModel.createPassResLiveData.observe(viewLifecycleOwner) {
            if (viewLifecycleOwner.lifecycle.currentState == Lifecycle.State.RESUMED) {
                if (it != null) {
                    if (it.success == true) {
                        it.message?.let { it1 ->
                            context?.let { it2 ->
                                DialogShowMessageApi(it2, it1) {
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
    }

    override fun initData() {

    }


}