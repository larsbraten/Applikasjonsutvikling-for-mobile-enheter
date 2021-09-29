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

        fun getNewValue1() {
            val valueLimitEditText: TextView = findViewById(R.id.valueLimitEditText)
            val valueLimit = Integer.parseInt(valueLimitEditText.text.toString())
            val intent = Intent(this, SecondActivity::class.java)
            intent.putExtra("valueLimit1", valueLimit)
            Log.e("test", valueLimit.toString())
            startActivityForResult(intent, 1)


        }
        fun getNewValue2() {
            val valueLimitEditText: TextView = findViewById(R.id.valueLimitEditText)
            val valueLimit2 = Integer.parseInt(valueLimitEditText.text.toString())
           val intent2 = Intent(this, SecondActivity::class.java)
            intent.putExtra("valueLimit2", valueLimit2)
            Log.e("test", valueLimit2.toString())
            startActivityForResult(intent2, 2)
        }

        fun checkCalculation(add: Boolean) {
            val number1TextView: TextView = findViewById(R.id.number_1)
            val number1 = Integer.parseInt(number1TextView.text.toString())
            val number2TextView: TextView = findViewById(R.id.number_2)
            val number2 = Integer.parseInt(number2TextView.text.toString())
            val toastText: String
            val sum: Int = if (add) {
                number1 + number2
            } else {
                number1 * number2
            }
            val answerEditText: TextView = findViewById(R.id.edittext_answer)
            val answer: Int = Integer.parseInt(answerEditText.text.toString())

            (if (sum == answer) {
                getString(R.string.Correct)
            } else {
                getString(R.string.Wrong) + " " + sum
            }).also { toastText = it }
            Toast.makeText(this, toastText, Toast.LENGTH_LONG).show()
        }

        val multiplyButton = findViewById<Button>(R.id.multiply_button)
        multiplyButton.setOnClickListener {
            checkCalculation(false)
            getNewValue1()
            getNewValue2()
        }
        val additionButton = findViewById<Button>(R.id.addition_button)
        additionButton.setOnClickListener {
            checkCalculation(true)
            getNewValue1()
            getNewValue2()
        }


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == 1 && resultCode == RESULT_OK) {
            val textView1 = findViewById<TextView>(R.id.number_1)
            if (data != null) {
                textView1.text = (data.getIntExtra("number", 1).toString())
            }
        }
        if (requestCode == 2 && resultCode == RESULT_OK) {
            val textView2 = findViewById<TextView>(R.id.number_2)
            if (data != null) {
                textView2.text = (data.getIntExtra("number2", 2).toString())
            }
        }
    }
}
