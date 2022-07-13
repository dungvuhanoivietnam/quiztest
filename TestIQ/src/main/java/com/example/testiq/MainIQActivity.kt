package com.example.testiq

import android.os.Bundle
import androidx.activity.viewModels
import com.example.testiq.databinding.ActivityMainTestIqBinding
import com.example.testiq.model.TestItem
import com.example.testiq.ui.BaseActivity
import com.example.testiq.utils.Const
import com.example.testiq.viewmodel.MainIQViewModel


class MainIQActivity : BaseActivity<MainIQViewModel, ActivityMainTestIqBinding>() {

    override val viewModel: MainIQViewModel by viewModels()

    companion object {
        var token: String = ""
        var type: String = ""
        var language: String = ""
        var moneyBonus: String = ""
        var feeStar: String = ""
        var testId: Int = 0
        var testType: String? = null
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

        intent.getIntExtra("test_id", 0).let {
            testId = it
        }

        intent.getStringExtra(Const.TEST_TYPE).let {
            testType = it
        }

        var bundle = Bundle()
        var fragmentIQStart = FragmentIQStart()
        bundle.putInt(Const.TEST_ID, testId)
        bundle.putString(Const.TEST_TYPE, testType)
        fragmentIQStart.arguments = bundle
        addFragment(R.id.frame_layout, fragmentIQStart, FragmentIQStart::class.java.simpleName)
    }

    override fun getViewBinding(): ActivityMainTestIqBinding =
        ActivityMainTestIqBinding.inflate(layoutInflater)

}