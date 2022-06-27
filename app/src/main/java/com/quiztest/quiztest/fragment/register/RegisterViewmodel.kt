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


class RegisterViewmodel(
    private var userRepository : AuthRepository = AuthRepository()
): ViewModel() {
    var resigter = MutableLiveData<MutableList<AuthResponse>>()


    fun registerAccount(email: String, name: String, password: String, confirm_password: String
    ){
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                resigter.postValue(userRepository.registerAccount(email,name,password,confirm_password))
            }
        }

    }



}