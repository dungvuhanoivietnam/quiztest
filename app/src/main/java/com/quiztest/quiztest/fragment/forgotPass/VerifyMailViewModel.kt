package com.quiztest.quiztest.fragment.forgotPass

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.quiztest.quiztest.model.VerifyEmailResponse
import com.quiztest.quiztest.repository.AuthRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class VerifyMailViewModel(
private var userRepository : AuthRepository = AuthRepository()): ViewModel(){
    val valiEmail = MutableLiveData<VerifyEmailResponse>()
    var isLoading = MutableLiveData<Boolean>()
    var errorMessage = MutableLiveData< String>()

    fun valiEmail( email: String){
        isLoading.postValue(true)
        userRepository.verifyEmail(email).enqueue(object : Callback<VerifyEmailResponse>{
            override fun onResponse(
                call: Call<VerifyEmailResponse>,
                response: Response<VerifyEmailResponse>) {
                isLoading.postValue(false)
                val data = response.body() as VerifyEmailResponse
                if(response.isSuccessful){
                    valiEmail.postValue(data)
                }else{
                    errorMessage.postValue(response.message())
                }

            }

            override fun onFailure(call: Call<VerifyEmailResponse>, t: Throwable) {
                isLoading.postValue(false)
                errorMessage.postValue(t.message)
            }

        })
    }
}