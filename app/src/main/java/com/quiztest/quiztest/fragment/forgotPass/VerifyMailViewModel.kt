package com.quiztest.quiztest.fragment.forgotPass

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.quiztest.quiztest.model.VerifyEmailResponse
import com.quiztest.quiztest.repository.AuthRepository
import com.quiztest.quiztest.utils.Utils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class VerifyMailViewModel(
    private var userRepository: AuthRepository = AuthRepository()
) : ViewModel() {
    val validEmailResLiveData = MutableLiveData<VerifyEmailResponse>()
    var isLoading = MutableLiveData<Boolean>()
    var errorMessage = MutableLiveData<String>()

    fun validEmail(context: Context, email: String) {
        isLoading.postValue(true)
        userRepository.verifyEmail(email).enqueue(object : Callback<VerifyEmailResponse> {
            override fun onResponse(
                call: Call<VerifyEmailResponse>,
                response: Response<VerifyEmailResponse>
            ) {
                isLoading.postValue(false)
                if (response.body() != null) {
                    if (response.isSuccessful) {
                        validEmailResLiveData.postValue(response.body())
                    } else {
                        errorMessage.postValue(response.message())
                    }
                } else {
                    errorMessage.postValue(Utils.errorMessage(context,response.code()))
                }
            }

            override fun onFailure(call: Call<VerifyEmailResponse>, t: Throwable) {
                isLoading.postValue(false)
                errorMessage.postValue(t.message)
            }

        })
    }
}