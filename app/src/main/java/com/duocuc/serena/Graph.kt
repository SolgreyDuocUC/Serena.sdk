package com.duocuc.serena

import android.content.Context
import com.duocuc.serena.bases.AppDatabase
import com.duocuc.serena.remote.ApiService
import com.duocuc.serena.remote.RetrofitInstance
import com.duocuc.serena.repository.EmotionalRegisterRepository
import com.duocuc.serena.repository.UserRepository

object Graph {
    lateinit var database: AppDatabase
        private set

    val apiService: ApiService by lazy { RetrofitInstance.api }

    val userRepository by lazy { UserRepository(apiService) }
    val emotionalRegisterRepository by lazy { EmotionalRegisterRepository(apiService) }

    fun provide(context: Context) {
        database = AppDatabase.get(context)
    }
}