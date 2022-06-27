package com.quiztest.quiztest

import android.app.Application

class App: Application() {
    override fun onCreate() {
        super.onCreate()
        instance = this
    }
    companion object{

        @Volatile
        private var instance: App? = null


        @JvmStatic

        fun getInstance(): App = instance ?: synchronized(this) {
            instance ?: App().also {
                instance = it
            }
        }
    }


}