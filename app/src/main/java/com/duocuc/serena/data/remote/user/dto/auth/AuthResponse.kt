package com.duocuc.serena.data.remote.user.dto.auth

data class AuthResponse(
    val accessToken: String,
    val refreshToken: String
)
