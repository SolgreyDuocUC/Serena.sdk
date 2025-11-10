package com.duocuc.serena.ui.theme.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.duocuc.serena.R
import com.duocuc.serena.factory.ViewModelFactory
import com.duocuc.serena.viewmodel.RegisterViewModel
import com.duocuc.serena.model.UserUiState
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(
    onRegisterSuccess: () -> Unit,
    onNavigateToLogin: () -> Unit,
    registerViewModel: RegisterViewModel = viewModel(factory = ViewModelFactory())
) {
    val uiState: UserUiState by registerViewModel.uiState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    LaunchedEffect(key1 = uiState.isRegistrationSuccessful, key2 = uiState.registrationError) {
        if (uiState.isRegistrationSuccessful) {
            onRegisterSuccess()
        }
        uiState.registrationError?.let {
            scope.launch {
                snackbarHostState.showSnackbar(it)
                registerViewModel.clearRegistrationError()
            }
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { padding ->
        Box(
            modifier = Modifier.fillMaxSize().padding(padding).padding(horizontal = 16.dp),
            contentAlignment = Alignment.Center
        ) {
            if (uiState.isLoading) {
                CircularProgressIndicator()
            } else {
                RegistroFormulario(
                    uiState = uiState,
                    viewModel = registerViewModel,
                    onNavigateToLogin = onNavigateToLogin,
                    onGoogleSuccess = onRegisterSuccess
                )
            }
        }
    }
}

@Composable
private fun RegistroFormulario(
    uiState: UserUiState,
    viewModel: RegisterViewModel,
    onNavigateToLogin: () -> Unit,
    onGoogleSuccess: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier.fillMaxWidth().wrapContentHeight()
    ) {
        Text("Crear una cuenta", style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = uiState.userName,
            onValueChange = viewModel::onUserNameChange,
            label = { Text("Nombre de usuario") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            isError = uiState.userNameError != null,
            supportingText = {
                if (uiState.userNameError != null) {
                    Text(uiState.userNameError!!)
                }
            },
            trailingIcon = {
                if (uiState.userNameError != null) {
                    Icon(Icons.Filled.Warning, contentDescription = "Error", tint = MaterialTheme.colorScheme.error)
                }
            }
        )

        OutlinedTextField(
            value = uiState.userEmail,
            onValueChange = viewModel::onUserEmailChange,
            label = { Text("Correo electrónico") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            isError = uiState.userEmailError != null,
            supportingText = {
                if (uiState.userEmailError != null) {
                    Text(uiState.userEmailError!!)
                }
            },
            trailingIcon = {
                if (uiState.userEmailError != null) {
                    Icon(Icons.Filled.Warning, contentDescription = "Error", tint = MaterialTheme.colorScheme.error)
                }
            }
        )

        OutlinedTextField(
            value = uiState.userPassword,
            onValueChange = viewModel::onUserPasswordChange,
            label = { Text("Contraseña") },
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            isError = uiState.userPasswordError != null,
            supportingText = {
                if (uiState.userPasswordError != null) {
                    Text(uiState.userPasswordError!!)
                }
            },
            trailingIcon = {
                if (uiState.userPasswordError != null) {
                    Icon(Icons.Filled.Warning, contentDescription = "Error", tint = MaterialTheme.colorScheme.error)
                }
            }
        )

        OutlinedTextField(
            value = uiState.userRepeatPassword,
            onValueChange = viewModel::onUserRepeatPasswordChange,
            label = { Text("Confirmar contraseña") },
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            isError = uiState.userRepeatPasswordError != null,
            supportingText = {
                if (uiState.userRepeatPasswordError != null) {
                    Text(uiState.userRepeatPasswordError!!)
                }
            },
            trailingIcon = {
                if (uiState.userRepeatPasswordError != null) {
                    Icon(Icons.Filled.Warning, contentDescription = "Error", tint = MaterialTheme.colorScheme.error)
                }
            }
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = uiState.userAcceptConditions,
                onCheckedChange = viewModel::onUserAcceptConditionsChange
            )
            Text(
                text = "Acepto los términos y condiciones",
                modifier = Modifier.padding(start = 8.dp)
            )
        }


        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = viewModel::register,
            modifier = Modifier.fillMaxWidth().height(50.dp),
            enabled = uiState.isFormValid && !uiState.isLoading
        ) {
            Text("Registrarse")
        }

        TextButton(onClick = onNavigateToLogin) {
            Text("¿Ya tienes una cuenta? Inicia sesión")
        }

        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "O", style = MaterialTheme.typography.labelMedium)
        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .border(
                    width = 1.dp,
                    color = Color(0xFFDDDDDD),
                    shape = RoundedCornerShape(8.dp)
                )
                .background(Color.White, shape = RoundedCornerShape(8.dp))
                .clickable {
                    onGoogleSuccess()
                },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(modifier = Modifier.width(12.dp))
            Image(
                painter = painterResource(id = R.mipmap.ic_google_launcher_foreground),
                contentDescription = "Logo de Google",
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = "Continuar con Google",
                color = Color.Black,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Center,
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(12.dp))
        }
    }
}