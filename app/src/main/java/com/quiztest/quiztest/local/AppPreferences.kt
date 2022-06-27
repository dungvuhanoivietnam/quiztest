package com.quiztest.quiztest.local

import android.content.Context
import android.content.SharedPreferences
import com.quiztest.quiztest.App

object AppPreferences {
    var preferences: SharedPreferences
    private var editor: SharedPreferences.Editor

    const val REFERENCES_NAME = "AppPreferences"
    const val KEY_USER_ACCESS_TOKEN = "USER_ACCESS_TOKEN"


    init {
        preferences = App.getInstance()
            .getSharedPreferences(REFERENCES_NAME, Context.MODE_PRIVATE)
        editor = preferences.edit()
    }

    fun getUserAccessToken(): String {
        return preferences.getString(KEY_USER_ACCESS_TOKEN, "") ?: ""
    }

    fun setUserAccessToken(token: String) {
        editor.also {
            it.putString(KEY_USER_ACCESS_TOKEN, token)
            it.commit()
        }
    }
}