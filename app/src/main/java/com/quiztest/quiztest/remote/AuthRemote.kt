package com.quiztest.quiztest.remote

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
    ): Call<ResponseBody> {
        return apiUser.registerAccount(email, name, password, confirm_password)
    }
}