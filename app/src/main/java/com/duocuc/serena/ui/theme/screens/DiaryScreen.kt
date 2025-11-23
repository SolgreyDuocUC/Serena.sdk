package com.duocuc.serena.ui.theme.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.Stop
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import java.time.LocalDate
import java.time.format.DateTimeFormatter

// --- Composable Principal (Diario Simple) ---

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SimpleDiaryEntryScreen() {
    // Estado interno (solo para que los campos funcionen en la preview)
    var title by remember { mutableStateOf("") }
    var content by remember { mutableStateOf("") }
    var isRecording by remember { mutableStateOf(false) }

    // Fecha actual simplificada
    val today = LocalDate.now().format(DateTimeFormatter.ofPattern("EEEE, dd MMMM"))

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Mi Diario", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = { /* Volver */ }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        },
        bottomBar = {
            BottomAppBar(
                modifier = Modifier.height(72.dp),
                containerColor = MaterialTheme.colorScheme.primaryContainer
            ) {
                // Botón de Guardar (simulado)
                Button(
                    onClick = { /* Acción de Guardar */ },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("Guardar Día")
                }
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            // Fecha
            Text(
                text = "Hoy, $today",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(bottom = 24.dp)
            )

            // 1. Título del Día
            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Título de mi día") },
                modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
                singleLine = true
            )

            // 2. Describir el día (Contenido)
            OutlinedTextField(
                value = content,
                onValueChange = { content = it },
                label = { Text("Describir cómo fue mi día...") },
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 180.dp) // Define la altura mínima
                    .padding(bottom = 24.dp)
            )

            // 3. Botón Simulado de Dictado por Voz
            DictationButtonSection(
                isRecording = isRecording,
                onToggleRecording = { isRecording = it }
            )
        }
    }
}

// --- Componente para el Dictado por Voz ---

@Composable
fun DictationButtonSection(
    isRecording: Boolean,
    onToggleRecording: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(top = 16.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (isRecording) {
            Text("¡Dictando por voz!", color = MaterialTheme.colorScheme.error, fontWeight = FontWeight.SemiBold)
            Spacer(modifier = Modifier.width(16.dp))
        }

        Button(
            onClick = {
                // Simula el inicio/fin de la grabación
                onToggleRecording(!isRecording)
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = if (isRecording) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary
            ),
            shape = CircleShape,
            modifier = Modifier.size(64.dp)
        ) {
            Icon(
                if (isRecording) Icons.Filled.Stop else Icons.Filled.Mic,
                contentDescription = "Dictado por Voz",
                modifier = Modifier.size(32.dp)
            )
        }
    }
}

// --- Previsualización (Previewer) ---

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun SimpleDiaryEntryScreenPreview() {
    MaterialTheme {
        SimpleDiaryEntryScreen()
    }
}