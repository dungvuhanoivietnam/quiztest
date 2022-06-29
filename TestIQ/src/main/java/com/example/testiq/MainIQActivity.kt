package com.example.testiq

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.HtmlCompat
import com.example.testiq.databinding.ActivityMainIqBinding

class MainIQActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainIqBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainIqBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.description.text =
            HtmlCompat.fromHtml(
                getString(R.string.txt_description_home_test_iq),
                HtmlCompat.FROM_HTML_MODE_LEGACY
            )

        binding.toolbar.setOnClickListener {
            finish()
        }
    }

}