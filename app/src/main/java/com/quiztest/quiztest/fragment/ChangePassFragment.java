package com.quiztest.quiztest.fragment;

import android.text.InputType;
import android.view.View;

import androidx.lifecycle.ViewModelProvider;

import com.quiztest.quiztest.MainActivity;
import com.quiztest.quiztest.R;
import com.quiztest.quiztest.base.BaseFragment;
import com.quiztest.quiztest.custom.ExtEditTextApp;
import com.quiztest.quiztest.custom.ExtTextView;
import com.quiztest.quiztest.fragment.forgotPass.VerifyMailFragment;
import com.quiztest.quiztest.model.ChangePassResponse;
import com.quiztest.quiztest.utils.SharePrefrenceUtils;
import com.quiztest.quiztest.viewmodel.UserViewModel;

public class ChangePassFragment extends BaseFragment {

    private ExtEditTextApp edt_current_pass, edt_new_pass, edt_re_pass;
    private ExtTextView btn_save;
    private boolean isPass, isRepass;
    private UserViewModel userViewModel;
    private ExtTextView tvResetPass;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_change_password;
    }

    @Override
    protected void initView(View v) {
        v.findViewById(R.id.iv_back).setOnClickListener(v1 -> backstackFragment());
        edt_current_pass = v.findViewById(R.id.edt_current_pass);
        edt_new_pass = v.findViewById(R.id.edt_new_pass);
        edt_re_pass = v.findViewById(R.id.edt_re_pass);
        btn_save = v.findViewById(R.id.btn_continue);
        tvResetPass = v.findViewById(R.id.tvResetPass);
    }

    @Override
    protected void initData() {
        userViewModel = new ViewModelProvider(getActivity()).get(UserViewModel.class);
        edt_current_pass.initData(ExtEditTextApp.TYPE_VALIDATE.PASSWORD, getString(R.string.incorrect_password),
                InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD, aBoolean -> {
                    isPass = aBoolean;
                    initButtonSave();
                });
        edt_current_pass.setHint(getString(R.string.current_password));
        edt_new_pass.initData(ExtEditTextApp.TYPE_VALIDATE.RE_PASSWORD, getString(R.string.password_does_not_match),
                InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD, aBoolean -> {
                    isRepass = aBoolean;
                    initButtonSave();
                });
        edt_new_pass.setHint(getString(R.string.enter_your_new_password));
        edt_new_pass.setConsumerTextChange(s -> {
            edt_re_pass.setTextFormat(s);
            edt_re_pass.addTextChange(isRepass ? ExtEditTextApp.TYPE_ERROR.DONE : ExtEditTextApp.TYPE_ERROR.ERROR);
        });
        edt_re_pass.initData(ExtEditTextApp.TYPE_VALIDATE.RE_PASSWORD, getString(R.string.password_does_not_match),
                InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD, aBoolean -> {
                    isRepass = aBoolean;
                    initButtonSave();
                });
        edt_re_pass.setHint(getString(R.string.enter_your_new_password_again));
        edt_re_pass.setConsumerTextChange(s -> {
            edt_new_pass.setTextFormat(s);
            edt_new_pass.addTextChange(isRepass ? ExtEditTextApp.TYPE_ERROR.DONE : ExtEditTextApp.TYPE_ERROR.ERROR);
        });

        btn_save.setOnClickListener(v -> {
            if (isPass && isRepass) {
                showLoading();
                userViewModel.changePass(requestAPI, edt_current_pass.getText(), edt_new_pass.getText(), edt_re_pass.getText(), o -> {
                    cancelLoading();
                    if (o instanceof ChangePassResponse) {
                        ChangePassResponse baseResponse = (ChangePassResponse) o;
                        if (baseResponse.getSuccess()) {
                            SharePrefrenceUtils.getInstance(mContext).saveAuth(baseResponse.getData().getAccessToken());
                            backstackFragment();
                        } else {
                            // chua lam thong bao loi
                        }
                    } else {
                        // chua lam bao loi
                    }
                });
            }
        });

        tvResetPass.setOnClickListener(view -> {
            replaceFragment(new VerifyMailFragment(), VerifyMailFragment.class.getSimpleName());
            ((MainActivity) getActivity()).hideOrShowBottomView(false);
        });

    }

    private void initButtonSave() {
        btn_save.setBackgroundResource(isPass && isRepass ? R.drawable.bg_blue_21 : R.drawable.bg_gray_b8);
    }
}
