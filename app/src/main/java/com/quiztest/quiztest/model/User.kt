package com.quiztest.quiztest.model

data class User (
    val name: String?=null,
    val email: String? =null,
    val password: String? = null,
    val confirm_password: String? = null
)