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
import com.example.testiq.utils.Status

class DialogResultCallApi(
    internal var context: Context, var status : Status
) : Dialog(context, R.style.MaterialDialogSheet) {

    private val binding = DialogResultApiBinding.inflate(LayoutInflater.from(context))

    init {
        window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        window!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        window!!.setGravity(Gravity.CENTER)
        window!!.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
        setContentView(binding.root)
        initUI()
    }

    private fun initUI() {
        binding.label.text = if (Status.SUCCESS == status) "Sucacess" else "Error"

        binding.submit.setOnClickListener {
            dismiss()
        }
    }
}
