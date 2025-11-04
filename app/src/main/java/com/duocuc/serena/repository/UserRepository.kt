package com.duocuc.serena.repository

import kotlinx.coroutines.delay
import kotlin.Result

class UserRepository {
    suspend fun registerUser(
        email: String,
        password: String,
        name: String,
        lastName: String,
        age: String
    ): Result<Boolean> {
        delay(500)

        if (email.contains("error")) {
            return Result.failure(Exception("Error de servidor simulado o email duplicado."))
        }
        return Result.success(true)
    }

    suspend fun loginUser(
        email: String,
        password: String,
    ): Result<Boolean> {
        delay(500)
        val correctEmail = "test@serena.com"
        val correctPassword = "Password123"

        if (email.contains("servererror")) {
            return Result.failure(Exception("Error de servidor no disponible."))
        }

        if (email == correctEmail && password == correctPassword) {
            return Result.success(true)
        } else {
            return Result.failure(Exception("Email o contraseña incorrectos."))
        }
    }
}