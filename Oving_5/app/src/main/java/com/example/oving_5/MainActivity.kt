package com.example.oving_5

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity() {
    /*
     Running server locally through tomcat since the NTNU server only returns error.
      */
    private val httpWrapper: HttpWrapper = HttpWrapper("http://10.0.2.2:8080/oving5/tallspill.jsp")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val buttonStart: Button = findViewById(R.id.button_start_new_game)
        buttonStart.setOnClickListener {
            startGame()
        }
    }

    private fun performRequest(
        network: HttpWrapper,
        parameterList: Map<String, String>,
        onResult: (String) -> Unit
    ) {
        CoroutineScope(IO).launch {
            val response: String = try {
                network.get(parameterList)
            } catch (e: Exception) {
                Log.e("performRequest()", e.message!!)
                e.printStackTrace().toString()
            }
            MainScope().launch {
                onResult(response)
            }
        }
    }

    private fun startGame() {
        val textViewAttempts: TextView = findViewById(R.id.textView_enter_number)
        val editTextAttempts: EditText = findViewById(R.id.editText_enter_number)
        val relativeLayout: RelativeLayout = findViewById(R.id.relativeLayout)
        val buttonStart: Button = findViewById(R.id.button_start_new_game)
        val editTextName: EditText = findViewById(R.id.edit_text_name_of_player)
        val editTextCardNumber: EditText = findViewById(R.id.edit_text_card_number_player)
        val name: String = editTextName.text.toString()
        val cardNumber: String = editTextCardNumber.text.toString()
        val idealMessage = "Oppgi et tall"
        textViewAttempts.visibility = View.GONE
        editTextAttempts.visibility = View.GONE
        relativeLayout.visibility = View.VISIBLE
        buttonStart.visibility = View.GONE
        val isEmpty = name.isEmpty() || cardNumber.isEmpty()
        if (!isEmpty) {
            performRequest(httpWrapper, mapOf("navn" to name, "kortnummer" to cardNumber)) {
                if (it.contains(idealMessage, ignoreCase = true)) {
                    postNumber(it)
                }
            }
        } else {
            val myToast = Toast.makeText(
                applicationContext,
                "Enter both name and card number!",
                Toast.LENGTH_SHORT
            )
            myToast.show()
            buttonStart.visibility = View.VISIBLE
        }

    }


    private fun postNumber(message: String) {
        val textViewEnterNumber: TextView = findViewById(R.id.textView_enter_number)
        val sendButton: Button = findViewById(R.id.button_send)
        val startButton: Button = findViewById(R.id.button_start_new_game)
        val editTextEnterNumber: EditText = findViewById(R.id.editText_enter_number)
        val relativeLayout: RelativeLayout = findViewById(R.id.relativeLayout)
        sendButton.visibility = View.VISIBLE
        textViewEnterNumber.visibility = View.VISIBLE
        textViewEnterNumber.text = message
        editTextEnterNumber.visibility = View.VISIBLE


        sendButton.setOnClickListener {
            val number: String = editTextEnterNumber.text.toString()
            if (number.isNotEmpty()) {
                performRequest(httpWrapper, mapOf("tall" to number)) {
                    val victoryMessage = "du har vunnet"
                    val numberTooSmallMessage = "er for lite"
                    val numberTooLargeMessage = "er for stort"

                    when {
                        it.contains(victoryMessage) -> {
                            textViewEnterNumber.text = it
                            startButton.visibility = View.VISIBLE
                            startButton.text = "Spill igjen"
                            sendButton.visibility = View.INVISIBLE
                        }

                        it.contains(numberTooSmallMessage) -> {
                            textViewEnterNumber.text = it
                            startButton.visibility = View.GONE
                            relativeLayout.visibility = View.GONE
                        }
                        it.contains(numberTooLargeMessage) -> {
                            textViewEnterNumber.text = it
                            startButton.visibility = View.GONE
                            relativeLayout.visibility = View.GONE
                        }
                        else -> {
                            textViewEnterNumber.text = it
                            editTextEnterNumber.visibility = View.INVISIBLE
                            sendButton.visibility = View.GONE
                            startButton.visibility = View.VISIBLE
                            relativeLayout.visibility = View.VISIBLE
                            startButton.text = "Spill igjen"
                        }
                    }
                }
            } else {
                val myToast = Toast.makeText(
                    applicationContext,
                    "You have to enter a number!",
                    Toast.LENGTH_SHORT
                )
                myToast.show()
            }
        }
    }
}