package com.duocuc.serena.data.remote.user

import android.util.Log
import com.duocuc.serena.client.RetrofitClient
import com.duocuc.serena.data.remote.user.dto.auth.AuthResponse
import com.duocuc.serena.data.remote.user.dto.auth.LoginRequestDto
import com.duocuc.serena.data.remote.user.dto.auth.RefreshTokenRequest
import com.duocuc.serena.data.remote.user.dto.auth.UserCreateRequestDto
import com.duocuc.serena.data.remote.user.dto.user.UserDto
import java.io.IOException

class UserService {

    private val api = RetrofitClient.userApi

    suspend fun findAll(): List<UserDto>? {
        return try {
            val response = api.findAll()
            if (response.isSuccessful) {
                response.body()
            } else {
                Log.e("UserService", "Error finding all users: ${response.errorBody()?.string()}")
                null
            }
        } catch (e: IOException) {
            Log.e("UserService", "Network error in findAll", e)
            null
        } catch (e: Exception) {
            Log.e("UserService", "Unexpected error in findAll", e)
            null
        }
    }

    suspend fun findByEmail(email: String): UserDto? {
        return try {
            val response = api.findByEmail(email)
            if (response.isSuccessful) {
                response.body()
            } else {
                Log.e("UserService", "Error finding user by email: ${response.errorBody()?.string()}")
                null
            }
        } catch (e: IOException) {
            Log.e("UserService", "Network error in findByEmail", e)
            null
        } catch (e: Exception) {
            Log.e("UserService", "Unexpected error in findByEmail", e)
            null
        }
    }

    suspend fun findById(id: Long): UserDto? {
        return try {
            val response = api.findById(id)
            if (response.isSuccessful) {
                response.body()
            } else {
                Log.e("UserService", "Error finding user by id: ${response.errorBody()?.string()}")
                null
            }
        } catch (e: IOException) {
            Log.e("UserService", "Network error in findById", e)
            null
        } catch (e: Exception) {
            Log.e("UserService", "Unexpected error in findById", e)
            null
        }
    }

    suspend fun register(request: UserCreateRequestDto): UserDto? {
        return try {
            val response = api.register(request)
            if (response.isSuccessful) {
                response.body()
            } else {
                Log.e("UserService", "Error registering user: ${response.errorBody()?.string()}")
                null
            }
        } catch (e: IOException) {
            Log.e("UserService", "Network error in register", e)
            null
        } catch (e: Exception) {
            Log.e("UserService", "Unexpected error in register", e)
            null
        }
    }

    suspend fun login(request: LoginRequestDto): AuthResponse? {
        return try {
            val response = api.login(request)
            if (response.isSuccessful) {
                response.body()
            } else {
                Log.e("UserService", "Error logging in: ${response.errorBody()?.string()}")
                null
            }
        } catch (e: IOException) {
            Log.e("UserService", "Network error in login", e)
            null
        } catch (e: Exception) {
            Log.e("UserService", "Unexpected error in login", e)
            null
        }
    }

    suspend fun refresh(request: RefreshTokenRequest): AuthResponse? {
        return try {
            val response = api.refresh(request)
            if (response.isSuccessful) {
                response.body()
            } else {
                Log.e("UserService", "Error refreshing token: ${response.errorBody()?.string()}")
                null
            }
        } catch (e: IOException) {
            Log.e("UserService", "Network error in refresh", e)
            null
        } catch (e: Exception) {
            Log.e("UserService", "Unexpected error in refresh", e)
            null
        }
    }

    suspend fun updateUser(id: Long, request: UserCreateRequestDto): UserDto? {
        return try {
            val response = api.updateUser(id, request)
            if (response.isSuccessful) {
                response.body()
            } else {
                Log.e("UserService", "Error updating user: ${response.errorBody()?.string()}")
                null
            }
        } catch (e: IOException) {
            Log.e("UserService", "Network error in updateUser", e)
            null
        } catch (e: Exception) {
            Log.e("UserService", "Unexpected error in updateUser", e)
            null
        }
    }

    suspend fun deleteUser(id: Long): Boolean {
        return try {
            val response = api.deleteUser(id)
            if (response.isSuccessful) {
                true
            } else {
                Log.e("UserService", "Error deleting user: ${response.errorBody()?.string()}")
                false
            }
        } catch (e: IOException) {
            Log.e("UserService", "Network error in deleteUser", e)
            false
        } catch (e: Exception) {
            Log.e("UserService", "Unexpected error in deleteUser", e)
            false
        }
    }
}
