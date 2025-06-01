package components
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import host
import kotlinx.coroutines.launch
import org.koin.mp.KoinPlatform.getKoin
import port
import viewmodels.HrmViewModel
import viewmodels.IModeViewModel
import viewmodels.TcpViewModel

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ControlPanelCard(viewModel: IModeViewModel) {
    Card(
        modifier = Modifier
            .width(400.dp)
            .padding(16.dp),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF1E3A3A) // Dark teal color
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 8.dp
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Title
            Text(
                text = "CONTROL PANEL",
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )

            Button(
                shape = MaterialTheme.shapes.medium,
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 20.dp),
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(contentColor = Color.White,
                    containerColor = Color(0xFF2E8B8B)
                ),
                onClick = {
                    viewModel.connect(host,port.toInt())
                }
            ) {
                Text(
                    fontSize = 16.sp,
                    color = Color.White,
                    text = "Start Connection"
                )
            }
            Spacer(
                modifier = Modifier.fillMaxWidth().background(Color.White)
                    .border(0.01.dp, Color.White, shape = RoundedCornerShape(4.dp)).padding(4.dp)
            )
            // Action Buttons Row
            ActionButtonsSection(viewModel)

            // Input Section
            InputSection(viewModel)

            // Emergency Stop Section
            EmergencyStopSection(viewModel)
        }
    }
}

@Composable
fun ActionButtonsSection(viewModel: IModeViewModel) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        ControlButton(
            text = "Tare Load Cell",
            modifier = Modifier.weight(1f),
            onClick = {
                viewModel.sendMessage("TARE")
            }
        )

        ControlButton(
            text = "Start Ignition",
            modifier = Modifier.weight(1f),
            onClick = {
                viewModel.sendMessage("ON")
            }
        )

        ControlButton(
            text = "Stop Ignition",
            modifier = Modifier.weight(1f),
            onClick = {
            /* Handle ignition action */
                viewModel.sendMessage("OFF")
            }
        )
    }
}

@Composable
fun InputSection(viewModel: IModeViewModel) {
    var inputText by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Input Row
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Box(
                modifier = Modifier
                    .weight(0.35f)
                    .background(
                        Color(0xFF2E8B8B),
                        RoundedCornerShape(8.dp)
                    )
                    .padding(vertical = 24.dp, horizontal = 12.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Enter value:",
                    color = Color.White,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium
                )
            }

            Box(
                modifier = Modifier
                    .weight(0.65f)
                    .background(
                        Color.White,
                        RoundedCornerShape(8.dp)
                    )
            ) {
                TextField(
                    value = inputText,
                    onValueChange = { inputText = it.filter { char -> char.isDigit() } },
                    placeholder = {
                        Text(
                            "Value in seconds",
                            fontSize = 12.sp,
                            color = Color.Gray
                        )
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    ),
                    singleLine = true,
                    textStyle = androidx.compose.ui.text.TextStyle(
                        fontSize = 24.sp,
                        color = Color.Black
                    )
                )
            }
        }

        // Buttons Row
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            ControlButton(
                text = "Insert",
                modifier = Modifier.weight(1f),
                backgroundColor = Color(0xFF2E8B8B),
                textColor = Color.White,
                onClick = {
                    viewModel.sendMessage( "$inputText" )

                }
            )

            ControlButton(
                text = "Clear",
                modifier = Modifier.weight(1f),
                backgroundColor = Color(0xFFB0C4DE),
                textColor = Color.Black,
                onClick = { inputText = "" }
            )
        }
    }
}

@Composable
fun EmergencyStopSection(viewModel: IModeViewModel) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Valve Emergency Stop",
            color = Color.White,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium
        )
        Row(){
            ControlButton(
                text = "Start Auto Ignition",
                modifier = Modifier.weight(0.5f),
                textColor = Color.White,
                onClick = {
                    viewModel.sendMessage("AUTO")
                }
            )
            Spacer(modifier = Modifier.padding(4.dp))
            ControlButton(
                text = "STOP",
                modifier = Modifier.weight(0.5f),
                backgroundColor = Color(0xFFDC143C), // Crimson red for emergency
                textColor = Color.White,
                onClick = {
                    viewModel.sendMessage("STOP")
                }
            )
        }


    }
}

@Composable
fun ControlButton(
    text: String,
    modifier: Modifier = Modifier,
    backgroundColor: Color = Color(0xFF2E8B8B),
    textColor: Color = Color.White,
    onClick: () -> Unit
) {
    Button(
        modifier = modifier,
        shape = RoundedCornerShape(8.dp),
        contentPadding = PaddingValues(16.dp),
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(contentColor = textColor,
                containerColor = backgroundColor
            )
    ) {
        Text(
            text = text,
            color = textColor,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            textAlign = TextAlign.Center
        )
    }
}
