package com.duocuc.serena.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import android.util.Patterns

// 1. EstadoRegistroUi CONTIENE TODO EL ESTADO DE LA PANTALLA
data class EstadoRegistroUi(
    // Estado de los campos del formulario
    val nombreUsuario: String = "",
    val correo: String = "",
    val contrasena: String = "",
    val confirmarContrasena: String = "",

    // Estado de los eventos de la UI
    val estaCargando: Boolean = false,
    val registroExitoso: Boolean = false,
    val error: String? = null
)

class RegisterViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(EstadoRegistroUi())
    val uiState = _uiState.asStateFlow()

    // 2. FUNCIONES PARA MANEJAR LOS CAMBIOS EN LA UI
    fun onNombreUsuarioChange(nombreUsuario: String) {
        _uiState.update { it.copy(nombreUsuario = nombreUsuario) }
    }

    fun onCorreoChange(correo: String) {
        _uiState.update { it.copy(correo = correo) }
    }

    fun onContrasenaChange(contrasena: String) {
        _uiState.update { it.copy(contrasena = contrasena) }
    }

    fun onConfirmarContrasenaChange(confirmarContrasena: String) {
        _uiState.update { it.copy(confirmarContrasena = confirmarContrasena) }
    }

    // 3. LA LÓGICA DE REGISTRO AHORA USA EL ESTADO INTERNO
    fun registrar() {
        _uiState.update { it.copy(estaCargando = true, error = null) }

        val estadoActual = _uiState.value

        if (estadoActual.nombreUsuario.isBlank() || estadoActual.correo.isBlank() || estadoActual.contrasena.isBlank()) {
            _uiState.update { it.copy(estaCargando = false, error = "Todos los campos son obligatorios") }
            return
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(estadoActual.correo).matches()) {
            _uiState.update { it.copy(estaCargando = false, error = "El formato del correo no es válido") }
            return
        }

        if (estadoActual.contrasena != estadoActual.confirmarContrasena) {
            _uiState.update { it.copy(estaCargando = false, error = "Las contraseñas no coinciden") }
            return
        }
        
        viewModelScope.launch {
            kotlinx.coroutines.delay(2000)
            _uiState.update { it.copy(estaCargando = false, registroExitoso = true) }
        }
    }
}