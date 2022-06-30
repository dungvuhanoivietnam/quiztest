package com.quiztest.quiztest.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;

import com.example.testiq.MainIQActivity;
import com.quiztest.quiztest.MainActivity;
import com.quiztest.quiztest.R;
import com.quiztest.quiztest.base.BaseFragment;
import com.quiztest.quiztest.custom.ExtTextView;
import com.quiztest.quiztest.retrofit.RequestAPI;


public class HomeFragment extends BaseFragment implements View.OnClickListener {

    public static final String TAG = HomeFragment.class.getSimpleName();
    private ExtTextView extLogin;
    private SearchView searchView;
    private RequestAPI requestAPI;

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
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    protected void initView(View v) {
        extLogin = v.findViewById(R.id.ext_login);
        searchView = v.findViewById(R.id.search_view);
    }


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initData() {
        extLogin.setOnClickListener(this);

        //check onCLick search view
        searchView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.ext_login) {
//            replaceFragment(new LoginFragment(), LoginFragment.class.getSimpleName());
//            if ((MainActivity) getActivity() != null) {
//                ((MainActivity) getActivity()).hideOrShowBottomView(false);
//            }
            Intent intent = new Intent(getActivity(), MainIQActivity.class);
            startActivity(intent);
        }
        if (view.getId() == R.id.search_view) {
            replaceFragment(new SearchFragment(), SearchFragment.class.getSimpleName());
            if (getActivity() != null) {
                ((MainActivity) getActivity()).hideOrShowBottomView(false);
            }
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        if (getActivity() != null) {
            ((MainActivity) getActivity()).hideOrShowBottomView(true);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
    }

}
