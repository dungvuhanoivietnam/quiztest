package com.quiztest.quiztest.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class AuthResponse (
    val success: Boolean?,
    val data: DataAuth?,
    val message: String?,
)

data class DataAuth (
    @SerializedName("access_token")
    val accessToken: String?,

    @SerializedName("token_type")
    val tokenType: String?,

    @SerializedName("expires_at")
    val expiresAt: String?
)