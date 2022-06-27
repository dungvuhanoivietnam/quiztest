package com.quiztest.quiztest.model

data class RegisterUser(
    val email: String?,
    val name: String?,
    val password: String?,
    val confirm_password: String?
)
const val KEY_REGISTER_USER = "KEY_REGISTER_USER"