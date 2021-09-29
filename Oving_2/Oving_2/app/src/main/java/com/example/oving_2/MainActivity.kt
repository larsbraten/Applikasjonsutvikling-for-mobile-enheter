package com.example.oving_2

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class MainActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val valueLimit = 100
        val button = findViewById<Button>(R.id.btn_click_me)
        button.setOnClickListener {
            val intent = Intent(this, SecondActivity::class.java)
            intent.putExtra("valueLimit", valueLimit)
            startActivityForResult(intent, 1)
            Log.d("test", "Sent valueLimit to other activity $valueLimit")
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        if (requestCode == 1 && resultCode == RESULT_OK) {
            Log.d("Test", "Received!")
            val textView = findViewById<TextView>(R.id.view_random_number)
            val text = "Number is: " + data.getIntExtra(
                "number",
                1
            ).toString()
            textView.text = text
            Toast.makeText(applicationContext, text, Toast.LENGTH_SHORT).show()
        }
    }
}