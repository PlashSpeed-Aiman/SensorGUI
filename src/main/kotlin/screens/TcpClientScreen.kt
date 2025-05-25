package screens

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Button
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import services.TcpClientService
import viewmodels.TcpViewModel

@Composable
fun TcpClientScreen(viewModel: TcpViewModel = remember { TcpViewModel() }) {
    var host by remember { mutableStateOf("localhost") }
    var port by remember { mutableStateOf("8080") }
    var message by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Connection controls
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextField(
                value = host,
                onValueChange = { host = it },
                label = { Text("Host") },
                modifier = Modifier.weight(2f)
            )

            TextField(
                value = port,
                onValueChange = { port = it },
                label = { Text("Port") },
                modifier = Modifier.weight(1f)
            )
            Button(
                shape = MaterialTheme.shapes.medium,
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 20.dp),
                onClick = {
                    when (viewModel.connectionStatus) {
                        is TcpClientService.ConnectionState.Connected -> viewModel.disconnect()
                        else -> if(host.isNotEmpty() && port.isNotEmpty()){
                            viewModel.connect(
                                host,
                                port.toInt()
                            )
                        }else{

                        }

                    }
                },
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    fontSize = 16.sp,
                    color = Color.White,
                    text =
                    if (viewModel.connectionStatus is TcpClientService.ConnectionState.Connected)
                        "Disconnect" else "Connect"
                )
            }
        }

        // Status indicator
        val statusColor = when (viewModel.connectionStatus) {
            is TcpClientService.ConnectionState.Connected -> Color.Green
            is TcpClientService.ConnectionState.Error -> Color.Red
            is TcpClientService.ConnectionState.Disconnected -> Color.Gray
            else -> Color.White
        }
        Text(
            text = "Status: ${viewModel.connectionStatus::class.simpleName}",
            color = statusColor,
            modifier = Modifier.padding(vertical = 8.dp)
        )

        // Message list
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .border(1.dp, Color.LightGray)
                .padding(8.dp)
        ) {
            items(viewModel.receivedMessages) { message ->
                Text(
                    text = message,
                    modifier = Modifier.padding(vertical = 4.dp)
                )
            }
        }

        // Send message controls
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            TextField(
                value = message,
                onValueChange = { message = it },
                label = { Text("Message") },
                modifier = Modifier.weight(1f)
            )

            Button(
                shape = MaterialTheme.shapes.medium,
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 20.dp),
                onClick = {
                    if (message.isNotEmpty()) {
                        viewModel.sendMessage(message)
                        message = ""
                    }
                },
                enabled = viewModel.connectionStatus is TcpClientService.ConnectionState.Connected
            ) {
                Text("Send")
            }
        }
    }
}