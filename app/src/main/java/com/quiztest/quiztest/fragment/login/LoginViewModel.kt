package com.quiztest.quiztest.fragment.login

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.quiztest.quiztest.model.AuthResponse
import com.quiztest.quiztest.model.UserReponse
import com.quiztest.quiztest.repository.AuthRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception

class LoginViewModel(
    private var userRepository : AuthRepository = AuthRepository()): ViewModel() {
    val loginAccount = MutableLiveData<AuthResponse>()
    var isLoading = MutableLiveData<Boolean>()
    var errorMessage = MutableLiveData<String>()

    fun loginAccount( email: String ,password: String){
        isLoading.postValue(true)
        userRepository.loginAccount(email,password).enqueue(object : Callback<AuthResponse>{
            override fun onResponse(call: Call<AuthResponse>, response: Response<AuthResponse>) {
                isLoading.postValue(false)
                try {
                    val data = response.body() as AuthResponse
                    if (response.isSuccessful){
                        loginAccount.postValue(data)
                    }else{
                        errorMessage.postValue(response.message())
                    }
                }catch (e : Exception){
                    Log.e(".......>.", "login cast not success")
                }
            }

            override fun onFailure(call: Call<AuthResponse>, t: Throwable) {
                isLoading.postValue(false)
                errorMessage.postValue(t.message)

            }

        } )

    }
}