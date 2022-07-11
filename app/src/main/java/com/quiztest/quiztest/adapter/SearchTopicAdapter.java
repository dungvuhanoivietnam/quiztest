package com.quiztest.quiztest.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.quiztest.quiztest.R;
import com.quiztest.quiztest.custom.ExtTextView;
import com.quiztest.quiztest.model.TestItem;

import java.util.ArrayList;

public class SearchTopicAdapter extends RecyclerView.Adapter<SearchTopicAdapter.SearchTopicViewHolder> {
    private Context context;
    private ArrayList<TestItem> listData;
    private ItemClickListener itemClickListener;

    public SearchTopicAdapter(Context context) {
        this.context = context;
    }

    public void setListData(ArrayList<TestItem> listData) {
        this.listData = listData;
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public SearchTopicViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_search_result, parent, false);
        return new SearchTopicViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchTopicViewHolder holder, int position) {
        TestItem item = listData.get(position);
        holder.bindData(item);

        if (itemClickListener != null) {
            holder.itemView.setOnClickListener(view -> {
                itemClickListener.onItemClickListener(position);
            });

            holder.itemView.setOnLongClickListener(view -> {
                itemClickListener.onItemLongClickListener(position);
                return false;
            });
        }
    }

    @Override
    public int getItemCount() {
        return listData == null ? 0 : listData.size();
    }

    public class SearchTopicViewHolder extends RecyclerView.ViewHolder {
        private ExtTextView extTitle, totalQuestion;
        private ImageView imgItem;

        public SearchTopicViewHolder(@NonNull View itemView) {
            super(itemView);
            extTitle = itemView.findViewById(R.id.txt_title);
            totalQuestion = itemView.findViewById(R.id.ext_total_question);
            imgItem = itemView.findViewById(R.id.iv_item);
        }

        public void bindData(TestItem item) {
            extTitle.setText(item.getTitle());
            totalQuestion.setText(item.getTotalQuestionCount());
            Glide.with(context).load(item.getImage()).into(imgItem);
        }
    }

    public interface ItemClickListener {
        void onItemClickListener(int position);

        void onItemLongClickListener(int position);
    }
}
