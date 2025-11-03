package com.duocuc.serena.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.duocuc.serena.ui.theme.screens.*
import kotlinx.coroutines.delay

@Composable
fun AppNav() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Route.Splash.path) {
        composable(Route.Splash.path) {
            // Use HomeScreen as the animated splash screen
            HomeScreen()
            LaunchedEffect(Unit) {
                delay(3000) // Display for 3 seconds
                navController.navigate(Route.Login.path) {
                    // Pop up to the splash screen to remove it from the back stack
                    popUpTo(Route.Splash.path) { inclusive = true }
                }
            }
        }
        composable(Route.Login.path) {
            LoginScreen(
                onLoginSuccess = {
                    navController.navigate(Route.Dashboard.path) {
                        popUpTo(Route.Login.path) { inclusive = true }
                    }
                },
                onNavigateToRegister = {
                    navController.navigate(Route.Register.path)
                }
            )
        }
        composable(Route.Register.path) {
            RegisterScreen(
                onRegisterSuccess = {
                     navController.navigate(Route.Login.path) {
                        popUpTo(Route.Login.path) { inclusive = true }
                     }
                },
                onNavigateToLogin = { navController.popBackStack() }
            )
        }
        composable(Route.Dashboard.path) {
            DashboardScreen(
                onLogout = {
                    navController.navigate(Route.Login.path) {
                        popUpTo(Route.Dashboard.path) { inclusive = true }
                    }
                },
                onNavigateToProfile = {
                    navController.navigate(Route.Profile.path)
                }
            )
        }
        composable(Route.Profile.path) {
            ProfileScreen(
                onNavigateBack = { navController.popBackStack() }
            )
        }
    }
}