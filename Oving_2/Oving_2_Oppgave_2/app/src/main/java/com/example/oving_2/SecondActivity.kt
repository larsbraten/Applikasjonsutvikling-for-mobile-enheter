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
        val valueLimit1 = intent.getIntExtra("valueLimit1", 10)
        val valueLimit2 = intent.getIntExtra("valueLimit2", valueLimit1)
        Log.d("test", valueLimit1.toString())
        Log.d("test", valueLimit2.toString())
        val number = (0..valueLimit1).random()
        val number2 = (0..valueLimit2).random()
        val intent = Intent()
        intent.putExtra("number", number)
        intent.putExtra("number2", number2)
        setResult(RESULT_OK, intent)
        this.finish()

    }
}