package com.quiztest.quiztest.callback;

import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public interface ActivityResultFragment {
    void result(int requestCode, int resultCode, @Nullable Intent data);

    void result(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults);
}
