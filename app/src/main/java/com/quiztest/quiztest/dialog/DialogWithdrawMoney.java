package com.quiztest.quiztest.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.ViewGroup;
import android.view.WindowManager;

import androidx.annotation.NonNull;

import com.quiztest.quiztest.R;
import com.quiztest.quiztest.custom.ExtEditTextApp;

public class DialogWithdrawMoney extends Dialog {

    public DialogWithdrawMoney(@NonNull Context context, int themeResId) {
        super(context, themeResId);
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
        ((ExtEditTextApp) findViewById(R.id.edt_account_address)).setHint(getContext().getResources().getString(R.string.input_your_account));
        ((ExtEditTextApp) findViewById(R.id.edt_withdrawal_amount)).setHint(getContext().getResources().getString(R.string.please_enter_withdrawal_amount));
    }
}
