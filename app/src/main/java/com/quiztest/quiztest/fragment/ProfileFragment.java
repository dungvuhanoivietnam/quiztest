package com.quiztest.quiztest.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.quiztest.quiztest.R;
import com.quiztest.quiztest.adapter.HistoryAdapter;
import com.quiztest.quiztest.base.BaseFragment;
import com.quiztest.quiztest.custom.ExtTextView;
import com.quiztest.quiztest.fragment.login.LoginFragment;
import com.quiztest.quiztest.model.UserInfoResponse;
import com.quiztest.quiztest.utils.SharePrefrenceUtils;
import com.quiztest.quiztest.viewmodel.UserViewModel;

public class ProfileFragment extends BaseFragment {

    private ExtTextView txt_title_create_acount, txt_content_create_account, txtTitle;
    private ImageView iv_profile, iv_create_account;
    private LinearLayout llLogin;
    private RecyclerView rcv_history;
    private HistoryAdapter historyAdapter;


    private UserViewModel userViewModel;

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
        v.findViewById(R.id.txt_Left).setVisibility(View.GONE);
        txtTitle = v.findViewById(R.id.txt_title);
        v.findViewById(R.id.llLogin).setOnClickListener(v1 -> replaceFragment(new LoginFragment(), LoginFragment.class.getSimpleName()));
        v.findViewById(R.id.ic_right).setOnClickListener(v1 -> replaceFragment(new SettingFragment(), SettingFragment.class.getSimpleName()));
        v.findViewById(R.id.txt_view_all).setOnClickListener(view -> replaceFragment(new HistoryFragment(), HistoryFragment.class.getSimpleName()));
        txt_title_create_acount = v.findViewById(R.id.txt_title_create_acount);
        txt_content_create_account = v.findViewById(R.id.txt_content_create_account);
        iv_profile = v.findViewById(R.id.iv_profile);
        iv_create_account = v.findViewById(R.id.iv_create_account);
        llLogin = v.findViewById(R.id.llLogin);
        rcv_history = v.findViewById(R.id.rcv_history);
        rcv_history.setLayoutManager(new LinearLayoutManager(mContext));
        historyAdapter = new HistoryAdapter(mContext, false);
        rcv_history.setAdapter(historyAdapter);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_profile;
    }

    @Override
    protected void initData() {
        txtTitle.setVisibility(View.VISIBLE);
        txtTitle.setText(getString(R.string.profile));
        userViewModel = new ViewModelProvider(getActivity()).get(UserViewModel.class);
        rcv_history.setVisibility(userViewModel.getHistories().size() > 0 ? View.VISIBLE : View.GONE);

        String auth = SharePrefrenceUtils.getInstance(getContext()).getAuth();
        if (auth != null && !"".equals(auth) && TextUtils.isEmpty(userViewModel.getUserInfoResponse().getEmail())) {
            showLoading();
            userViewModel.getUserInfo(requestAPI, o -> {
                cancelLoading();
                if (!TextUtils.isEmpty(userViewModel.getUserInfoResponse().getAvatar())) {
                    updateView();
                } else {

                }
            });
        } else if (!TextUtils.isEmpty(userViewModel.getUserInfoResponse().getEmail())) {
            updateView();
        }

        if (auth != null && !"".equals(auth) && userViewModel.getHistories().size() == 0) {
            userViewModel.getHistory(requestAPI, 1, o -> {
                cancelLoading();
                rcv_history.setVisibility(userViewModel.getHistories().size() > 0 ? View.VISIBLE : View.GONE);
                historyAdapter.setData(userViewModel.getHistories());
            });
        } else if (!TextUtils.isEmpty(userViewModel.getUserInfoResponse().getEmail())) {
            updateView();
        }

        if (userViewModel.getHistories().size() > 0) {
            rcv_history.setVisibility(userViewModel.getHistories().size() > 0 ? View.VISIBLE : View.GONE);
            historyAdapter.setData(userViewModel.getHistories());
        }
    }

    private void updateView() {
        UserInfoResponse.UserInfo userInfo = userViewModel.getUserInfoResponse();
        txt_title_create_acount.setText(userInfo.getName() != null ? userInfo.getName() : getString(R.string.create_account));
        Glide.with(mContext).load(userInfo.getAvatar() != null ? userInfo.getAvatar() : R.drawable.ic_create_account_profile).placeholder(R.drawable.ic_create_account_profile).circleCrop().into(iv_profile);
        txt_content_create_account.setText(userInfo.getEmail() != null ? userInfo.getEmail() : getString(R.string.create_an_account_and_take_the_quiz));
        llLogin.setVisibility(userInfo.getEmail() == null ? View.VISIBLE : View.GONE);
        iv_create_account.setVisibility(userInfo.getEmail() == null ? View.VISIBLE : View.GONE);
    }

}
