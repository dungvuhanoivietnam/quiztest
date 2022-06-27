package com.quiztest.quiztest

import android.os.Bundle
import android.os.PersistableBundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class TestAct : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login);
        Toast.makeText(this,"aaaaaaaaaa", Toast.LENGTH_SHORT).show()
    }

}