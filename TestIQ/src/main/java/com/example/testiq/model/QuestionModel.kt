package com.example.testiq.model

import com.google.gson.annotations.SerializedName

data class QuizTestResponse(
    @SerializedName("success") var success: Boolean?,
    @SerializedName("data") var data: Data?,
    @SerializedName("message") var message : String?,
)

data class Data(
    @SerializedName("questions") var questions: ArrayList<QuestionModel>?,
    @SerializedName("key_quiz_test") var key_quiz_test: String?
)

data class QuestionModel(
    @SerializedName("id") var id: Int?,
    @SerializedName("type") var type: String?,
    @SerializedName("question_name") var question_name: String?,
    @SerializedName("question_description") var question_description: String?,
    @SerializedName("options_answer") var options_answer: ArrayList<OptionsAnswer>?,
)

data class OptionsAnswer(
    @SerializedName("image") var image: String?,
    @SerializedName("option_name") var option_name: String?,
    @SerializedName("type") var type: String?,
    @SerializedName("id") var id: Int?,
    var selected: Boolean = false,
)