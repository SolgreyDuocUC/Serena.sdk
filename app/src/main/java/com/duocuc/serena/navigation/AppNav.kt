package com.duocuc.serena.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.duocuc.serena.ui.theme.screens.*

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AppNav() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Route.Load.path
    ) {
        // Pantalla de carga (Splash / Load)
        composable(Route.Load.path) {
            LoadScreen {
                navController.navigate(Route.Login.path) {
                    popUpTo(Route.Load.path) { inclusive = true }
                }
            }
        }

        // Login
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

        // Registro
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

        // Home (con calendario integrado y menú lateral)
        composable(Route.Home.path) {
            HomeAppScreen(nav = navController)
        }

        // Calendario
        composable(Route.Calendar.path) {
            CalendarScreen()
        }

        // Registro emocional (Journal)
        composable(Route.Journal.path) {
            JournalScreen()
        }

        // Mensaje del día
        composable(Route.MessageOfDay.path) {
            MessageOfDayScreen()
        }

        // Ruta de aprendizaje emocional
        composable(Route.LearningPath.path) {
            LearningPathScreen()
        }

        // Devocional
        composable(Route.Devotional.path) {
            DevotionalScreen()
        }

        // Configuración general
        composable(Route.Settings.path) {
            SettingsScreen()
        }

        // Perfil de usuario (configuración personal)
        composable(Route.Profile.path) {
            ProfileScreen()
        }

        // Cerrar sesión
        composable(Route.Logout.path) {
            LogoutScreen()
        }

        // Ayuda
        composable(Route.Help.path) {
            HelpScreen()
        }

        // Acerca de la app
        composable(Route.About.path) {
            AboutScreen()
        }
    }
}

@Composable
fun JournalScreen() {
    TODO("Not yet implemented")
}

@Composable
fun SettingsScreen() {
    TODO("Not yet implemented")
}

@Composable fun CalendarScreen() {}
@Composable fun MessageOfDayScreen() {}
@Composable fun LearningPathScreen() {}
@Composable fun DevotionalScreen() {}
@Composable fun ProfileScreen() {}
@Composable fun LogoutScreen() {}
@Composable fun HelpScreen() {}
@Composable fun AboutScreen() {}




