package com.duocuc.serena.remote.Dto

data class UserDto(
    val id: Long? = null,
    val userName: String?,
    val userEmail: String,
    val userPassword: String,
    val userAceptConditions: Boolean,
    val userImageUri: String?
)