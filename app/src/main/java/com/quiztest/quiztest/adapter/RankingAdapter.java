package com.quiztest.quiztest.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.quiztest.quiztest.R;
import com.quiztest.quiztest.custom.ExtTextView;
import com.quiztest.quiztest.model.RankerResponse;
import com.quiztest.quiztest.utils.StringUtils;

import java.util.ArrayList;

public class RankingAdapter extends RecyclerView.Adapter<RankingAdapter.RankingViewHolder> {
    private Context context;
    private ArrayList<RankerResponse> listData;
    private ItemClickListener itemClickListener;

    public RankingAdapter(Context context) {
        this.context = context;
    }

    public void setListData(ArrayList<RankerResponse> listData) {
        this.listData = listData;
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public RankingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_list_ranking, parent, false);
        return new RankingViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RankingViewHolder holder, int position) {
        RankerResponse item = listData.get(position);
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

    public class RankingViewHolder extends RecyclerView.ViewHolder {
        private ExtTextView extName, extScore;

        public RankingViewHolder(@NonNull View itemView) {
            super(itemView);
            extName = itemView.findViewById(R.id.ext_name);
            extScore = itemView.findViewById(R.id.ext_score);
        }

        public void bindData(RankerResponse item) {
            extName.setText(item.getName());
            extScore.setText(context.getResources().getString(R.string.score, item.getScore()));
        }
    }

    public interface ItemClickListener {
        void onItemClickListener(int position);

        void onItemLongClickListener(int position);
    }
}
