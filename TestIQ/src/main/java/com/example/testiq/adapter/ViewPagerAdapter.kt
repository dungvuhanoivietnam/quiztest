package com.example.testiq.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.testiq.databinding.ItemPageBinding
import com.example.testiq.model.QuestionModel

class ViewPagerAdapter(var context: Context, var onClick : (Pair<ArrayList<QuestionModel>,Int>) -> Unit) : RecyclerView.Adapter<ViewHolder>() {

    private var lstData = ArrayList<QuestionModel>()
    private var adapterAnswer: AnswerAdapter? = null

    fun getData(): ArrayList<QuestionModel> {
        return lstData
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(lstQuestion: ArrayList<QuestionModel>) {
        lstData.clear()
        if (lstQuestion.isNotEmpty()) {
            lstData.addAll(lstQuestion)
        }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemBinding =
            ItemPageBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(itemBinding)
    }

    override fun getItemCount(): Int = lstData.size

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.itemView.run {
        val binding = holder.binding
        val item = lstData[position]
        if (lstData.isNotEmpty()) {

            binding.questionName.text = "Câu ${position + 1}: ${item.question_name}"

            // list answer
            adapterAnswer = AnswerAdapter(context) {
                onClick.invoke(Pair(lstData, position))
            }
            item.options_answer?.let {
                adapterAnswer?.updateData(it)
                binding.rclOptionsAnswer.adapter = adapterAnswer
            }
        }

    }
}

class ViewHolder(var binding: ItemPageBinding) : RecyclerView.ViewHolder(binding.root)

