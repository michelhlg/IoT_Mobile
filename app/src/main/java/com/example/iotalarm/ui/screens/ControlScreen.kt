package com.example.iotalarm.ui.screens

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
fun ControlScreen(
    onBack: () -> Unit = {}
) {
    var relay1State by remember { mutableStateOf(true) }
    var relay2State by remember { mutableStateOf(false) }
    var alarmState by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Panel de Control") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = IoTPrimary,
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White
                )
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
            // Relay 1 Control
            ControlCard(
                title = "Relay 1",
                subtitle = "Control de dispositivo principal",
                icon = Icons.Default.Power,
                isActive = relay1State,
                activeColor = RelayColor,
                onToggle = { relay1State = !relay1State }
            )

            // Relay 2 Control
            ControlCard(
                title = "Relay 2",
                subtitle = "Control de dispositivo secundario",
                icon = Icons.Default.Power,
                isActive = relay2State,
                activeColor = RelayColor,
                onToggle = { relay2State = !relay2State }
            )

            // Alarm Control
            ControlCard(
                title = "Alarma",
                subtitle = "Activar/Desactivar alarma de seguridad",
                icon = Icons.Default.Alarm,
                isActive = alarmState,
                activeColor = IoTError,
                onToggle = { alarmState = !alarmState }
            )

            Spacer(modifier = Modifier.weight(1f))

            // Status Summary
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = IoTPrimary.copy(alpha = 0.1f)
                )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Text(
                        text = "Estado del Sistema",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    StatusRow("Relay 1", relay1State)
                    StatusRow("Relay 2", relay2State)
                    StatusRow("Alarma", alarmState)
                }
            }
        }
    }
}

@Composable
fun ControlCard(
    title: String,
    subtitle: String,
    icon: ImageVector,
    isActive: Boolean,
    activeColor: Color,
    onToggle: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                icon,
                contentDescription = null,
                tint = if (isActive) activeColor else OfflineColor,
                modifier = Modifier.size(40.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
                Text(
                    text = subtitle,
                    fontSize = 12.sp,
                    color = Color.Gray
                )
            }
            Switch(
                checked = isActive,
                onCheckedChange = { onToggle() },
                colors = SwitchDefaults.colors(
                    checkedThumbColor = activeColor,
                    checkedTrackColor = activeColor.copy(alpha = 0.3f)
                )
            )
        }
    }
}

@Composable
fun StatusRow(label: String, isActive: Boolean) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = label, fontSize = 14.sp)
        Text(
            text = if (isActive) "ACTIVADO" else "DESACTIVADO",
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = if (isActive) IOTSuccess else OfflineColor
        )
    }
}
