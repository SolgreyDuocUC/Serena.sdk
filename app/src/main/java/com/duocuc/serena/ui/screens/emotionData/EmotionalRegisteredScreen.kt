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
import com.duocuc.serena.data.modelData.EmotionalRegister
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
    navController: NavController,
    date: LocalDate,
    viewModel: EmotionalRegisterViewModel = viewModel(factory = ViewModelFactory())
) {
    val registers by viewModel.registers.collectAsState()
    val error by viewModel.error.collectAsState()
    val formatter = DateTimeFormatter.ofPattern("dd MMM yyyy", Locale("es", "ES"))
    var showDialog by remember { mutableStateOf(false) }

    var selectedRegister by remember { mutableStateOf<EmotionalRegister?>(null) }

    val dailyRegisters = registers.filter { LocalDate.parse(it.date).isEqual(date) }

    LaunchedEffect(Unit) { viewModel.loadRegisters() }

    Scaffold(
        bottomBar = { BottomNavBar(navController = navController) },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    selectedRegister = null
                    showDialog = true
                },
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(Icons.Filled.Add, contentDescription = "Agregar nueva emoción", tint = MaterialTheme.colorScheme.onPrimary)
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
                            onView = {
                                selectedRegister = register
                                showDialog = true
                            },
                            onEdit = {
                                selectedRegister = register
                                showDialog = true
                            },
                            onDelete = { /* TODO: Implementar onDelete */ }
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
                isReadOnly = selectedRegister != null,
                onDismiss = { showDialog = false },
                onSave = { emotionId, descriptionText ->
                    if (emotionId != null) {
                        if (selectedRegister == null) {
                            viewModel.registerEmotion(emotionId, descriptionText, date)
                        } else {
                            // TODO: Implementar updateEmotion
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
    register: EmotionalRegister?,
    date: LocalDate,
    isReadOnly: Boolean,
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

    var selectedEmotionId by remember { mutableStateOf(register?.emotion?.id?.toInt()) }
    var description by remember { mutableStateOf(register?.description ?: "") }

    val canEdit = !isReadOnly

    val emotionDisplayed = emotions.find { it.id == selectedEmotionId }

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
                        text = when {
                            canEdit && register == null -> "Agregar Registro"
                            canEdit -> "Editar Registro"
                            else -> "Detalles del Registro"
                        },
                        style = MaterialTheme.typography.titleLarge,
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
                    text = date.format(DateTimeFormatter.ofPattern("EEEE, dd 'de' MMMM", Locale("es", "ES"))).replaceFirstChar { it.uppercase() },
                    style = MaterialTheme.typography.titleSmall.copy(color = MaterialTheme.colorScheme.onSurfaceVariant),
                    modifier = Modifier.padding(top = 4.dp, bottom = 16.dp)
                )

                Text(
                    text = "¿Cómo te sentiste este día?",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(top = 8.dp, bottom = 16.dp)
                )

                if (canEdit) {
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
                } else if (emotionDisplayed != null) {
                    OutlinedCard(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline)
                    ) {
                        Row(
                            modifier = Modifier.padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = emotionDisplayed.emoji,
                                style = MaterialTheme.typography.headlineLarge,
                                modifier = Modifier.padding(end = 12.dp)
                            )
                            Text(
                                text = emotionDisplayed.label,
                                style = MaterialTheme.typography.titleMedium,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                if (canEdit) {
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
                } else if (description.isNotBlank()) {
                    Column(modifier = Modifier.fillMaxWidth().background(MaterialTheme.colorScheme.surfaceVariant, RoundedCornerShape(12.dp)).padding(16.dp)) {
                        Text(
                            text = "Tu Descripción:",
                            style = MaterialTheme.typography.labelLarge,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = description,
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                } else {
                    Text(
                        text = "No se proporcionó una descripción para este registro.",
                        style = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.onSurfaceVariant),
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                if (canEdit) {
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
                } else {
                    Button(
                        onClick = onDismiss,
                        modifier = Modifier.fillMaxWidth().height(48.dp),
                        shape = RoundedCornerShape(12.dp)
                    ) { Text("Cerrar") }
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
    register: EmotionalRegister,
    formatter: DateTimeFormatter,
    onView: () -> Unit,
    onEdit: () -> Unit,
    onDelete: () -> Unit
) {
    val emotionInfo = when (register.emotion.id.toInt()) {
        1 -> Triple("Feliz 😊", "¡Qué bien! Un día lleno de alegría y satisfacción.", 1)
        2 -> Triple("Triste 😢", "Tómate un momento. Está bien sentirse así a veces.", 1)
        3 -> Triple("Neutral 😐", "Un día tranquilo. A veces, la calma es lo que se necesita.", 1)
        4 -> Triple("Enamorado ❤️", "¡El amor está en el aire! Siente esas mariposas.", 1)
        5 -> Triple("Ansioso 😰", "Respira profundo. Recuerda que la ansiedad es temporal.", 1)
        6 -> Triple("Enojado 😠", "Identifica qué te molesta. No dejes que la rabia te controle.", 1)
        else -> Triple("Desconocido 🤔", "Explora tus sentimientos. ¿Qué emoción te define ahora?", 1)
    }

    val emotionLabel = emotionInfo.first
    val accompanyingPhrase = emotionInfo.second

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onView),
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
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = accompanyingPhrase,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = MaterialTheme.colorScheme.primary
                    ),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = register.description ?: "",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    ),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = LocalDate.parse(register.date).format(formatter),
                    style = MaterialTheme.typography.bodySmall.copy(
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