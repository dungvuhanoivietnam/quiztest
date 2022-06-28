package com.quiztest.quiztest.fragment;

import android.view.View;
import android.widget.ImageView;

import com.quiztest.quiztest.R;
import com.quiztest.quiztest.base.BaseFragment;

public class SearchFragment extends BaseFragment {
    private ImageView ivBack;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_search;
    }

    @Override
    protected void initView(View v) {
        ivBack = v.findViewById(R.id.iv_back);
    }

    @Override
    protected void initData() {
        ivBack.setOnClickListener(v -> backstackFragment());
    }
}
