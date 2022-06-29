package com.quiztest.quiztest.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.quiztest.quiztest.R;
import com.quiztest.quiztest.base.BaseFragment;
import com.quiztest.quiztest.custom.ExtTextView;
import com.quiztest.quiztest.model.UserInfoResponse;
import com.quiztest.quiztest.utils.SharePrefrenceUtils;
import com.quiztest.quiztest.viewmodel.UserViewModel;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileFragment extends BaseFragment {

    private ExtTextView txt_title_create_acount, txt_content_create_account;
    private ImageView iv_profile, iv_create_account;
    private LinearLayout llLogin;

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
        v.findViewById(R.id.llLogin).setOnClickListener(v1 -> {
            replaceFragment(new LoginFragment(), LoginFragment.class.getSimpleName());
        });
        v.findViewById(R.id.ic_right).setOnClickListener(v1 -> {
            replaceFragment(new SettingFragment(), SettingFragment.class.getSimpleName());
        });
        txt_title_create_acount = v.findViewById(R.id.txt_title_create_acount);
        txt_content_create_account = v.findViewById(R.id.txt_content_create_account);
        iv_profile = v.findViewById(R.id.iv_profile);
        iv_create_account = v.findViewById(R.id.iv_create_account);
        llLogin = v.findViewById(R.id.llLogin);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_profile;
    }

    @Override
    protected void initData() {
        userViewModel = new ViewModelProvider(getActivity()).get(UserViewModel.class);
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
