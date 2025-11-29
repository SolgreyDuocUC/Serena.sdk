package com.duocuc.serena.viewmodel.profile

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.duocuc.serena.model.ProfileUiState
import com.duocuc.serena.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import androidx.core.net.toUri

class ProfileViewModel(
    private val userRepository: UserRepository,
    private val sessionViewModel: SessionViewModel
) : ViewModel() {

    private val _uiState = MutableStateFlow(ProfileUiState())
    val uiState = _uiState.asStateFlow()

    init {
        // Cargar los datos del usuario activo
        viewModelScope.launch {
            sessionViewModel.activeUser.collectLatest { user ->
                if (user != null) {
                    _uiState.update {
                        it.copy(
                            name = user.userName ?: "",
                            email = user.userEmail,
                            imageUri = user.userImageUri?.toUri()
                        )
                    }
                }
            }
        }
    }

    // Actualizar campos del formulario
    fun onNameChange(name: String) {
        _uiState.update { it.copy(name = name) }
    }

    fun onEmailChange(email: String) {
        _uiState.update { it.copy(email = email) }
    }

    fun onOldPasswordChange(password: String) {
        _uiState.update { it.copy(oldPassword = password) }
    }

    fun onNewPasswordChange(password: String) {
        _uiState.update { it.copy(newPassword = password) }
    }

    fun onImageChange(uri: Uri) {
        _uiState.update { it.copy(imageUri = uri) }
    }

    // Guardar los cambios en el repositorio
    fun saveChanges() {
        viewModelScope.launch {
            val currentUser = sessionViewModel.activeUser.value
            if (currentUser == null) {
                _uiState.update { it.copy(errorMessage = "No se pudo obtener el usuario actual") }
                return@launch
            }

            // Validar contraseña actual si se ingresó
            if (_uiState.value.oldPassword.isNotEmpty() &&
                _uiState.value.oldPassword != currentUser.userPassword
            ) {
                _uiState.update { it.copy(errorMessage = "La contraseña actual es incorrecta") }
                return@launch
            }

            // Convertir la Uri a String para guardarla en la base de datos
            val imageUriString = _uiState.value.imageUri?.toString()

            val updatedUser = currentUser.copy(
                userName = _uiState.value.name,
                userEmail = _uiState.value.email,
                userPassword = _uiState.value.newPassword.ifEmpty { currentUser.userPassword },
                userImageUri = imageUriString
            )

            try {
                userRepository.updateUser(updatedUser)
                _uiState.update {
                    it.copy(
                        saveSuccess = true,
                        snackbarMessage = "Cambios guardados correctamente",
                        oldPassword = "",
                        newPassword = ""
                    )
                }
            } catch (e: Exception) {
                _uiState.update { it.copy(errorMessage = e.message) }
            }
        }
    }

    fun clearSnackbar() {
        _uiState.update { it.copy(snackbarMessage = null) }
    }
}
