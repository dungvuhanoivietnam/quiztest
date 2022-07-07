package com.quiztest.quiztest.fragment.login

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
import androidx.lifecycle.ViewModelProvider
import com.quiztest.quiztest.MainActivity
import com.quiztest.quiztest.R
import com.quiztest.quiztest.base.BaseFragment
import com.quiztest.quiztest.databinding.FragmentLoginBinding
import com.quiztest.quiztest.fragment.HomeFragment
import com.quiztest.quiztest.fragment.forgotPass.VerifyMailFragment
import com.quiztest.quiztest.fragment.register.RegisterFragment
import com.quiztest.quiztest.utils.SharePrefrenceUtils
import com.quiztest.quiztest.utils.Utils
import java.util.regex.Pattern


class LoginFragment : BaseFragment() {
    private val viewModel by lazy {
        ViewModelProvider(this)[LoginViewModel::class.java]
    }
    private lateinit var binding: FragmentLoginBinding
    private var isSuccessEmail: Boolean = false
    private var isSuccessPass: Boolean = false
    private var ishowPassword: Boolean = false
    private var email = ""
    private var password = ""
    private val emailPattern = ""


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        initView(binding.root)
        return binding.root
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_login
    }


    override fun initData() {

    }

    override fun initView(v: View?) {
        val height = Utils.getHeight(activity) * 220 / 800
        val layoutParams: ViewGroup.LayoutParams =
            ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height)
        binding.imvThum.layoutParams = layoutParams

        setupView()
        setupObserver()
    }


    private fun setupView() {

//        binding.edtMail.initData(
//            ExtEditTextApp.TYPE_VALIDATE.EMAIL, context?.getString(R.string.malformed_account)
//                ?: "", InputType.TYPE_CLASS_TEXT
//        ) { t ->
//            kotlin.run {
//                isSuccessEmail = t
//                initButtonLogin()
//            }
//        }
//        binding.edtPass.initData(ExtEditTextApp.TYPE_VALIDATE.PASSWORD, context?.getString(R.string.incorrect_password)
//            ?: "", InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
//        ) { t ->
//            kotlin.run {
//                isSuccessPass = t
//                initButtonLogin()
//            }
//
//        }
        binding.ivBack.setOnClickListener {
            backstackFragment()
        }
//
        binding.txtRegister.setOnClickListener {
            replaceFragment(RegisterFragment(), RegisterFragment::class.java.simpleName)
            (activity as MainActivity?)!!.hideOrShowBottomView(false)
        }

        binding.txtFogotPass.setOnClickListener {
            replaceFragment(VerifyMailFragment(), VerifyMailFragment::class.java.simpleName)
            (activity as MainActivity?)!!.hideOrShowBottomView(false)
        }

        binding.imvEyePass.setOnClickListener {
            if (ishowPassword) {
                binding.edtPass.transformationMethod = HideReturnsTransformationMethod.getInstance()
                binding.imvEyePass.setImageResource(R.drawable.ic_open_eye)
                Log.e("->>>>>>>", "setupView: ", )


            } else {
                binding.edtPass.transformationMethod = PasswordTransformationMethod.getInstance()
                binding.imvEyePass.setImageResource(R.drawable.ic_close_eye)

            }
            ishowPassword = !ishowPassword
        }

        binding.edtPass.doOnTextChanged { text, start, before, count ->
            password = text.toString().trim()
            validatePassWord()
            checkButtonLogin()
        }
        binding.edtMail.doOnTextChanged { text, start, before, count ->
            email = text.toString().trim()
            validateEmail()
            checkButtonLogin()
        }

        binding.btnLogin.setOnClickListener {
            handleLogin()
        }

    }



    private fun validateEmail() {
        if (email.matches(emailPattern) && email.length < 100) {
            binding.txtErrorEmail.isVisible = false
            binding.edtMail.setBackgroundResource(R.drawable.boarde_blue_21)
            isSuccessEmail = true

        } else {
            binding.txtErrorEmail.isVisible = true
            binding.txtErrorEmail.setText(R.string.email_invalidate)
            binding.edtMail.setBackgroundResource(R.drawable.boarde_red)
            isSuccessEmail = false
            return
        }


    }

    private fun validatePassWord() {
        if (password.length < 6) {
            binding.txtErrorPass.isVisible = true
            binding.txtErrorPass.setText(R.string.password_cannot_less_6_characters)
            binding.edtPass.setBackgroundResource(R.drawable.boarde_red)
            isSuccessPass = false
            return
        }
        if (password.length >= 6) {
            binding.txtErrorPass.isVisible = false
            binding.edtPass.setBackgroundResource(R.drawable.boarde_blue_21)
            isSuccessPass = true

        }


    }


    private fun handleLogin() {
        if (isSuccessEmail and isSuccessPass) {
            binding.btnLogin.setBackgroundResource(R.drawable.btn_background_done)

            viewModel.loginAccount(email, password)

        } else {
            Toast.makeText(
                requireContext(),
                "Please check your information again",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun checkButtonLogin() {
        binding.btnLogin.isEnabled = isSuccessEmail && isSuccessPass
    }


    private fun setupObserver() {
        viewModel.isLoading.observe(viewLifecycleOwner) { isShowLoading ->
            if (isShowLoading) {
                showLoading()
            } else {
                cancelLoading()
            }
        }

        viewModel.loginAccount.observe(viewLifecycleOwner) {
            if (it.success == true) {
                it.data?.accessToken?.let {
                    SharePrefrenceUtils.getInstance(mContext).saveAuth(it)
//                    replaceFragment(HomeFragment(), HomeFragment::class.java.simpleName)
                    backstackFragment()
                    if (activity is MainActivity) {
                        val activity = activity as MainActivity?
                        activity!!.actionLogout()
                    }
//                    (requireActivity() as MainActivity?)?.hideOrShowBottomView(false)
                }?: kotlin.run {
                    Toast.makeText(requireContext(), "Not find token", Toast.LENGTH_SHORT).show()
                }


            }

        }
    }

    private fun String.matches(regex: String): Boolean {
        val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+.+[a-z]"
        return Pattern.matches(emailPattern, this)
    }
}


