package com.duocuc.serena.ui.theme.ui.components

import androidx.compose.runtime.Composable

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

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
        Text(
            text = "Cargando Serena...",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )
    }
}
