package com.quiztest.quiztest.remote

import com.quiztest.quiztest.model.AuthResponse
import com.quiztest.quiztest.model.UserReponse
import com.quiztest.quiztest.model.VerifyEmailResponse
import com.quiztest.quiztest.retrofit.RequestAPI
import com.quiztest.quiztest.retrofit.RetrofitClient
import okhttp3.ResponseBody
import retrofit2.Call

class AuthRemote(
    private val apiUser: RequestAPI = RetrofitClient.getInstance()!!.create(RequestAPI::class.java)
) {
    fun registerAccount(
        email: String,
        name: String,
        password: String,
        confirm_password: String
    ): Call<AuthResponse> {
        return apiUser.registerAccount(email, name, password, confirm_password)
    }

    fun loginAccount(
        email: String,
        password: String,
    ): Call<AuthResponse> {
        return apiUser.loginAccount(email, password)
    }

    fun verifyEmail( email: String): Call< VerifyEmailResponse>{
        return apiUser.verifyMail(email)
    }
}