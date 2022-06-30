package com.example.testiq.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.testiq.databinding.BottomSheetAnswerBinding
import com.example.testiq.model.QuestionModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class BottomSheetAnswer(var lstResult : ArrayList<QuestionModel>) : BottomSheetDialogFragment() {
    private lateinit var binding: BottomSheetAnswerBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = BottomSheetAnswerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.d("AAAAAAAAAAAA", "${lstResult.size}")
        binding.ivBack.setOnClickListener {
            dismiss()
        }
    }
}