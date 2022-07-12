package com.example.testiq

import android.content.Intent
import androidx.fragment.app.FragmentManager
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
        with(binding) {
            back.setOnClickListener {
                val fm = activity
                    ?.supportFragmentManager
                fm?.popBackStack(
                    FragmentQuestion::class.java.simpleName,
                    FragmentManager.POP_BACK_STACK_INCLUSIVE
                )
            }
            total.text = "${submitQuizTestResponse?.total_score}"
            review.text = fromHtml(submitQuizTestResponse?.review)

            reward.setOnClickListener {
                (Objects.requireNonNull(requireActivity()) as MainIQActivity).addFragment(
                    R.id.frame_layout,
                    FragmentGiftBaskets(submitQuizTestResponse, quizTestResponse),
                    FragmentQuestion::class.java.simpleName
                )
            }

            share.setOnClickListener {
                context?.resources?.getString(R.string.link_share)?.let { it1 -> shareLink(it1) }
            }
        }
    }


    private fun shareLink(link: String) {
        val intent = Intent(Intent.ACTION_SEND)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_SUBJECT, "Quizoid");
        intent.putExtra(Intent.EXTRA_TEXT, link);
        startActivity(Intent.createChooser(intent, "Share via"));
    }

    override fun setupObservers() {
    }

    override fun getViewBinding(): FragmentCongratulationsBinding =
        FragmentCongratulationsBinding.inflate(layoutInflater)

    override fun onBackPressed() {
        binding.back.performClick()
    }
}