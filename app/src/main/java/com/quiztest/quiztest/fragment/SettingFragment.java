package com.quiztest.quiztest.fragment;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.quiztest.quiztest.MainActivity;
import com.quiztest.quiztest.R;
import com.quiztest.quiztest.base.BaseFragment;
import com.quiztest.quiztest.custom.ExtEditText;
import com.quiztest.quiztest.custom.ExtTextView;
import com.quiztest.quiztest.dialog.DialogChooseImage;
import com.quiztest.quiztest.model.UserInfoResponse;
import com.quiztest.quiztest.viewmodel.UserViewModel;

public class SettingFragment extends BaseFragment {

    private ExtTextView txt_title, txt_content_create_account, txt_link_google, txt_link_facebook;
    private ImageView ivAvatar, ivUpload;
    private UserViewModel userViewModel;
    private UserInfoResponse.UserInfo userInfo;
    private LinearLayout ll_setting_content, ll_login, ll_logout;
    private ExtEditText edtName, edtGmail;
    private DialogChooseImage dialogChooseImage;

    private boolean isLogin = false;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_setting;
    }

    @Override
    protected void initView(View v) {
        v.findViewById(R.id.txt_Left).setVisibility(View.GONE);
        v.findViewById(R.id.ic_right).setVisibility(View.GONE);
        v.findViewById(R.id.iv_back).setVisibility(View.VISIBLE);
        v.findViewById(R.id.iv_back).setOnClickListener(view -> backstackFragment());
        txt_title = v.findViewById(R.id.txt_title);
        ivAvatar = v.findViewById(R.id.ivAvatar);
        ivUpload = v.findViewById(R.id.ivUpload);
        ll_setting_content = v.findViewById(R.id.ll_setting_content);
        edtName = v.findViewById(R.id.edtName);
        edtGmail = v.findViewById(R.id.edtGmail);
        txt_content_create_account = v.findViewById(R.id.txt_content_create_account);
        ll_login = v.findViewById(R.id.ll_login);
        txt_link_google = v.findViewById(R.id.txt_link_google);
        txt_link_facebook = v.findViewById(R.id.txt_link_facebook);
        ll_logout = v.findViewById(R.id.ll_logout);

        dialogChooseImage = new DialogChooseImage(getContext(), R.style.MaterialDialogSheet,type_select -> {

        });

        v.findViewById(R.id.txt_change_info).setOnClickListener(view -> {

        });
        v.findViewById(R.id.ivUpload).setOnClickListener(view -> {
            if (!dialogChooseImage.isShowing())
                dialogChooseImage.show();
        });

        ll_login.setOnClickListener(view -> replaceFragment(new LoginFragment(), LoginFragment.class.getSimpleName()));
        ll_logout.setOnClickListener(view -> {
            backstackFragment();
            if (getActivity() instanceof MainActivity) {
                MainActivity activity = (MainActivity) getActivity();
                activity.actionLogout();
            }
        });
    }

    @Override
    protected void initData() {
        userViewModel = new ViewModelProvider(getActivity()).get(UserViewModel.class);
        txt_title.setText(getString(R.string.setting));
        txt_title.setVisibility(View.VISIBLE);
        userInfo = userViewModel.getUserInfoResponse();
        isLogin = userInfo.getEmail() != null;
        Glide.with(mContext).load(userInfo.getAvatar()).circleCrop().placeholder(R.drawable.ic_create_account_profile).into(ivAvatar);
        ivUpload.setVisibility(isLogin ? View.VISIBLE : View.GONE);
        ll_setting_content.setVisibility(isLogin ? View.VISIBLE : View.GONE);
        edtName.setText(userInfo.getName() != null ? userInfo.getName() : "");
        edtGmail.setText(userInfo.getEmail() != null ? userInfo.getEmail() : "");
        txt_content_create_account.setVisibility(isLogin ? View.GONE : View.VISIBLE);

        txt_link_google.setTextColor(getContext().getResources().getColor(userInfo.getGoogleProviderId() == null ? R.color.color_B8BDC2 : R.color.color_21C3FF));
        txt_link_facebook.setTextColor(getContext().getResources().getColor(userInfo.getFacebookProviderId() == null ? R.color.color_B8BDC2 : R.color.color_21C3FF));
        ll_login.setVisibility(isLogin ? View.GONE : View.VISIBLE);
        ll_logout.setVisibility(isLogin ? View.VISIBLE : View.GONE);
    }
}
