package com.duocuc.serena

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.navigation.compose.rememberNavController
import com.duocuc.serena.navigation.AppNav
import com.duocuc.serena.ui.theme.theme.SerenaTheme

/*
Rama de desarrollo para la app Serena.
Desarrolladores:
- Diego Arias
- Solgrey Medina
 */

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Graph.provide(this)
        enableEdgeToEdge()
        setContent {
            SerenaTheme {
                val navController = rememberNavController()
                AppNav()
            }
        }
    }
}
