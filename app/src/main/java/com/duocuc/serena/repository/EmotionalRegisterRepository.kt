package com.duocuc.serena.repository

import com.duocuc.serena.DAO.RegistroEmocionalDao
import com.duocuc.serena.data.EmotionalRegisterData
import java.time.LocalDate
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.Result

class EmotionalRegisterRepository(private val dao: RegistroEmocionalDao) {

    suspend fun registerEmotion(idEmocion: Int, fecha: LocalDate): Result<Boolean> {
        return withContext(Dispatchers.IO) {
            try {
                dao.insertEmotion(EmotionalRegisterData(idEmocion = idEmocion, fecha = fecha))
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
