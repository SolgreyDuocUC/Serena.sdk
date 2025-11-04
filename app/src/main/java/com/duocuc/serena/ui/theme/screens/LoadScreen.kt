package com.duocuc.serena.ui.theme.screens

import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp // Necesario para Spacer
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

// Componentes para el Icono
import androidx.compose.material.icons.Icons // Importa la fuente de iconos
import androidx.compose.material.icons.filled.Star // El icono que quieres usar
import androidx.compose.material3.Icon // El componente Icon
import androidx.compose.ui.graphics.Color // Para el color del icono

@Composable
fun SplashScreen(onComplete: () -> Unit) {
    LaunchedEffect(Unit) {
        delay(1500)
        onComplete()
    }
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = Icons.Filled.Star,
                contentDescription = "Logo de la aplicación",
                modifier = Modifier.size(72.dp),
                tint = Color(0xFF6200EE)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Cargando Serena...",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}