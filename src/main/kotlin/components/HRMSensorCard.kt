package components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun HRMSensorReadingCard() {
    Card(
        modifier = Modifier
            .width(500.dp)
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
                .verticalScroll(rememberScrollState())
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Title
            Text(
                text = "SENSORS READING:",
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.padding(0.5.dp))

            // Load Cell Row
            SensorRow(
                label = "Load Cell",
                value = "0",
                unit = "N",
                backgroundColor = Color(0xFF2E8B8B) // Teal color
            )
            Spacer(modifier = Modifier.padding(1.dp))

            // Ignition Status Row
            SensorRow(
                label = "Ignition Status",
                value = "OFF",
                unit = null,
                backgroundColor = Color(0xFFB0C4DE) // Light blue-gray
            )
            Spacer(modifier = Modifier.padding(0.5.dp))

            // Ignition Status Row
            SensorRow(
                label = "Temperature Inlet",
                value = 0.toString(),
                unit = "C",
                backgroundColor = Color(0xFFB0C4DE) // Light blue-gray
            )
            Spacer(modifier = Modifier.padding(0.5.dp))

            // Ignition Status Row
            SensorRow(
                label = "Temperature Outlet",
                value = 0.toString(),
                unit = "C",
                backgroundColor = Color(0xFFB0C4DE) // Light blue-gray
            )
            Spacer(modifier = Modifier.padding(0.5.dp))

            SensorRow(
                label = "Pressure Inlet",
                value = 0.toString(),
                unit = "bar",
                backgroundColor = Color(0xFFB0C4DE) // Light blue-gray
            )
            Spacer(modifier = Modifier.padding(0.5.dp))

            SensorRow(
                label = "Pressure Outlet",
                value = 0.toString(),
                unit = "bar",
                backgroundColor = Color(0xFFB0C4DE) // Light blue-gray
            )
            Spacer(modifier = Modifier.padding(0.5.dp))
            // Valve Section
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // Valve Status Row
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    // Valve label
                    Box(
                        modifier = Modifier
                            .weight(0.4f)
                            .background(
                                Color(0xFFB0C4DE),
                                RoundedCornerShape(8.dp)
                            )
                            .padding(vertical = 24.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Valve",
                            color = Color.Black,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }

                    // Status section
                    Column(
                        modifier = Modifier.weight(0.6f),
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        // Status label and value
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .weight(0.5f)
                                    .background(
                                        Color(0xFF2E8B8B),
                                        RoundedCornerShape(6.dp)
                                    )
                                    .padding(vertical = 8.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "Status",
                                    color = Color.White,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Medium
                                )
                            }

                            Box(
                                modifier = Modifier
                                    .weight(0.5f)
                                    .background(
                                        Color(0xFFB0C4DE),
                                        RoundedCornerShape(6.dp)
                                    )
                                    .padding(vertical = 8.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "OFF",
                                    color = Color.Black,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Medium
                                )
                            }
                        }
                        // On Time section
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .weight(0.5f)
                                    .background(
                                        Color(0xFF2E8B8B),
                                        RoundedCornerShape(6.dp)
                                    )
                                    .padding(vertical = 8.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "On Time",
                                    color = Color.White,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Medium
                                )
                            }
                            Row(
                                modifier = Modifier.weight(0.5f),
                                horizontalArrangement = Arrangement.spacedBy(2.dp)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .weight(0.8f)
                                        .background(
                                            Color.White,
                                            RoundedCornerShape(6.dp)
                                        )
                                        .padding(vertical = 8.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = "0",
                                        color = Color.Black,
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Medium
                                    )
                                }

                                Box(
                                    modifier = Modifier
                                        .weight(0.2f)
                                        .background(
                                            Color(0xFF2E8B8B),
                                            RoundedCornerShape(6.dp)
                                        )
                                        .padding(vertical = 8.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = "s",
                                        color = Color.White,
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Medium
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}