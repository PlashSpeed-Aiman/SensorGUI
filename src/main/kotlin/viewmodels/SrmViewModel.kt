package viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import services.CsvWriterService
import services.TcpClientService
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class SrmViewModel : IModeViewModel {
    private val tcpClient = TcpClientService()
    private val csvWriter = CsvWriterService("srm_data_${LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"))}.csv")

    val sensorViewModel = SensorViewModel()
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
                println("Received data: $data")
                sensorViewModel.updateFromTcpMessage(data)
                receivedMessages = receivedMessages.takeLast(99) + data // Keep last 100 messages
                scope.launch(Dispatchers.IO) {
                    csvWriter.appendData(sensorViewModel.toCsv())
                }

            }
        }
    }


    override fun connect(host: String, port: Int) {
        scope.launch {
            tcpClient.connect(host, port)
        }
    }

    override fun disconnect() {
        tcpClient.disconnect()
    }

    override fun sendMessage(message: String) {
        scope.launch {
            tcpClient.sendData(message)
        }
    }
}