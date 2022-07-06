package com.example.testiq

import androidx.fragment.app.FragmentManager
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import com.example.testiq.databinding.FragmentGiftBasketsBinding
import com.example.testiq.model.QuizTestResponse
import com.example.testiq.model.SubmitDataResponse
import com.example.testiq.ui.BaseFragment
import com.example.testiq.ui.DialogResultCallApi
import com.example.testiq.utils.Status
import com.example.testiq.viewmodel.MainViewModel
import java.util.*

class FragmentGiftBaskets(
    private var submitQuizTestResponse: SubmitDataResponse?,
    var quizTestResponse: QuizTestResponse?,
) :
    BaseFragment<ViewModel, FragmentGiftBasketsBinding>(R.layout.fragment_congratulations) {

    override val viewModel: MainViewModel by viewModels()

    override fun setupViews() {
        if (MainIQActivity.token.isNotEmpty()) {
            viewModel.token = MainIQActivity.token
        }
    }

    override fun setupListeners() {
        with(binding) {
            back.setOnClickListener {
                val fm = activity
                    ?.supportFragmentManager
                fm?.popBackStack(FragmentQuestion::class.java.simpleName, FragmentManager.POP_BACK_STACK_INCLUSIVE)
            }

            imgGift.setOnClickListener {
                if (MainIQActivity.token.isNotEmpty()){
                    viewModel.confirmOpenGif(quizTestResponse?.data?.key_quiz_test ?: "")
                }else{
                    (Objects.requireNonNull(requireActivity()) as MainIQActivity).addFragment(
                        R.id.frame_layout,
                        FragmentGiftBasketsResult(submitQuizTestResponse,quizTestResponse),
                        FragmentQuestion::class.java.simpleName
                    )
                }
            }
        }
    }

    override fun setupObservers() {

        viewModel.confirmOpenGifs.observe(viewLifecycleOwner) {
            when(it.status){
                Status.SUCCESS -> {
                    (Objects.requireNonNull(requireActivity()) as MainIQActivity).addFragment(
                        R.id.frame_layout,
                        FragmentGiftBasketsResult(submitQuizTestResponse,quizTestResponse),
                        FragmentQuestion::class.java.simpleName
                    )
                    cancelLoading()
                }
                Status.ERROR -> {
                    DialogResultCallApi(requireContext(), Status.ERROR, it.message ?:"").show()
                    cancelLoading()
                }
                Status.LOADING -> {
                    showLoading()
                }
            }
        }
    }

    override fun getViewBinding(): FragmentGiftBasketsBinding =
        FragmentGiftBasketsBinding.inflate(layoutInflater)

    override fun onBackPressed() {
        binding.back.performClick()
    }
}