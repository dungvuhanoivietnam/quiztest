package com.example.testiq

import androidx.lifecycle.ViewModel
import com.example.testiq.databinding.ActivityMainTestIqBinding
import com.example.testiq.ui.BaseActivity


class MainIQActivity : BaseActivity<ViewModel, ActivityMainTestIqBinding>() {

    override fun initView() {
        addFragment(R.id.frame_layout, FragmentIQStart(), FragmentIQStart::class.java.simpleName)
    }

    override fun getViewBinding(): ActivityMainTestIqBinding = ActivityMainTestIqBinding.inflate(layoutInflater)

}