package com.duocuc.serena.data.remote.user.dto.auth

data class UserCreateRequestDto(
    val userName: String,
    val userEmail: String,
    val userPassword: String,
    val userAceptConditions: Boolean,
    val userImageUri: String?
)