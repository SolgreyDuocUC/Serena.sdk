package com.duocuc.serena.repository

import com.duocuc.serena.DAO.EmotionalRegisterDao
import com.duocuc.serena.data.EmotionalRegisterData

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.Result

class EmotionalRegisterRepository(private val dao: EmotionalRegisterDao) {

    suspend fun registerEmotion(idEmocion: Int): Result<Boolean> {
        return withContext(Dispatchers.IO) {
            try {
                dao.insertEmotion(EmotionalRegisterData(idEmcoion = idEmocion))
                Result.success(true)
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }

    suspend fun getAllRegisters(): Result<List<EmotionalRegisterData>> {
        return withContext(Dispatchers.IO) {
            try {
                Result.success(dao.getAllRegisters())
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }
}
