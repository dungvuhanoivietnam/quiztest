package com.example.testiq

import androidx.core.text.HtmlCompat
import androidx.fragment.app.viewModels
import com.example.testiq.databinding.ActivityMainIqBinding
import com.example.testiq.ui.BaseFragment
import com.example.testiq.viewmodel.MainViewModel
import java.util.*

class FragmentIQStart :
    BaseFragment<MainViewModel, ActivityMainIqBinding>(R.layout.activity_main_iq) {

    override val viewModel: MainViewModel by viewModels()

    override fun setupViews() {
        with(binding) {
            description.text =
                HtmlCompat.fromHtml(
                    getString(R.string.txt_description_home_test_iq),
                    HtmlCompat.FROM_HTML_MODE_LEGACY)

            toolbar.setOnClickListener {
                activity?.finish()
            }
        }
    }

    override fun setupListeners() {
        binding.start.setOnClickListener {
            (Objects.requireNonNull(requireActivity()) as MainIQActivity).addFragment(
                R.id.frame_layout, FragmentQuestion(), FragmentQuestion::class.java.simpleName
            )
        }
    }

    override fun setupObservers() {
    }

    override fun getViewBinding(): ActivityMainIqBinding = ActivityMainIqBinding.inflate(layoutInflater)
    override fun onBackPressed() {
        binding.toolbar.performClick()
    }
}