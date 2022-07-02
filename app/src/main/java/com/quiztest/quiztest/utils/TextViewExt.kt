package com.quiztest.quiztest.utils

import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.quiztest.quiztest.App
import com.quiztest.quiztest.R


fun TextView.setClickSpan(clickAbleSpan: ClickableSpan?, message:String, clickText:String){
    val spannableStringBuilder = SpannableStringBuilder(message)
    val startIndex = message.indexOf(clickText)
    if (startIndex != -1) {
        val endIndex = startIndex + clickText.length
        spannableStringBuilder.setSpan(
            clickAbleSpan,
            startIndex, endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        this.text = spannableStringBuilder
        this.movementMethod = LinkMovementMethod.getInstance()
        this.setLinkTextColor(
            ContextCompat.getColor(
                App.getInstance().applicationContext,
                R.color.color_21C3FF
            )
        )
    }
}