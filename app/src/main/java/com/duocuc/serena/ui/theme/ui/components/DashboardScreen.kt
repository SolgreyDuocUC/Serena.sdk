package com.duocuc.serena.ui.theme.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun DashboardScreen(onLogout: () -> Unit) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text("Bienvenido al Dashboard de Serena")
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = onLogout) {
                Text("Cerrar sesión")
            }
        }
    }
}
