package com.duocuc.serena.data.modelData

data class User(
    val id: Long,
    val userName: String?,
    val userEmail: String,
    val userAceptConditions: Boolean?,
    val userImageUri: String?
)