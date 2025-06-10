package viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.core.parameter.parametersOf
import org.koin.mp.KoinPlatform.getKoin
import services.CsvWriterService
import services.TcpClientService
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class SrmViewModel : IModeViewModel {
    private val tcpClient = TcpClientService()
    private lateinit var csvWriter : CsvWriterService

    val sensorViewModel : SensorViewModel = getKoin().get { parametersOf(this) }
    private val scope = CoroutineScope(Dispatchers.Main)

    var receivedMessages by mutableStateOf<List<String>>(emptyList())
        private set

    override var connectionStatus by mutableStateOf<TcpClientService.ConnectionState>(
        TcpClientService.ConnectionState.Disconnected
    )

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
        csvWriter = CsvWriterService("srm_data_${LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"))}.csv")
        scope.launch {
            tcpClient.connect(host, port)
            if ( tcpClient.connectionState.value == TcpClientService.ConnectionState.Connected ) {
                csvWriter = CsvWriterService("./results/srm_data_${LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"))}.csv")
            } else {
                println("Failed to connect to server")
            }
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
    override fun dispose(){
        tcpClient.disconnect()
    }
}