package com.quiztest.quiztest.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.quiztest.quiztest.R;
import com.quiztest.quiztest.base.BaseFragment;

public class RankingFragment extends BaseFragment {

    public static final String TAG = RankingFragment.class.getSimpleName();

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

    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }
}
