package com.example.testiq

import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import com.example.testiq.databinding.FragmentCongratulationsBinding
import com.example.testiq.model.QuizTestResponse
import com.example.testiq.model.SubmitDataResponse
import com.example.testiq.ui.BaseFragment
import java.util.*

class FragmentCongratulation(
    private var submitQuizTestResponse: SubmitDataResponse?,
    var quizTestResponse: QuizTestResponse?
) :
    BaseFragment<ViewModel, FragmentCongratulationsBinding>(R.layout.fragment_congratulations) {

    override val viewModel: ViewModel by viewModels()

    override fun setupViews() {
    }

    override fun setupListeners() {
        with(binding){
            back.setOnClickListener {
                Objects.requireNonNull(requireActivity()).supportFragmentManager.popBackStack()
            }
            total.text = "${submitQuizTestResponse?.total_score}"
            review.text = fromHtml(submitQuizTestResponse?.review)

            reward.setOnClickListener {
                (Objects.requireNonNull(requireActivity()) as MainIQActivity).addFragment(
                    R.id.frame_layout,
                    FragmentGiftBaskets(submitQuizTestResponse,quizTestResponse),
                    FragmentQuestion::class.java.simpleName
                )
            }
        }
    }

    override fun setupObservers() {
    }

    override fun getViewBinding(): FragmentCongratulationsBinding  = FragmentCongratulationsBinding.inflate(layoutInflater)

    override fun onBackPressed() {
        binding.back.performClick()
    }
}