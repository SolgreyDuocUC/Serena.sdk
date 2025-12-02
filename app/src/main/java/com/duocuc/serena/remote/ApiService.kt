package com.duocuc.serena.remote

import com.duocuc.serena.data.modelData.EmotionalRegister
import com.duocuc.serena.data.modelData.User
import com.duocuc.serena.data.modelData.UserActiveSession
import com.duocuc.serena.model.Emotion
import com.duocuc.serena.model.EmotionalRegisterCreateRequest
import com.duocuc.serena.model.LoginRequest
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ApiService {

    // USERS
    @GET("api/v1/users")
    suspend fun getUsers(): List<User>

    @GET("api/v1/users/{id}")
    suspend fun getUserById(@Path("id") id: Long): User

    @GET("api/v1/users/email/{email}")
    suspend fun getUserByEmail(@Path("email") email: String): User

    @POST("api/v1/users")
    suspend fun createUser(@Body user: User): User

    @PUT("api/v1/users/{id}")
    suspend fun updateUser(@Path("id") id: Long, @Body user: User): User

    // AUTH
    @POST("api/v1/login")
    suspend fun login(@Body request: LoginRequest): User

    // EMOTIONS
    @GET("api/v1/emotions")
    suspend fun getEmotions(): List<Emotion>

    @GET("api/v1/emotions/{id}")
    suspend fun getEmotionById(@Path("id") id: Long): Emotion

    // EMOTIONAL REGISTER
    @GET("api/v1/emotional-registers")
    suspend fun getEmotionalRegisters(): List<EmotionalRegister>

    @POST("api/v1/emotional-registers")
    suspend fun createEmotionalRegister(
        @Body body: EmotionalRegisterCreateRequest
    ): EmotionalRegister

    // ACTIVE SESSION
    @GET("api/v1/user-active-sessions")
    suspend fun getActiveSessions(): List<UserActiveSession>

    @POST("api/v1/user-active-sessions")
    suspend fun createActiveSession(
        @Body body: UserActiveSession
    ): UserActiveSession
    
    @DELETE("api/v1/user-active-sessions/{id}")
    suspend fun deleteActiveSession(@Path("id") id: Long)

}