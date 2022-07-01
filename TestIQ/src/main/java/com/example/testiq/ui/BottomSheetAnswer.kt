package com.example.testiq.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.testiq.adapter.AdapterSelectQuestion
import com.example.testiq.databinding.BottomSheetAnswerBinding
import com.example.testiq.model.QuestionModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class BottomSheetAnswer(
    private var lstResult: ArrayList<QuestionModel>,
    var onclick: (Int) -> Unit
) : BottomSheetDialogFragment() {
    private lateinit var binding: BottomSheetAnswerBinding
    private lateinit var adapter: AdapterSelectQuestion

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = BottomSheetAnswerBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var checkSelected: Boolean
        var countSelect = 0

        adapter = AdapterSelectQuestion(requireContext()) {
            onclick.invoke(it)
            dismiss()
        }

        lstResult.forEach { question ->
            checkSelected =
                question.options_answer?.any { options -> return@any options.selected } ?: false
            if (checkSelected) {
                countSelect++
            }
        }

        with(binding) {
            total.text = "${countSelect}/${lstResult.size}"
            rclResult.adapter = adapter
            ivBack.setOnClickListener {
                dismiss()
            }
            adapter.updateData(lstResult)
        }
        Log.i("countSelect", "$countSelect")
    }
}