package com.example.testiq

import android.annotation.SuppressLint
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import com.example.testiq.adapter.AnswerAdapter
import com.example.testiq.adapter.ViewPagerAdapter
import com.example.testiq.databinding.ActivityQuestionBinding
import com.example.testiq.model.OptionsAnswer
import com.example.testiq.model.QuestionModel
import com.example.testiq.ui.BaseActivity
import com.example.testiq.ui.BottomSheetAnswer
import com.example.testiq.ui.DialogConfirm
import com.example.testiq.utils.NetworkHelper
import com.example.testiq.utils.Status
import com.example.testiq.viewmodel.MainViewModel


class ActivityQuestion : BaseActivity<MainViewModel, ActivityQuestionBinding>() {

    private val mainViewModel: MainViewModel by viewModels()
    private var lstData = ArrayList<QuestionModel>()
    private var lstOptopn = ArrayList<OptionsAnswer>()
    private var lstOptopn3 = ArrayList<OptionsAnswer>()
    private var lstOptopn1 = ArrayList<OptionsAnswer>()
    private var lstOptopn2 = ArrayList<OptionsAnswer>()
    private var adapterAnswer: AnswerAdapter? = null
    private var adapterViewPager : ViewPagerAdapter?= null

    override fun getViewBinding(): ActivityQuestionBinding =
        ActivityQuestionBinding.inflate(layoutInflater)

    override fun initObserver() {

        mainViewModel.questions.observe(this) {
            when (it.status) {
                Status.SUCCESS -> {
                    cancelLoading()
                    if (it.data?.success == true) {
                        it.data.data?.questions?.let { it1 ->
                            updateResult()
                        }
                    }
                }
                Status.ERROR -> {
                    cancelLoading()
                }
                Status.LOADING -> {
                    showLoading()
                }
            }
        }


    }

    override fun initView() {
        if (NetworkHelper(this).isNetworkConnected()) {
//            mainViewModel.fetchQuestion()
        } else {

        }

        // // option image
        lstOptopn.add(OptionsAnswer("", "Cái giá 1 ", "IMAGE", 1))
        lstOptopn.add(OptionsAnswer("", "Cái giá 2", "IMAGE", 2))
        lstOptopn.add(OptionsAnswer("", "Cái giá 3", "IMAGE", 3))
        lstOptopn.add(OptionsAnswer("", "Cái giá 4", "IMAGE", 4))

        lstOptopn3.add(OptionsAnswer("", "Cái giá 1 ", "IMAGE", 1))
        lstOptopn3.add(OptionsAnswer("", "Cái giá 2", "IMAGE", 2))
        lstOptopn3.add(OptionsAnswer("", "Cái giá 3", "IMAGE", 3))
        lstOptopn3.add(OptionsAnswer("", "Cái giá 4", "IMAGE", 4))

        // option text
        lstOptopn1.add(OptionsAnswer("", "Cái giá 1 ", "TEXT", 1))
        lstOptopn1.add(OptionsAnswer("", "Cái giá 2", "TEXT", 2))
        lstOptopn1.add(OptionsAnswer("", "Cái giá 3", "TEXT", 3))
        lstOptopn1.add(OptionsAnswer("", "Cái giá 4", "TEXT", 4))

        lstOptopn2.add(OptionsAnswer("", "Cái giá 1 ", "TEXT", 1))
        lstOptopn2.add(OptionsAnswer("", "Cái giá 2", "TEXT", 2))
        lstOptopn2.add(OptionsAnswer("", "Cái giá 3", "TEXT", 3))
        lstOptopn2.add(OptionsAnswer("", "Cái giá 4", "TEXT", 4))

        lstData.add(QuestionModel(1, "TEXT", "Bạn Bạn đã trả lời sai 10 câu hỏi. Kết quả thang điểm của bạn chỉ đạt 60%.", "", lstOptopn))
        lstData.add(QuestionModel(2, "TEXT", "TEST IMAGE MỘT CHÚT XEM SAO", "", lstOptopn))
        lstData.add(QuestionModel(3, "TEXT", "Test3", "", lstOptopn3))
        lstData.add(QuestionModel(4, "TEXT", "Test4", "", lstOptopn1))
        lstData.add(QuestionModel(5, "TEXT", "Test5", "", lstOptopn2))

        mainViewModel.lstQuestion.addAll(lstData)
        mainViewModel.questionModel = mainViewModel.lstQuestion[0]

        adapterViewPager = ViewPagerAdapter(this@ActivityQuestion) {

        }

        binding.pageQuestion.adapter = adapterViewPager
        adapterViewPager?.updateData(lstData)
        binding.pbMoney.max = mainViewModel.lstQuestion.size -1
        binding.pbMoney.progress = mainViewModel.index +1
//
        with(binding){
            rclOptionsAnswer.adapter = adapterAnswer


            viewNext.setOnClickListener {
                pageQuestion.currentItem++
            }

            viewPrevious.setOnClickListener {
                pageQuestion.currentItem--
            }

            submit.setOnClickListener {
                DialogConfirm(this@ActivityQuestion, "10") {

                }.show()

            }

            binding.ivBack.setOnClickListener {
                finish()
            }

            binding.ivUp.setOnClickListener {
                BottomSheetAnswer(mainViewModel.lstQuestion).show(supportFragmentManager, "")
            }
        }

    }

    @SuppressLint("SetTextI18n")
    private fun updateResult() {
        binding.questionName.text = mainViewModel.questionModel?.question_name
        mainViewModel.questionModel = mainViewModel.lstQuestion[mainViewModel.index]

        mainViewModel.questionModel?.options_answer?.let {
            adapterAnswer?.updateData(it)
        }
        binding.result.text = "${mainViewModel.index + 1}/ ${mainViewModel.lstQuestion.size}"
    }
}