package com.duocuc.serena.model

data class LoginUiState(

    val userEmail: String = "",
    val userPassword: String = "",
    val userEmailError: String? = null,
    val userPasswordError: String? = null,
    val isLoading: Boolean = false,
    val isLoginSuccessful: Boolean = false,
    val loginError: String? = null
) {
    val isFormValid: Boolean
        get() = userEmailError == null &&
                userPasswordError == null &&
                userEmail.isNotEmpty() &&
                userPassword.isNotEmpty()
}