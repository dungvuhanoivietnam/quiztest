package com.example.testiq.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import com.example.testiq.model.ConfirmOpenGifResponse
import com.example.testiq.model.QuestionModel
import com.example.testiq.model.QuizTestResponse
import com.example.testiq.model.SubmitQuizTestResponse
import com.example.testiq.utils.Resource
import com.google.gson.Gson
import org.json.JSONObject

class MainViewModel(

) : ViewModel() {

    var token : String = ""
    private val _users = MutableLiveData<Resource<QuizTestResponse>>()
    private val _submit = MutableLiveData<Resource<SubmitQuizTestResponse>>()
    private val _confirmOpenGif = MutableLiveData<Resource<ConfirmOpenGifResponse>>()

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
                     _users.postValue(Resource.error(error.errorBody.toString(),null))
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
                    _users.postValue(Resource.error(error.errorBody.toString(),null))
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
                    _submit.postValue(Resource.error(error.message.toString(),null))
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
                    _submit.postValue(Resource.error(error.message.toString(),null))
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
                    _confirmOpenGif.postValue(Resource.error("Error connection",null))
                }
            })
    }
}