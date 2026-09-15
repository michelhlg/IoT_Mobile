package com.example.iotalarm.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.iotalarm.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    onLogout: () -> Unit = {},
    onNavigateToControl: () -> Unit = {}
) {
    var isEsp32Connected by remember { mutableStateOf(true) }
    var temperature by remember { mutableStateOf("24.5") }
    var humidity by remember { mutableStateOf("65.2") }
    var lightLevel by remember { mutableStateOf("780") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("IoT Alarm Dashboard") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = IoTPrimary,
                    titleContentColor = Color.White
                ),
                actions = {
                    IconButton(onClick = { }) {
                        Icon(
                            Icons.Default.Refresh,
                            contentDescription = "Refresh",
                            tint = Color.White
                        )
                    }
                    IconButton(onClick = onLogout) {
                        Icon(
                            Icons.Default.Logout,
                            contentDescription = "Logout",
                            tint = Color.White
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Connection Status
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = if (isEsp32Connected) IOTSuccess.copy(alpha = 0.1f)
                    else IoTError.copy(alpha = 0.1f)
                )
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        if (isEsp32Connected) Icons.Default.CheckCircle
                        else Icons.Default.Error,
                        contentDescription = null,
                        tint = if (isEsp32Connected) IOTSuccess else IoTError,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text(
                            text = if (isEsp32Connected) "ESP32 Conectado" else "ESP32 Desconectado",
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp
                        )
                        Text(
                            text = if (isEsp32Connected) "Última actualización: Ahora" else "Verificar conexión",
                            fontSize = 12.sp,
                            color = Color.Gray
                        )
                    }
                }
            }

            // Sensor Cards
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                SensorCard(
                    modifier = Modifier.weight(1f),
                    title = "Temperatura",
                    value = "$temperature°C",
                    icon = Icons.Default.Thermostat,
                    color = TemperatureColor
                )
                SensorCard(
                    modifier = Modifier.weight(1f),
                    title = "Humedad",
                    value = "$humidity%",
                    icon = Icons.Default.WaterDrop,
                    color = HumidityColor
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                SensorCard(
                    modifier = Modifier.weight(1f),
                    title = "Nivel de Luz",
                    value = "$lightLevel lux",
                    icon = Icons.Default.LightMode,
                    color = LightColor
                )
                SensorCard(
                    modifier = Modifier.weight(1f),
                    title = "Relay",
                    value = "ON",
                    icon = Icons.Default.Power,
                    color = RelayColor
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            // Control Button
            Button(
                onClick = onNavigateToControl,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = IoTSecondary
                )
            ) {
                Icon(Icons.Default.Settings, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Panel de Control", fontSize = 16.sp)
            }
        }
    }
}

@Composable
fun SensorCard(
    modifier: Modifier = Modifier,
    title: String,
    value: String,
    icon: ImageVector,
    color: Color
) {
    Card(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .height(120.dp),
        colors = CardDefaults.cardColors(
            containerColor = color.copy(alpha = 0.1f)
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Icon(
                icon,
                contentDescription = null,
                tint = color,
                modifier = Modifier.size(28.dp)
            )
            Column {
                Text(
                    text = title,
                    fontSize = 12.sp,
                    color = Color.Gray
                )
                Text(
                    text = value,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = color
                )
            }
        }
    }
}
