package com.duocuc.serena.repository

class UserRepository {
    suspend fun registerUser(
        email: String,
        password: String,
        name: String,
        lastName: String,
        age: String
    ): Result<Boolean> {
        if (email.contains("error")) {
            return Result.failure(Exception("Error de servidor simulado o email duplicado."))
        }
        return Result.success(true)
    }
}