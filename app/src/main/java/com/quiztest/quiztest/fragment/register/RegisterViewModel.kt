package com.quiztest.quiztest.fragment.register

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.quiztest.quiztest.model.AuthResponse
import com.quiztest.quiztest.repository.AuthRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class RegisterViewModel(
    private var userRepository: AuthRepository = AuthRepository()
) : ViewModel() {
    val registerResLiveData = MutableLiveData<AuthResponse>()
    val loginSocialResLiveData = MutableLiveData<AuthResponse>()
    var isLoading = MutableLiveData<Boolean>()
    var errorMessage = MutableLiveData<String>()


    fun registerAccount(
        email: String,
        name: String,
        password: String,
        confirm_password: String,
        language: String
    ) {
        isLoading.postValue(true)
        userRepository.registerAccount(email, name, password, confirm_password, language)
            .enqueue(object : Callback<AuthResponse> {
                override fun onResponse(
                    call: Call<AuthResponse>,
                    response: Response<AuthResponse>
                ) {
                    isLoading.postValue(false)
                    if (response.body() != null) {
                        if (response.isSuccessful) {
                            registerResLiveData.postValue(response.body())
                        } else {
                            errorMessage.postValue(response.message())
                        }
                    } else {
                        errorMessage.postValue(response.errorBody().toString())
                    }
                }

                override fun onFailure(call: Call<AuthResponse>, t: Throwable) {
                    isLoading.postValue(false)
                    errorMessage.postValue(t.message)
                }

            })
    }


    fun loginSocial(
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
                            errorMessage.postValue(response.message())
                        }
                    } else {
                        errorMessage.postValue(response.errorBody().toString())
                    }
                }

                override fun onFailure(call: Call<AuthResponse>, t: Throwable) {
                    isLoading.postValue(false)
                    errorMessage.postValue(t.message)
                }

            })
    }


}