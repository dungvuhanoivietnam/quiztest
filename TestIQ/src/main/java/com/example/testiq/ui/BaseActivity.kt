package com.example.testiq.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.viewbinding.ViewBinding
import com.androidnetworking.AndroidNetworking
import com.example.testiq.R
import com.facebook.stetho.okhttp3.StethoInterceptor

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit


abstract class BaseActivity<VM : ViewModel, B : ViewBinding> : AppCompatActivity() {

    lateinit var binding: B
    private var loading : DialogLoading? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = getViewBinding()
        setContentView(binding.root)
        loading = DialogLoading(this, R.style.MaterialDialogSheet)
        AndroidNetworking.initialize(this)
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .connectTimeout(30, TimeUnit.SECONDS)
            .addNetworkInterceptor(StethoInterceptor())
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build();
        AndroidNetworking.initialize(applicationContext, okHttpClient)

        initView()
        initObserver()
    }

    protected open fun showLoading() {
        if (loading != null && loading?.isShowing != true) {
            loading?.show()
        }
    }

    protected open fun cancelLoading() {
        if (loading != null && loading?.isShowing == true) {
            loading?.hide()
        }
    }

    abstract fun initObserver()

    abstract fun initView()

    abstract fun getViewBinding(): B
}