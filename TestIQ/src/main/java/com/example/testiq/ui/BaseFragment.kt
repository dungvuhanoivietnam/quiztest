package com.example.testiq.ui

import android.os.Build
import android.os.Bundle
import android.text.Html
import android.text.SpannableString
import android.text.Spanned
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.viewbinding.ViewBinding
import com.androidnetworking.AndroidNetworking
import com.example.testiq.R
import com.facebook.stetho.okhttp3.StethoInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit

abstract class BaseFragment<viewModel : ViewModel, Binding : ViewBinding>(
    layoutID: Int
) : Fragment(layoutID) {

    protected abstract val viewModel: ViewModel
    lateinit var binding: Binding
    private var loading : DialogLoading? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = getViewBinding()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loading = DialogLoading(requireContext(), R.style.MaterialDialogSheet)
        AndroidNetworking.initialize(requireContext())
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .connectTimeout(30, TimeUnit.SECONDS)
            .addNetworkInterceptor(StethoInterceptor())
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()
        AndroidNetworking.initialize(requireActivity(), okHttpClient)
        setupViews()
        setupListeners()
        setupObservers()
    }

    protected open fun showLoading() {
        if (loading != null && loading?.isShowing != true) {
            loading?.show()
        }
    }

    protected open fun cancelLoading() {
        if (loading != null && loading?.isShowing == true) {
            loading?.dismiss()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        cancelLoading()
    }

    override fun onResume() {
        super.onResume()
        if (view == null) {
            return
        }
        requireView().isFocusableInTouchMode = true
        requireView().requestFocus()
        requireView().setOnKeyListener(View.OnKeyListener { v, keyCode, event ->
            if (event.action == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {
                // handle back button's click listener
                onBackPressed()
                return@OnKeyListener true
            }
            false
        })
    }

    fun fromHtml(html: String?): Spanned? {
        return if (html == null) {
            // return an empty spannable if the html is null
            SpannableString("")
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            // FROM_HTML_MODE_LEGACY is the behaviour that was used for versions below android N
            // we are using this flag to give a consistent behaviour
            Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY)
        } else {
            Html.fromHtml(html)
        }
    }

    abstract fun setupViews()

    abstract fun setupListeners()

    abstract fun setupObservers()

    abstract fun getViewBinding(): Binding

    protected abstract fun onBackPressed()

}