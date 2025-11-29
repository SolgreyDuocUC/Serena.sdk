package com.duocuc.serena.viewmodel.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.duocuc.serena.data.UserData
import com.duocuc.serena.model.ProfileUiState
import com.duocuc.serena.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val userRepository: UserRepository,
    private val sessionViewModel: SessionViewModel
) : ViewModel() {

    private val _uiState = MutableStateFlow(ProfileUiState())
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            sessionViewModel.activeUser.collectLatest { user ->
                if (user != null) {
                    _uiState.update {
                        it.copy(name = user.userName ?: "", email = user.userEmail)
                    }
                }
            }
        }
    }

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

    fun saveChanges() {
        viewModelScope.launch {
            val currentUser = sessionViewModel.activeUser.value
            if (currentUser == null) {
                _uiState.update { it.copy(errorMessage = "No se pudo obtener el usuario actual") }
                return@launch
            }

            if (_uiState.value.oldPassword.isNotEmpty() && _uiState.value.oldPassword != currentUser.userPassword) {
                _uiState.update { it.copy(errorMessage = "La contraseña actual es incorrecta") }
                return@launch
            }

            val updatedUser = currentUser.copy(
                userName = _uiState.value.name,
                userEmail = _uiState.value.email,
                userPassword = if (_uiState.value.newPassword.isNotEmpty()) _uiState.value.newPassword else currentUser.userPassword
            )

            try {
                userRepository.updateUser(updatedUser)
                _uiState.update { it.copy(saveSuccess = true, snackbarMessage = "Cambios guardados correctamente") }
            } catch (e: Exception) {
                _uiState.update { it.copy(errorMessage = e.message) }
            }
        }
    }

    fun clearSnackbar() {
        _uiState.update { it.copy(snackbarMessage = null) }
    }
}