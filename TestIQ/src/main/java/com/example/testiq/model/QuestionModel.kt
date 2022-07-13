package com.example.testiq.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class QuizTestResponse(
    @SerializedName("success") var success: Boolean?,
    @SerializedName("data") var data: Data?,
    @SerializedName("message") var message: String?,
)

class DetailTopicResponse {
    @SerializedName("success") var success: Boolean? = null

    @SerializedName("message") var message: String? = null

    @SerializedName("data")
    @Expose
    val data: Data? = null

    inner class Data {
        @SerializedName("id")
        @Expose
        var id = 0

        @SerializedName("title")
        @Expose
        var title: String? = null

        @SerializedName("description")
        @Expose
        var description: String? = null

        @SerializedName("time_to_do")
        @Expose
        var timeToDo: Int? = null

        @SerializedName("allotment")
        @Expose
        var allotment: String? = null

        @SerializedName("money_bonus")
        @Expose
        var moneyBonus: Int? = null

        @SerializedName("star_bonus")
        @Expose
        var starBonus: Int? = null

        @SerializedName("fee_star")
        @Expose
        var feeStar: Int? = null

        @SerializedName("total_question_count")
        @Expose
        var totalQuestionCount: Int? = null
    }
}


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

data class SubmitQuizTestResponse(
    @SerializedName("success") var success: Boolean?,
    @SerializedName("data") var submitData: SubmitDataResponse?,
    @SerializedName("message") var message: String?,
)

data class SubmitDataResponse(
    @SerializedName("answer_correct") var questions: Int?,
    @SerializedName("total_score") var total_score: Int? = 0,
    @SerializedName("review") var review: String?,
    @SerializedName("money_bonus") var money_bonus: Int? = 0,
    @SerializedName("star_bonus") var star_bonus: Int? = 0,
)

data class ConfirmOpenGifResponse(
    @SerializedName("success") var success: Boolean?,
    @SerializedName("data") var data: String? = "",
    @SerializedName("message") var message: String?
)