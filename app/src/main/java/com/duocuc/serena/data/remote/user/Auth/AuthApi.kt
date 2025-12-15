package com.duocuc.serena.data.remote.user.Auth

import com.duocuc.serena.data.remote.user.dto.auth.AuthResponseDto
import com.duocuc.serena.data.remote.user.dto.auth.LoginRequestDto
import com.duocuc.serena.data.remote.user.dto.auth.UserCreateRequestDto
import com.duocuc.serena.data.remote.user.dto.user.UserDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {

    @POST("api/v1/auth/login")
    suspend fun login(
        @Body request: LoginRequestDto
    ): Response<AuthResponseDto>

    @POST("api/v1/auth/register")
    suspend fun register(
        @Body request: UserCreateRequestDto
    ): Response<UserDto>

    @POST("api/v1/auth/refresh")
    suspend fun refreshToken(
        @Body request: Map<String, String>
    ): Response<AuthResponseDto>
}