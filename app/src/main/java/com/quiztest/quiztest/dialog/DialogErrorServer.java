package com.quiztest.quiztest.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.quiztest.quiztest.R;
import com.quiztest.quiztest.custom.ExtTextView;

public class DialogErrorServer extends Dialog {

    private ImageView iv_title;
    private ExtTextView txt_title, txt_error, txt_close;

    public DialogErrorServer(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        getWindow().setGravity(Gravity.CENTER);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        setContentView(R.layout.dialog_infomation);
        findViewById(R.id.txt_title).setOnClickListener(v -> dismiss());
        iv_title = findViewById(R.id.iv_title);
        txt_title = findViewById(R.id.txt_title);
        txt_error = findViewById(R.id.txt_error);
        txt_close = findViewById(R.id.txt_close);
        txt_close.setOnClickListener(v -> dismiss());
    }

    private void initData(boolean isSuccess,String textError) {
        txt_error.setText(textError);
    }


}
