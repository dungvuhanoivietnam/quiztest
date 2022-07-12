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
        var language : String = ""
        var moneyBonus : String = ""
        var feeStar : String = ""
    }

    override fun initView() {

        intent.getStringExtra("token")?.let {
            if (intent.getStringExtra("token")?.isNotEmpty() == true) {
                token = "Bearer $it"
            }
        }

        intent.getStringExtra("language")?.let {
            if (intent.getStringExtra("language")?.isNotEmpty() == true) {
                language = it
            }
        }

        intent.getStringExtra("data")?.let {
            val data = it.split(",")
            moneyBonus = data[0]
            feeStar = data[1]
        }

        addFragment(R.id.frame_layout, FragmentIQStart(), FragmentIQStart::class.java.simpleName)
    }

    override fun getViewBinding(): ActivityMainTestIqBinding =
        ActivityMainTestIqBinding.inflate(layoutInflater)

}