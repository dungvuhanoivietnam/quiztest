package com.example.testiq.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import com.example.testiq.model.*
import com.example.testiq.utils.Resource
import com.google.gson.Gson
import org.json.JSONObject

class MainViewModel(

) : ViewModel() {

    var token : String = "" // Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJhdWQiOiI5Njk5ZjAxMS1iYmY4LTRlNjUtOWMyOS1kNTQ0NWM3MWRmMjYiLCJqdGkiOiIzNjU4MTc5ZDFiN2IzYmQ3ZTU0N2E1ZmFlN2M3MDNmYzg5YzJiNzZiNDgzMzljNzJmMmZlMmM5MDQ0ZTNiZWQ4MzI3OTk5ZjMwMTQxYmVmNSIsImlhdCI6MTY1Njk1MjU2OC4zNjk1MTgsIm5iZiI6MTY1Njk1MjU2OC4zNjk1MjEsImV4cCI6MTgxNDcxODk2OC4zMzUxMzYsInN1YiI6IjM0Iiwic2NvcGVzIjpbXX0.e9I5MR8zyeJJx87j-jpAaNNiDAdDWNh-jJ9S2Vfv9dFuwCmseljenjJjtFJD0Uh1kHduh0EkYjeqSj79O-ZhQLFrGIKcLTcinwNMeItd-SFI3NQaa_5X4k1fu3tOcgC3K7y38vusbCAgYm8-7Jl0IaEcHmiXTWXXz0QNAuXh1dcBYzd5apguDCxr7wvtBSOvvNyc6DnxSQsIrM5SCJHJzXx6nGeYuXCaaoFTw4ccHARRZ6JFL7UK2Dwp0SZwA35p9x1NRGa9cZaWZrjzw3G2s1lE4-q1Dx4dxHs3VfEjsyUXoKN5XV27HUcWO9rDzKf-aJBDps93x-lVUrGqo-Ujfg1NaIdYQe9U1fVBX2FoxOALKheFxeorXPHjK2-2Kkt5qRsjJ51t-aoKCnnm6VzRl-MWsnvEkpNu51hxNWR4NX5j3FrEWy7CYZEly03heP_7WE29y1EDW_QYNK5hjynXLhdQ0HOSuBFtwQOi7rwTZPWbUhBl0mu5ooKLMWN-bN4osTYaceRBYtk8V3W9u_42IP7bjK5CRmRGqKv1_pYKHEZ-wAFDEaJomEdCa8NEBCsBhDYkc4wrb-MqP1-zrfTObSZ9GD5KyUDZgg0KbfsyMlzK5B6-cdmAmBqGlJLLGn3d05Ct88D1iNw8VhBYH4_a9yeq5rBXmoJqblskOd2PvoA
    private val _users = MutableLiveData<Resource<QuizTestResponse>>()
    private val _submit = MutableLiveData<Resource<SubmitQuizTestResponse>>()
    private val _confirmOpenGif = MutableLiveData<Resource<ConfirmOpenGifResponse>>()
    private val _question = MutableLiveData<Resource<DetailTopicResponse>>()
    var testId : Int = 0

    var lstQuestion = ArrayList<QuestionModel>()
    var questionModel : QuestionModel? = null
    var index : Int = 0
    var quizTestResponse : QuizTestResponse?= null
    var submitResponse  : SubmitQuizTestResponse?= null
    var lstJSONObject  = JSONObject()

    val questions: LiveData<Resource<QuizTestResponse>>
        get() = _users

    val submitQuizTests: LiveData<Resource<SubmitQuizTestResponse>>
        get() = _submit

    val confirmOpenGifs: LiveData<Resource<ConfirmOpenGifResponse>>
        get() = _confirmOpenGif

    val detailTopic: LiveData<Resource<DetailTopicResponse>>
        get() = _question

     fun fetchQuestion() {
         _users.postValue(Resource.loading(null))
         AndroidNetworking.post("https://quiz-test.merryblue.llc/api/v1/quiz/start-quiz-test")
             .addHeaders("Authorization", token)
             .addHeaders("key-app", "quiztest")
             .addQueryParameter("topic_id", "1")
             .setTag("test")
             .setPriority(Priority.MEDIUM)
             .build()
             .getAsJSONObject(object : JSONObjectRequestListener {
                 override fun onResponse(response: JSONObject?) {
                     val dataResponse : QuizTestResponse = Gson().fromJson(
                         response.toString(),
                         QuizTestResponse::class.java
                     )
                     _users.postValue(Resource.success(dataResponse))
                 }

                 override fun onError(error: ANError) {
                     // handle error
                     _users.postValue(Resource.error("Failed connection...",null))
                 }
             })
    }

    fun fetchDetailQuestion(testId :Int) {
        _users.postValue(Resource.loading(null))
        AndroidNetworking.get("https://quiz-test.merryblue.llc/api/v1/quiz/get-detail-topic/$testId")
            .addHeaders("Authorization", token)
            .addHeaders("key-app", "quiztest")
            .setTag("test")
            .setPriority(Priority.MEDIUM)
            .build()
            .getAsJSONObject(object : JSONObjectRequestListener {
                override fun onResponse(response: JSONObject?) {
                    val dataResponse : DetailTopicResponse = Gson().fromJson(
                        response.toString(),
                        DetailTopicResponse::class.java
                    )
                    _question.postValue(Resource.success(dataResponse))
                }

                override fun onError(error: ANError) {
                    // handle error
                    _users.postValue(Resource.error("Failed connection...",null))
                }
            })
    }

    fun fetChQuestionNoToken(){
        _users.postValue(Resource.loading(null))
        AndroidNetworking.post("https://quiz-test.merryblue.llc/api/v1/quiz/start-quiz-test-for-guest")
            .addHeaders("key-app", "quiztest")
            .addQueryParameter("topic_id", "1")
            .setTag("FetchData")
            .setPriority(Priority.MEDIUM)
            .build()
            .getAsJSONObject(object : JSONObjectRequestListener {
                override fun onResponse(response: JSONObject?) {
                    val dataResponse : QuizTestResponse = Gson().fromJson(
                        response.toString(),
                        QuizTestResponse::class.java
                    )
                    _users.postValue(Resource.success(dataResponse))
                }

                override fun onError(error: ANError) {
                    // handle error
                    _users.postValue(Resource.error("Failed connection...",null))
                }
            })
    }

    fun submitQuizTest(keyQuizTest : String){
        _submit.postValue(Resource.loading(null))
        AndroidNetworking.post("https://quiz-test.merryblue.llc/api/v1/quiz/submit-quiz-test")
            .addHeaders("Authorization", token)
            .addHeaders("key-app", "quiztest")
            .addQueryParameter("answers", lstJSONObject.toString())
            .addQueryParameter("key_quiz_test", keyQuizTest)
            .setTag("test")
            .setPriority(Priority.MEDIUM)
            .build()
            .getAsJSONObject(object : JSONObjectRequestListener {
                override fun onResponse(response: JSONObject?) {
                    val submitResponse : SubmitQuizTestResponse = Gson().fromJson(
                        response.toString(),
                        SubmitQuizTestResponse::class.java
                    )
                    _submit.postValue(Resource.success(submitResponse))
                }

                override fun onError(error: ANError) {
                    // handle error
                    _submit.postValue(Resource.error("Failed connection...",null))
                }
            })
    }

    fun submitQuizTestNoToken(keyQuizTest : String){
        _submit.postValue(Resource.loading(null))
        AndroidNetworking.post("https://quiz-test.merryblue.llc/api/v1/quiz/submit-quiz-test-for-guest")
            .addHeaders("key-app", "quiztest")
            .addQueryParameter("answers", lstJSONObject.toString())
            .addQueryParameter("key_quiz_test", keyQuizTest)
            .addQueryParameter("topic_id", "1")
            .addQueryParameter("language", "English")
            .setTag("SubmitNoToken")
            .setPriority(Priority.MEDIUM)
            .build()
            .getAsJSONObject(object : JSONObjectRequestListener {
                override fun onResponse(response: JSONObject?) {
                    val submitResponse : SubmitQuizTestResponse = Gson().fromJson(
                        response.toString(),
                        SubmitQuizTestResponse::class.java
                    )
                    _submit.postValue(Resource.success(submitResponse))
                }

                override fun onError(error: ANError) {
                    // handle error
                    _submit.postValue(Resource.error("Failed connection...",null))
                }
            })
    }

    fun confirmOpenGif(keyQuizTest : String){
        _confirmOpenGif.postValue(Resource.loading(null))
        AndroidNetworking.post("https://quiz-test.merryblue.llc/api/v1/quiz/confirm-open-gif")
            .addHeaders("Authorization", token)
            .addHeaders("key-app", "quiztest")
            .addQueryParameter("key_quiz_test", keyQuizTest)
            .setTag("test")
            .setPriority(Priority.MEDIUM)
            .build()
            .getAsJSONObject(object : JSONObjectRequestListener {
                override fun onResponse(response: JSONObject?) {
                    val confirmGiftResponse : ConfirmOpenGifResponse = Gson().fromJson(
                        response.toString(),
                        ConfirmOpenGifResponse::class.java
                    )
                    _confirmOpenGif.postValue(Resource.success(confirmGiftResponse))
                }

                override fun onError(error: ANError) {
                    // handle error
                    _confirmOpenGif.postValue(Resource.error("Failed connection...",null))
                }
            })
    }
}