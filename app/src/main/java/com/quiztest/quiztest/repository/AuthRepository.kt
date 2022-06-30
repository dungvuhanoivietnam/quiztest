package com.quiztest.quiztest.repository

import com.quiztest.quiztest.model.AuthResponse
import com.quiztest.quiztest.model.UserReponse
import com.quiztest.quiztest.remote.AuthRemote

import okhttp3.ResponseBody
import retrofit2.Call

class AuthRepository {
private val remoteUser:AuthRemote = AuthRemote()
    fun registerAccount(
        email: String,
        name: String,
        password: String,
        confirm_password: String
    ): Call<ResponseBody> {
        return remoteUser.registerAccount(email, name, password, confirm_password)
    }
    fun loginAccount(
        email: String,
        password: String,
    ): Call<AuthResponse> {
        return remoteUser.loginAccount(email, password)
    }
}