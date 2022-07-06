package com.example.testiq

import androidx.activity.viewModels
import com.example.testiq.databinding.ActivityMainTestIqBinding
import com.example.testiq.ui.BaseActivity
import com.example.testiq.viewmodel.MainIQViewModel


class MainIQActivity : BaseActivity<MainIQViewModel, ActivityMainTestIqBinding>() {

    override val viewModel: MainIQViewModel by viewModels()

    companion object {
        var token: String = ""
        var type : String = ""
    }

    override fun initView() {

        intent.getStringExtra("token")?.let {
            if (intent.getStringExtra("token")?.isNotEmpty() == true) {
                token = it
            }
        }

        intent.getStringExtra("type")?.let {
            type = it
        }

        addFragment(R.id.frame_layout, FragmentIQStart(), FragmentIQStart::class.java.simpleName)
    }

    override fun getViewBinding(): ActivityMainTestIqBinding =
        ActivityMainTestIqBinding.inflate(layoutInflater)

}