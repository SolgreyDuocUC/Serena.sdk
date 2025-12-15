package com.duocuc.serena

import android.content.Context
import com.duocuc.serena.bases.AppDatabase
import com.duocuc.serena.data.remote.user.Auth.AuthDataStore
import com.duocuc.serena.data.remote.user.Auth.AuthRepository
import com.duocuc.serena.data.remote.user.UserService
import com.duocuc.serena.repository.EmotionalRegisterRepository
import com.duocuc.serena.repository.UserRepository

object Graph {
    lateinit var database: AppDatabase
        private set

    lateinit var appContext: Context

    val userService by lazy { UserService() }

    val authDataStore by lazy { AuthDataStore(appContext) }

    val authRepository by lazy { AuthRepository(userService, authDataStore) }
    val userRepository by lazy { UserRepository(userService, authDataStore) }
    val emotionalRegisterRepository by lazy { EmotionalRegisterRepository(database.registroEmocionalDao()) }

    fun provide(context: Context) {
        appContext = context.applicationContext
        database = AppDatabase.get(context)
    }
}
