package com.example.testiq

import android.annotation.SuppressLint
import android.os.CountDownTimer
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.example.testiq.adapter.AnswerAdapter
import com.example.testiq.databinding.FragmentQuestionIqBinding
import com.example.testiq.model.OptionsAnswer
import com.example.testiq.model.QuestionModel
import com.example.testiq.ui.BaseFragment
import com.example.testiq.ui.BottomSheetAnswer
import com.example.testiq.ui.DialogConfirm
import com.example.testiq.ui.DialogResultCallApi
import com.example.testiq.utils.NetworkHelper
import com.example.testiq.utils.Status
import com.example.testiq.viewmodel.MainViewModel
import org.json.JSONObject
import java.util.*


class FragmentQuestion :
    BaseFragment<MainViewModel, FragmentQuestionIqBinding>(R.layout.fragment_question_iq) {

    override val viewModel: MainViewModel by viewModels()

    private var lstData = ArrayList<QuestionModel>()
    private var lstOptopn = ArrayList<OptionsAnswer>()
    private var lstOptopn3 = ArrayList<OptionsAnswer>()
    private var lstOptopn1 = ArrayList<OptionsAnswer>()
    private var lstOptopn2 = ArrayList<OptionsAnswer>()
    private var adapterAnswer: AnswerAdapter? = null

    override fun setupViews() {
        if (NetworkHelper(requireContext()).isNetworkConnected()) {
            viewModel.fetchQuestion()
        } else {

        }

        showLoading()
//        lstOptopn.add(OptionsAnswer("", "Test image 0", "IMAGE", 1))
//        lstOptopn.add(OptionsAnswer("", "Test image 0", "IMAGE", 2))
//        lstOptopn.add(OptionsAnswer("", "Test image 0", "IMAGE", 3))
//        lstOptopn.add(OptionsAnswer("", "Test image 0", "IMAGE", 4))
//
//        // option text
//        lstOptopn1.add(OptionsAnswer("", "Test text in dex 1", "TEXT", 1))
//        lstOptopn1.add(OptionsAnswer("", "Test text in dex 1", "TEXT", 2))
//        lstOptopn1.add(OptionsAnswer("", "Test text in dex 1", "TEXT", 3))
//        lstOptopn1.add(OptionsAnswer("", "Test text in dex 1", "TEXT", 4))
//
//        lstOptopn2.add(OptionsAnswer("", "Test text in dex 2", "TEXT", 1))
//        lstOptopn2.add(OptionsAnswer("", "Test text in dex 2", "TEXT", 2))
//        lstOptopn2.add(OptionsAnswer("", "Test text in dex 2", "TEXT", 3))
//        lstOptopn2.add(OptionsAnswer("", "Test text in dex 2", "TEXT", 4))
//
//        lstOptopn3.add(OptionsAnswer("", "Test text in dex 3", "TEXT", 1))
//        lstOptopn3.add(OptionsAnswer("", "Test text in dex 3", "TEXT", 2))
//        lstOptopn3.add(OptionsAnswer("", "Test text in dex 3", "TEXT", 3))
//        lstOptopn3.add(OptionsAnswer("", "Test text in dex 3", "TEXT", 4))
//
//        lstData.add(QuestionModel(1, "IMAGE", "Test text in dex 0", "", lstOptopn))
//        lstData.add(QuestionModel(2, "TEXT", "Test text in dex 1", "", lstOptopn1))
//        lstData.add(QuestionModel(3, "TEXT", "Test text in dex 2", "", lstOptopn2))
//        lstData.add(
//            QuestionModel(
//                5,
//                "TEXT",
//                "Test text in dex 3",
//                "Test text in dex 4",
//                lstOptopn3
//            )
//        )

        adapterAnswer = AnswerAdapter(requireContext()) {

        }

    }

    override fun setupListeners() {
        with(binding) {
            viewNext.setOnClickListener {
                viewModel.index++
                updateResult()
            }

            viewPrevious.setOnClickListener {
                viewModel.index--
                updateResult()
            }

            submit.setOnClickListener {
                DialogConfirm(requireContext(), "10", {
//                    (Objects.requireNonNull(requireActivity()) as MainIQActivity).addFragment(
//                        R.id.frame_layout, FragmentCongratulation("60"), FragmentQuestion::class.java.simpleName
//                    )
                    filterListSubmit()
                    viewModel.submitQuizTest(viewModel.quizTestResponse?.data?.key_quiz_test ?: "")
                }).show()
            }

            ivBack.setOnClickListener {
                Objects.requireNonNull(requireActivity()).supportFragmentManager.popBackStack()
            }

            ivUp.setOnClickListener {
                BottomSheetAnswer(viewModel.lstQuestion) { indexSelect ->
                    viewModel.index = indexSelect
                    updateResult()
                }.show(childFragmentManager, "")
            }

            pbMoney.max = viewModel.lstQuestion.size - 1
            pbMoney.progress = 0
            rclOptionsAnswer.adapter = adapterAnswer
        }
    }

    override fun setupObservers() {
        viewModel.questions.observe(viewLifecycleOwner) {
            when (it.status) {
                Status.SUCCESS -> {
                    cancelLoading()
                    DialogResultCallApi(requireContext(), Status.SUCCESS).show()
                    if (it.data?.success == true) {
                        viewModel.quizTestResponse = it.data
                        it.data.data?.questions?.let { it1 ->
                            handlerListQuestion(it1)
                        }
                    }
                }
                Status.ERROR -> {
                    DialogResultCallApi(requireContext(), Status.ERROR).show()
                    cancelLoading()
                }
                Status.LOADING -> {
                    showLoading()
                }
            }
        }

        viewModel.submitQuizTests.observe(viewLifecycleOwner) {
            when (it.status) {
                Status.SUCCESS -> {
                    cancelLoading()
                    if (it.data?.success == true) {
                        viewModel.submitResponse = it.data
                        it.data.submitData?.let { submitDataResponse ->
                            (Objects.requireNonNull(requireActivity()) as MainIQActivity).addFragment(
                                R.id.frame_layout,
                                FragmentCongratulation(submitDataResponse.total_score),
                                FragmentQuestion::class.java.simpleName
                            )
                        }
                    }
                }
                Status.ERROR -> {
                    DialogResultCallApi(requireContext(), Status.ERROR).show()
                    cancelLoading()
                }
                Status.LOADING -> {
                    showLoading()
                }
            }
        }
    }

    private fun handlerListQuestion(lstQuestion: ArrayList<QuestionModel>) {
        viewModel.lstQuestion.addAll(lstQuestion)
        viewModel.questionModel = viewModel.lstQuestion[0]
        Handler(Looper.getMainLooper()).postDelayed({
            startTimer()
        }, 300)
        updateResult()
    }

    @SuppressLint("SetTextI18n")
    fun updateResult() {
        viewModel.questionModel = viewModel.lstQuestion[viewModel.index]
        Glide.with(requireActivity()).load(viewModel.questionModel?.question_description ?: "")
            .into(binding.imgQuestion);
        binding.pbMoney.progress = viewModel.index
        binding.questionName.text =
            "Câu ${viewModel.index + 1}: ${viewModel.questionModel?.question_name}"
        binding.imgQuestion.visibility =
            if (viewModel.questionModel?.type == "IMAGE") View.VISIBLE else View.GONE
        viewModel.questionModel?.options_answer?.let {
            adapterAnswer?.updateData(it)
        }
        binding.result.text = "${viewModel.index + 1}/ ${viewModel.lstQuestion.size}"
    }

    //
    private fun startTimer() {
        val timer = object : CountDownTimer(1320000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                var seconds = (millisUntilFinished / 1000).toInt()
                val minutes = seconds / 60
                seconds %= 60
                binding.tvTime.text =
                    "${String.format("%02d", minutes) + ":" + String.format("%02d", seconds)}"
            }

            override fun onFinish() {
                Toast.makeText(requireContext(), "Finish", Toast.LENGTH_SHORT).show()
            }
        }
        timer.start()
    }

    fun filterListSubmit() {
        viewModel.lstQuestion.forEach { question ->
            question.options_answer?.let {
                if (it.isNotEmpty()) {
                    for (i in it) {
                        if (i.selected) {
                            viewModel.lstJSONObject.put(question.id.toString(), i.id)
                        }
                    }
                }
            }
        }
        Log.d("AAAAAAAAAAAAAAAAA", viewModel.lstJSONObject.toString())
    }

    override fun getViewBinding(): FragmentQuestionIqBinding =
        FragmentQuestionIqBinding.inflate(layoutInflater)

    override fun onBackPressed() {
        binding.ivBack.performClick()
    }
}