package models

import kotlinx.serialization.Serializable
@Serializable
data class SensorData(
    val loadCell: Double?,
    val ignitionStatus: Boolean?,
    val ignitionType: String?,
    val temperatureInlet: Double?,
    val temperatureOutlet: Double?,
    val pressureInlet: Double?,
    val pressureOutlet: Double?,
    val valveStatus: Boolean?,
    val burnTime: Double?
)
