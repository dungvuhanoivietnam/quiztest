package com.quiztest.quiztest.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.quiztest.quiztest.R;
import com.quiztest.quiztest.base.BaseFragment;

public class ProfileFragment extends BaseFragment {

    private LinearLayout llLogin;

    public static final String TAG = ProfileFragment.class.getSimpleName();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    protected void initView(View v) {
        llLogin = v.findViewById(R.id.llLogin);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_profile;
    }

    @Override
    protected void initView() {
    }



    @Override
    protected void initData() {

    }
}
