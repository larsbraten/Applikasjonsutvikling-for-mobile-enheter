package com.example.server

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.Socket

class ClientHandler(clientSocket: Socket) {
    private val socket: Socket = clientSocket


    fun start() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                delay(5000)
                CoroutineScope(Dispatchers.IO).launch {
                    Server.welcomeMessage(socket)
                    val name = readFromClient(socket)
                    while (true) {
                        delay(1000)
                        val message = readFromClient(socket)
                        if (!message.isNullOrEmpty()) {
                            Server.sendToClients("$name: $message")
                        }
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun getSocket(): Socket {
        return socket
    }

    private fun readFromClient(socket: Socket): String? {
        val reader = BufferedReader(InputStreamReader(socket.getInputStream()))
        val message = reader.readLine()
        return message
    }


}