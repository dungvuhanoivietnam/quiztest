package com.quiztest.quiztest.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.quiztest.quiztest.R;
import com.quiztest.quiztest.base.BaseFragment;

public class ProfileFragment extends BaseFragment {


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
        v.findViewById(R.id.llLogin).setOnClickListener(v1 -> {
            replaceFragment(new LoginFragment(), LoginFragment.class.getSimpleName());
        });
        v.findViewById(R.id.ic_right).setOnClickListener(v1 -> {
            replaceFragment(new SettingFragment(), SettingFragment.class.getSimpleName());
        });
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
