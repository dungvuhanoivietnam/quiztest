package com.quiztest.quiztest.fragment.register

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.quiztest.quiztest.model.User
import com.quiztest.quiztest.repository.AuthRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class RegisterViewmodel(
    private var userRepository : AuthRepository = AuthRepository()
): ViewModel() {
    var userLiveData = MutableLiveData<User>()
    var errorLiveData = MutableLiveData< String>()

    fun registerAccount(email: String, name: String, password: String, confirm_password: String
    ){

    }



}