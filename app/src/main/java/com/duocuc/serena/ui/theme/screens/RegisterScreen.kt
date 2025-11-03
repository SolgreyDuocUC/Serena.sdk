package com.duocuc.serena.ui.theme.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.duocuc.serena.viewmodel.RegisterViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(
    onRegisterSuccess: () -> Unit,
    onNavigateToLogin: () -> Unit,
    registerViewModel: RegisterViewModel = viewModel()
) {

    val uiState by registerViewModel.uiState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    LaunchedEffect(key1 = uiState) {
        if (uiState.registroExitoso) { // Nombre actualizado
            onRegisterSuccess()
        }
        uiState.error?.let {
            scope.launch {
                snackbarHostState.showSnackbar(it)
            }
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { padding ->
        Box(
            modifier = Modifier.fillMaxSize().padding(padding).padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            if (uiState.estaCargando) { // Nombre actualizado
                CircularProgressIndicator()
            } else {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text("Crear una cuenta", style = MaterialTheme.typography.headlineMedium)

                    Spacer(modifier = Modifier.height(16.dp))

                    OutlinedTextField(
                        value = uiState.nombreUsuario, // PASO 2: Valor viene del ViewModel
                        onValueChange = { registerViewModel.onNombreUsuarioChange(it) }, // PASO 3: Notifica al ViewModel
                        label = { Text("Nombre de usuario") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth(),
                        isError = uiState.error != null
                    )

                    OutlinedTextField(
                        value = uiState.correo,
                        onValueChange = { registerViewModel.onCorreoChange(it) },
                        label = { Text("Correo electrónico") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth(),
                        isError = uiState.error != null
                    )

                    OutlinedTextField(
                        value = uiState.contrasena,
                        onValueChange = { registerViewModel.onContrasenaChange(it) },
                        label = { Text("Contraseña") },
                        visualTransformation = PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth(),
                        isError = uiState.error != null
                    )

                    OutlinedTextField(
                        value = uiState.confirmarContrasena,
                        onValueChange = { registerViewModel.onConfirmarContrasenaChange(it) },
                        label = { Text("Confirmar contraseña") },
                        visualTransformation = PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth(),
                        isError = uiState.error != null
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    Button(
                        onClick = { registerViewModel.registrar() }, // PASO 4: El ViewModel ya tiene los datos
                        modifier = Modifier.fillMaxWidth().height(50.dp)
                    ) {
                        Text("Registrarse")
                    }

                    TextButton(onClick = onNavigateToLogin) {
                        Text("¿Ya tienes una cuenta? Inicia sesión")
                    }
                }
            }
        }
    }
}