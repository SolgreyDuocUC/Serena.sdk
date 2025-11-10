package com.duocuc.serena.model

data class ProfileUiState(
    val name: String = "",
    val email: String = "",
    val oldPassword: String = "",
    val newPassword: String = "",
    val isLoading: Boolean = false,
    val saveSuccess: Boolean = false,
    val errorMessage: String? = null,
    val snackbarMessage: String? = null
)
