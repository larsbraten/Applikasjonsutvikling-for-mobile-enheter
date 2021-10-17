package com.example.client

import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.io.PrintWriter
import java.net.Socket

class Client(
    private val textView: TextView,
    private val messageButton: Button,
    private val editTextView: EditText,
    private val SERVER_IP: String = "10.0.2.2",
    private val SERVER_PORT: Int = 12345,

    ) {

    /**
     * Egendefinert set() som gjør at vi enkelt kan endre teksten som vises i skjermen til
     * emulatoren med
     *
     * ```
     * ui = "noe"
     * ```
     */
    private val chatLog = mutableListOf<String>()
    private var ui: String? = ""
        set(str) {
            MainScope().launch { textView.text = chatLog.joinToString("") }//.removePrefix(", ").removeSuffix(", ") }
            field = str
        }

    fun start() {
        CoroutineScope(IO).launch {
            chatLog.add("Connecting to server...\n")
            ui = chatLog.toString()
            try {
                val socket = Socket(SERVER_IP, SERVER_PORT)
                //delay(5000)
                //readFromServer(socket)
                CoroutineScope(IO).launch {
                    while (true) {
                        delay(1000)
                        readFromServer(socket)
                        delay(1000)

                    }
                }
                while(true){
                    delay(1000)
                    messageButton.setOnClickListener{
                        val message :String = editTextView.text.toString()


                        CoroutineScope(IO).launch {
                        sendToServer(socket,message)
                        Log.d("hehe", "Sent to server")}
                    }
                }


            } catch (e: IOException) {
                e.printStackTrace()
                chatLog.add(e.message.toString())
                ui = chatLog.toString()
            }
        }
    }

    private fun readFromServer(socket: Socket) {
        val reader = BufferedReader(InputStreamReader(socket.getInputStream()))
        val message = reader.readLine()
        if(!message.isNullOrBlank()) {
            chatLog.add(message + "\n")
            ui = chatLog.toString()
        }

    }

    private fun sendToServer(socket: Socket, message: String) {
        val writer = PrintWriter(socket.getOutputStream(), true)
        if (message.isNotEmpty() || message.isNotBlank()) {
            writer.run {
                println(message)
                flush()
            }
        } else Log.d("error", "Empty message")


    }

}
