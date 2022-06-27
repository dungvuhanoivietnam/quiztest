package com.quiztest.quiztest.model

import com.google.gson.annotations.SerializedName
import com.squareup.moshi.Json

data class AuthResponse (
    val success: Boolean,
    val data: Data,
    val message: String
)

data class Data (
    @SerializedName("access_token")
    val accessToken: String,

    @SerializedName("token_type")
    val tokenType: String,

    @SerializedName("expires_at")
    val expiresAt: String
)