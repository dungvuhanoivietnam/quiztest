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
    internal var context: Context,
    var status: Status,
    var description: String,
    var onclick: () -> Unit
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
        binding.label.text = if (Status.SUCCESS == status) "Success" else "Error"
        binding.description.text = description
        binding.icon.setImageResource(
            if (Status.SUCCESS == status) R.drawable.ic_success else R.drawable.ic_error
        )
        binding.submit.setOnClickListener {
            dismiss()
            if (Status.ERROR == status) {
                onclick.invoke()
            }
        }
    }

    fun setTextAndIcon(title: String, icon: Int) {
        binding.label.text = title
        binding.icon.setImageResource(icon)
    }
}
