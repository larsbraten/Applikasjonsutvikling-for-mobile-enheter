package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class ShowFriends : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_friends)
        val button: Button = findViewById(R.id.button_cancel_add_new_user)
        button.setOnClickListener() {
            onCancelButtonClicked(button)
        }
        val button2: Button = findViewById(R.id.add_user_button)
        button2.setOnClickListener() {
            onNewUserButtonClicked(button2)
        }
    }

    fun onNewUserButtonClicked(view: View) {
        val editTextName: EditText = findViewById(R.id.editText_new_user_name)
        val editTextBirthday: EditText = findViewById(R.id.editText_new_user_birthday)
        val name: String = editTextName.text.toString()
        val birthDate: String = editTextBirthday.text.toString()

        if (name == "" || birthDate == ("")) {
            val toast: Toast = Toast.makeText(this, "Please fill in both fields", Toast.LENGTH_LONG)
            toast.show()
        } else {
            val intent: Intent = Intent()
            intent.putExtra("name", name)
            intent.putExtra("birthday", birthDate)
            setResult(RESULT_OK, intent)
            finish()
        }
    }

    fun onCancelButtonClicked(view: View) {
        setResult(RESULT_CANCELED)
        finish()
    }

}
