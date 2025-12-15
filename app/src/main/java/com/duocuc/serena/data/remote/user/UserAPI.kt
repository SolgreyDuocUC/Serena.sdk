package com.duocuc.serena.data.remote.user

import com.duocuc.serena.data.remote.user.dto.auth.AuthResponse
import com.duocuc.serena.data.remote.user.dto.auth.LoginRequestDto
import com.duocuc.serena.data.remote.user.dto.auth.RefreshTokenRequest
import com.duocuc.serena.data.remote.user.dto.auth.UserCreateRequestDto
import com.duocuc.serena.data.remote.user.dto.user.UserDto
import retrofit2.Response
import retrofit2.http.*

interface UserApi {

    @GET("api/v1/users")
    suspend fun findAll(): Response<List<UserDto>>

    @GET("api/v1/users/{id}")
    suspend fun findById(
        @Path("id") id: Long
    ): Response<UserDto>

    @GET("api/v1/users/email/{email}")
    suspend fun findByEmail(
        @Path("email") email: String
    ): Response<UserDto>

    @POST("api/v1/auth/register")
    suspend fun register(
        @Body request: UserCreateRequestDto
    ): Response<UserDto>

    @POST("api/v1/auth/login")
    suspend fun login(
        @Body request: LoginRequestDto
    ): Response<AuthResponse>

    @POST("api/v1/auth/refresh")
    suspend fun refresh(
        @Body request: RefreshTokenRequest
    ): Response<AuthResponse>

    @PUT("api/v1/users/{id}")
    suspend fun updateUser(
        @Path("id") id: Long,
        @Body request: UserCreateRequestDto
    ): Response<UserDto>

    @DELETE("api/v1/users/{id}")
    suspend fun deleteUser(
        @Path("id") id: Long
    ): Response<Unit>
}
