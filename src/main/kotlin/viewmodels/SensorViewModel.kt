package viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import kotlinx.serialization.json.Json
import models.SensorData

class SensorViewModel {
    var loadCell by mutableStateOf("0")
        private set
    var ignitionStatus by mutableStateOf("OFF")
        private set
    var temperatureInlet by mutableStateOf("0")
        private set
    var temperatureOutlet by mutableStateOf("0")
        private set
    var pressureInlet by mutableStateOf("0")
        private set
    var pressureOutlet by mutableStateOf("0")
        private set
    var valveStatus by mutableStateOf("OFF")
        private set
    var burnTime by mutableStateOf("0")
        private set

    fun updateFromTcpMessage(message: String) {
        // Assuming message format is JSON or CSV, parse accordingly
        try {
            // Example for JSON parsing
            val data = Json{ ignoreUnknownKeys = true }.decodeFromString<SensorData>(message)
            loadCell = data.loadCell.toString()
            ignitionStatus = data.ignitionStatus?: ""
            temperatureInlet = data.temperatureInlet.toString()
            temperatureOutlet = data.temperatureOutlet.toString()
            pressureInlet = data.pressureInlet.toString()
            pressureOutlet = data.pressureOutlet.toString()
            valveStatus = data.valveStatus?: ""
            burnTime = data.burnTime.toString()
        } catch (e: Exception) {
            println("Error parsing TCP message: ${e.message}")
        }
    }

    fun toCsv(): String {
        return "$loadCell,$ignitionStatus,$temperatureInlet,$temperatureOutlet,$pressureInlet,$pressureOutlet,$valveStatus,$burnTime"
    }
}