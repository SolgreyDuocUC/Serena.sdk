package com.duocuc.serena.ui.theme.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.duocuc.serena.data.EmotionalRegisterData
import com.duocuc.serena.factory.ViewModelFactory
import com.duocuc.serena.viewmodel.EmotionalRegisterViewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmotionalRegisteredScreen(
    viewModel: EmotionalRegisterViewModel = viewModel(factory = ViewModelFactory())
) {
    val registers by viewModel.registers.collectAsState()
    val error by viewModel.error.collectAsState()
    val formatter = DateTimeFormatter.ofPattern("dd MMM yyyy")
    var showDialog by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.loadRegisters()
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showDialog = true },
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(Icons.Filled.Add, contentDescription = "Agregar emoción", tint = MaterialTheme.colorScheme.onPrimary)
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(MaterialTheme.colorScheme.background)
                .padding(horizontal = 20.dp, vertical = 16.dp)
        ) {
            Text(
                text = "Registro Emocional",
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp),
                textAlign = TextAlign.Center
            )

            if (error != null) {
                Text(
                    text = "Error: $error",
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
            }

            if (registers.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(24.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Text(
                            text = "Aún no has registrado emociones.",
                            style = MaterialTheme.typography.bodyLarge.copy(
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            ),
                            textAlign = TextAlign.Center
                        )

                        Button(
                            onClick = { showDialog = true },
                            shape = MaterialTheme.shapes.medium,
                            modifier = Modifier.height(48.dp)
                        ) {
                            Text("Agregar primera emoción")
                        }
                    }
                }
            } else {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 8.dp)
                ) {
                    items(registers) { register ->
                        EmotionCard(register, formatter)
                    }
                    item { Spacer(modifier = Modifier.height(80.dp)) }
                }
            }
        }

        if (showDialog) {
            EmotionDialog(
                onDismiss = { showDialog = false },
                onSave = { selectedEmotionId ->
                    if (selectedEmotionId != null) {
                        viewModel.registerEmotion(
                            idEmocion = selectedEmotionId,
                            fecha = LocalDate.now()
                        )
                    }
                    showDialog = false
                }
            )
        }
    }
}

@Composable
fun EmotionDialog(
    onDismiss: () -> Unit,
    onSave: (Int?) -> Unit
) {
    var selectedEmotion by remember { mutableStateOf<Int?>(null) }

    val emotions = listOf(
        1 to "Feliz 😊",
        2 to "Triste 😢",
        3 to "Ansioso 😰",
        4 to "Enojado 😡",
        5 to "Tranquilo 😌",
        6 to "Motivado 💪"
    )

    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {},
        text = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Selecciona tu emoción de hoy",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                    modifier = Modifier.padding(bottom = 12.dp)
                )

                Row(
                    modifier = Modifier
                        .horizontalScroll(rememberScrollState())
                        .padding(bottom = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    emotions.forEach { (id, label) ->
                        val isSelected = selectedEmotion == id
                        Box(
                            modifier = Modifier
                                .size(70.dp)
                                .clip(CircleShape)
                                .background(
                                    if (isSelected) MaterialTheme.colorScheme.primaryContainer
                                    else MaterialTheme.colorScheme.surfaceVariant
                                )
                                .clickable { selectedEmotion = id },
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = label.split(" ")[1],
                                fontSize = MaterialTheme.typography.headlineSmall.fontSize
                            )
                        }
                    }
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    TextButton(onClick = onDismiss) { Text("Cancelar") }
                    Button(
                        onClick = { onSave(selectedEmotion) },
                        enabled = selectedEmotion != null
                    ) {
                        Text("Guardar")
                    }
                }
            }
        },
        containerColor = MaterialTheme.colorScheme.surface,
        shape = MaterialTheme.shapes.large
    )
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun EmotionCard(register: EmotionalRegisterData, formatter: DateTimeFormatter) {
    val emotionLabel = when (register.idEmocion) {
        1 -> "Feliz 😊"
        2 -> "Triste 😢"
        3 -> "Ansioso 😰"
        4 -> "Enojado 😡"
        5 -> "Tranquilo 😌"
        6 -> "Motivado 💪"
        else -> "Desconocido 🤔"
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { },
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp)
        ) {
            Text(
                text = emotionLabel,
                style = MaterialTheme.typography.titleMedium.copy(
                    color = MaterialTheme.colorScheme.onSurface,
                    fontWeight = FontWeight.Medium
                )
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = register.fecha.format(formatter),
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            )
        }
    }
}
