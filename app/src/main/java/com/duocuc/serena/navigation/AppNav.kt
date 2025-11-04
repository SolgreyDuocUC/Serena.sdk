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

    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Route.Splash.path) {

        composable(Route.Splash.path) {
            HomeScreen()
            LaunchedEffect(Unit) {
                delay(3000)
                navController.navigate(Route.Login.path) {
                    popUpTo(Route.Splash.path) { inclusive = true }
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
