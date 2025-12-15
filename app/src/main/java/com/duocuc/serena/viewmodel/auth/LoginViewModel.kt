package com.duocuc.serena.viewmodel.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.duocuc.serena.model.LoginUiState
import com.duocuc.serena.data.remote.user.Auth.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginViewModel(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    fun onUserEmailChange(newEmail: String) {
        _uiState.update {
            it.copy(
                userEmail = newEmail,
                userEmailError = if (newEmail.matches("^[A-Za-z](.*)(@)(.+)(\\.)(.+)".toRegex()))
                    null else "Formato de email no válido"
            )
        }
    }

    fun onUserPasswordChange(newPassword: String) {
        _uiState.update {
            it.copy(
                userPassword = newPassword,
                userPasswordError =
                    if (newPassword.length >= 8) null else "La contraseña debe tener al menos 8 caracteres"
            )
        }
    }

    fun clearLoginError() {
        _uiState.update { it.copy(loginError = null) }
    }

    private fun validateAllFields(state: LoginUiState): LoginUiState {
        val emailError = when {
            state.userEmail.isEmpty() -> "El email no puede estar vacío"
            !state.userEmail.matches("^[A-Za-z](.*)(@)(.+)(\\.)(.+)".toRegex()) ->
                "Formato de email no válido"
            else -> null
        }

        val passwordError = when {
            state.userPassword.isEmpty() -> "La contraseña no puede estar vacía"
            state.userPassword.length < 8 ->
                "La contraseña debe tener al menos 8 caracteres"
            else -> null
        }

        return state.copy(
            userEmailError = emailError,
            userPasswordError = passwordError
        )
    }

    fun login() {
        _uiState.update { validateAllFields(it) }

        val state = _uiState.value
        if (!state.isFormValid) return

        _uiState.update { it.copy(isLoading = true, loginError = null) }

        viewModelScope.launch {
            val result = authRepository.login(
                email = state.userEmail,
                password = state.userPassword
            )

            if (result.isSuccess) {
                _uiState.update {
                    it.copy(
                        isLoginSuccessful = true,
                        isLoading = false
                    )
                }
            } else {
                _uiState.update {
                    it.copy(
                        loginError = result.exceptionOrNull()?.message
                            ?: "Credenciales incorrectas",
                        isLoading = false
                    )
                }
            }
        }
    }
}
