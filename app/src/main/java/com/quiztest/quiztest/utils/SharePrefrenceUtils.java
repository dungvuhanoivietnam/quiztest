package com.quiztest.quiztest.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SharePrefrenceUtils {
    public static final String KEY_SHARE = "KEY_SHARE";
    public static final String KEY_SHARE_AUTH = "KEY_SHARE_AUTH";
    public static final String REFERENCES_NAME = "AppPreferences";
    public static final String KEY_USER_ACCESS_TOKEN = "USER_ACCESS_TOKEN";
    public static final String OPEN_APP_FIRST_TIME = "open_app_first_time";


    private SharedPreferences mPreference;
    private SharedPreferences.Editor mPrefEditor;

    public static SharePrefrenceUtils getInstance(Context context) {
        return new SharePrefrenceUtils(context);
    }

    public SharePrefrenceUtils(Context context) {
        mPreference = context.getSharedPreferences(KEY_SHARE, Context.MODE_PRIVATE);
        mPrefEditor = mPreference.edit();
    }

    public boolean saveAuth(String auth) {
        mPrefEditor.putString(KEY_SHARE_AUTH, auth);
        return mPrefEditor.commit();
    }

    public String getAuth() {
        return mPreference.getString(KEY_SHARE_AUTH, "");
    }

    public boolean setUserAccessToken( String token){
        mPrefEditor.putString(KEY_USER_ACCESS_TOKEN, token);
        return mPrefEditor.commit();
    }
    public String getUserAccessToken(){
        return mPreference.getString(KEY_USER_ACCESS_TOKEN, "");
    }

    public static void saveBoolean(Context context, String str, boolean z) {
        SharedPreferences.Editor edit = context.getSharedPreferences(REFERENCES_NAME, 0).edit();
        edit.putBoolean(str, z);
        edit.apply();
    }

    public static boolean getBoolean(Context context, String str, boolean z) {
        return context.getSharedPreferences(REFERENCES_NAME, 0).getBoolean(str, z);
    }

}
