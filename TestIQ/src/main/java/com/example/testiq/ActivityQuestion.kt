package com.example.testiq

import android.annotation.SuppressLint
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.activity.viewModels
import com.example.testiq.adapter.AnswerAdapter
import com.example.testiq.adapter.ViewPagerAdapter
import com.example.testiq.databinding.ActivityQuestionBinding
import com.example.testiq.model.OptionsAnswer
import com.example.testiq.model.QuestionModel
import com.example.testiq.ui.BaseActivity
import com.example.testiq.ui.BottomSheetAnswer
import com.example.testiq.ui.DialogConfirm
import com.example.testiq.ui.DialogResultCallApi
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

    override fun getViewBinding(): ActivityQuestionBinding =
        ActivityQuestionBinding.inflate(layoutInflater)

    override fun initObserver() {

        mainViewModel.questions.observe(this) {
            when (it.status) {
                Status.SUCCESS -> {
                    cancelLoading()
                    DialogResultCallApi(this, Status.SUCCESS).show()
                    if (it.data?.success == true) {
                        mainViewModel.quizTestResponse = it.data
                        it.data.data?.questions?.let { it1 ->
                            updateResult()
                        }
                    }
                }
                Status.ERROR -> {
                    DialogResultCallApi(this, Status.ERROR).show()
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

        showLoading()
        lstOptopn.add(OptionsAnswer("", "Test image 0", "IMAGE", 1))
        lstOptopn.add(OptionsAnswer("", "Test image 0", "IMAGE", 2))
        lstOptopn.add(OptionsAnswer("", "Test image 0", "IMAGE", 3))
        lstOptopn.add(OptionsAnswer("", "Test image 0", "IMAGE", 4))

        // option text
        lstOptopn1.add(OptionsAnswer("", "Test text in dex 1", "TEXT", 1))
        lstOptopn1.add(OptionsAnswer("", "Test text in dex 1", "TEXT", 2))
        lstOptopn1.add(OptionsAnswer("", "Test text in dex 1", "TEXT", 3))
        lstOptopn1.add(OptionsAnswer("", "Test text in dex 1", "TEXT", 4))

        lstOptopn2.add(OptionsAnswer("", "Test text in dex 2", "TEXT", 1))
        lstOptopn2.add(OptionsAnswer("", "Test text in dex 2", "TEXT", 2))
        lstOptopn2.add(OptionsAnswer("", "Test text in dex 2", "TEXT", 3))
        lstOptopn2.add(OptionsAnswer("", "Test text in dex 2", "TEXT", 4))

        lstOptopn3.add(OptionsAnswer("", "Test text in dex 3", "TEXT", 1))
        lstOptopn3.add(OptionsAnswer("", "Test text in dex 3", "TEXT", 2))
        lstOptopn3.add(OptionsAnswer("", "Test text in dex 3", "TEXT", 3))
        lstOptopn3.add(OptionsAnswer("", "Test text in dex 3", "TEXT", 4))

        lstData.add(QuestionModel(2, "TEXT", "Test text in dex 0", "", lstOptopn))
        lstData.add(QuestionModel(3, "TEXT", "Test text in dex 1", "", lstOptopn1))
        lstData.add(QuestionModel(4, "TEXT", "Test text in dex 2", "", lstOptopn2))
        lstData.add(
            QuestionModel(
                5,
                "TEXT",
                "Test text in dex 3",
                "Test text in dex 4",
                lstOptopn3
            )
        )

        mainViewModel.lstQuestion.addAll(lstData)
        mainViewModel.questionModel = mainViewModel.lstQuestion[0]
        binding.pbMoney.max = mainViewModel.lstQuestion.size - 1
        binding.pbMoney.progress = 0

        with(binding) {
            adapterAnswer = AnswerAdapter(this@ActivityQuestion) {

            }
            rclOptionsAnswer.adapter = adapterAnswer

            viewNext.setOnClickListener {
                mainViewModel.index++
                updateResult()
            }

            viewPrevious.setOnClickListener {
                mainViewModel.index--
                updateResult()
            }

            submit.setOnClickListener {
                DialogConfirm(this@ActivityQuestion, "10") {
//                    mainViewModel.submitQuizTest(mainViewModel.questionModel.)
                }.show()
            }

            binding.ivBack.setOnClickListener {
                finish()
            }

            binding.ivUp.setOnClickListener {
                BottomSheetAnswer(mainViewModel.lstQuestion) { indexSelect ->
                    mainViewModel.index = indexSelect
                    updateResult()
                }.show(supportFragmentManager, "")
            }
        }

        Handler(Looper.getMainLooper()).postDelayed({
            /* Create an Intent that will start the Menu-Activity. */
            cancelLoading()
            Handler(Looper.getMainLooper()).postDelayed({
                /* Create an Intent that will start the Menu-Activity. */
                updateResult()
                DialogResultCallApi(this,Status.SUCCESS).show()
            }, 900)
        }, 2000)

    }

    @SuppressLint("SetTextI18n")
    fun updateResult() {
        mainViewModel.questionModel = mainViewModel.lstQuestion[mainViewModel.index]
        binding.pbMoney.progress = mainViewModel.index
        binding.questionName.text = "Câu ${mainViewModel.index}: ${mainViewModel.questionModel?.question_name}"
        binding.imgQuestion.visibility =
            if (mainViewModel.questionModel?.type == "IMAGE") View.VISIBLE else View.GONE
        mainViewModel.questionModel?.options_answer?.let {
            adapterAnswer?.updateData(it)
        }
        binding.result.text = "${mainViewModel.index + 1}/ ${mainViewModel.lstQuestion.size}"
    }

}