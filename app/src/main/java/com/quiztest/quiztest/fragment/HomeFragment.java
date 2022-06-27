package com.quiztest.quiztest.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.quiztest.quiztest.MainActivity;
import com.quiztest.quiztest.R;
import com.quiztest.quiztest.base.BaseFragment;
import com.quiztest.quiztest.custom.ExtTextView;

public class HomeFragment extends BaseFragment implements View.OnClickListener {

    public static final String TAG = HomeFragment.class.getSimpleName();
    private ExtTextView extLogin;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initView() {
        extLogin = getActivity().findViewById(R.id.ext_login);
    }

    @Override
    protected void initData() {
        extLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.ext_login) {
            replaceFragment(new LoginFragment(), LoginFragment.class.getSimpleName());
            if ((MainActivity) getActivity() != null) {
                ((MainActivity) getActivity()).hideOrShowBottomView(false);
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if ((MainActivity) getActivity() != null) {
            ((MainActivity) getActivity()).hideOrShowBottomView(true);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
    }
}
