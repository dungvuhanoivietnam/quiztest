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

class AdapterSelectQuestion(var context: Context) : RecyclerView.Adapter<ViewHolderCheckBox>() {

    private var questions: ArrayList<QuestionModel> = ArrayList()

    @SuppressLint("NotifyDataSetChanged")
    fun setData(lstData : ArrayList<QuestionModel>){
        if (questions.isNotEmpty()){
            questions.clear()
            questions.addAll(lstData)
        }
        notifyDataSetChanged()
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderCheckBox {
        val itemBinding =
            ItemCheckBottomSheetBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolderCheckBox(itemBinding)
    }

    override fun getItemCount(): Int {
       return questions.size
    }

    override fun onBindViewHolder(holder: ViewHolderCheckBox, position: Int) {
        val binding = holder.binding
        val item = questions[position]
        binding.txt.text = "$position"
    }
}

class ViewHolderCheckBox(var binding: ItemCheckBottomSheetBinding) : RecyclerView.ViewHolder(binding.root)