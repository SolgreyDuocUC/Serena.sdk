package com.duocuc.serena.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import android.util.Patterns

// Estado de la interfaz de usuario para la pantalla de registro.
data class RegisterUiState(
    val isLoading: Boolean = false,
    val registrationSuccess: Boolean = false,
    val error: String? = null
)

class RegisterViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(RegisterUiState())
    val uiState = _uiState.asStateFlow()

    fun register(username: String, email: String, password: String, confirmPassword: String) {
        // Iniciar el estado de carga
        _uiState.update { it.copy(isLoading = true, error = null) }

        // Validaciones
        if (username.isBlank() || email.isBlank() || password.isBlank()) {
            _uiState.update { it.copy(isLoading = false, error = "Todos los campos son obligatorios") }
            return
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _uiState.update { it.copy(isLoading = false, error = "El formato del correo no es válido") }
            return
        }

        if (password != confirmPassword) {
            _uiState.update { it.copy(isLoading = false, error = "Las contraseñas no coinciden") }
            return
        }
        
        // Simular llamada a la red o base de datos
        viewModelScope.launch {
            kotlinx.coroutines.delay(2000) // Simula una operación de red de 2 segundos

            // Aquí iría la lógica real para registrar al usuario en tu backend o BD.
            // Por ahora, simplemente simulamos el éxito.

            _uiState.update { it.copy(isLoading = false, registrationSuccess = true) }
        }
    }
}