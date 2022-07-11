package com.example.testiq

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.net.Uri
import android.provider.MediaStore
import android.view.View
import androidx.annotation.NonNull
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import com.example.testiq.databinding.FragmentCongratulationsBinding
import com.example.testiq.model.QuizTestResponse
import com.example.testiq.model.SubmitDataResponse
import com.example.testiq.ui.BaseFragment
import java.io.*
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
        with(binding){
            back.setOnClickListener {
                val fm = activity
                    ?.supportFragmentManager
                fm?.popBackStack(FragmentQuestion::class.java.simpleName, FragmentManager.POP_BACK_STACK_INCLUSIVE)
            }
            total.text = "${submitQuizTestResponse?.total_score}"
            review.text = fromHtml(submitQuizTestResponse?.review)

            reward.setOnClickListener {
                (Objects.requireNonNull(requireActivity()) as MainIQActivity).addFragment(
                    R.id.frame_layout,
                    FragmentGiftBaskets(submitQuizTestResponse,quizTestResponse),
                    FragmentQuestion::class.java.simpleName
                )
            }

            share.setOnClickListener {
                shareBitmap( takeScreenshotOfView(scrool, scrool.height,scrool.width))
            }
        }
    }

    private fun takeScreenshotOfView(view: View, height: Int, width: Int): Bitmap {
        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        val bgDrawable = view.background
        if (bgDrawable != null) {
            bgDrawable.draw(canvas)
        } else {
            canvas.drawColor(Color.WHITE)
        }
        view.draw(canvas)
        return bitmap
    }

    private fun shareBitmap(@NonNull bitmap: Bitmap) {
        val intent = Intent(Intent.ACTION_SEND)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        intent.putExtra(Intent.EXTRA_STREAM, getImageUri(requireContext(),bitmap))
        intent.type = "*/*"
        startActivity(Intent.createChooser(intent, "Share with"))
    }

    fun getImageUri(inContext: Context, inImage: Bitmap): Uri? {
        val bytes = ByteArrayOutputStream()
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
        val path: String =
            MediaStore.Images.Media.insertImage(inContext.contentResolver, inImage, "Title", null)
        return Uri.parse(path)
    }

    override fun setupObservers() {
    }

    override fun getViewBinding(): FragmentCongratulationsBinding  = FragmentCongratulationsBinding.inflate(layoutInflater)

    override fun onBackPressed() {
        binding.back.performClick()
    }
}