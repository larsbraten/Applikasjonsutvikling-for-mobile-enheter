package com.example.oving_2

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast

class SecondActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)
        val valueLimit = intent.getIntExtra("valueLimit", 0)
        val number = (0..valueLimit).random()
        val intent = Intent()
        intent.putExtra("number", number)
        setResult(RESULT_OK, intent)
        this.finish()

    }
}