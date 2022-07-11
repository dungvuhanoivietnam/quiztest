package com.quiztest.quiztest.fragment.forgotPass

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.quiztest.quiztest.model.AuthResponse
import com.quiztest.quiztest.repository.AuthRepository
import com.quiztest.quiztest.utils.Utils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CreatePassViewModel(
    private var userRepository: AuthRepository = AuthRepository()
) : ViewModel() {
    val createPassResLiveData = MutableLiveData<AuthResponse>()
    var isLoading = MutableLiveData<Boolean>()
    var errorMessage = MutableLiveData<String>()

    fun setNewPass(context: Context, password: String, confirmPass: String) {
        isLoading.postValue(true)
        userRepository.setNewPass(password, confirmPass)
            .enqueue(object : Callback<AuthResponse> {
                override fun onResponse(
                    call: Call<AuthResponse>,
                    response: Response<AuthResponse>
                ) {
                    isLoading.postValue(false)
                    if (response.body() != null) {
                        if (response.isSuccessful) {
                            createPassResLiveData.postValue(response.body())
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