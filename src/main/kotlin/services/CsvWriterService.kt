package services

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileWriter
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


class CsvWriterService(private val filePath: String) {
    private val file = File(filePath)
    private val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")

    init {
        if (!file.exists()) {
            file.createNewFile()
            FileWriter(file, true).use { writer ->
                writer.append("timestamp" +
                        ",loadCell" +
                        ",ignitionType" +
                        ",ignitionStatus" +
                        ",temperatureInlet" +
                        ",temperatureOutlet" +
                        ",pressureInlet" +
                        ",pressureOutlet" +
                        ",valveStatus" +
                        ",burnTime\n")
            }
        }
    }

    fun appendData(data: String) {
        try {
            FileWriter(file, true).use { writer ->
                val timestamp = LocalDateTime.now().format(dateFormatter)
                writer.append("$timestamp,$data\n")
            }
        } catch (e: Exception) {
            println("Error writing to CSV: ${e.message}")
        }
    }
}
