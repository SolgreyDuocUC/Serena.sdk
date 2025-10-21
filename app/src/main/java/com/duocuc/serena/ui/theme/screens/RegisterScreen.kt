package com.duocuc.serena.ui.theme.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun RegisterScreen(onRegisterSuccess: () -> Unit, onNavigateToLogin: () -> Unit) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text("Pantalla de registro")
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = onRegisterSuccess) { Text("Registrarse") }
            TextButton(onClick = onNavigateToLogin) { Text("Volver a inicio de sesión") }
        }
    }
}
