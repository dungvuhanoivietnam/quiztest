package com.quiztest.quiztest.fragment.forgotPass

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.quiztest.quiztest.model.AuthResponse
import com.quiztest.quiztest.model.OtpDataResponse
import com.quiztest.quiztest.repository.AuthRepository
import com.quiztest.quiztest.utils.Utils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OtpViewModel(
    private var userRepository: AuthRepository = AuthRepository()
) : ViewModel() {
    val otpResLiveData = MutableLiveData<OtpDataResponse>()
    val verifyOtpResLiveData = MutableLiveData<AuthResponse>()
    var isLoading = MutableLiveData<Boolean>()
    var errorMessage = MutableLiveData<String>()

    fun resendOtp(context: Context, email: String, verifyStyle: String, verifyType: String) {
        isLoading.postValue(true)
        userRepository.resendOtp(email, verifyStyle, verifyType)
            .enqueue(object : Callback<OtpDataResponse> {
                override fun onResponse(
                    call: Call<OtpDataResponse>,
                    response: Response<OtpDataResponse>
                ) {
                    isLoading.postValue(false)
                    if (response.body() != null) {
                        if (response.isSuccessful) {
                            otpResLiveData.postValue(response.body())
                        } else {
                            errorMessage.postValue(response.message())
                        }
                    } else {
                        errorMessage.postValue(Utils.errorMessage(context, response.code()))
                    }
                }

                override fun onFailure(call: Call<OtpDataResponse>, t: Throwable) {
                    isLoading.postValue(false)
                    errorMessage.postValue(t.message)
                }

            })
    }

    fun verifyOtp(context: Context, email: String, verifyStyle: String, otp: String) {
        isLoading.postValue(true)
        userRepository.verifyOtp(verifyStyle, email, otp)
            .enqueue(object : Callback<AuthResponse> {
                override fun onResponse(
                    call: Call<AuthResponse>,
                    response: Response<AuthResponse>
                ) {
                    isLoading.postValue(false)
                    if (response.body() != null) {
                        if (response.isSuccessful) {
                            verifyOtpResLiveData.postValue(response.body())
                        } else {
                            errorMessage.postValue(response.message())
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