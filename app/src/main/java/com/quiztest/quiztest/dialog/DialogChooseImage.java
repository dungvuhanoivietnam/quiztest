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
import androidx.core.util.Consumer;

import com.quiztest.quiztest.R;

public class DialogChooseImage extends Dialog {

    public enum TYPE_SELECT {
        CAMERA, ALBUM
    }

    private Consumer<TYPE_SELECT> consumer;

    public DialogChooseImage(@NonNull Context context, int themeResId, Consumer<TYPE_SELECT> consumer) {
        super(context, themeResId);
        this.consumer = consumer;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        getWindow().setGravity(Gravity.CENTER);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        setContentView(R.layout.dialog_choose_image);
        findViewById(R.id.llCamera).setOnClickListener(v -> {
            consumer.accept(TYPE_SELECT.CAMERA);
            dismiss();
        });

        findViewById(R.id.llAlbum).setOnClickListener(v -> {
            consumer.accept(TYPE_SELECT.ALBUM);
            dismiss();
        });

        findViewById(R.id.iv_close).setOnClickListener(v -> {
            dismiss();
        });
    }
}
