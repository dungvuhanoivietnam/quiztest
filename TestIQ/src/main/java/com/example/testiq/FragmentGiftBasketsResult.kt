package com.example.testiq

import androidx.core.text.HtmlCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import com.example.testiq.databinding.FragmentCongratulationsBinding
import com.example.testiq.databinding.FragmentGiftBasketsBinding
import com.example.testiq.databinding.FragmentGiftBasketsResultBinding
import com.example.testiq.model.QuizTestResponse
import com.example.testiq.model.SubmitDataResponse
import com.example.testiq.ui.BaseFragment
import java.util.*

class FragmentGiftBasketsResult(
    private var submitQuizTestResponse: SubmitDataResponse?,
    var quizTestResponse: QuizTestResponse?
) :
    BaseFragment<ViewModel, FragmentGiftBasketsResultBinding>(R.layout.fragment_congratulations) {

    override val viewModel: ViewModel by viewModels()

    override fun setupViews() {
    }

    override fun setupListeners() {
        with(binding) {
            back.setOnClickListener {
                Objects.requireNonNull(requireActivity()).supportFragmentManager.popBackStack()
            }

            getGift.setOnClickListener {

            }

            starts.text = context?.resources?.getString(
                R.string.txt_gift_baskets_stars,
                submitQuizTestResponse?.star_bonus ?: "")
                ?.let {
                    HtmlCompat.fromHtml(
                        it,
                        HtmlCompat.FROM_HTML_MODE_LEGACY
                    )
                }

            bonus.text = context?.resources?.getString(
                R.string.txt_gift_baskets_stars,
                submitQuizTestResponse?.money_bonus ?: "")
                ?.let {
                    HtmlCompat.fromHtml(
                        it,
                        HtmlCompat.FROM_HTML_MODE_LEGACY
                    )
                }

        }
    }

    override fun setupObservers() {
    }

    override fun getViewBinding(): FragmentGiftBasketsResultBinding =
        FragmentGiftBasketsResultBinding.inflate(layoutInflater)

    override fun onBackPressed() {
        binding.back.performClick()
    }
}