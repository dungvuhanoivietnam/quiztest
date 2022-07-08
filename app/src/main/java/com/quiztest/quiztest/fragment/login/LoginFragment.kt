package com.quiztest.quiztest.fragment.login

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
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
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.quiztest.quiztest.MainActivity
import com.quiztest.quiztest.R
import com.quiztest.quiztest.base.BaseFragment
import com.quiztest.quiztest.databinding.FragmentLoginBinding
import com.quiztest.quiztest.fragment.HomeFragment
import com.quiztest.quiztest.fragment.forgotPass.VerifyMailFragment
import com.quiztest.quiztest.fragment.register.RegisterFragment
import com.quiztest.quiztest.utils.Const
import com.quiztest.quiztest.utils.SharePrefrenceUtils
import com.quiztest.quiztest.utils.Utils
import java.util.*
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
    private var mGoogleSignInClient: GoogleSignInClient? = null
    private var mCallbackManager: CallbackManager? = null
    private val RC_SIGN_IN = 1


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
        initLoginGoogleFacebook()
    }

    private fun initLoginGoogleFacebook() {
        val googleSignInOptions: GoogleSignInOptions = GoogleSignInOptions
            .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        val deviceId =
            Settings.Secure.getString(context?.contentResolver, Settings.Secure.ANDROID_ID)
        val language = Locale.getDefault().language

        mGoogleSignInClient = context?.let { GoogleSignIn.getClient(it, googleSignInOptions) }
        mCallbackManager = CallbackManager.Factory.create()
        LoginManager.getInstance()
            .registerCallback(mCallbackManager, object : FacebookCallback<LoginResult> {
                override fun onCancel() {
                    Log.e("Cancel", "cancel")
                }

                override fun onError(error: FacebookException) {
                    Log.e("onError", "onError")
                }

                override fun onSuccess(loginResult: LoginResult) {
                    Log.d("AcessToken", loginResult.accessToken.token)
                    viewModel.loginSocial(
                        Const.PROVIDE_NAME_FACEBOOK,
                        loginResult.accessToken.token,
                        deviceId,
                        language
                    )
                }
            })
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
                Log.e("->>>>>>>", "setupView: ")


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

        binding.ivLoginFacebook.setOnClickListener {
            LoginManager.getInstance()
                .logInWithReadPermissions(this, Arrays.asList("public_profile", "email"))
        }


        binding.ivLoginGoogle.setOnClickListener {
            val signInIntent = mGoogleSignInClient!!.signInIntent
            startActivityForResult(signInIntent, RC_SIGN_IN)
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

        viewModel.loginResLiveData.observe(viewLifecycleOwner) {
            if (it.success == true) {
                it.data?.accessToken?.let {
                    SharePrefrenceUtils.getInstance(mContext).saveAuth(it)
                    replaceFragment(HomeFragment(), HomeFragment::class.java.simpleName)
//                    (requireActivity() as MainActivity?)?.hideOrShowBottomView(false)
                } ?: kotlin.run {
                    Toast.makeText(requireContext(), "Not find token", Toast.LENGTH_SHORT).show()
                }


            }

        }
    }

    private fun String.matches(regex: String): Boolean {
        val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+.+[a-z]"
        return Pattern.matches(emailPattern, this)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val deviceId =
                    Settings.Secure.getString(context?.contentResolver, Settings.Secure.ANDROID_ID)
                val language = Locale.getDefault().language
                val account = task.getResult(ApiException::class.java)
                Log.e("Token Account: ", account.idToken!!)
                if (account.idToken != null) {
                    viewModel.loginSocial(
                        Const.PROVIDE_NAME_GOOGLE,
                        account.idToken!!,
                        deviceId,
                        language
                    )
                }
            } catch (e: ApiException) {
                // Google Sign In failed, update UI appropriately
                Log.e("TAG", "Google sign in failed")
            }
        } else {
            mCallbackManager!!.onActivityResult(requestCode, resultCode, data)
        }
    }
}


