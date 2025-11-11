package com.duocuc.serena.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.duocuc.serena.model.LoginUiState
import com.duocuc.serena.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginViewModel(
    private val userRepository: UserRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    fun onUserEmailChange(newEmail: String) {
        _uiState.update { currentState ->
            val emailRegex = "^[A-Za-z](.*)(@)(.+)(\\.)(.+)"
            currentState.copy(
                userEmail = newEmail,
                userEmailError = if (newEmail.matches(emailRegex.toRegex())) null else "Formato de email no válido"
            )
        }
    }

    fun onUserPasswordChange(newPassword: String) {
        _uiState.update { currentState ->
            // Validación mínima para el login
            val error = if (newPassword.length >= 8) null else "La contraseña debe tener al menos 8 caracteres"
            currentState.copy(
                userPassword = newPassword,
                userPasswordError = error
            )
        }
    }

    fun clearLoginError() {
        _uiState.update { it.copy(loginError = null) }
    }
    private fun validateAllFields(currentState: LoginUiState): LoginUiState {
        val emailRegex = "^[A-Za-z](.*)(@)(.+)(\\.)(.+)"
        val emailError = when {
            currentState.userEmail.isEmpty() -> "El email no puede estar vacío"
            !currentState.userEmail.matches(emailRegex.toRegex()) -> "Formato de email no válido"
            else -> null
        }

        val passwordError = when {
            currentState.userPassword.isEmpty() -> "La contraseña no puede estar vacía"
            currentState.userPassword.length < 8 -> "La contraseña debe tener al menos 8 caracteres"
            else -> null
        }

        return currentState.copy(
            userEmailError = emailError,
            userPasswordError = passwordError
        )
    }

    fun login() {
        _uiState.update { currentState ->
            validateAllFields(currentState)
        }

        val state = _uiState.value

        if (!state.isFormValid) {
            return
        }

        _uiState.update { it.copy(isLoading = true, loginError = null) }

        viewModelScope.launch {
            try {
                val result = userRepository.loginUser(state.userEmail, state.userPassword)

                if (result.isSuccess && result.getOrNull() != null) {
                    _uiState.update { it.copy(
                        isLoginSuccessful = true,
                        isLoading = false
                    )}
                } else {
                    _uiState.update { it.copy(
                        loginError = result.exceptionOrNull()?.message ?: "Credenciales incorrectas.",
                        isLoading = false
                    )}
                }

            } catch (e: Exception) {
                _uiState.update { it.copy(
                    loginError = e.message ?: "Error de red o conexión.",
                    isLoading = false
                )}
            }
        }
    }
}