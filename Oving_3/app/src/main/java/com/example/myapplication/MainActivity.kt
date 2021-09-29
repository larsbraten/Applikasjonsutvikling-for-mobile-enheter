package com.example.myapplication

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.widget.*


class MainActivity : Activity() {
    var users: MutableList<User> = ArrayList<User>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val user1 = User("Lars", "27. August 1998")
        val user2 = User("Karl", "15. April 1994")
        val user3 = User("Maria", "04. September 1999")
        users.add(user1)
        users.add(user2)
        users.add(user3)
        initSpinner()
    }

    private fun initSpinner() {
        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item, users
        )
        val spinner = findViewById<Spinner>(R.id.spinner)
        spinner.adapter = adapter
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View,
                position: Int,
                id: Long
            ) {
                val textViewName: TextView = findViewById(R.id.actual_name)
                textViewName.text = users[position].getName().toString()
                val textViewBirthday: TextView = findViewById(R.id.actual_birthday)
                textViewBirthday.text = users[position].getBirthday().toString()
                val editTextName: EditText = findViewById(R.id.editText_name)
                editTextName.setText("")
                val editTextBirthday: EditText = findViewById(R.id.editText_birthday)
                editTextBirthday.setText("")
            }


            override fun onNothingSelected(arg0: AdapterView<*>) {

            }
        }
        val button: Button = findViewById(R.id.edit_user_button)
        button.setOnClickListener() {
            changeUser(button)
        }
        val button2: Button = findViewById(R.id.discard_edits_button)
        button2.setOnClickListener() {
            dismissChanges(button2)
        }
        val button3: Button = findViewById(R.id.add_user_button)
        button3.setOnClickListener() {
            addNewUser(button3)
        }
        val button4: Button = findViewById(R.id.button_save_changes)
        button4.setOnClickListener() {
            saveChanges(button4)
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == RESULT_OK) {
            val name = data?.getStringExtra("name");
            val birthday = data?.getStringExtra("birthday");
            val newUser = User(name.toString(), birthday.toString())
            users.add(newUser)
        }
    }

    private fun addNewUser(view: View) {
        val intent = Intent(this, ShowFriends::class.java)
        startActivityForResult(intent, 1)
    }

    private fun changeUser(view: View) {
        val gridLayout: GridLayout = findViewById(R.id.grid_layout_change_user)
        gridLayout.visibility = VISIBLE
        val button: Button = findViewById(R.id.discard_edits_button)
        button.visibility = VISIBLE
        val changeButton: Button = findViewById(R.id.edit_user_button)
        changeButton.visibility = INVISIBLE
    }

    private fun saveChanges(view: View) {
        val spinner: Spinner = findViewById(R.id.spinner)
        val selectedUser: User = spinner.selectedItem as User
        val editTextName: EditText = findViewById(R.id.editText_name)
        val editTextBirthday: EditText = findViewById(R.id.editText_birthday)

        var changedSomething = false;
        val newName = editTextName.text.toString()
        if (newName != "") {
            selectedUser.name = newName
            changedSomething = true
            val textViewName: TextView = findViewById(R.id.actual_name)
            textViewName.text = newName
        }
        val newBirthday: String = editTextBirthday.text.toString()
        if (newBirthday != "") {
            selectedUser.birthday = newBirthday
            changedSomething = true
            val textView: TextView = findViewById(R.id.actual_birthday)
            textView.text = newBirthday
        }
        val gridLayout: GridLayout = findViewById(R.id.grid_layout_change_user)
        gridLayout.visibility = INVISIBLE
        val button: Button = findViewById(R.id.discard_edits_button)
        button.visibility = INVISIBLE
        val button1: Button = findViewById(R.id.edit_user_button)
        button1.visibility = VISIBLE


        val toastText: String = if (changedSomething) {
            "Changes were saved"
        } else {
            "No new changes"
        }
        val toast = Toast.makeText(this, toastText, Toast.LENGTH_LONG)
        toast.show()

    }

    private fun dismissChanges(view: View) {
        val editTextName: EditText = findViewById(R.id.editText_name)
        editTextName.text = Editable.Factory.getInstance().newEditable("")
        val editTextBirthday: EditText = findViewById(R.id.editText_birthday)
        editTextBirthday.text = Editable.Factory.getInstance().newEditable("")
        val gridLayout: GridLayout = findViewById(R.id.grid_layout_change_user)
        gridLayout.visibility = INVISIBLE
        val button: Button = findViewById(R.id.discard_edits_button)
        button.visibility = INVISIBLE
        val button2: Button = findViewById(R.id.edit_user_button)
        button2.visibility = INVISIBLE


    }

}
