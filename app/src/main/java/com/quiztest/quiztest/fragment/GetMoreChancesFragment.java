package com.quiztest.quiztest.fragment;

import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.quiztest.quiztest.R;
import com.quiztest.quiztest.base.BaseFragment;
import com.quiztest.quiztest.custom.ExtTextView;
import com.quiztest.quiztest.dialog.DialogWithdrawMoney;

public class GetMoreChancesFragment extends BaseFragment implements View.OnClickListener {
    public static final int TYPE_GET_MORE_STAR = 1;
    public static final int TYPE_GET_MORE_MONEY = 2;

    private ExtTextView extTitleActionBar, extWithdrawMoney;
    private RelativeLayout rlWithdrawMoney;
    private ImageView ivBack;

    private int current_type;

    public GetMoreChancesFragment(int type_get_more) {
        current_type = type_get_more;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_get_more_chances;
    }

    @Override
    protected void initView(View v) {
        extTitleActionBar = v.findViewById(R.id.ext_title_action_bar);
        rlWithdrawMoney = v.findViewById(R.id.rl_withdraw_money);
        extWithdrawMoney = v.findViewById(R.id.ext_withdraw_money);
        ivBack = v.findViewById(R.id.iv_back);
    }

    @Override
    protected void initData() {
        ivBack.setOnClickListener(v -> {
            backstackFragment();
        });
        extWithdrawMoney.setOnClickListener(this);
        setLayoutType(current_type);
    }

    private void setLayoutType(int current_type) {
        if (current_type == TYPE_GET_MORE_STAR) {
            extTitleActionBar.setText(R.string.get_more_star_chances);
            rlWithdrawMoney.setVisibility(View.GONE);
        } else if (current_type == TYPE_GET_MORE_MONEY) {
            extTitleActionBar.setText(R.string.get_more_money_chances);
            rlWithdrawMoney.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.ext_withdraw_money) {
            DialogWithdrawMoney dialogWithdrawMoney = new DialogWithdrawMoney(getContext(), R.style.MaterialDialogSheet);
            dialogWithdrawMoney.show();

        }
    }
}
