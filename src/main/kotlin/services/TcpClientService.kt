package services

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.PrintWriter
import java.net.Socket
import java.util.concurrent.atomic.AtomicBoolean

class TcpClientService {
    private var socket: Socket? = null
    private var reader: BufferedReader? = null
    private var writer: PrintWriter? = null
    private val isConnected = AtomicBoolean(false)

    private val _receivedData = MutableSharedFlow<String>(
        replay = 0,
        extraBufferCapacity = 64 // Adjust buffer size based on your needs
    )
    val receivedData = _receivedData.asSharedFlow()

    private val _connectionState = MutableStateFlow<ConnectionState>(ConnectionState.Disconnected)
    val connectionState = _connectionState.asStateFlow()

    private var receiveJob: Job? = null

    sealed class ConnectionState {
        object Connected : ConnectionState()
        object Disconnected : ConnectionState()
        data class Error(val message: String) : ConnectionState()
    }

    suspend fun connect(host: String, port: Int) {
        try {
            withContext(Dispatchers.IO) {
                socket = Socket(host, port)
                reader = BufferedReader(InputStreamReader(socket!!.getInputStream()))
                writer = PrintWriter(socket!!.outputStream, true)
                isConnected.set(true)
                _connectionState.emit(ConnectionState.Connected)
                startReceiving()
            }
        } catch (e: Exception) {
            _connectionState.emit(ConnectionState.Error("Failed to connect: ${e.message}"))
            disconnect()
        }
    }

    private fun startReceiving() {
        receiveJob = CoroutineScope(Dispatchers.IO).launch {
            try {
                while (isConnected.get()) {
                    reader?.readLine()?.let { data ->
                        _receivedData.emit(data)
                    }
                }
            } catch (e: Exception) {
                _connectionState.emit(ConnectionState.Error("Reception error: ${e.message}"))
                disconnect()
            }
        }
    }

    suspend fun sendData(data: String) {
        try {
            withContext(Dispatchers.IO) {
                if (isConnected.get()) {
                    writer?.println(data)
                }
            }
        } catch (e: Exception) {
            _connectionState.emit(ConnectionState.Error("Send error: ${e.message}"))
            disconnect()
        }
    }

    fun disconnect() {
        isConnected.set(false)
        receiveJob?.cancel()

        writer?.close()
        reader?.close()
        socket?.close()

        writer = null
        reader = null
        socket = null

        CoroutineScope(Dispatchers.Main).launch {
            _connectionState.emit(ConnectionState.Disconnected)
        }
    }
}
