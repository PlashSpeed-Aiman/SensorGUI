package components
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ControlPanelCard() {
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

            // Action Buttons Row
            ActionButtonsSection()

            // Input Section
            InputSection()

            // Emergency Stop Section
            EmergencyStopSection()
        }
    }
}

@Composable
fun ActionButtonsSection() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        ControlButton(
            text = "Tare Load Cell",
            modifier = Modifier.weight(1f),
            onClick = { /* Handle tare action */ }
        )

        ControlButton(
            text = "Start Ignition",
            modifier = Modifier.weight(1f),
            onClick = { /* Handle ignition action */ }
        )
    }
}

@Composable
fun InputSection() {
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
                onClick = { /* Handle insert */ }
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
fun EmergencyStopSection() {
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

        ControlButton(
            text = "STOP",
            modifier = Modifier.fillMaxWidth(),
            backgroundColor = Color(0xFFDC143C), // Crimson red for emergency
            textColor = Color.White,
            onClick = { /* Handle emergency stop */ }
        )
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
