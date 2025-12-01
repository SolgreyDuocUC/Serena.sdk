package com.duocuc.serena.ui.screens.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
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
import com.duocuc.serena.model.LoginUiState
import com.duocuc.serena.viewmodel.auth.LoginViewModel
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(
    onLoginSuccess: () -> Unit,
    onNavigateToRegister: () -> Unit,
    loginViewModel: LoginViewModel = viewModel(factory = ViewModelFactory())
) {
    val uiState: LoginUiState by loginViewModel.uiState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    LaunchedEffect(key1 = uiState.isLoginSuccessful, key2 = uiState.loginError) {
        if (uiState.isLoginSuccessful) {
            onLoginSuccess()
        }
        uiState.loginError?.let {
            scope.launch {
                snackbarHostState.showSnackbar(it)
                loginViewModel.clearLoginError()
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
            if (uiState.isLoading) {
                CircularProgressIndicator()
            } else {
                LoginForm(uiState, loginViewModel, onLoginSuccess, onNavigateToRegister)
            }
        }
    }
}

@Composable
private fun LoginForm(
    uiState: LoginUiState,
    viewModel: LoginViewModel,
    onLoginSuccess: () -> Unit,
    onNavigateToRegister: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(0.9f),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Column(
            modifier = Modifier.padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(8.dp))
            Image(
                painter = painterResource(id = R.mipmap.ic_google_launcher_foreground),
                contentDescription = "Logo Serena",
                modifier = Modifier.size(80.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Bienvenido",
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Inicia sesión en Serena",
                color = Color.Gray,
                fontSize = 16.sp
            )

            Spacer(modifier = Modifier.height(24.dp))

            OutlinedTextField(
                value = uiState.userEmail,
                onValueChange = viewModel::onUserEmailChange,
                label = { Text("Correo electrónico") },
                modifier = Modifier.fillMaxWidth().testTag("LoginEmailField"),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                isError = uiState.userEmailError != null,
                supportingText = {
                    if (uiState.userEmailError != null) {
                        Text(uiState.userEmailError!!)
                    }
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = uiState.userPassword,
                onValueChange = viewModel::onUserPasswordChange,
                label = { Text("Contraseña") },
                modifier = Modifier.fillMaxWidth().testTag("LoginPasswordField"),
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                isError = uiState.userPasswordError != null,
                supportingText = {
                    if (uiState.userPasswordError != null) {
                        Text(uiState.userPasswordError!!)
                    }
                }
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = viewModel::login, // Llama a la lógica centralizada
                modifier = Modifier.fillMaxWidth().testTag("LoginButton"),
                enabled = uiState.isFormValid && !uiState.isLoading
            ) {
                Text("Iniciar sesión")
            }

            Spacer(modifier = Modifier.height(24.dp))

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
                    .clickable { onLoginSuccess() }, // Navegación directa
                verticalAlignment = Alignment.CenterVertically
            ) {
                Spacer(modifier = Modifier.width(12.dp))
                Image(
                    painter = painterResource(id = R.mipmap.ic_google_launcher_foreground),
                    contentDescription = "Google logo",
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

            Spacer(modifier = Modifier.height(8.dp))
            TextButton(onClick = onNavigateToRegister) {
                Text("Crear cuenta nueva")
            }
        }
    }
}
