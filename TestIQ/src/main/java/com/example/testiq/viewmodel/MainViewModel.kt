package com.example.testiq.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import com.example.testiq.model.QuestionModel
import com.example.testiq.model.QuizTestResponse
import com.example.testiq.model.SubmitQuizTestResponse
import com.example.testiq.utils.Resource
import com.google.gson.Gson
import org.json.JSONObject

class MainViewModel(

) : ViewModel() {

    var token : String = "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJhdWQiOiI5Njk5ZjAxMS1iYmY4LTRlNjUtOWMyOS1kNTQ0NWM3MWRmMjYiLCJqdGkiOiJiYjM5YzMwMTBkYjQxOTI3M2FkMWZiZGI4NDZkZTJmMzhkOGEzZmQwNjM3NDI3ZDUxNjYwODNmZjY5ZTBlY2FmOWY3Y2QxNTc2YjgzZmE4YyIsImlhdCI6MTY1NjY2OTk3Ny4zMDYyNjksIm5iZiI6MTY1NjY2OTk3Ny4zMDYyNzEsImV4cCI6MTgxNDQzNjM3Ny4yOTkwNiwic3ViIjoiMjkiLCJzY29wZXMiOltdfQ.m4QHGTCmrq5_4dhQj-qsCm7IIKpzsFz3O-7d-aF9Ye0szTbcCfLmqi2u1R7wzPrO2GJd-o0kT9cgcgZQgNHPfYwJrlBkfETcSk1nLHDKHTATvU1V0lVgv5yHvaZ2vNt37ZPCWxjvRUgOljC1qUZTGzKRTXd3uwAnQlxSLNSI6RC8T8HVDT7mFc5aAE_OUeJBAooN-pKmAD_lW1wfXIes8osSE5aQRHHYI-df_Sl2bIz2pWfOixblpkNuIbBGuPsUnPYJtVbnCXgDlQ0FJl5cC2cQH2BCjPOW9CPjv5vKTyCLWJ_tShH9Z1pKVh7V4s3rCfKjT5rYtaRYiFER5gp9aJ81teWFSzQx-TV_b_9Km6RiA2S0nmoEUAlTWnO5X3HA21A3JLZZaPanLhKZAFg0C6Faau3Ql-ycFcYM5E56m8gpjPxRRHxIC0qIelPaHOY4x8y4x080uOj0MIhUN0sedDePVhO8MEBN29BavJAgieTv0E4CPHCfqEOCVTzCzKc3nx5m_My2S0i-v3DaNbT2W9ZJAXFWljcWLf7BCiDGqmJcN58Vsx14Wi_EZ6WXMZ3fpQASIYa7iVwwXPG7fvLElT0wOyZFIMM79beS0Lol9n7kRayqkmY_tSJYO3pXsukGPRYC7grZEbwr4_XkEi4Ijz61usy3fy05RhSyCz3HbNw"
    private val _users = MutableLiveData<Resource<QuizTestResponse>>()
    var lstQuestion = ArrayList<QuestionModel>()
    var questionModel : QuestionModel? = null
    var index : Int = 0
    var quizTestResponse : QuizTestResponse?= null
    var submitResponse  : SubmitQuizTestResponse?= null
    var lstJSONObject  = JSONObject()
    private val _submit = MutableLiveData<Resource<SubmitQuizTestResponse>>()

    val questions: LiveData<Resource<QuizTestResponse>>
        get() = _users

    val submitQuizTests: LiveData<Resource<SubmitQuizTestResponse>>
        get() = _submit

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
                     _users.postValue(Resource.error(error.message.toString(),null))
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
}