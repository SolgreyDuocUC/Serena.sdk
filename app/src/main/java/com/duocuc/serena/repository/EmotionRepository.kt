package com.duocuc.serena.repository


import com.duocuc.serena.model.EmotionsUiState
import com.duocuc.serena.data.EmotionsData
import kotlinx.coroutines.delay
import kotlin.Result

class EmotionRepository {

    // Lista simulada de emociones en formato de Room
    private val emocionesDB = mutableListOf(
        EmotionsData(1, "Alegría", "Sentimiento de felicidad y bienestar"),
        EmotionsData(2, "Tristeza", "Sentimiento de pena o desánimo"),
        EmotionsData(3, "Miedo", "Sensación de amenaza o peligro"),
        EmotionsData(4, "Ira", "Emoción de enfado o molestia intensa"),
        EmotionsData(5, "Sorpresa", "Reacción ante algo inesperado"),
        EmotionsData(6, "Asco", "Repulsión hacia algo desagradable")
    )

    suspend fun getAllEmotions(): Result<List<EmotionsUiState>> {
        delay(300)
        val uiList = emocionesDB.map { EmotionsUiState(it.idEntradaEmocional, it.nombreEmocion) }
        return Result.success(uiList)
    }

    // Registrar una emoción seleccionada (simulado)
    suspend fun registerEmotion(emotionName: String): Result<Boolean> {
        delay(300)
        val existe = emocionesDB.any { it.nombreEmocion == emotionName }
        return if (existe) {
            Result.success(true)
        } else {
            Result.failure(Exception("Emoción no válida"))
        }
    }

    // Opcional: obtener descripción de una emoción
    suspend fun getEmotionDescription(emotionId: Int): Result<String> {
        delay(200)
        val emocion = emocionesDB.find { it.idEntradaEmocional == emotionId }
        return if (emocion != null) {
            Result.success(emocion.descripcionEmocion)
        } else {
            Result.failure(Exception("Emoción no encontrada"))
        }
    }
}

