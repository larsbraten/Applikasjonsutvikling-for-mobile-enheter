package com.example.server


import android.widget.TextView
import kotlinx.coroutines.*
import java.io.IOException
import java.io.PrintWriter
import java.net.ServerSocket
import java.net.Socket


class Server(
    private val textView: TextView,
    private val PORT: Int = 12345) {

    /**
     * Egendefinert set() som gjør at vi enkelt kan endre teksten som vises i skjermen til
     * emulatoren med
     *
     * ```
     * ui = "noe"
     * ```
     */
    companion object {

        val clientHandlers: ArrayList<ClientHandler> = ArrayList()
        fun sendToClients(message: String) {
            for (handler in clientHandlers) {
                val writer = PrintWriter(handler.getSocket().getOutputStream(), true)
                writer.println(message)
            }
        }
        suspend fun welcomeMessage(socket : Socket){
            for (handler in clientHandlers) {
                if(socket.equals(handler.getSocket())) {
                    val writer = PrintWriter(handler.getSocket().getOutputStream(), true)
                    writer.println("You have connected to the server")
                    delay(1000)
                    writer.println("Reply to this message with your username")
                    delay(1000)
                    writer.println("Number of connected clients: " + clientHandlers.size)
                }
            }
        }
    }

    var ui: String? = ""
        set(str) {
            MainScope().launch { textView.text = str }
            field = str
        }

    fun start() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                ServerSocket(PORT).use { serverSocket: ServerSocket ->
                    while (true) {
                        val clientSocket = serverSocket.accept()
                        ClientHandler(clientSocket).start()
                        clientHandlers.add(ClientHandler(clientSocket))
                    }
                }
            } catch (e: IOException) {
                e.printStackTrace()
                ui = e.message
            }
        }
    }
}