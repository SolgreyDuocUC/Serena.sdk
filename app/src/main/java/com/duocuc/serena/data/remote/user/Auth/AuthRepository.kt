package com.duocuc.serena.data.remote.user.Auth

import com.duocuc.serena.data.remote.user.UserService
import com.duocuc.serena.data.remote.user.dto.auth.LoginRequestDto
import com.duocuc.serena.data.remote.user.dto.auth.UserCreateRequestDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AuthRepository(
    private val userService: UserService,
    private val authDataStore: AuthDataStore
) {

    suspend fun login(
        email: String,
        password: String
    ): Result<Boolean> {
        return withContext(Dispatchers.IO) {
            try {
                val authResponse = userService.login(
                    LoginRequestDto(email, password)
                )

                authDataStore.saveTokens(
                    access = authResponse?.accessToken ?: "",
                    refresh = authResponse?.refreshToken ?: ""
                )

                val user = userService.findByEmail(email)
                    ?: return@withContext Result.failure(Exception("Usuario no encontrado"))

                authDataStore.saveUserId(user.id)

                Result.success(true)
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }

    suspend fun register(
        name: String,
        email: String,
        password: String,
        acceptConditions: Boolean
    ): Result<Boolean> {
        return withContext(Dispatchers.IO) {
            try {
                userService.register(
                    UserCreateRequestDto(
                        userName = name,
                        userEmail = email,
                        userPassword = password,
                        userAceptConditions = acceptConditions,
                        userImageUri = null
                    )
                )

                Result.success(true)
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }

    suspend fun logout() {
        authDataStore.clear()
    }
}
