package com.quiztest.quiztest.fragment;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.testiq.ui.DialogResultCallApi;
import com.example.testiq.utils.Status;
import com.quiztest.quiztest.R;
import com.quiztest.quiztest.adapter.HistoryAdapter;
import com.quiztest.quiztest.adapter.LanguageAdapter;
import com.quiztest.quiztest.base.BaseFragment;
import com.quiztest.quiztest.custom.ExtTextView;
import com.quiztest.quiztest.dialog.DialogWithdrawMoney;
import com.quiztest.quiztest.fragment.login.LoginFragment;
import com.quiztest.quiztest.model.ChangeLanguageResponse;
import com.quiztest.quiztest.model.UserInfoResponse;
import com.quiztest.quiztest.model.WithDrawResponse;
import com.quiztest.quiztest.utils.LanguageConfig;
import com.quiztest.quiztest.utils.SharePrefrenceUtils;
import com.quiztest.quiztest.viewmodel.UserViewModel;

import java.util.Objects;

public class ProfileFragment extends BaseFragment implements View.OnClickListener, DialogWithdrawMoney.OnWithDrawClickLisenter {

    private ExtTextView txt_title_create_acount, txt_content_create_account, txtTitle;
    private ImageView iv_create_account;
    private LinearLayout llInActiveUser, llActiveUser;
    private ExtTextView starCount, moneyCount, tvWarning;
    private Spinner sp_language;
    private LinearLayout llHistory, llShare, llFeedback, llPrivacy, llLogin, llInfoUser;
    private ExtTextView btnWithDrawMoney, tvProfile;

    private UserViewModel userViewModel;

    public static final String TAG = ProfileFragment.class.getSimpleName();
    String[] countryNames = {"Tiếng Việt", "English"};
    String[] countryFlag = {"https://manager-apps.merryblue.llc/storage/flags/Vietnam.png", "https://upload.wikimedia.org/wikipedia/commons/thumb/a/ae/Flag_of_the_United_Kingdom.svg/1920px-Flag_of_the_United_Kingdom.svg.png"};


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
        v.findViewById(R.id.ic_right).setVisibility(View.GONE);
        sp_language = v.findViewById(R.id.sp_language);
        txtTitle = v.findViewById(R.id.txt_title);
        txt_title_create_acount = v.findViewById(R.id.txt_title_create_acount);
        llActiveUser = v.findViewById(R.id.llActiveUser);
        llInfoUser = v.findViewById(R.id.llInfoUser);
        llLogin = v.findViewById(R.id.ll_login);
        tvProfile = v.findViewById(R.id.tvProfile);
        llHistory = v.findViewById(R.id.llHistory);
        llShare = v.findViewById(R.id.ll_share);
        llFeedback = v.findViewById(R.id.ll_feed_back);
        llPrivacy = v.findViewById(R.id.ll_policy);
        btnWithDrawMoney = v.findViewById(R.id.btnWithDrawMoney);
        llInActiveUser = v.findViewById(R.id.llInActiveUser);
        starCount = v.findViewById(R.id.ext_star_count);
        moneyCount = v.findViewById(R.id.ext_money_count);
        tvWarning = v.findViewById(R.id.tvWarning);
        txt_content_create_account = v.findViewById(R.id.txt_content_create_account);
        iv_create_account = v.findViewById(R.id.iv_create_account);
        llHistory.setOnClickListener(this);
        llLogin.setOnClickListener(this);
        llShare.setOnClickListener(this);
        llFeedback.setOnClickListener(this);
        llPrivacy.setOnClickListener(this);
        llInfoUser.setOnClickListener(this);
        btnWithDrawMoney.setOnClickListener(this);
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

        String auth = SharePrefrenceUtils.getInstance(getContext()).getAuth();
        if (auth != null && !"".equals(auth) && TextUtils.isEmpty(userViewModel.getUserInfoResponse().getEmail())) {
            showLoading();
            userViewModel.getUserInfo(requestAPI, o -> {
                cancelLoading();
                if (!TextUtils.isEmpty(userViewModel.getUserInfoResponse().getAvatar())) {
                    llActiveUser.setVisibility(View.VISIBLE);
                    llInActiveUser.setVisibility(View.GONE);
                    btnWithDrawMoney.setVisibility(View.VISIBLE);
                    tvWarning.setVisibility(View.GONE);
                    updateView();
                } else {
                    llInActiveUser.setVisibility(View.VISIBLE);
                    llActiveUser.setVisibility(View.GONE);
                    btnWithDrawMoney.setVisibility(View.GONE);
                    tvWarning.setVisibility(View.VISIBLE);
                }
            });
        } else if (!TextUtils.isEmpty(userViewModel.getUserInfoResponse().getEmail())) {
            tvWarning.setVisibility(View.GONE);
            btnWithDrawMoney.setVisibility(View.VISIBLE);
            llActiveUser.setVisibility(View.VISIBLE);
            llInActiveUser.setVisibility(View.GONE);
            updateView();
        } else {
            tvWarning.setVisibility(View.VISIBLE);
            btnWithDrawMoney.setVisibility(View.GONE);
            UserInfoResponse.UserInfo userInfo = userViewModel.getUserInfoResponse();
            if (userInfo != null) {
                int total_money = userInfo.getTotalMoney();
                int total_star = userInfo.getTotalStar();
                starCount.setText(getString(R.string.value_, total_star));
                moneyCount.setText(getString(R.string.value_, total_money));
            }

            llActiveUser.setVisibility(View.GONE);
            llInActiveUser.setVisibility(View.VISIBLE);
        }

        if (auth != null && !"".equals(auth) && userViewModel.getHistories().size() == 0) {
            userViewModel.getHistory(requestAPI, 1, o -> {
                cancelLoading();
            });
        } else if (!TextUtils.isEmpty(userViewModel.getUserInfoResponse().getEmail())) {
            updateView();
        }

        sp_language.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                callApiChangeLanguage(countryNames[position]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        LanguageAdapter customAdapter = new LanguageAdapter(getContext(), countryFlag, countryNames);
        sp_language.setAdapter(customAdapter);
        String currentLanguage = LanguageConfig.INSTANCE.getCurrentLanguage();
        for (int i = 0; i < countryNames.length; i++) {
            if (Objects.equals(countryNames[i], currentLanguage)) {
                sp_language.setSelection(i);
                break;
            }
        }
    }

    private void updateView() {
        UserInfoResponse.UserInfo userInfo = userViewModel.getUserInfoResponse();
        txt_title_create_acount.setText(userInfo.getName() != null ? userInfo.getName() : getString(R.string.create_account));
        txt_content_create_account.setText(userInfo.getEmail() != null ? userInfo.getEmail() : getString(R.string.create_an_account_and_take_the_quiz));
        iv_create_account.setVisibility(userInfo.getEmail() == null ? View.VISIBLE : View.GONE);

        int total_money = userInfo.getTotalMoney();
        int total_star = userInfo.getTotalStar();

        starCount.setText(getString(R.string.value_, total_star));
        moneyCount.setText(getString(R.string.value_, total_money));

        tvProfile.setText(userInfo.getName().substring(0, 1));

    }

    private void callApiChangeLanguage(String language) {
        showLoading();
        userViewModel.changeLanguage(requestAPI, language, o -> {
            if (o instanceof ChangeLanguageResponse) {
                boolean isChangeSuccess = ((ChangeLanguageResponse) o).getSuccess();
                if (isChangeSuccess) {
                    LanguageConfig.INSTANCE.changeLanguage(getActivity(), language);
                    LanguageConfig.INSTANCE.setCurrentLanguage(language);
                }
            }
            cancelLoading();
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.llHistory:
                replaceFragment(new HistoryFragment(), HistoryFragment.class.getSimpleName());
                break;

            case R.id.ll_share:
                Intent shareIntent = new Intent("android.intent.action.SEND");
                shareIntent.setType("text/plain");
                shareIntent.putExtra("android.intent.extra.SUBJECT", "Now " + getString(R.string.app_name) + " Available on Google Playstore please download it on share it");
                shareIntent.putExtra("android.intent.extra.TEXT", " - https://play.google.com/store/apps/details?id=" + getContext().getPackageName() + " \n\n");
                startActivity(Intent.createChooser(shareIntent, "Share Via"));
                break;

            case R.id.ll_feed_back:
                final String appPackageName = getContext().getPackageName();
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse("market://details?id=" + appPackageName)));
                } catch (ActivityNotFoundException anfe) {
                    startActivity(new Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse("https://play.google.com/store/apps/details?id="
                                    + appPackageName)));
                }
                break;

            case R.id.ll_policy:
                replaceFragment(new PrivacyFragment(), PrivacyFragment.class.getSimpleName());
                break;

            case R.id.ll_login:
                replaceFragment(new LoginFragment(), LoginFragment.class.getSimpleName());
                break;

            case R.id.llInfoUser:
                replaceFragment(new SettingFragment(), SettingFragment.class.getSimpleName());
                break;

            case R.id.btnWithDrawMoney:
                UserInfoResponse.UserInfo userInfo = userViewModel.getUserInfoResponse();
                if (userInfo != null) {
                    DialogWithdrawMoney dialogWithdrawMoney = new DialogWithdrawMoney(getContext(), R.style.MaterialDialogSheet, this);
                    dialogWithdrawMoney.setTotalMoney(userInfo.getTotalMoney());
                    dialogWithdrawMoney.show();
                }
                break;
        }
    }

    @Override
    public void onWidthDrawClick(String email, int money) {

        if (userViewModel != null) {
            showLoading();
            userViewModel.requestWithDraw(requestAPI, email, money, o -> {
                if (o instanceof WithDrawResponse) {
                    if (getContext() != null) {
                        if (Boolean.TRUE.equals(((WithDrawResponse) o).getSuccess())) {
                            new DialogResultCallApi(getContext(), Status.SUCCESS, ((WithDrawResponse) o).getMessage(), () -> null).show();
                        } else {
                            DialogResultCallApi dialogResultCallApi = new DialogResultCallApi(getContext(), Status.ERROR,
                                    ((WithDrawResponse) o).getMessage(), () -> null);
                            dialogResultCallApi.setTextAndIcon(getString(R.string.minimum_money), R.drawable.ic_error);
                        }
                    }
                } else {
                    if (getContext() != null) {
                        DialogResultCallApi dialogResultCallApi = new DialogResultCallApi(getContext(), Status.ERROR,
                                getString(R.string.error_message_default), () -> null);
                        dialogResultCallApi.setTextAndIcon(getString(R.string.error), R.drawable.ic_error);
                        dialogResultCallApi.show();
                    }
                }
                cancelLoading();
            });
        }
    }
}
