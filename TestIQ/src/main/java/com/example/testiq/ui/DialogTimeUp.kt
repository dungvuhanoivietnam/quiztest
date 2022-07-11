package com.example.testiq.ui

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.WindowManager
import com.example.testiq.R
import com.example.testiq.databinding.DialogResultApiBinding
import com.example.testiq.databinding.DialogTimeUpBinding
import com.example.testiq.utils.Status

class DialogTimeUp(
    internal var context: Context, var onclick : () -> Unit
) : Dialog(context, R.style.MaterialDialogSheet) {

    private val binding = DialogTimeUpBinding.inflate(LayoutInflater.from(context))

    init {
        window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        window!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        window!!.setGravity(Gravity.CENTER)
        window!!.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
        setContentView(binding.root)
        initUI()
    }

    private fun initUI() {
        binding.submit.setOnClickListener {
            dismiss()
            onclick.invoke()
        }
    }
}
