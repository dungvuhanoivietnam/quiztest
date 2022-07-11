package com.quiztest.quiztest.utils;

import android.os.Environment;

public class Const {
    public static final String REGEX_EMAIL = "[a-zA-Z0-9._-]+@[a-z]+\\\\.+[a-z]+";
    public static final int CAMERA_REQUEST_CODE = 9999;
    public static final int ALBUM_REQUEST_CODE = 9998;
    public static final String pathCallSDK30 = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC).getAbsolutePath();

    public static final String PROVIDE_NAME_FACEBOOK = "facebook";
    public static final String PROVIDE_NAME_GOOGLE = "google";
    public static final String KEY_EMAIL = "KEY_EMAIL";
    public static final String KEY_VERIFY_STYLE = "EMAIL";
    public static final String KEY_VERIFY_TYPE = "RESET_PASSWORD";
    public static final int KEY_CODE_400 = 400;
    public static final int KEY_CODE_401 = 401;
    public static final int KEY_CODE_404 = 404;
    public static final int KEY_CODE_500 = 500;
    public static final int KEY_CODE_502 = 502;

    public static final String BEARER = "Bearer ";
    public static final String DATA = "data";
    public static final String TOKEN = "token";
    public static final String TYPE = "type";
    public static final String LANGUAGE = "language";

}
