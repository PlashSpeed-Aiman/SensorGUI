package services

import java.io.File
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class LoggingService private constructor(builder: Builder) {
    private val logFile: File
    private val dateFormat: DateTimeFormatter
    private val includeTimestamp: Boolean
    private val logLevel: LogLevel
    
    init {
        this.logFile = builder.logFile
        this.dateFormat = builder.dateFormat
        this.includeTimestamp = builder.includeTimestamp
        this.logLevel = builder.logLevel
    }
    
    fun log(message: String, level: LogLevel = LogLevel.INFO) {
        if (level.severity >= logLevel.severity) {
            val timestamp = if (includeTimestamp) {
                "[${LocalDateTime.now().format(dateFormat)}] "
            } else ""
            
            val logMessage = "$timestamp[${level.name}] $message\n"
            logFile.appendText(logMessage)
        }
    }
    
    enum class LogLevel(val severity: Int) {
        DEBUG(0),
        INFO(1),
        WARN(2),
        ERROR(3)
    }
    
    class Builder {
        lateinit var logFile: File
        var dateFormat: DateTimeFormatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME
        var includeTimestamp: Boolean = true
        var logLevel: LogLevel = LogLevel.INFO
        
        fun setLogFile(filePath: String) = apply {
            this.logFile = File(filePath)
            if (!this.logFile.exists()) {
                this.logFile.createNewFile()
            }
        }
        
        fun setDateFormat(format: DateTimeFormatter) = apply {
            this.dateFormat = format
        }
        
        fun setIncludeTimestamp(include: Boolean) = apply {
            this.includeTimestamp = include
        }
        
        fun setLogLevel(level: LogLevel) = apply {
            this.logLevel = level
        }
        
        fun build(): LoggingService {
            require(::logFile.isInitialized) { "Log file path must be set" }
            return LoggingService(this)
        }
    }
    
    companion object {
        fun builder(): Builder = Builder()
    }
}