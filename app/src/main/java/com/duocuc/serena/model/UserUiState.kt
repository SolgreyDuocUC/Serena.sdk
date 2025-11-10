package com.duocuc.serena.model

data class UserUiState(
    val userName: String = "",
    val userEmail: String = "",
    val userPassword: String = "",
    val userRepeatPassword: String = "",
    val userAcceptConditions: Boolean = false,
    val userNameError: String? = null,
    val userEmailError: String? = null,
    val userPasswordError: String? = null,
    val userRepeatPasswordError: String? = null,
    val userAcceptConditionsError: String? = null,
    val isLoading: Boolean = false,
    val isRegistrationSuccessful: Boolean = false,
    val registrationError: String? = null
) {
    val isFormValid: Boolean
        get() = userNameError == null &&
                userEmailError == null &&
                userPasswordError == null &&
                userRepeatPasswordError == null &&
                userAcceptConditions
}