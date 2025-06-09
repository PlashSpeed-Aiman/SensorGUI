package viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import services.TcpClientService

interface IModeViewModel {
    fun connect(host: String, port: Int)

    fun disconnect()

    fun sendMessage(message: String)

    fun dispose()

    var connectionStatus: TcpClientService.ConnectionState
}
