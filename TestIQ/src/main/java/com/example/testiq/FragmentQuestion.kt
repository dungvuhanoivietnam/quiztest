package com.example.testiq

import android.annotation.SuppressLint
import android.os.CountDownTimer
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.example.testiq.adapter.AnswerAdapter
import com.example.testiq.databinding.FragmentQuestionIqBinding
import com.example.testiq.model.QuestionModel
import com.example.testiq.ui.*
import com.example.testiq.utils.Const
import com.example.testiq.utils.NetworkHelper
import com.example.testiq.utils.Status
import com.example.testiq.viewmodel.MainViewModel
import java.util.*


class FragmentQuestion :
    BaseFragment<MainViewModel, FragmentQuestionIqBinding>(R.layout.fragment_question_iq) {

    override val viewModel: MainViewModel by viewModels()

    private var adapterAnswer: AnswerAdapter? = null
    var cTimer: CountDownTimer? = null
    private var idTopic: Int = 0


    override fun setupViews() {

        if (MainIQActivity.token.isNotEmpty()) {
            viewModel.token = MainIQActivity.token
        }

        if (arguments?.getInt(Const.TEST_ID) != 0) {
            idTopic = arguments?.getInt(Const.TEST_ID)!!
        }

        if (NetworkHelper(requireContext()).isNetworkConnected()) {
            // khi da login
            if (viewModel.token.isNotEmpty()) {
                viewModel.fetchQuestion(idTopic)
            } else {
                // khi chua login
                viewModel.fetChQuestionNoToken(idTopic)
            }
        } else {
            DialogResultCallApi(requireContext(), Status.ERROR, "Connect internet") {
                Objects.requireNonNull(requireActivity()).supportFragmentManager.popBackStack()
            }.show()
        }

        adapterAnswer = AnswerAdapter(requireContext()) {

        }

    }

    override fun setupListeners() {
        with(binding) {
            viewNext.setOnClickListener {
                if (viewModel.index == viewModel.lstQuestion.size - 1) {
                    return@setOnClickListener
                }
                viewModel.index++
                updateResult()
            }

            viewPrevious.setOnClickListener {
                if (viewModel.index == 0) {
                    return@setOnClickListener
                }
                viewModel.index--
                updateResult()
            }

            submit.setOnClickListener {
                DialogConfirm(requireContext(), "${checkDataSelect()}", viewModel.lstQuestion, {
                    filterListSubmit()
                    // khi chua login
                    submit()
                    // da login
                }).show()
            }

            ivBack.setOnClickListener {
                DialogConfirmBack(requireContext()) {
                    Objects.requireNonNull(requireActivity()).supportFragmentManager.popBackStack()
                }.show()
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

    private fun submit() {
        filterListSubmit()
        // khi chua login
        if (viewModel.token.isNotEmpty()) {
            viewModel.submitQuizTest(
                viewModel.quizTestResponse?.data?.key_quiz_test ?: ""
            )
        } else {
            viewModel.submitQuizTestNoToken(
                viewModel.quizTestResponse?.data?.key_quiz_test ?: ""
            )
        }
    }

    override fun setupObservers() {
        viewModel.questions.observe(viewLifecycleOwner) {
            when (it.status) {
                Status.SUCCESS -> {
                    cancelLoading()
//                    DialogResultCallApi(
//                        requireContext(),
//                        Status.SUCCESS,
//                        "Start Test successfully."
//                    ) {
//
//                    }.show()
                    if (it.data?.success == true) {
                        viewModel.quizTestResponse = it.data
                        it.data.data?.questions?.let { it1 ->
                            handlerListQuestion(it1)
                        }
                    }
                }
                Status.ERROR -> {
                    DialogResultCallApi(requireContext(), Status.ERROR, it.message ?: "") {
                        activity?.finish()
                    }.show()
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
                    cTimer?.cancel()
                    if (it.data?.success == true) {
                        viewModel.submitResponse = it.data
                        it.data.submitData?.let { submitDataResponse ->
                            (Objects.requireNonNull(requireActivity()) as MainIQActivity).addFragment(
                                R.id.frame_layout,
                                FragmentCongratulation(
                                    submitDataResponse,
                                    viewModel.quizTestResponse
                                ),
                                FragmentQuestion::class.java.simpleName
                            )
                        }
                    }
                }
                Status.ERROR -> {
                    DialogResultCallApi(requireContext(), Status.ERROR, it.message ?: "") {

                    }.show()
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
            .into(binding.imgQuestion)
        binding.pbMoney.max = viewModel.lstQuestion.size
        binding.pbMoney.progress = viewModel.index + 1
        binding.questionName.text =
            "Câu ${viewModel.index + 1}: ${viewModel.questionModel?.question_name}"
        binding.imgQuestion.visibility =
            if (viewModel.questionModel?.type == "IMAGE") View.VISIBLE else View.GONE
        viewModel.questionModel?.options_answer?.let {
            adapterAnswer?.updateData(it)
        }
        binding.result.text = "${viewModel.index + 1}/ ${viewModel.lstQuestion.size}"
    }

    private fun startTimer() {/*1320000*/
        cTimer = object : CountDownTimer(30000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                var seconds = (millisUntilFinished / 1000).toInt()
                val minutes = seconds / 60
                seconds %= 60
                binding.tvTime.text =
                    "${String.format("%02d", minutes) + ":" + String.format("%02d", seconds)}"
            }

            override fun onFinish() {
                DialogTimeUp(requireContext()) {
                    submit()
                }.show()
            }
        }
        (cTimer as CountDownTimer).start()
    }

    private fun filterListSubmit() {
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

    private fun checkDataSelect(): Int {
        var checkSelected: Boolean
        var countSelect = 0
        viewModel.lstQuestion.forEach { question ->
            checkSelected =
                question.options_answer?.any { options -> return@any options.selected } ?: false
            if (checkSelected) {
                countSelect++
            }
        }
        return countSelect
    }

    override fun onDestroy() {
        super.onDestroy()
        cTimer?.cancel()
        Log.d("AAAAAAAAAAA", "onDestroy")
    }

    override fun onPause() {
        super.onPause()
        cTimer?.cancel()
        Log.d("AAAAAAAAAAA", "onPause")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d("AAAAAAAAAAA", "onDestroyView")
    }

    override fun onStop() {
        super.onStop()
        Log.d("AAAAAAAAAAA", "onStop")
    }

    override fun getViewBinding(): FragmentQuestionIqBinding =
        FragmentQuestionIqBinding.inflate(layoutInflater)

    override fun onBackPressed() {
        binding.ivBack.performClick()
    }
}