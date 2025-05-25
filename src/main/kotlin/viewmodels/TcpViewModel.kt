package viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import services.TcpClientService

class TcpViewModel {
    private val tcpClient = TcpClientService()
    private val scope = CoroutineScope(Dispatchers.Main)

    var receivedMessages by mutableStateOf<List<String>>(emptyList())
        private set

    var connectionStatus by mutableStateOf<TcpClientService.ConnectionState>(
        TcpClientService.ConnectionState.Disconnected
    )
        private set

    init {
        // Collect connection state
        scope.launch {
            tcpClient.connectionState.collect { state ->
                connectionStatus = state
            }
        }

        // Collect received data
        scope.launch {
            tcpClient.receivedData.collect { data ->
                receivedMessages = receivedMessages.takeLast(99) + data // Keep last 100 messages
            }
        }
    }

    fun connect(host: String, port: Int) {
        scope.launch {
            tcpClient.connect(host, port)
        }
    }

    fun disconnect() {
        tcpClient.disconnect()
    }

    fun sendMessage(message: String) {
        scope.launch {
            tcpClient.sendData(message)
        }
    }
}