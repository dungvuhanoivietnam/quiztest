package com.example.testiq

import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.SpannableStringBuilder
import android.util.Log
import androidx.core.text.color
import androidx.fragment.app.viewModels
import com.example.testiq.databinding.ActivityMainIqBinding
import com.example.testiq.model.DetailTopicResponse
import com.example.testiq.model.TestItem
import com.example.testiq.ui.BaseFragment
import com.example.testiq.ui.DialogResultCallApi
import com.example.testiq.utils.Const
import com.example.testiq.utils.StarEnum
import com.example.testiq.utils.Status
import com.example.testiq.viewmodel.MainViewModel
import java.util.*

class FragmentIQStart :
    BaseFragment<MainViewModel, ActivityMainIqBinding>(R.layout.activity_main_iq) {

    override val viewModel: MainViewModel by viewModels()

    private var testType: String? = null
    private var testId: Int = 0

    override fun setupViews() {

        if (arguments?.getInt(Const.TEST_ID) != 0) {
            testId = arguments?.getInt(Const.TEST_ID)!!
        }

        if (arguments?.getString(Const.TEST_TYPE) != null) {
            testType = arguments?.getString(Const.TEST_TYPE)
        }

        testId.let { viewModel.fetchDetailQuestion(it) }

        binding.toolbar.setOnClickListener {
            activity?.finish()
        }
    }

    override fun setupListeners() {
        binding.start.setOnClickListener {
            var bundle = Bundle()
            bundle.putInt(Const.TEST_ID, testId)
            var fragmentQuestion = FragmentQuestion()
            fragmentQuestion.arguments = bundle
            (Objects.requireNonNull(requireActivity()) as MainIQActivity).addFragment(
                R.id.frame_layout, fragmentQuestion, FragmentQuestion::class.java.simpleName
            )
        }
    }

    override fun setupObservers() {
        viewModel.detailTopic.observe(viewLifecycleOwner) {
            if (it != null) {
                when (it.status) {
                    Status.SUCCESS -> {
                        if (it.data != null) {
                            Handler(Looper.getMainLooper()).post {
                                updateView(it.data.data)
                            }
                        }
                        cancelLoading()
                    }
                    Status.ERROR -> {
                        DialogResultCallApi(
                            requireContext(),
                            Status.ERROR,
                            it.message ?: ""
                        ) {}.show()
                        cancelLoading()
                    }
                    Status.LOADING -> {
                        showLoading()
                    }
                }
            }
        }
    }

    private fun updateView(it: DetailTopicResponse.Data?) {

        val numberQues = SpannableStringBuilder()
            .color(Color.parseColor("#8D959D")) { append(context?.resources?.getString(R.string.number_question)) }
            .append(" : ")
            .color(Color.parseColor("#21C3FF")) { append(it?.totalQuestionCount.toString()) }

        val time = SpannableStringBuilder()
            .color(Color.parseColor("#8D959D")) { append(context?.resources?.getString(R.string.time)) }
            .append(" : ")
            .color(Color.parseColor("#21C3FF")) { append(it?.timeToDo.toString()) }

        val allotment = SpannableStringBuilder()
            .color(Color.parseColor("#8D959D")) { append(context?.resources?.getString(R.string.allotment)) }
            .append(" : ")
            .color(Color.parseColor("#21C3FF")) { append(it?.allotment.toString()) }

        val target = SpannableStringBuilder()
            .color(Color.parseColor("#8D959D")) { append(context?.resources?.getString(R.string.target)) }
            .append(" : ")
            .color(Color.parseColor("#F44336")) { append(it?.feeStar.toString() + "/" + it?.totalQuestionCount + "  correct ") }


        binding.numberQuestion.text = numberQues
        binding.time.text = time
        binding.allotment.text = allotment
        binding.target.text = target

        binding.tvTitle.text = it?.title
        binding.tvMoney.text = String.format("%s %s", "Get", it?.moneyBonus.toString())

        if (testType != null) {
            if (testType!! == StarEnum.FEESTAR.toString()) {
                binding.tvRate.text = String.format("%s %s", "-", it?.feeStar.toString())
            } else {
                binding.tvRate.text = String.format("%s %s", "-", it?.starBonus.toString())
            }
        }
    }

    override fun getViewBinding(): ActivityMainIqBinding =
        ActivityMainIqBinding.inflate(layoutInflater)

    override fun onBackPressed() {
        binding.toolbar.performClick()
    }
}