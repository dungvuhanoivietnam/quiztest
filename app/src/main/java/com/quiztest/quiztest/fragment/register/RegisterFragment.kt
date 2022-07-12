package com.quiztest.quiztest.fragment.register

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.provider.Settings
import android.text.SpannableStringBuilder
import android.text.TextPaint
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.text.style.ClickableSpan
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.text.color
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.ViewModelProvider
import com.facebook.CallbackManager
import com.facebook.CallbackManager.Factory.create
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.FacebookSdk
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.quiztest.quiztest.MainActivity
import com.quiztest.quiztest.R
import com.quiztest.quiztest.base.BaseFragment
import com.quiztest.quiztest.databinding.FragmentRegisterBinding
import com.quiztest.quiztest.fragment.HomeFragment
import com.quiztest.quiztest.model.UserInfoResponse
import com.quiztest.quiztest.retrofit.RetrofitClient
import com.quiztest.quiztest.utils.Const
import com.quiztest.quiztest.utils.SharePrefrenceUtils
import com.quiztest.quiztest.utils.Utils
import com.quiztest.quiztest.utils.setClickSpan
import java.util.*
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
    private val emailPattern: String = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
    private var mGoogleSignInClient: GoogleSignInClient? = null
    private var mCallbackManager: CallbackManager? = null
    private val RC_SIGN_IN = 1

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
        context?.let { FacebookSdk.sdkInitialize(it) }
        setupView()
        setupObserver()
        initLoginGoogleFacebook()
        Utils.printHashKey(requireContext())

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
        mGoogleSignInClient = GoogleSignIn.getClient(requireContext(), googleSignInOptions)
        mCallbackManager = create()
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
                        requireContext(),
                        Const.PROVIDE_NAME_FACEBOOK,
                        loginResult.accessToken.token,
                        deviceId,
                        language
                    )
                }
            })
    }

    private fun setupView() {
        binding.imvEyePass.setOnClickListener {
            ishowPassword = !ishowPassword
            if (ishowPassword) {
                binding.edtPass.transformationMethod = HideReturnsTransformationMethod.getInstance()
                binding.imvEyePass.setImageResource(R.drawable.ic_open_eye)
                Log.e("---...", "pass: 1")
            } else {
                binding.edtPass.transformationMethod = PasswordTransformationMethod.getInstance()
                binding.imvEyePass.setImageResource(R.drawable.ic_close_eye)
                Log.e("---...", "pass: 2")
            }

        }

        binding.imvEyeRepass.setOnClickListener {
            ishowConfirmPassword = !ishowConfirmPassword
            if (ishowConfirmPassword) {
                binding.edtConfirmPass.transformationMethod =
                    HideReturnsTransformationMethod.getInstance()
                binding.imvEyeRepass.setImageResource(R.drawable.ic_open_eye)

            } else {
                binding.edtConfirmPass.transformationMethod =
                    PasswordTransformationMethod.getInstance()
                binding.imvEyeRepass.setImageResource(R.drawable.ic_close_eye)
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
            validatePassWord()
            checkButtonRegister()
        }
        binding.edtConfirmPass.doOnTextChanged { text, start, before, count ->
            confirmPassword = text.toString().trim()
            validateConfirmPassword()
            checkButtonRegister()
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


        binding.ivLoginFacebook.setOnClickListener {
            LoginManager.getInstance()
                .logInWithReadPermissions(this, Arrays.asList("public_profile", "email"))
        }


        binding.ivLoginGoogle.setOnClickListener {
            if (mGoogleSignInClient != null) {
                val signInIntent = mGoogleSignInClient!!.signInIntent
                startActivityForResult(signInIntent, RC_SIGN_IN)
            }
        }

        binding.ivBack.setOnClickListener {
            backstackFragment()
        }

    }


    private fun handleRegister() {
        if (isSuccessEmail && isSuccessName && isSuccessPass && isSuccessConfirmPass) {
            binding.btnRegister.setBackgroundResource(R.drawable.btn_background_done)
            val language = Locale.getDefault().language
            if (language.isNotEmpty()) {
                viewModel.registerAccount(
                    requireContext(),
                    email,
                    name,
                    password,
                    confirmPassword,
                    language
                )
            }

        } else {
            Toast.makeText(
                requireContext(),
                "Please check your information again",
                Toast.LENGTH_SHORT
            ).show()
        }
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
                        requireContext(),
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

    private fun validateName() {
        if (name.length > 100) {
            binding.txtErrorName.isVisible = true
            binding.txtErrorName.setText(R.string.Your_name_cannot_exceed_32_characters)
            binding.edtName.setBackgroundResource(R.drawable.boarde_red)
            isSuccessName = false
            return
        }
        binding.txtErrorName.isVisible = false
        binding.edtName.setBackgroundResource(R.drawable.boarde_blue_21)
        isSuccessName = true
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


    private fun validateConfirmPassword() {
        if (confirmPassword.length < 6) {
            binding.txtErrorConfirmpass.isVisible = true
            binding.txtErrorConfirmpass.setText(R.string.password_cannot_less_6_characters)
            binding.edtConfirmPass.setBackgroundResource(R.drawable.boarde_red)
            isSuccessConfirmPass = false
            return
        } else if (confirmPassword != password) {
            binding.txtErrorConfirmpass.isVisible = true
            binding.edtConfirmPass.setBackgroundResource(R.drawable.boarde_red)
            binding.txtErrorConfirmpass.setText(R.string.re_entered_password_does_not_match)
            isSuccessConfirmPass = false
            return
        } else {
            binding.txtErrorConfirmpass.isVisible = false
            binding.edtConfirmPass.setBackgroundResource(R.drawable.boarde_blue_21)
            isSuccessConfirmPass = true
        }
    }

    private fun checkButtonRegister() {
        binding.btnRegister.isEnabled =
            isSuccessEmail && isSuccessPass && isSuccessName && isSuccessConfirmPass
    }

    private fun setupObserver() {

        viewModel.isLoading.observe(viewLifecycleOwner) { isShowLoading ->
            if (isShowLoading) {
                showLoading()
            } else {
                cancelLoading()
            }
        }

        viewModel.registerResLiveData.observe(viewLifecycleOwner) {
            if (it != null) {
                if (it.success == true) {
                    SharePrefrenceUtils.getInstance(requireContext()).userAccessToken =
                        it.data?.accessToken ?: ""
//                    replaceFragment(HomeFragment(), HomeFragment::class.java.simpleName)
                    SharePrefrenceUtils.getInstance(mContext).saveAuth(it.data?.accessToken)
                    UserInfoResponse.setCurrentUser(null)
                    RetrofitClient.setOurInstance(null)
                    backstackFragment()
                    if (activity is MainActivity) {
                        val activity = activity as MainActivity?
                        activity!!.actionLogout()
                    }
                } else {
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                }
            }
        }

        viewModel.errorMessage.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        }


        viewModel.loginSocialResLiveData.observe(viewLifecycleOwner) {
            if (it != null) {
                if (it.success == true) {
                    SharePrefrenceUtils.getInstance(requireContext()).userAccessToken =
                        it.data?.accessToken ?: ""
                    replaceFragment(HomeFragment(), HomeFragment::class.java.simpleName)
                    (activity as MainActivity?)!!.hideOrShowBottomView(false)
                } else {
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                }
            }
        }

    }

    private fun String.matches(regex: String): Boolean {
        return Pattern.matches(emailPattern, this)

    }
}







