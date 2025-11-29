package com.duocuc.serena.repository

import com.duocuc.serena.dao.RegistroEmocionalDao
import com.duocuc.serena.data.dataModel.EmotionalRegisterData
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

    // EmotionalRegisterRepository.kt
    suspend fun updateEmotion(id: Int, newIdEmocion: Int, newFecha: LocalDate): Result<Boolean> {
        return withContext(Dispatchers.IO) {
            try {
                val register = dao.getRegistersById(id).firstOrNull()
                    ?: return@withContext Result.failure(Exception("Registro no encontrado"))
                dao.updateEmotion(register.copy(idEmocion = newIdEmocion, fecha = newFecha))
                Result.success(true)
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }

    suspend fun deleteEmotion(id: Int): Result<Boolean> {
        return withContext(Dispatchers.IO) {
            try {
                val register = dao.getRegistersById(id).firstOrNull()
                    ?: return@withContext Result.failure(Exception("Registro no encontrado"))
                dao.deleteEmotion(register)
                Result.success(true)
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }
}

