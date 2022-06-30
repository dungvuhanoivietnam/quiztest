package com.quiztest.quiztest.fragment.Login

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.quiztest.quiztest.model.AuthResponse
import com.quiztest.quiztest.model.UserReponse
import com.quiztest.quiztest.repository.AuthRepository
import com.quiztest.quiztest.utils.SharePrefrenceUtils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewModel(
    private var userRepository : AuthRepository = AuthRepository()): ViewModel() {
    val loginAccount = MutableLiveData<UserReponse>()
    var isLoading = MutableLiveData<Boolean>()
    var errorMessage = MutableLiveData<String>()

    fun loginAccount( email: String ,password: String){
        isLoading.postValue(true)
        userRepository.loginAccount(email,password).enqueue(object : Callback<UserReponse>{
            override fun onResponse(call: Call<UserReponse>, response: Response<UserReponse>) {
                isLoading.postValue(false)

//                val data = response.body() as UserReponse
                Log.e("----------", "call:${ call.request()} " )
                Log.e("----------", "body:${ response.body()} " )
                Log.e("----------", "onResponse:${ response} " )
//                if (response.isSuccessful){
//                    loginAccount.postValue(data)
//                }else{
//                    errorMessage.postValue(response.message())
//                }
            }

            override fun onFailure(call: Call<UserReponse>, t: Throwable) {
                isLoading.postValue(false)
                errorMessage.postValue(t.message)

            }

        } )

    }
}