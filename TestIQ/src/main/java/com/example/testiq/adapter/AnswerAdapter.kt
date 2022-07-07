package com.example.testiq.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.testiq.R
import com.example.testiq.databinding.ItemAnswerImageBinding
import com.example.testiq.databinding.ItemAnswerTextBinding
import com.example.testiq.model.OptionsAnswer

class AnswerAdapter(
    var context: Context,
    var onclick: (Pair<Int, OptionsAnswer>) -> Unit,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        const val VIEW_TYPE_ONE = 0
        const val VIEW_TYPE_TWO = 1
    }

    private var lstAnswer = ArrayList<OptionsAnswer>()

    fun updateData(list: ArrayList<OptionsAnswer>) {
        lstAnswer.clear()
        lstAnswer.addAll(list)
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int): Int {
        if (lstAnswer[position].type == "TEXT")
            return 0
        return 1
    }


    override fun getItemCount(): Int = lstAnswer.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == VIEW_TYPE_ONE) {
            ViewHolder1(ItemAnswerTextBinding.inflate(LayoutInflater.from(context), parent, false))
        } else {
            ViewHolder2(ItemAnswerImageBinding.inflate(LayoutInflater.from(context), parent, false))
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun updateViewT(index: Int, optionsAnswer: OptionsAnswer) {
        lstAnswer.forEach { item ->
            if (item.id == optionsAnswer.id) {
                item.selected = !item.selected
            } else {
                item.selected = false
            }
        }

        notifyDataSetChanged()
    }

    @SuppressLint("SetTextI18n")
    private inner class ViewHolder1(var binding: ItemAnswerTextBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            if (lstAnswer.isNotEmpty()) {
                val item = lstAnswer[position]
                binding.tvAnswer.text = item.option_name

                if (item.selected) {
                    binding.background.setBackgroundColor(
                        ContextCompat.getColor(
                            context,
                            R.color.color_21C3FF
                        )
                    )
                    binding.tvAnswer.setTextColor(ContextCompat.getColor(context, R.color.white))
                } else {
                    binding.background.setBackgroundColor(Color.WHITE)
                    binding.tvAnswer.setTextColor(
                        ContextCompat.getColor(
                            context,
                            R.color.color_22313F
                        )
                    )
                }

                when (position) {
                    0 -> binding.tvAnswer.text = "A. ${item.option_name}"
                    1 -> binding.tvAnswer.text = "B. ${item.option_name}"
                    2 -> binding.tvAnswer.text = "C. ${item.option_name}"
                    3 -> binding.tvAnswer.text = "D. ${item.option_name}"
                }
                binding.root.setOnClickListener {
                    onclick.invoke(Pair(position, item))
                    updateViewT(position, item)
                }
            }
        }
    }

    private inner class ViewHolder2(var binding: ItemAnswerImageBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            if (lstAnswer.isNotEmpty()) {
                val item = lstAnswer[position]

                if (item.selected) {
                    binding.background.setBackgroundColor(
                        ContextCompat.getColor(
                            context,
                            R.color.color_21C3FF
                        )
                    )
                    binding.txt.setTextColor(ContextCompat.getColor(context, R.color.white))
                } else {
                    binding.background.setBackgroundColor(Color.WHITE)
                    binding.txt.setTextColor(
                        ContextCompat.getColor(
                            context,
                            R.color.color_22313F
                        )
                    )
                }

                // load image
                item.image?.let {
                    Glide.with(context).load(item.image ?: "").into(binding.image)
                }

                binding.txt.text = convertPosition(position)

                binding.root.setOnClickListener {
                    onclick.invoke(Pair(position, item))
                    updateViewT(position, item)
                }
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (lstAnswer[position].type == "TEXT") {
            (holder as ViewHolder1).bind(position)
        } else {
            (holder as ViewHolder2).bind(position)
        }
    }

    fun convertPosition(position : Int) : String{
        return when (position) {
            0 -> "A. "
            1 -> "B. "
            2 -> "C. "
            3 -> "D. "
            4 -> "E. "
            5 -> "F. "
            6 -> "F. "
            else -> ""
        }
    }

}


