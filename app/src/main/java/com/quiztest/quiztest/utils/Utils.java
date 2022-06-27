package com.quiztest.quiztest.utils;

import android.app.Activity;
import android.util.DisplayMetrics;

public class Utils {

    public static int getHeight(Activity activity) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.heightPixels;
    }
}
