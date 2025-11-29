package com.duocuc.serena.viewmodel.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.duocuc.serena.model.UserUiState
import com.duocuc.serena.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RegisterViewModel(
    private val userRepository: UserRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(UserUiState())
    val uiState: StateFlow<UserUiState> = _uiState.asStateFlow()

    fun onUserNameChange(newUserName: String) {
        _uiState.update { currentState ->
            currentState.copy(
                userName = newUserName,
                userNameError = if (newUserName.length >= 3) null else "El nombre debe tener al menos 3 caracteres"
            )
        }
    }

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
            val error = when {
                newPassword.length < 8 -> "La contraseña debe tener al menos 8 caracteres"
                !newPassword.any { it.isDigit() } -> "Debe contener al menos un número"
                else -> null
            }
            currentState.copy(
                userPassword = newPassword,
                userPasswordError = error,
                userRepeatPasswordError = if (currentState.userRepeatPassword.isNotEmpty() && currentState.userRepeatPassword != newPassword) "Las contraseñas no coinciden" else null
            )
        }
    }

    fun onUserRepeatPasswordChange(newRepeatPassword: String) {
        _uiState.update { currentState ->
            currentState.copy(
                userRepeatPassword = newRepeatPassword,
                userRepeatPasswordError = if (newRepeatPassword == currentState.userPassword) null else "Las contraseñas no coinciden"
            )
        }
    }

    fun onUserAcceptConditionsChange(accepted: Boolean) {
        _uiState.update { currentState ->
            currentState.copy(
                userAcceptConditions = accepted,
                userAcceptConditionsError = if (accepted) null else "Debe aceptar las condiciones"
            )
        }
    }

    fun clearRegistrationError() {
        _uiState.update { it.copy(registrationError = null) }
    }

    private fun validateAllFields(currentState: UserUiState): UserUiState {
        val nameError = if (currentState.userName.length >= 3) null else "El nombre debe tener al menos 3 caracteres"

        val emailRegex = "^[A-Za-z](.*)(@)(.+)(\\.)(.+)"
        val emailError = if (currentState.userEmail.matches(emailRegex.toRegex())) null else "Formato de email no válido"

        val passwordError = when {
            currentState.userPassword.length < 8 -> "La contraseña debe tener al menos 8 caracteres"
            !currentState.userPassword.any { it.isDigit() } -> "Debe contener al menos un número"
            else -> null
        }

        val repeatPasswordError = if (currentState.userRepeatPassword == currentState.userPassword && currentState.userRepeatPassword.isNotEmpty()) null else "Las contraseñas no coinciden"

        val conditionsError = if (currentState.userAcceptConditions) null else "Debe aceptar las condiciones"

        return currentState.copy(
            userNameError = nameError,
            userEmailError = emailError,
            userPasswordError = passwordError,
            userRepeatPasswordError = repeatPasswordError,
            userAcceptConditionsError = conditionsError
        )
    }

    fun register() {
        _uiState.update { currentState ->
            validateAllFields(currentState)
        }

        val state = _uiState.value

        if (!state.isFormValid) {
            return
        }

        _uiState.update { it.copy(isLoading = true, registrationError = null) }

        viewModelScope.launch {
            try {
                val result = userRepository.registerUser(
                    email = state.userEmail,
                    password = state.userPassword,
                    name = state.userName,
                )

                if (result.isSuccess) {
                    _uiState.update { it.copy(
                        isRegistrationSuccessful = true,
                        isLoading = false
                    )}
                } else {
                    _uiState.update { it.copy(
                        registrationError = result.exceptionOrNull()?.message ?: "Error desconocido en el registro.",
                        isLoading = false
                    )}
                }

            } catch (e: Exception) {
                _uiState.update { it.copy(
                    registrationError = e.message ?: "Error de red o conexión.",
                    isLoading = false
                )}
            }
        }
    }
}