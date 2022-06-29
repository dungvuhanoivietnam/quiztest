package com.quiztest.quiztest.fragment;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.quiztest.quiztest.R;
import com.quiztest.quiztest.base.BaseFragment;
import com.quiztest.quiztest.custom.ExtTextView;

public class RankingFragment extends BaseFragment implements View.OnClickListener {

    public static final String TAG = RankingFragment.class.getSimpleName();

    private ExtTextView extWeeklyRanking, extOverallRating;
    private final int ButtonWeeklyRanking = 1;
    private final int ButtonOverallRating = 2;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_ranking;
    }

    @Override
    protected void initView(View v) {
        extWeeklyRanking = v.findViewById(R.id.ext_weekly_ranking);
        extOverallRating = v.findViewById(R.id.ext_overall_rating);
    }

    @Override
    protected void initData() {
        extWeeklyRanking.setOnClickListener(this);
        extOverallRating.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.ext_weekly_ranking) {
            setButtonOptionEnable(ButtonWeeklyRanking);
        }
        if (view.getId() == R.id.ext_overall_rating) {
            setButtonOptionEnable(ButtonOverallRating);
        }
    }

    private void setButtonOptionEnable(int btnEnable) {
        if (btnEnable == ButtonWeeklyRanking) {
            setButtonUI(extWeeklyRanking, true);
            setButtonUI(extOverallRating, false);
        } else if (btnEnable == ButtonOverallRating) {
            setButtonUI(extWeeklyRanking, false);
            setButtonUI(extOverallRating, true);
        }
    }

    private void setButtonUI(ExtTextView extTextView, boolean enable) {
        if (getActivity() != null) {
            if (enable) {
                extTextView.setBackgroundTintList(ContextCompat.getColorStateList(getActivity(), R.color.color_21C3FF));
                extTextView.setElevation(1f);
                extTextView.setTextColor(getResources().getColor(R.color.white));
            } else {
                extTextView.setBackgroundTintList(ContextCompat.getColorStateList(getActivity(), R.color.white));
                extTextView.setElevation(0f);
                extTextView.setTextColor(getResources().getColor(R.color.color_8D959D));
            }
        }
    }
}
