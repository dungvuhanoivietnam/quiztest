package com.quiztest.quiztest.repository

import com.quiztest.quiztest.model.AuthResponse
import com.quiztest.quiztest.model.UserReponse
import com.quiztest.quiztest.model.VerifyEmailResponse
import com.quiztest.quiztest.remote.AuthRemote

import okhttp3.ResponseBody
import retrofit2.Call

class AuthRepository {
    private val remoteUser: AuthRemote = AuthRemote()
    fun registerAccount(
        email: String,
        name: String,
        password: String,
        confirm_password: String,
        language: String
    ): Call<AuthResponse> {
        return remoteUser.registerAccount(email, name, password, confirm_password, language)
    }

    fun loginAccount(
        email: String,
        password: String,
    ): Call<AuthResponse> {
        return remoteUser.loginAccount(email, password)
    }

    fun loginSocial(
        providerName: String,
        accessToken: String,
        deviceId: String,
        language: String
    ): Call<AuthResponse> {
        return remoteUser.loginSocial(providerName, accessToken, deviceId, language)
    }

    fun verifyEmail(email: String): Call<VerifyEmailResponse> {
        return remoteUser.verifyEmail(email)
    }
}