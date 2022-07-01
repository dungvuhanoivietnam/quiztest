package com.example.testiq.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.testiq.databinding.ItemCheckBottomSheetBinding
import com.example.testiq.databinding.ItemPageBinding
import com.example.testiq.model.QuestionModel


class AdapterSelectQuestion(
    var context: Context,
    var onClickItem: (QuestionModel) -> Unit,
) : RecyclerView.Adapter<AdapterSelectQuestion.ViewHolder>() {

    private var listLanguage = ArrayList<QuestionModel>()

    fun updateData(list: ArrayList<QuestionModel>) {
        listLanguage.clear()
        listLanguage.addAll(list)
        notifyDataSetChanged()
    }

    inner class ViewHolder(var binding: ItemCheckBottomSheetBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun getItemCount(): Int = listLanguage.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemBinding =
            ItemCheckBottomSheetBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(itemBinding)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val binding = holder.binding
        val item = listLanguage[position]
        binding.txt.text = "$position"

    }

}

