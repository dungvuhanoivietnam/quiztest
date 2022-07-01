package com.example.testiq.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.testiq.R
import com.example.testiq.databinding.ItemCheckBottomSheetBinding
import com.example.testiq.model.QuestionModel

class AdapterSelectQuestion(
    var context: Context,
    var onClickItem: (Int) -> Unit,
) : RecyclerView.Adapter<AdapterSelectQuestion.ViewHolder>() {

    private var lstQuestionModel = ArrayList<QuestionModel>()

    fun updateData(list: ArrayList<QuestionModel>) {
        lstQuestionModel.clear()
        lstQuestionModel.addAll(list)
        notifyDataSetChanged()
    }

    inner class ViewHolder(var binding: ItemCheckBottomSheetBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun getItemCount(): Int = lstQuestionModel.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemBinding =
            ItemCheckBottomSheetBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(itemBinding)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val binding = holder.binding
        if (lstQuestionModel.isNotEmpty()) {
            val item = lstQuestionModel[position]
            binding.txt.text = "$position"
            var checkSelected: Boolean? = false
            item.options_answer?.let { it ->
                checkSelected = it.any { options -> return@any options.selected }
            }

            if (checkSelected == true) {
                binding.iv.setImageResource(R.drawable.bg_oval_active)
            } else {
                binding.iv.setImageResource(R.drawable.bg_oval_disable)
            }

            binding.root.setOnClickListener {
                onClickItem.invoke(position)
            }

        }
    }

}

