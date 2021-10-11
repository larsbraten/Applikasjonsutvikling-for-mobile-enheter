package com.example.server


import android.widget.TextView
import kotlinx.coroutines.*
import java.io.*
import java.net.ServerSocket
import java.net.Socket

class Server(
    private val textView: TextView,
    private val PORT: Int = 12345,
) {
    var clients: MutableMap<Int, Socket> = HashMap()


    private var ui: String? = ""
        set(str) {
            MainScope().launch { textView.text = str }
            field = str
        }

    fun start() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                ui = "Starting server..."
                ServerSocket(PORT).use { serverSocket: ServerSocket ->
                    ui = "ServerSocket created. Waiting for connections...."
                    val clientSocket = serverSocket.accept()
                        ui = "A client has connected\n$clientSocket"
                        clients[clientSocket.port] = clientSocket
                        sendToClient(clientSocket, "Enter your username!")
                        CoroutineScope(Dispatchers.IO).launch {
                            while (true) {
                                // Fetch message from Client
                                delay(1000)
                                //
                                //sendToAllClient(clientSocket, message)
                                readFromClient(clientSocket)
                                val message : String = readFromClient(clientSocket)
                                if
                                        (message.isNotBlank()) {
                                    sendToClient(clientSocket, message)
                                }

                        }
                    }
                }

            } catch (e: IOException) {
                e.printStackTrace()
                ui = e.message
            }
        }
    }

    private fun readFromClient(socket: Socket) : String{
        val reader = BufferedReader(InputStreamReader(socket.getInputStream()))
        val message = reader.readLine()
        ui = message
        return message
    }

    private fun sendToClient(socket: Socket, message: String) {
        val writer = PrintWriter(socket.getOutputStream(), true)
        writer.println(message)
        ui = message
    }

    private fun sendToAllClient(socket: Socket, message: String) {
        run {
            val iter: Iterator<Int> = clients.keys.iterator()
            while (iter.hasNext()) {
                val key = iter.next()
                val client = clients[key]
                // Sending the response back to the client.
                // Note: Ideally you want all these in a try/catch/finally block
                val os: OutputStream = client!!.getOutputStream()
                val osw = OutputStreamWriter(os)
                val bw = BufferedWriter(osw)
                try {
                    bw.write("Some message")
                } catch (e: IOException) {
                    e.printStackTrace()
                } finally {
                    bw.flush()
                }

            }
        }
    }
}
