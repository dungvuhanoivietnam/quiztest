package com.quiztest.quiztest.utils;

import android.os.Environment;

public class Const {
    public static final String REGEX_EMAIL = "[a-zA-Z0-9._-]+@[a-z]+\\\\.+[a-z]+";
    public static final int CAMERA_REQUEST_CODE = 9999;
    public static final int ALBUM_REQUEST_CODE = 9998;
    public static final String pathCallSDK30 = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC).getAbsolutePath();


}
