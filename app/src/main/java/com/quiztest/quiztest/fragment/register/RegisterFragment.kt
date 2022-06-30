package com.quiztest.quiztest.fragment.register

import android.graphics.Color
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.text.TextPaint
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.text.style.ClickableSpan
import android.util.Log
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.text.color
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.ViewModelProvider
import com.quiztest.quiztest.MainActivity
import com.quiztest.quiztest.R
import com.quiztest.quiztest.base.BaseFragment
import com.quiztest.quiztest.databinding.FragmentRegisterBinding
import com.quiztest.quiztest.fragment.HomeFragment
import com.quiztest.quiztest.utils.SharePrefrenceUtils
import com.quiztest.quiztest.utils.Utils
import com.quiztest.quiztest.utils.setClickSpan
import java.util.regex.Pattern


class RegisterFragment : BaseFragment() {
    private val viewModel by lazy {
        ViewModelProvider(this)[RegisterViewModel::class.java]
    }
    private lateinit var binding: FragmentRegisterBinding
    private var isSuccessEmail: Boolean = false
    private var isSuccessPass: Boolean = false
    private var isSuccessConfirmPass: Boolean = false
    private var isSuccessName: Boolean = false
    private var ishowPassword: Boolean = false
    private var ishowConfirmPassword: Boolean = false

    private var email = ""
    private var name = ""
    private var password = ""
    private var confirmPassword = ""
    private val emailPattern : String = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegisterBinding.inflate(inflater, container, false)
        initView(binding.root)
        return binding.root

    }

    override fun getLayoutId(): Int = R.layout.fragment_register

    override fun initData() {}


    override fun initView(v: View?) {
        val height = Utils.getHeight(activity) * 220 / 800
        val layoutParams: ViewGroup.LayoutParams =
            ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height)
        binding.imvThum.layoutParams = layoutParams
        setupView()
        setupObserver()


//
//     binding.edtName.initData(
//            ExtEditTextApp.TYPE_VALIDATE.NAME,
//            context?.getString(R.string.Your_name_cannot_exceed_32_characters)
//                ?: "", InputType.TYPE_CLASS_TEXT
//        ) { t ->
//            kotlin.run {
//                isSuccessName = t
//                initButtonRegister()
//            }
//        }
//
//
//        binding.edtMail.initData(
//            ExtEditTextApp.TYPE_VALIDATE.EMAIL, context?.getString(R.string.malformed_account)
//                ?: "",InputType.TYPE_CLASS_TEXT
//        ) { t ->
//            kotlin.run {
//                isSuccessEmail = t
//                initButtonRegister()
//            }
//        }
//
//        binding.edtPass.initData(ExtEditTextApp.TYPE_VALIDATE.COMFIRNPASSWORD,
//            context?.getString(R.string.incorrect_password)
//                ?: "",
//            InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
//        ) { t ->
//            kotlin.run {
//                isSuccessPass = t
//                initButtonRegister()
//            }
//        }
//
//        binding.edtConfirmPass.initData(ExtEditTextApp.TYPE_VALIDATE.COMFIRNPASSWORD,
//            context?.getString(R.string.incorrect_password)
//                ?: "",
//            InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
//        ) { t ->
//            kotlin.run {
//                isSuccessPass = t
//                initButtonRegister()
//            }
//

    }


    private fun setupView() {
        binding.imvEyePass.setOnClickListener {
            ishowPassword = !ishowPassword
            if (ishowPassword) {
                binding.edtPass.transformationMethod = HideReturnsTransformationMethod.getInstance()
                binding.imvEyePass.setImageResource(R.drawable.ic_open_eye)
                Log.e("---...", "pass: 1", )
            } else {
                binding.edtPass.transformationMethod = PasswordTransformationMethod.getInstance()
                binding.imvEyePass.setImageResource(R.drawable.ic_close_eye)
                Log.e("---...", "pass: 2", )
            }

        }

        binding.imvEyeRepass.setOnClickListener {
            ishowConfirmPassword = !ishowConfirmPassword
            if (ishowConfirmPassword) {
                binding.edtConfirmPass.transformationMethod =
                    HideReturnsTransformationMethod.getInstance()
                binding.imvEyeRepass.setImageResource(R.drawable.ic_open_eye)
                Log.e("---...", "repass: 1", )

            } else {
                binding.edtConfirmPass.transformationMethod = PasswordTransformationMethod.getInstance()
                binding.imvEyeRepass.setImageResource(R.drawable.ic_close_eye)
                Log.e("---...", "repass: 2", )
            }

        }


        binding.edtName.doOnTextChanged { text, start, before, count ->
            name = text.toString().trim()
            validateName()
            checkButtonRegister()
        }
        binding.edtMail.doOnTextChanged { text, start, before, count ->
            email = text.toString().trim()
            validateEmail()
            checkButtonRegister()
        }
        binding.edtPass.doOnTextChanged { text, start, before, count ->
            password = text.toString().trim()
            Log.e("---...", "mpass: ${password} ", )
            validatePassWord()
            checkButtonRegister()
        }
        binding.edtConfirmPass.doOnTextChanged { text, start, before, count ->
            confirmPassword = text.toString().trim()
            validateConfirmPassword()
            checkButtonRegister()
            Log.e("---...", "confirmpass: ${confirmPassword} ", )
        }

        binding.btnRegister.setOnClickListener {
            handleRegister()
        }

        val message = SpannableStringBuilder()
            .color(Color.parseColor("#8D959D")) { append(getString(R.string.when_you_click_register_you_agree_to)) }
            .append(" ")
            .color(Color.parseColor("#21C3FF")) { append(getString(R.string.our_terms_and_conditions_of_use)) }

        binding.txtOurTerm.apply {
            val clickAbleSpan = object : ClickableSpan() {
                override fun onClick(widget: View) {
                    Toast.makeText(requireContext(), "aaaaa", Toast.LENGTH_SHORT).show()
                }

                override fun updateDrawState(ds: TextPaint) {
                    super.updateDrawState(ds)
                    ds.isUnderlineText = false
                }
            }

            setClickSpan(
                clickAbleSpan,
                message = message.toString(),
                clickText = getString(R.string.our_terms_and_conditions_of_use)
            )
        }

    }


    private fun handleRegister() {
        if (isSuccessEmail && isSuccessName && isSuccessPass && isSuccessConfirmPass) {
            binding.btnRegister.setBackgroundResource(R.drawable.btn_background_done)
            binding.btnRegister.setOnClickListener {
                viewModel.registerAccount(email, name, password, confirmPassword)
            }


        } else {
            Toast.makeText(
                requireContext(),
                "Please check your information again",
                Toast.LENGTH_SHORT
            ).show()
        }
    }


    private fun validateEmail() {

        if (email.matches(emailPattern) && email.length > 0) {
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

    private fun validateName() {
        if (name.length > 32) {
            binding.txtErrorName.isVisible = true
            binding.txtErrorName.setText(R.string.Your_name_cannot_exceed_32_characters)
            binding.edtName.setBackgroundResource(R.drawable.boarde_red)
            isSuccessName = false
            return
        }
        binding.txtErrorPass.isVisible = false
        binding.edtPass.setBackgroundResource(R.drawable.boarde_blue_21)
        isSuccessName = true



    }

    private fun validatePassWord() {
        if (password.length < 8) {
            binding.txtErrorPass.isVisible = true
            binding.txtErrorPass.setText(R.string.password_cannot_less_8_characters)
            binding.edtPass.setBackgroundResource(R.drawable.boarde_red)
            isSuccessPass = false
            return
        }
        if (password.length >= 8) {
            binding.txtErrorPass.isVisible = false
            binding.edtPass.setBackgroundResource(R.drawable.boarde_blue_21)
            isSuccessPass = true

        }
    }


    private fun validateConfirmPassword() {
        if (confirmPassword.length < 8) {
            binding.txtErrorConfirmpass.isVisible = true
            binding.txtErrorConfirmpass.setText(R.string.password_cannot_less_8_characters)
            binding.edtConfirmPass.setBackgroundResource(R.drawable.boarde_red)
            isSuccessConfirmPass = false
            return
        }else if (confirmPassword != password) {
            binding.txtErrorConfirmpass.isVisible = true
            binding.edtConfirmPass.setBackgroundResource(R.drawable.boarde_red)
            binding.txtErrorConfirmpass.setText(R.string.re_entered_password_does_not_match)
            isSuccessConfirmPass = false
            return
        } else {
        binding.txtErrorConfirmpass.isVisible = false
        binding.edtPass.setBackgroundResource(R.drawable.boarde_blue_21)
        isSuccessConfirmPass = true}

    }

    private fun checkButtonRegister(){
        binding.btnRegister.isEnabled = isSuccessEmail && isSuccessPass && isSuccessName && isSuccessConfirmPass
    }


    //    fun hotlineDescriptionText(textView: TextView, boolean: Boolean){
//        val pattern1 = requireContext().getString(R.string.when_you_click_register_you_agree_to)+ " "
//        val pattern2 = requireContext().getString(R.string.our_terms_and_conditions_of_use)
//
//
//        val clickableSpan = object : ClickableSpan(){
//            override fun onClick(widget: View) {
//
//            }
//
//            override fun updateDrawState(ds: TextPaint) {
//                super.updateDrawState(ds)
//                ds.isUnderlineText = true
//            }
//        }
//
//        textView.setClickSpan(
//            clickableSpan,
//            pattern1 + pattern2,
//            pattern2
//        )
//
//    }
    private fun setupObserver() {

        viewModel.isLoading.observe(viewLifecycleOwner) { isShowLoading ->
            if (isShowLoading) {
                showLoading()
            } else {
                cancelLoading()
            }
        }

        viewModel.registerAccount.observe(viewLifecycleOwner) {
            if (it.success == true) {
                SharePrefrenceUtils.getInstance(requireContext()).apply {
                    setUserAccessToken("")
                }
                replaceFragment(HomeFragment(), HomeFragment::class.java.simpleName)
                (activity as MainActivity?)!!.hideOrShowBottomView(false)
            } else {
                Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun String.matches(regex: String): Boolean {
        val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
        return Pattern.matches(emailPattern, this)

    }
}







