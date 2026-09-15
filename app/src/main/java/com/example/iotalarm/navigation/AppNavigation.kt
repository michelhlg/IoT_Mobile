package com.example.iotalarm.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.iotalarm.ui.screens.ControlScreen
import com.example.iotalarm.ui.screens.DashboardScreen
import com.example.iotalarm.ui.screens.LoginScreen

object Routes {
    const val LOGIN = "login"
    const val DASHBOARD = "dashboard"
    const val CONTROL = "control"
}

@Composable
fun AppNavigation(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Routes.LOGIN
    ) {
        composable(Routes.LOGIN) {
            LoginScreen(
                onLoginSuccess = {
                    navController.navigate(Routes.DASHBOARD) {
                        popUpTo(Routes.LOGIN) { inclusive = true }
                    }
                }
            )
        }

        composable(Routes.DASHBOARD) {
            DashboardScreen(
                onLogout = {
                    navController.navigate(Routes.LOGIN) {
                        popUpTo(Routes.DASHBOARD) { inclusive = true }
                    }
                },
                onNavigateToControl = {
                    navController.navigate(Routes.CONTROL)
                }
            )
        }

        composable(Routes.CONTROL) {
            ControlScreen(
                onBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}
