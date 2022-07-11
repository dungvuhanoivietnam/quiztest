package com.quiztest.quiztest.fragment.login

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.quiztest.quiztest.model.AuthResponse
import com.quiztest.quiztest.repository.AuthRepository
import com.quiztest.quiztest.utils.Utils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception

class LoginViewModel(
    private var userRepository: AuthRepository = AuthRepository()
) : ViewModel() {
    val loginResLiveData = MutableLiveData<AuthResponse>()
    val loginSocialResLiveData = MutableLiveData<AuthResponse>()
    var isLoading = MutableLiveData<Boolean>()
    var errorMessage = MutableLiveData<String>()

    fun loginAccount(context: Context, email: String, password: String) {
        isLoading.postValue(true)
        userRepository.loginAccount(email, password).enqueue(object : Callback<AuthResponse> {
            override fun onResponse(call: Call<AuthResponse>, response: Response<AuthResponse>) {
                isLoading.postValue(false)
                if (response.body() != null) {
                    if (response.isSuccessful) {
                        loginResLiveData.postValue(response.body())
                    } else {
                        errorMessage.postValue(Utils.errorMessage(context, response.code()))
                    }
                } else {
                    errorMessage.postValue(Utils.errorMessage(context, response.code()))
                }
            }

            override fun onFailure(call: Call<AuthResponse>, t: Throwable) {
                isLoading.postValue(false)
                errorMessage.postValue(t.message)

            }

        })

    }

    fun loginSocial(
        context: Context,
        provideName: String,
        accessToken: String,
        deviceId: String,
        language: String
    ) {
        isLoading.postValue(true)
        userRepository.loginSocial(provideName, accessToken, deviceId, language)
            .enqueue(object : Callback<AuthResponse> {
                override fun onResponse(
                    call: Call<AuthResponse>,
                    response: Response<AuthResponse>
                ) {
                    isLoading.postValue(false)
                    if (response.body() != null) {
                        if (response.isSuccessful) {
                            loginSocialResLiveData.postValue(response.body())
                        } else {
                            errorMessage.postValue(Utils.errorMessage(context, response.code()))
                        }
                    } else {
                        errorMessage.postValue(Utils.errorMessage(context, response.code()))
                    }
                }

                override fun onFailure(call: Call<AuthResponse>, t: Throwable) {
                    isLoading.postValue(false)
                    errorMessage.postValue(t.message)
                }

            })
    }

}