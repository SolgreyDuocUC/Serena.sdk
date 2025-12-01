@file:Suppress("DEPRECATION")

package com.duocuc.serena.ui.screens.emotionData

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.duocuc.serena.data.dataModel.EmotionalRegisterData
import com.duocuc.serena.factory.ViewModelFactory
import com.duocuc.serena.ui.screens.BottomNavBar
import com.duocuc.serena.ui.theme.theme.*
import com.duocuc.serena.viewmodel.emotionData.EmotionalRegisterViewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

// ------------------------- SCREEN ------------------------

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmotionalRegisteredScreen(
    navController: NavController, // Se añade NavController
    date: LocalDate,
    viewModel: EmotionalRegisterViewModel = viewModel(factory = ViewModelFactory())
) {
    val registers by viewModel.registers.collectAsState()
    val error by viewModel.error.collectAsState()
    val formatter = DateTimeFormatter.ofPattern("dd MMM yyyy", Locale("es", "ES"))
    var showDialog by remember { mutableStateOf(false) }
    var selectedRegister by remember { mutableStateOf<EmotionalRegisterData?>(null) }

    // Filtramos los registros para mostrar solo los de la fecha seleccionada
    val dailyRegisters = registers.filter { it.fecha.isEqual(date) }

    LaunchedEffect(Unit) { viewModel.loadRegisters() }

    Scaffold(
        bottomBar = { BottomNavBar(navController = navController) }, // Añadir BottomNavBar
        floatingActionButton = {
            // Permitir agregar solo si no hay registro para el día seleccionado
            if (dailyRegisters.isEmpty() || selectedRegister != null) {
                FloatingActionButton(
                    onClick = {
                        selectedRegister = dailyRegisters.firstOrNull() // Si ya existe, se edita
                        showDialog = true
                    },
                    containerColor = MaterialTheme.colorScheme.primary
                ) {
                    Icon(Icons.Filled.Add, contentDescription = "Agregar emoción", tint = MaterialTheme.colorScheme.onPrimary)
                }
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
                text = "Diario Emocional",
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                textAlign = TextAlign.Center
            )
            Text(
                text = date.format(DateTimeFormatter.ofPattern("EEEE, dd 'de' MMMM", Locale("es", "ES"))).replaceFirstChar { it.uppercase() },
                style = MaterialTheme.typography.titleMedium.copy(color = MaterialTheme.colorScheme.onSurfaceVariant),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
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

            // Mostrar sólo el registro del día seleccionado
            if (dailyRegisters.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize().padding(24.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(16.dp)) {
                        Text(
                            text = "Aún no has registrado emociones para este día.",
                            style = MaterialTheme.typography.bodyLarge.copy(color = MaterialTheme.colorScheme.onSurfaceVariant),
                            textAlign = TextAlign.Center
                        )
                        Button(
                            onClick = {
                                selectedRegister = null
                                showDialog = true
                            },
                            shape = MaterialTheme.shapes.medium,
                            modifier = Modifier.defaultMinSize(minHeight = 48.dp)
                        ) { Text("Agregar emoción") }
                    }
                }
            } else {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.fillMaxSize().padding(top = 8.dp)
                ) {
                    items(dailyRegisters) { register ->
                        EmotionCard(
                            register = register,
                            formatter = formatter,
                            onEdit = {
                                selectedRegister = register
                                showDialog = true
                            },
                            onDelete = { viewModel.deleteEmotion(register.id) }
                        )
                    }
                    item { Spacer(modifier = Modifier.height(80.dp)) }
                }
            }
        }

        if (showDialog) {
            EmotionalDataDialog(
                register = selectedRegister,
                date = date,
                onDismiss = { showDialog = false },
                onSave = { emotionId, descriptionText ->
                    if (emotionId != null) {
                        if (selectedRegister == null) {
                            // Nuevo registro con la fecha seleccionada
                            viewModel.registerEmotion(emotionId, descriptionText, date)
                        } else {
                            // Actualizar el registro existente
                            viewModel.updateEmotion(selectedRegister!!.id, emotionId, descriptionText, date)
                        }
                    }
                    showDialog = false
                }
            )
        }
    }
}

// ------------------------- DIALOG Y COMPONENTES ------------------------

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun EmotionalDataDialog(
    register: EmotionalRegisterData?,
    date: LocalDate,
    onDismiss: () -> Unit,
    onSave: (Int?, String) -> Unit
) {
    val emotions = listOf(
        EmotionInfo(1, "Feliz", "😊", LightSuccess),
        EmotionInfo(2, "Triste", "😢", LightPrimary),
        EmotionInfo(3, "Neutral", "😐", LightMutedForeground),
        EmotionInfo(4, "Enamorado", "❤️", LightAccent),
        EmotionInfo(5, "Ansioso", "😰", LightSecondary),
        EmotionInfo(6, "Enojado", "😠", LightDestructive)
    )

    var selectedEmotionId by remember { mutableStateOf(register?.idEmocion) }
    var description by remember { mutableStateOf(register?.descripcion ?: "") }

    Dialog(onDismissRequest = onDismiss) {
        Card(
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
        ) {
            Column(
                modifier = Modifier.padding(20.dp)
            ) {
                Box(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = date.format(DateTimeFormatter.ofPattern("EEEE, dd 'de' MMMM", Locale("es", "ES"))).replaceFirstChar { it.uppercase() },
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.align(Alignment.CenterStart),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    IconButton(
                        onClick = onDismiss,
                        modifier = Modifier.align(Alignment.CenterEnd)
                    ) {
                        Icon(Icons.Default.Close, contentDescription = "Cerrar")
                    }
                }

                Text(
                    text = "¿Cómo te sentiste este día?",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(top = 8.dp, bottom = 16.dp)
                )

                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                        emotions.take(3).forEach { emotion ->
                            EmotionButton(
                                emotion = emotion,
                                isSelected = selectedEmotionId == emotion.id,
                                onClick = { selectedEmotionId = emotion.id },
                                modifier = Modifier.weight(1f)
                            )
                        }
                    }
                    Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                        emotions.drop(3).forEach { emotion ->
                            EmotionButton(
                                emotion = emotion,
                                isSelected = selectedEmotionId == emotion.id,
                                onClick = { selectedEmotionId = emotion.id },
                                modifier = Modifier.weight(1f)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text("Describe tu día (opcional)") },
                    placeholder = { Text("¿Qué pasó? ¿Qué te hizo sentir así?") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(min = 90.dp, max = 150.dp),
                    shape = RoundedCornerShape(12.dp),
                    maxLines = 5
                )

                Spacer(modifier = Modifier.height(24.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Button(
                        onClick = { onSave(selectedEmotionId, description.trim()) },
                        enabled = selectedEmotionId != null,
                        modifier = Modifier
                            .weight(1f)
                            .height(48.dp),
                        shape = RoundedCornerShape(12.dp)
                    ) { Text("Guardar") }
                    OutlinedButton(
                        onClick = onDismiss,
                        modifier = Modifier
                            .weight(1f)
                            .height(48.dp),
                        shape = RoundedCornerShape(12.dp)
                    ) { Text("Cancelar") }
                }
            }
        }
    }
}

@Composable
fun EmotionButton(
    emotion: EmotionInfo,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val backgroundColor = if (isSelected) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surfaceVariant
    val borderColor = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline

    OutlinedCard(
        modifier = modifier
            .aspectRatio(1f)
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = backgroundColor),
        border = BorderStroke(if (isSelected) 2.dp else 1.dp, borderColor)
    ) {
        Column(
            modifier = Modifier.fillMaxSize().padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = emotion.emoji,
                style = MaterialTheme.typography.headlineLarge,
                maxLines = 1
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = emotion.label,
                color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.labelMedium,
                textAlign = TextAlign.Center,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

data class EmotionInfo(val id: Int, val label: String, val emoji: String, val color: Color)

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun EmotionCard(
    register: EmotionalRegisterData,
    formatter: DateTimeFormatter,
    onEdit: () -> Unit,
    onDelete: () -> Unit
) {
    val emotionLabel = when (register.idEmocion) {
        1 -> "Feliz 😊"
        2 -> "Triste 😢"
        3 -> "Neutral 😐"
        4 -> "Enamorado ❤️"
        5 -> "Ansioso 😰"
        6 -> "Enojado 😠"
        else -> "Desconocido 🤔"
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = emotionLabel,
                    style = MaterialTheme.typography.titleMedium.copy(
                        color = MaterialTheme.colorScheme.onSurface,
                        fontWeight = FontWeight.Medium
                    )
                )
                Spacer(modifier = Modifier.height(4.dp))
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = register.fecha.format(formatter),
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                )
            }
            Row {
                IconButton(onClick = onEdit) { Icon(Icons.Filled.Edit, contentDescription = "Editar") }
                IconButton(onClick = onDelete) { Icon(Icons.Filled.Delete, contentDescription = "Eliminar") }
            }
        }
    }
}
