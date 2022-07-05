package com.example.testiq

import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import com.example.testiq.databinding.ActivityMainTestIqBinding
import com.example.testiq.ui.BaseActivity
import com.example.testiq.utils.SharePrefrenceUtils
import com.example.testiq.viewmodel.MainIQViewModel
import com.example.testiq.viewmodel.MainViewModel


class MainIQActivity : BaseActivity<MainIQViewModel, ActivityMainTestIqBinding>() {

    override val viewModel: MainIQViewModel by viewModels()

    companion object {
        var token: String = ""
    }

    override fun initView() {


        intent.getStringExtra("token")?.let {
            if (intent.getStringExtra("token")?.isNotEmpty() == true) {
                token = it
            }
        }

        addFragment(R.id.frame_layout, FragmentIQStart(), FragmentIQStart::class.java.simpleName)
    }

    override fun getViewBinding(): ActivityMainTestIqBinding =
        ActivityMainTestIqBinding.inflate(layoutInflater)

}