package com.quiztest.quiztest.model

import com.google.gson.annotations.SerializedName

data class VerifyEmailResponse(
    val data: DataMail,
    val message: String,
    val success: Boolean
)

data class DataMail (
    @SerializedName( "verify_type")
    val verifyType: String
)