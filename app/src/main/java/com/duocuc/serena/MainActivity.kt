package com.duocuc.serena

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.*
import com.duocuc.serena.ui.theme.theme.SerenaTheme
import com.duocuc.serena.ui.theme.screens.DashboardScreen
import com.duocuc.serena.ui.theme.screens.HomeScreen
import com.duocuc.serena.ui.theme.screens.LoginScreen
import com.duocuc.serena.ui.theme.screens.RegisterScreen
import com.duocuc.serena.ui.theme.screens.SplashScreen
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SerenaTheme {
                AppNavigator()
            }
        }
    }
}

@Composable
fun AppNavigator() {
    var currentScreen by remember { mutableStateOf("home") }

    when (currentScreen) {
        "home" -> {
            HomeScreen()
            LaunchedEffect(Unit) {
                delay(2000)
                currentScreen = "splash"
            }
        }
        "splash" -> SplashScreen { currentScreen = "login" }
        "login" -> LoginScreen(
            onLoginSuccess = { currentScreen = "dashboard" },
            onNavigateToRegister = { currentScreen = "register" }
        )
        "register" -> RegisterScreen(
            onRegisterSuccess = { currentScreen = "dashboard" },
            onNavigateToLogin = { currentScreen = "login" }
        )
        "dashboard" -> DashboardScreen(onLogout = { currentScreen = "login" })
    }
}


