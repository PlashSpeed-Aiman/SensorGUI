package viewmodels

interface IModeViewModel {
    fun connect(host: String, port: Int)

    fun disconnect()

    fun sendMessage(message: String)
}
