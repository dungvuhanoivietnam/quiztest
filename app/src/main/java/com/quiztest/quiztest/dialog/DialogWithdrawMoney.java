package com.quiztest.quiztest.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.InputType;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import androidx.annotation.NonNull;

import com.quiztest.quiztest.R;
import com.quiztest.quiztest.custom.ExtEditText;
import com.quiztest.quiztest.custom.ExtEditTextApp;
import com.quiztest.quiztest.custom.ExtTextView;
import com.quiztest.quiztest.model.AuthResponse;

public class DialogWithdrawMoney extends Dialog {
    private ExtEditText edtAccountAddress, edtWithdrawalAmount;
    private ExtTextView extWithdrawMoney, extAll;
    private OnWithDrawClickLisenter onWithDrawClickLisenter;
    private int totalMoney;

    public DialogWithdrawMoney(@NonNull Context context, int themeResId, OnWithDrawClickLisenter lisenter) {
        super(context, themeResId);
        this.onWithDrawClickLisenter = lisenter;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        getWindow().setGravity(Gravity.CENTER);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        setContentView(R.layout.dialog_withdraw_money);
        setCanceledOnTouchOutside(true);
        initView();
        initData();

    }

    private void initData() {
        extWithdrawMoney.setOnClickListener(v -> {
            if (onWithDrawClickLisenter != null) {
                String email = edtAccountAddress.getText().toString().trim();
                String money = edtWithdrawalAmount.getText().toString().trim();
                try {
                    onWithDrawClickLisenter.onWidthDrawClick(email, Integer.parseInt(money));
                } catch (NumberFormatException e) {

                }
                dismiss();
            }
        });
        extAll.setOnClickListener(v -> {
            edtWithdrawalAmount.setText(totalMoney + "");
            edtWithdrawalAmount.setSelection(edtWithdrawalAmount.getText().toString().length());
        });
    }

    private void initView() {
        edtAccountAddress = findViewById(R.id.edt_account_address);
        edtWithdrawalAmount = findViewById(R.id.edt_withdrawal_amount);
        extWithdrawMoney = findViewById(R.id.ext_withdraw_money);
        extAll = findViewById(R.id.ext_all);
    }

    public void setTotalMoney(int totalMoney) {
        this.totalMoney = totalMoney;
    }

    public interface OnWithDrawClickLisenter {
        void onWidthDrawClick(String email, int money);
    }
}
