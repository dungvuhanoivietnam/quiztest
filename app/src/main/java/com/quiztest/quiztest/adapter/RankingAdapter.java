package com.quiztest.quiztest.adapter;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.quiztest.quiztest.R;
import com.quiztest.quiztest.custom.ExtTextView;
import com.quiztest.quiztest.model.RankerResponse;
import com.quiztest.quiztest.model.UserRankingResponse;
import com.quiztest.quiztest.utils.StringUtils;

import java.util.ArrayList;

public class RankingAdapter extends RecyclerView.Adapter<RankingAdapter.RankingViewHolder> {
    private Context context;
    private ArrayList<UserRankingResponse.UserRanking> listData;
    private ItemClickListener itemClickListener;

    public RankingAdapter(Context context) {
        this.context = context;
    }

    public void setListData(ArrayList<UserRankingResponse.UserRanking> listData) {
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
        UserRankingResponse.UserRanking item = listData.get(position);
        holder.bindData(item);
        if (position > 2) {
            holder.rlContent.setBackgroundColor(Color.WHITE);
            holder.tvStt.setText(context.getString(R.string.value_, position + 1));
            holder.tvStt.setVisibility(View.VISIBLE);
            holder.icRank.setVisibility(View.INVISIBLE);
        } else {
            holder.tvStt.setVisibility(View.INVISIBLE);
            holder.icRank.setVisibility(View.VISIBLE);
        }

        switch (position) {
            case 0:
                holder.icRank.setImageDrawable(AppCompatResources.getDrawable(context, R.drawable.ic_rank_gold));
                break;
            case 1:
                holder.icRank.setImageDrawable(AppCompatResources.getDrawable(context, R.drawable.ic_rank_silver));
                break;
            case 2:
                holder.icRank.setImageDrawable(AppCompatResources.getDrawable(context, R.drawable.ic_rank_bronze));
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

    public class RankingViewHolder extends RecyclerView.ViewHolder {
        private ExtTextView extName, extScore, tvStt;
        private RelativeLayout rlContent;
        private ImageView ivAvatar, icRank;

        public RankingViewHolder(@NonNull View itemView) {
            super(itemView);
            extName = itemView.findViewById(R.id.ext_name);
            extScore = itemView.findViewById(R.id.ext_score);
            ivAvatar = itemView.findViewById(R.id.imv_avatar);
            icRank = itemView.findViewById(R.id.ic_rank);
            rlContent = itemView.findViewById(R.id.rl_content);
            tvStt = itemView.findViewById(R.id.tv_stt);
        }

        public void bindData(UserRankingResponse.UserRanking item) {
            extName.setText(item.getName());
            extScore.setText(context.getResources().getString(R.string.score, item.getTotal_bonus()));
            Glide.with(context).load(item.getAvatar()).circleCrop().into(ivAvatar);
        }
    }

    public interface ItemClickListener {
        void onItemClickListener(int position);

        void onItemLongClickListener(int position);
    }
}
