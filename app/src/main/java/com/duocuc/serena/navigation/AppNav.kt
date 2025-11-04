package com.duocuc.serena.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.duocuc.serena.ui.theme.screens.*
import kotlinx.coroutines.delay

@Composable
fun AppNav(navController1: NavHostController) {
    // Este controlador es el principal de navegación de la app
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Route.Splash.path) {

        // --- Splash ---
        composable(Route.Splash.path) {
            HomeScreen()
            LaunchedEffect(Unit) {
                delay(3000)
                navController.navigate(Route.Login.path) {
                    popUpTo(Route.Splash.path) { inclusive = true }
                }
            }
        }

        // --- Login ---
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

        // --- Registro ---
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

        // --- Pantalla Principal (HomeAppScreen) ---
        composable(Route.Home.path) {
            HomeAppScreen(nav = navController)
        }

        // --- Subpantallas internas ---
        composable(Route.Journal.path) {
            JournalScreen(
                onNavigateBack = { navController.popBackStack() }
            )
        }

        composable(Route.Analysis.path) {
            AnalysisScreen(
                onNavigateBack = { navController.popBackStack() }
            )
        }

        composable(Route.Profile.path) {
            ProfileScreen(
                onNavigateBack = { navController.popBackStack() }
            )
        }
    }
}

@Composable
fun AnalysisScreen(onNavigateBack: () -> Boolean) {
    TODO("Not yet implemented")
}

@Composable
fun JournalScreen(onNavigateBack: () -> Boolean) {
    TODO("Not yet implemented")
}
