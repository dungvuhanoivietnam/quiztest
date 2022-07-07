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

public class GetMoreStarsAdapter extends RecyclerView.Adapter<GetMoreStarsAdapter.GetMoreStarsViewHolder> {
    private Context context;
    private ArrayList<TestItem> listData;
    private RankingAdapter.ItemClickListener itemClickListener;

    public GetMoreStarsAdapter(Context context) {
        this.context = context;
    }

    public void setListData(ArrayList<TestItem> listData) {
        this.listData = listData;
    }

    public void setItemClickListener(RankingAdapter.ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public GetMoreStarsAdapter.GetMoreStarsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_all_quiz_new, parent, false);
        return new GetMoreStarsAdapter.GetMoreStarsViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull GetMoreStarsAdapter.GetMoreStarsViewHolder holder, int position) {
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

    public class GetMoreStarsViewHolder extends RecyclerView.ViewHolder {
        private ExtTextView txtTitle, starCanGet;
        private ImageView ivItem;

        public GetMoreStarsViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTitle = itemView.findViewById(R.id.txt_title);
            ivItem = itemView.findViewById(R.id.iv_item);
            starCanGet = itemView.findViewById(R.id.star_can_get);

        }

        public void bindData(TestItem item) {
            txtTitle.setText(item.getTitle());
            starCanGet.setText(String.format(context.getString(R.string.get_s_star),item.getStarBonus()));
            Glide.with(context).load(item.getImage()).into(ivItem);
        }
    }

    public interface ItemClickListener {
        void onItemClickListener(int position);

        void onItemLongClickListener(int position);
    }
}
