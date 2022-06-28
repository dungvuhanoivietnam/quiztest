package com.quiztest.quiztest.fragment.register

import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.quiztest.quiztest.MainActivity
import com.quiztest.quiztest.R
import com.quiztest.quiztest.base.BaseFragment
import com.quiztest.quiztest.custom.ExtEditTextApp
import com.quiztest.quiztest.databinding.FragmentRegisterBinding
import com.quiztest.quiztest.fragment.HomeFragment
import com.quiztest.quiztest.local.AppPreferences
import com.quiztest.quiztest.utils.Utils


class RegisterFragment : BaseFragment() {
    private val viewModel by lazy {
        ViewModelProvider(this)[RegisterViewModel::class.java]
    }
    private lateinit var binding: FragmentRegisterBinding
    private var isSuccessEmail: Boolean = false
    private var isSuccessPass: Boolean = false
    private var isSuccessName: Boolean = false

    private var email = ""
    private var name = ""
    private var password = ""
    private var confirmPassword = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegisterBinding.inflate(inflater, container, false)
        initView(binding.root)
        return binding.root

    }

    override fun getLayoutId(): Int = R.layout.fragment_register


    override fun initView(v: View?) {
        val height = Utils.getHeight(activity) * 220 / 800
        val layoutParams: ViewGroup.LayoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height)
        binding.imvThum.layoutParams = layoutParams


        viewModel.isLoading.observe(viewLifecycleOwner) { isShowLoading ->
            if (isShowLoading) {
                showLoading()
            } else {
                cancelLoading()
            }
        }

        viewModel.registerAccount.observe(viewLifecycleOwner) {
            if (it.success == true) {
                AppPreferences.apply {
                    setUserAccessToken("")
                }
                replaceFragment(HomeFragment(), HomeFragment::class.java.simpleName)
                (activity as MainActivity?)!!.hideOrShowBottomView(false)
            } else {
                Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
            }
        }


        binding.edtName.initData(
            ExtEditTextApp.TYPE_VALIDATE.NAME,
            context?.getString(R.string.Your_name_cannot_exceed_32_characters)
                ?: "", InputType.TYPE_CLASS_TEXT
        ) { t ->
            kotlin.run {
                isSuccessName = t
                initButtonRegister()
            }
        }


        binding.edtMail.initData(
            ExtEditTextApp.TYPE_VALIDATE.EMAIL, context?.getString(R.string.malformed_account)
                ?: "",InputType.TYPE_CLASS_TEXT
        ) { t ->
            kotlin.run {
                isSuccessEmail = t
                initButtonRegister()
            }
        }

        binding.edtPass.initData(ExtEditTextApp.TYPE_VALIDATE.COMFIRNPASSWORD,
            context?.getString(R.string.incorrect_password)
                ?: "",
            InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
        ) { t ->
            kotlin.run {
                isSuccessPass = t
                initButtonRegister()
            }
        }

        binding.edtConfirmPass.initData(ExtEditTextApp.TYPE_VALIDATE.COMFIRNPASSWORD,
            context?.getString(R.string.incorrect_password)
                ?: "",
            InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
        ) { t ->
            kotlin.run {
                isSuccessPass = t
                initButtonRegister()
            }
        }

    }



    override fun initData() {
        binding.btnRegister.setOnClickListener {
            name = binding.edtName.toString().trim()
            email =binding.edtMail.toString().trim()
            password = binding.edtPass.toString().trim()
            confirmPassword = binding.edtConfirmPass.toString().trim()

            viewModel.registerAccount(email,name,password,confirmPassword)


        }
    }

    private fun initButtonRegister() {
        if (isSuccessEmail and isSuccessPass and isSuccessName)
            binding.btnRegister.setBackgroundResource(R.drawable.bg_blue_21)
        else
            binding.btnRegister.setBackgroundResource(R.drawable.bg_gray_b8)
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

}