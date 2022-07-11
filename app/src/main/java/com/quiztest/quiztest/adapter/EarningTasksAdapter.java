package com.quiztest.quiztest.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.quiztest.quiztest.R;
import com.quiztest.quiztest.custom.ExtTextView;
import com.quiztest.quiztest.model.TestItem;

import java.util.ArrayList;

public class EarningTasksAdapter extends RecyclerView.Adapter<EarningTasksAdapter.EarningTaskViewHolder> {
    private Context context;
    private ArrayList<TestItem> listData;
    private ItemClickListener itemClickListener;

    public EarningTasksAdapter(Context context) {
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
    public EarningTasksAdapter.EarningTaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_earning_task, parent, false);
        return new EarningTasksAdapter.EarningTaskViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull EarningTasksAdapter.EarningTaskViewHolder holder, int position) {
        TestItem item = listData.get(position);
        holder.bindData(item);
        switch (position) {
            case 0:
                holder.cardView.setCardBackgroundColor(context.getResources().getColor(R.color.color_FBE8E7));
                break;
            case 1:
                holder.cardView.setCardBackgroundColor(context.getResources().getColor(R.color.color_ECE8FD));
                break;
            case 2:
                holder.cardView.setCardBackgroundColor(context.getResources().getColor(R.color.color_DEF5E8));
                break;
            case 3:
                holder.cardView.setCardBackgroundColor(context.getResources().getColor(R.color.color_FEF8EA));
                break;
        }
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

    public class EarningTaskViewHolder extends RecyclerView.ViewHolder {
        private ExtTextView extMoneyGet, extStarPay, extTitle;
        private ImageView imgItem;
        private CardView cardView;

        public EarningTaskViewHolder(@NonNull View itemView) {
            super(itemView);
            extMoneyGet = itemView.findViewById(R.id.ext_money_get);
            extStarPay = itemView.findViewById(R.id.ext_star_pay);
            extTitle = itemView.findViewById(R.id.ext_title);
            imgItem = itemView.findViewById(R.id.img_item);
            cardView = itemView.findViewById(R.id.card_view);

        }

        public void bindData(TestItem item) {
            extTitle.setText(item.getTitle());
            extMoneyGet.setText(String.format(context.getString(R.string.money_get), item.getMoneyBonus()));
            extStarPay.setText(String.format(context.getString(R.string.star_pay), item.getFeeStar()));
            Glide.with(context).load(item.getImage()).into(imgItem);
        }
    }

    public interface ItemClickListener {
        void onItemClickListener(int position);

        void onItemLongClickListener(int position);
    }
}
