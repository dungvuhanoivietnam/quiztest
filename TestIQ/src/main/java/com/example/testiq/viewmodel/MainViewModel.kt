package com.example.testiq.viewmodel

import android.R.attr.data
import android.util.Log
import androidx.lifecycle.*
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import com.example.testiq.model.QuestionModel
import com.example.testiq.model.QuizTestResponse
import com.example.testiq.utils.NetworkHelper
import com.example.testiq.utils.Resource
import com.google.gson.Gson
import org.json.JSONObject


class MainViewModel(

) : ViewModel() {

    var token : String = "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJhdWQiOiI5Njk5ZjAxMS1iYmY4LTRlNjUtOWMyOS1kNTQ0NWM3MWRmMjYiLCJqdGkiOiJiNDhiZTkzNjYyNWYxMmMzNTkwNjI0MTZjOGIwODI3ZDc2MjMxZWQ5NDk1YzkxMTI3NzI0MjM3MzhmNGI3NmRiYWVhZTI1M2M4NWMyYTg4NiIsImlhdCI6MTY1NjQ4ODM1Ny43MTMwODksIm5iZiI6MTY1NjQ4ODM1Ny43MTMwOTIsImV4cCI6MTgxNDI1NDc1Ny43MDU3NzEsInN1YiI6IjEiLCJzY29wZXMiOltdfQ.aLEVhbBj_GL7Vi2_SyWBhtyZmajkUae2KR1vb-CDMO3G5myLfWf4nkPB-KVdfcTGpbhYquP_bokkXlPwKOiKSONidb7CX6jfGr6oK8V9Sup_VUoVcnK88gmcJvssGhiWDEIian4aRnmBygw-1iQ5cWXnPb_dUjxKT4yPLiKXfZvc6xfwJ4MLytW_8nWLH8DUBEh93Snv-AjcJM8uIzl6zSQCZqhj5NQBvuCvUewEfcvYcEtT4SUkAV8BL2_RCOWq-ifA-gBgfOeLsbkbQt7XdLgZAt5R0E8iIW1Jp2DgR0qN10cY_D3R2YJrroP8B1YhFRWXa-mmYsuZ8HlL4E1wRPZz-ws-m-5vET1jdHFCORyR212vaC6zAUr-XJJ1CAJ6tlHvQEEJDg3RmAuz3Kr9Rao4ngG2fZfm4A3dTk76y8XR5F05W1_nvmx79ywxIIYkvjbQX0D747kYIHadmYwHjzOC9iVzSzzwXevx5gXgWOK-S7zQW8JWGBdgB9JsWVS_LkJpyBjZW1E0pFRRm796oxqp9NDeGrn_hrYr6JQQr9fBvyGIJ40Xn8meRfxaQ_XcMm_LbbG_BqKh12tNtiXPQxYZZy7cCi9YWdrhihSHsvRci6yZNsYPFfTvpRxtm3wMhBMFlt9hHcZQ5RD75stnVPAj7JsWf3ikP4b7JRdstX8"
    private val _users = MutableLiveData<Resource<QuizTestResponse>>()
    var lstQuestion = ArrayList<QuestionModel>()
    var questionModel : QuestionModel? = null
    var index : Int = 0

    val questions: LiveData<Resource<QuizTestResponse>>
        get() = _users

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
                     val response: QuizTestResponse = Gson().fromJson(
                         response.toString(),
                         QuizTestResponse::class.java
                     )
                     _users.postValue(Resource.success(response))
                 }

                 override fun onError(error: ANError) {
                     // handle error
                     _users.postValue(Resource.error(error.message.toString(),null))
                 }
             })
    }
}