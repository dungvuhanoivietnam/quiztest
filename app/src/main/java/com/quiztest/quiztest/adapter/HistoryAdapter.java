package com.quiztest.quiztest.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.quiztest.quiztest.R;
import com.quiztest.quiztest.custom.ExtTextView;
import com.quiztest.quiztest.model.HistoryResponse;
import com.quiztest.quiztest.utils.FormatDateUtils;

import java.util.ArrayList;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {

    private ArrayList<HistoryResponse.History> histories = new ArrayList<>();
    private Context context;
    private boolean isShowAll;

    public HistoryAdapter(Context context, boolean isShowAll) {
        this.context = context;
        this.isShowAll = isShowAll;
    }

    public void setData(ArrayList<HistoryResponse.History> histories) {
        this.histories = histories;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_history_result, parent, false);
        return new HistoryAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        HistoryResponse.History history = histories.get(position);
        if (history == null)
            return;
        holder.txt_date.setText(history.getCreatedTime() != null ? FormatDateUtils.formatDateSaver(history.getCreatedTime()) : "");
        holder.txt_name.setText(history.getTopicName() != null ? history.getTopicName() : "");
        holder.txt_result.setText(history.getAnswerCorrect() + "/" + history.getTotalQuestion());

    }

    @Override
    public int getItemCount() {
        return histories != null ? (isShowAll ? histories.size() : Math.min(histories.size(), 3)) : 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        ExtTextView txt_date, txt_name, txt_result;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_date = itemView.findViewById(R.id.txt_date);
            txt_name = itemView.findViewById(R.id.txt_name);
            txt_result = itemView.findViewById(R.id.txt_result);

        }
    }

}
