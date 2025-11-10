package com.duocuc.serena

import android.content.Context
import com.duocuc.serena.bases.AppDatabase
import com.duocuc.serena.repository.EmotionalRegisterRepository
import com.duocuc.serena.repository.UserRepository

object Graph {
    lateinit var database: AppDatabase
        private set

    val userRepository by lazy { UserRepository(database.userDao(), database.userSessionDao()) }
    val emotionalRegisterRepository by lazy { EmotionalRegisterRepository(database.registroEmocionalDao()) }

    fun provide(context: Context) {
        database = AppDatabase.get(context)
    }
}