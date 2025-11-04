package com.duocuc.serena.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.duocuc.serena.ui.theme.screens.HomeAppScreen
import com.duocuc.serena.ui.theme.screens.LoadScreen
import com.duocuc.serena.ui.theme.screens.LoginScreen
import com.duocuc.serena.ui.theme.screens.RegisterScreen

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AppNav() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Route.Load.path
    ) {

        composable(Route.Load.path) {
            LoadScreen {
                navController.navigate(Route.Login.path) {
                    popUpTo(Route.Load.path) { inclusive = true }
                }
            }
        }

        composable(Route.Login.path) {
            LoginScreen(
                onLoginSuccess = {
                    navController.navigate(Route.Home.path) {
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

        composable(Route.Home.path) {
            HomeAppScreen(nav = navController)
        }

        composable(Route.Calendar.path) {
        }

        composable(Route.Settings.path) {
            SettingsScreen()
        }

        composable(Route.Journal.path) {
            JournalScreen()
        }

        composable(Route.Analysis.path) {
            AnalysisScreen()
        }
    }
}

@Composable
fun AnalysisScreen() {
    TODO("Not yet implemented")
}

@Composable
fun JournalScreen() {
    TODO("Not yet implemented")
}

@Composable
fun SettingsScreen() {
    TODO("Not yet implemented")
}



