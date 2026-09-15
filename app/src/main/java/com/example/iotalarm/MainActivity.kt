package com.example.iotalarm

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.example.iotalarm.navigation.AppNavigation
import com.example.iotalarm.ui.theme.IoTAlarmTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            IoTAlarmTheme {
                val navController = rememberNavController()
                AppNavigation(navController = navController)
            }
        }
    }
}
