package com.quiztest.quiztest.fragment.register

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.quiztest.quiztest.base.BaseFragment
import com.quiztest.quiztest.model.AuthResponse
import com.quiztest.quiztest.repository.AuthRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class RegisterViewmodel(
    private var userRepository : AuthRepository = AuthRepository()
): ViewModel() {
    val resigterAccount = MutableLiveData<AuthResponse>()
    var isLoading = MutableLiveData<Boolean>()
    var errorMessage  = MutableLiveData<String>()


    fun registerAccount(email: String, name: String, password: String, confirm_password: String){
        isLoading.postValue(true)
        userRepository.registerAccount(email,name,password,confirm_password).enqueue(object  : Callback<ResponseBody>{
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                isLoading.postValue(false)
                val data =  response.body() as AuthResponse
                if (response.isSuccessful){
                    resigterAccount.postValue(data)
                }else{
                    errorMessage.postValue(response.message())
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                isLoading.postValue(false)
                errorMessage.postValue(t.message)
            }

        })
    }



}