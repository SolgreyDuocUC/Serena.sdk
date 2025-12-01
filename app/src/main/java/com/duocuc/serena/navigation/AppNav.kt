package com.duocuc.serena.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.duocuc.serena.ui.screens.HomeAppScreen
import com.duocuc.serena.ui.screens.auth.LoginScreen
import com.duocuc.serena.ui.screens.auth.RegisterScreen
import com.duocuc.serena.ui.screens.emotionData.EmotionalRegisteredScreen
import com.duocuc.serena.ui.screens.learning.LearningPathScreen
import com.duocuc.serena.ui.screens.profile.ProfileScreen

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

        composable(Route.MessageOfDay.path) { /* Placeholder */ }
        composable(Route.LearningPath.path) { LearningPathScreen(navController = navController) }

        composable(Route.EmotionalRegistered.path) {
            EmotionalRegisteredScreen(navController = navController)
        }

        composable(Route.Profile.path) {
            ProfileScreen(onNavigateBack = { navController.popBackStack() })
        }

        composable(Route.Settings.path) { /* Placeholder */ }
        composable(Route.Logout.path) { /* Placeholder */ }
        composable(Route.Help.path) { /* Placeholder */ }
        composable(Route.About.path) { /* Placeholder */ }
    }
}

@Composable
fun LoadScreen(onLoadFinished: () -> Unit) {
    LaunchedEffect(Unit) {
        onLoadFinished()
    }
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}