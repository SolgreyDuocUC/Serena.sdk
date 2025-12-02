package com.duocuc.serena.repository

import com.duocuc.serena.data.modelData.EmotionalRegister
import com.duocuc.serena.model.EmotionalRegisterCreateRequest
import com.duocuc.serena.remote.ApiService
import java.time.LocalDate
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.Result

class EmotionalRegisterRepository(
    private val apiService: ApiService
) {

    // INSERT
    suspend fun registerEmotion(idEmocion: Int, descripcion: String, fecha: LocalDate): Result<EmotionalRegister> {
        return withContext(Dispatchers.IO) {
            try {
                val request = EmotionalRegisterCreateRequest(
                    emotionId = idEmocion.toLong(),
                    userId = 1, // TODO: Reemplazar con el ID del usuario actual
                    description = descripcion,
                    date = fecha.toString()
                )
                val newRegister = apiService.createEmotionalRegister(request)
                Result.success(newRegister)
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }

    // GET ALL
    suspend fun getAllRegisters(): Result<List<EmotionalRegister>> {
        return withContext(Dispatchers.IO) {
            try {
                Result.success(apiService.getEmotionalRegisters())
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }

    // TODO: Implementar los métodos de actualizar y eliminar utilizando la API
}