package com.example.testiq.ui

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.text.Html
import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.WindowManager
import androidx.core.text.HtmlCompat
import com.example.testiq.R
import com.example.testiq.databinding.DialogConfirmSubmitBinding

class DialogConfirm(
    internal var context: Context,
    var value: String,
    var onclick: () -> Unit
) : Dialog(context, R.style.MaterialDialogSheet) {

    private val binding = DialogConfirmSubmitBinding.inflate(LayoutInflater.from(context))

    init {
        window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        window!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        window!!.setGravity(Gravity.CENTER)
        window!!.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
        setContentView(binding.root)
        initUI()
    }

    private fun initUI() {

        binding.description.text =
            HtmlCompat.fromHtml(
                context.resources.getString(R.string.txt_description_confirm, value),
                HtmlCompat.FROM_HTML_MODE_LEGACY
            )

        binding.cancelButton.setOnClickListener {
            dismiss()
        }

        binding.submit.setOnClickListener {
            onclick.invoke()
            dismiss()
        }
    }
}
